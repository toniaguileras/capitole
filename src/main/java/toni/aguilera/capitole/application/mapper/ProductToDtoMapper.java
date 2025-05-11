package toni.aguilera.capitole.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import toni.aguilera.capitole.application.products.ProductDto;
import toni.aguilera.capitole.domain.products.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductToDtoMapper {

    @Mapping(source = "sku.value", target = "sku")
    @Mapping(source = "category.name", target = "category")
    ProductDto map(Product product);

    List<ProductDto> mapList(List<Product> dto);
}
