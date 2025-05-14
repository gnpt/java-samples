package org.gnpt.sample.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class AccountAddress {

    @NonNull
    private final String country;

    @NonNull
    private final String city;

    @NonNull
    private final String street;

    @NonNull
    private final int house;
}
