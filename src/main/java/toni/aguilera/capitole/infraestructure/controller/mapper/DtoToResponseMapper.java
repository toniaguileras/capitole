package toni.aguilera.capitole.infraestructure.controller.mapper;

import org.mapstruct.Mapper;
import toni.aguilera.capitole.application.products.ProductDto;
import toni.aguilera.generated.model.ProductResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DtoToResponseMapper {

    ProductResponse toResponse(ProductDto dto);

    List<ProductResponse> toResponseList(List<ProductDto> dto);
}
