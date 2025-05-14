package org.gnpt.sample;

import org.assertj.core.api.Assertions;
import org.gnpt.integration.DbIntegrationTest;
import org.gnpt.integration.TestConstants;
import org.gnpt.sample.constants.QuerydslSampleTestConstants;
import org.gnpt.sample.filter.QueryFilters;
import org.gnpt.sample.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@SpringBootTest
@ActiveProfiles("integration")
@TestConstants(constantsInterfaces = QuerydslSampleTestConstants.class)
public class QuerydslSampleTest extends DbIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected List<String> dbScriptPaths() {
        return List.of("/integration/sql/init.sql");
    }

    @Test
    void testAccountsQuery() {
        var res = accountRepository.findAccountsWithOrdersBetweenDates(
                new QueryFilters(null, null, null, null, null),
                PageRequest.of(0, 4));
        Assertions.assertThat(res.getTotalElements()).isEqualTo(5);
        Assertions.assertThat(res.getContent()).hasSize(4);

        res = accountRepository.findAccountsWithOrdersBetweenDates(
                new QueryFilters(
                    LocalDate.parse("2023-03-01").atStartOfDay(ZoneId.systemDefault()).toInstant(),
                    LocalDate.parse("2023-05-02").atStartOfDay(ZoneId.systemDefault()).toInstant(),
                        2,
                        QueryFilters.OrderBy.ACCOUNT_NAME,
                        QueryFilters.OrderType.ASC
                ),
                PageRequest.of(0, 4));
        Assertions.assertThat(res.getContent()).hasSize(3);
        Assertions.assertThat(res.getContent().get(0).accountName()).isEqualTo(QuerydslSampleTestConstants.USER_1_NAME);
        Assertions.assertThat(res.getContent().get(2).accountName()).startsWith(QuerydslSampleTestConstants.USER_5_NAME);

        res = accountRepository.findAccountsWithOrdersBetweenDates(
                new QueryFilters(
                        null,
                        null,
                        null,
                        QueryFilters.OrderBy.ORDERS_COUNT,
                        QueryFilters.OrderType.DESC
                ),
                PageRequest.of(0, 4));
        Assertions.assertThat(res.getContent()).hasSize(4);
        Assertions.assertThat(res.getContent().get(0).orderCount()).isEqualTo(13);
        Assertions.assertThat(res.getContent().get(3).orderCount()).isEqualTo(6);
    }
}
