package org.gnpt.sample.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class AccountContacts {

    private final AccountAddress address;

    @NonNull
    private final String email;

}
