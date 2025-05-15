package org.gnpt.sample.domain.product.model.dto;

import lombok.Data;

@Data
public class ProductDto {

    private final int code;

    private final String name;

    private final String comment;

    private final Integer typeCode;

}
