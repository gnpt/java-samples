package org.gnpt.sample.domain.product.mapper;

import org.gnpt.sample.domain.common.mapper.CommonMapper;
import org.gnpt.sample.domain.product.model.Product;
import org.gnpt.sample.domain.product.model.dto.ProductDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends CommonMapper<Product, ProductDto> {

    @Named("toDto")
    ProductDto toDto(Product produce);

    @Mapping(target = "comment", ignore = true)
    ProductDto toShortDto(Product produce);

    @Override
    @IterableMapping(qualifiedByName = "toDto")
    List<ProductDto> toDtos(Collection<Product> source);
}
