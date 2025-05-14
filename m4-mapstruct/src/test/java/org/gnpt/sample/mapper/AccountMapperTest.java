package org.gnpt.sample.mapper;

import org.assertj.core.api.Assertions;
import org.gnpt.sample.model.Account;
import org.gnpt.sample.model.AccountAddress;
import org.gnpt.sample.model.AccountContacts;
import org.gnpt.sample.model.AccountDetails;
import org.gnpt.sample.model.Order;
import org.gnpt.sample.model.dto.AccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    private static final UUID ACCOUNT_ID_1 = UUID.randomUUID();
    private static final UUID ACCOUNT_ID_2 = UUID.randomUUID();
    private static final String ACCOUNT_NAME_1 = "Sarah Connor";
    private static final String ACCOUNT_NAME_2 = "Thomas Anderson";
    private static final String ORDER_CODE_1 = "J4RF2";
    private static final String ORDER_CODE_2 = "B5HD5";

    @Test
    void testAccountMapper() {

        Account account = new Account(ACCOUNT_ID_1,
                ACCOUNT_NAME_1,
                AccountDetails.builder()
                        .balance(-10)
                        .build(),
                List.of(new Order(ORDER_CODE_1), new Order(ORDER_CODE_2)),
                LocalDate.of(2023, Month.JANUARY, 18).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                new AccountContacts(new AccountAddress("New Greenland", "Folkstown", "Dumbledore Street", 5), "sample@mail.org"));

        AccountDto accountDto = accountMapper.toDto(account);

        makeAssertions(accountDto);

        List<AccountDto> accountDtos = accountMapper.toDtos(List.of(account,
                new Account(ACCOUNT_ID_2,
                        ACCOUNT_NAME_2,
                        AccountDetails.builder()
                                .balance(100)
                                .age(40)
                                .build(),
                        Collections.emptyList(),
                        LocalDate.of(2023, Month.MARCH, 10).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                        new AccountContacts(null, "example@mail.org"))));
        Assertions.assertThat(accountDtos).hasSize(2);
        makeAssertions(accountDtos.get(0));
        Assertions.assertThat(accountDtos.get(1).getName()).isEqualTo(ACCOUNT_NAME_2);
        Assertions.assertThat(accountDtos.get(1).getOrders()).isEmpty();
        Assertions.assertThat(accountDtos.get(1).isActive()).isTrue();
        Assertions.assertThat(accountDtos.get(1).getContacts().getAddress()).isNull();
        Assertions.assertThat(accountDtos.get(1).getContacts().getEmail()).isEqualTo("example@mail.org");
    }

    private void makeAssertions(AccountDto accountDto) {
        Assertions.assertThat(accountDto.getName()).isEqualTo(ACCOUNT_NAME_1);
        Assertions.assertThat(accountDto.isActive()).isFalse();
        Assertions.assertThat(accountDto.getCreatedAt()).isEqualTo("17.01.2023 21:00:00");
        Assertions.assertThat(accountDto.getOrders()).hasSize(2);
        Assertions.assertThat(accountDto.getOrders().get(0).getCode()).isEqualTo(ORDER_CODE_1);
        Assertions.assertThat(accountDto.getOrders().get(1).getCode()).isEqualTo(ORDER_CODE_2);
        Assertions.assertThat(accountDto.getContacts().getAddress()).isEqualTo("5 Dumbledore Street, Folkstown, New Greenland");
        Assertions.assertThat(accountDto.getContacts().getEmail()).isEqualTo("sample@mail.org");
    }
}