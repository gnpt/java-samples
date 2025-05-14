package org.gnpt.sample.filter;

import java.time.Instant;

public record QueryFilters(Instant dateFrom, Instant dateTo, Integer categoryCode, OrderBy orderBy, OrderType orderType) {

    public enum OrderType {
        ASC, DESC
    }

    public enum OrderBy {
        ACCOUNT_NAME, ORDERS_COUNT
    }
}
