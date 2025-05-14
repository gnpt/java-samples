package org.gnpt.integration;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class DbIntegrationTest {
    protected static JdbcTemplate jdbcTemplate;
    protected static final boolean isManualContainersStart;

    protected static DockerComposeContainer<?> environment;

    private static DockerComposeContainer<?> createDockerComposeEnvironment() {
        InputStream inputStream = DbIntegrationTest.class.getResourceAsStream("/integration/docker-compose/docker-compose-postgres.yml");
        File dockerComposeFile;
        try {
            dockerComposeFile = Files.createTempFile("docker-compose-postgres", ".yml").toFile();
            FileUtils.copyInputStreamToFile(inputStream, dockerComposeFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new DockerComposeContainer<>(dockerComposeFile)
                .withExposedService("db", 5432,
                        Wait.forLogMessage(".*database system is ready to accept connections.*\\n", 1)
                                .withStartupTimeout(Duration.ofMinutes(1)));
    }

    static {
        isManualContainersStart = Boolean.parseBoolean(System.getenv("MANUAL_CONTAINERS_START"));
        if (!isManualContainersStart) {
            environment = createDockerComposeEnvironment();
            environment.start();
        }

        init();
        log.info("Initialization complete");
    }

    {
        resetDb();
    }

    @BeforeAll
    protected void reset() {
        for (String dbScriptPath : dbScriptPaths()) {
            runScript(jdbcTemplate, dbScriptPath);
        }
    }

    protected List<String> dbScriptPaths() {
        return Collections.emptyList();
    }

    private static void init() {
        DbConfig dbConfig = DbConfig.builder()
                .url("jdbc:postgresql://localhost:15432/test-db?currentSchema=public")
                .user("test-user")
                .password("test-password")
                .build();
        DataSource dataSource = createDateSource(dbConfig);
        waitForDbConnection(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static DataSource createDateSource(DbConfig config) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(config.url());
        dataSource.setUser(config.user());
        dataSource.setPassword(config.password());
        return dataSource;
    }

    private void runScript(JdbcTemplate jdbcTemplate, String scriptPath) {
        String template = getFileContents(scriptPath);
        Map<String, String> constants = getConstants();
        String script = new StringSubstitutor(constants).replace(template);
        jdbcTemplate.execute(script);
    }

    private Map<String, String> getConstants() {
        Map<String, String> constants = new HashMap<>();
        TestConstants testConstantsAnnotation = AnnotatedElementUtils.findMergedAnnotation(this.getClass(), TestConstants.class);
        if (testConstantsAnnotation != null) {
            record ConstantsFieldAndInterface(Field field, Class<?> constantsInterface) {}
            Arrays.stream(testConstantsAnnotation.constantsInterfaces())
                    .filter(Class::isInterface)
                    .flatMap(i -> Arrays.stream(i.getDeclaredFields()).map(f -> new ConstantsFieldAndInterface(f, i)))
                    .forEach(pair -> {
                        try {
                            pair.field().setAccessible(true);
                            constants.put(pair.field().getName(), pair.field().get(pair.constantsInterface()).toString());
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        return constants;
    }

    private static String getFileContents(String path) {
        try {
            InputStream stream = DbIntegrationTest.class.getResourceAsStream(path);
            StringBuilder builder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                int c;
                while ((c = reader.read()) != -1) {
                    builder.append((char) c);
                }
            }
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void resetDb() {
        log.info("resetting db...");
        runScript(jdbcTemplate, "/integration/sql/reset_db.sql");
    }

    private static void waitForDbConnection(DataSource dataSource) {
        int attempts = 100;
        while (attempts-- > 0) {
            try {
                log.info("Waiting for database to be available... Attempts left: " + attempts);
                Connection connection = DataSourceUtils.getConnection(dataSource);
                return;
            } catch (CannotGetJdbcConnectionException e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        throw new RuntimeException("Could not connect to database");
    }

    @Builder
    private record DbConfig(String url, String user, String password) {
    }
}
