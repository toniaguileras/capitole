package toni.aguilera.capitole.application.products;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
    private String sku;
    private Double price;
    private String description;
    private String category;
}
