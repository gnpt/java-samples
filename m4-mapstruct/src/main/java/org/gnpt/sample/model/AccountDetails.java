package org.gnpt.sample.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class AccountDetails {

    @NonNull
    private final Integer balance;

    private final Integer age;

}
