package org.gnpt.sample.domain.product.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Product {

    private final int code;

    private final String name;

    private final String comment;

}
