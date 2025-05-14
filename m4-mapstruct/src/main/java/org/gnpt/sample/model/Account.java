package org.gnpt.sample.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Account {

    private final UUID id ;

    private final String name;

    private final AccountDetails accountDetails;

    private final List<Order> orders;

    private final Instant createdAt;

    private final AccountContacts contacts;

}
