package org.gnpt.sample.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto {

    private final String id;

    private final String name;

    private final List<OrderDto> orders;

    private final boolean isActive;

    private final String createdAt;

    private final Integer age;

    private final AccountContactsDto contacts;

}
