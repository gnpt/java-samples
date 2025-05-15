package org.gnpt.sample.domain.account.mapper;

import org.gnpt.sample.domain.account.model.Account;
import org.gnpt.sample.domain.account.model.dto.AccountDto;
import org.gnpt.sample.domain.common.mapper.CommonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = AccountAddressMapper.class)
public abstract class AccountMapper implements CommonMapper<Account, AccountDto> {

    @Autowired
    private DateTimeFormatter accountDateTimeFormatter;

    @Mapping(target = "age", source = "accountDetails.age")
    @Mapping(target = "isActive", expression = "java(account.getAccountDetails().getBalance() >= 0)")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "formatDate")
    public abstract  AccountDto toDto(Account account);

    @Named("formatDate")
    protected String formatDate(Instant date){
        return accountDateTimeFormatter.format(date);
    }

}
