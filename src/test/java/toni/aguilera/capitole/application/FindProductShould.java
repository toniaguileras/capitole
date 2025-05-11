package toni.aguilera.capitole.application;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import toni.aguilera.capitole.application.mapper.ProductToDtoMapper;
import toni.aguilera.capitole.application.products.FindProducts;
import toni.aguilera.capitole.application.products.GetProductsCommand;
import toni.aguilera.capitole.application.products.ProductDto;
import toni.aguilera.capitole.domain.products.Category;
import toni.aguilera.capitole.domain.products.OrderBy;
import toni.aguilera.capitole.domain.products.Product;
import toni.aguilera.capitole.domain.products.ProductQuery;
import toni.aguilera.capitole.domain.products.ProductsRepository;
import toni.aguilera.capitole.domain.products.SkuId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindProductShould {

    @Test
    void return_products_with_correct_parameters() {
        var category = "ELECTRONICS";
        var orderBy = "SKU";

        var productRepository = Mockito.mock(ProductsRepository.class);
        var mapper = Mockito.mock(ProductToDtoMapper.class);
        Mockito.when(mapper.mapList(Mockito.anyList())).thenReturn(List.of(
                new ProductDto("SKU0001", 11D, "desc1", "Electronics"),
                new ProductDto("SKU0002", 22D, "desc2", "Electronics"),
                new ProductDto("SKU0003", 33D, "desc3", "Electronics"),
                new ProductDto("SKU0004", 44D, "desc4", "Electronics"),
                new ProductDto("SKU0005", 55D, "desc5", "Electronics")));

        Mockito.when(productRepository.find(
                new ProductQuery(Category.valueOf(category),
                        OrderBy.valueOf(orderBy)))).thenReturn(List.of(
                new Product(new SkuId("SKU0001"), 11D, "desc1", Category.valueOf(category)),
                new Product(new SkuId("SKU0002"), 22D, "desc2", Category.valueOf(category)),
                new Product(new SkuId("SKU0003"), 33D, "desc3", Category.valueOf(category)),
                new Product(new SkuId("SKU0004"), 44D, "desc4", Category.valueOf(category)),
                new Product(new SkuId("SKU0005"), 55D, "desc5", Category.valueOf(category))
        ));
        var findProduct = new FindProducts(productRepository, mapper);

        var result = findProduct.execute(new GetProductsCommand(category, orderBy));
        assertNotNull(result);
        assertEquals(11D, result.getFirst().getPrice());
        assertEquals("SKU0001", result.getFirst().getSku());
        assertEquals(55D, result.get(4).getPrice());
    }

    @Test
    void fail_when_applicationDate_format_is_invalid() {
        var category = "ELECTRONICS";
        var invalidOrderBy = "car";

        var productRepository = Mockito.mock(ProductsRepository.class);
        var mapper = Mockito.mock(ProductToDtoMapper.class);

        var findProduct = new FindProducts(productRepository, mapper);

        assertThrows(IllegalArgumentException.class, () -> {
            findProduct.execute(new GetProductsCommand(category, invalidOrderBy));
        });
    }
}