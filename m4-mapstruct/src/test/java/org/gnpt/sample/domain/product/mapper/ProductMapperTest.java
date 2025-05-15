package org.gnpt.sample.domain.product.mapper;

import org.assertj.core.api.Assertions;
import org.gnpt.sample.domain.account.model.Account;
import org.gnpt.sample.domain.account.model.AccountAddress;
import org.gnpt.sample.domain.account.model.AccountContacts;
import org.gnpt.sample.domain.account.model.AccountDetails;
import org.gnpt.sample.domain.account.model.Order;
import org.gnpt.sample.domain.account.model.dto.AccountDto;
import org.gnpt.sample.domain.product.model.Product;
import org.gnpt.sample.domain.product.model.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void testProductMapper() {
        List<Product> products = List.of(new Product(0, "product1", "comment1"),
                new Product(1, "product2", "comment2"));

        ProductDto shortProductDto = productMapper.toShortDto(products.get(0));
        Assertions.assertThat(shortProductDto.getName()).isEqualTo("product1");
        Assertions.assertThat(shortProductDto.getCode()).isEqualTo(0);
        Assertions.assertThat(shortProductDto.getComment()).isNull();
        Assertions.assertThat(shortProductDto.getTypeCode()).isNull();

        List<ProductDto> productDtos = productMapper.toDtos(products);
        Assertions.assertThat(productDtos.get(0).getName()).isEqualTo("product1");
        Assertions.assertThat(productDtos.get(0).getCode()).isEqualTo(0);
        Assertions.assertThat(productDtos.get(0).getComment()).isEqualTo("comment1");
        Assertions.assertThat(productDtos.get(0).getTypeCode()).isNull();
        Assertions.assertThat(productDtos.get(1).getName()).isEqualTo("product2");
        Assertions.assertThat(productDtos.get(1).getCode()).isEqualTo(1);
        Assertions.assertThat(productDtos.get(1).getComment()).isEqualTo("comment2");
        Assertions.assertThat(productDtos.get(1).getTypeCode()).isNull();
    }
}