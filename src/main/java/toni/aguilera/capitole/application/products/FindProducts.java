package toni.aguilera.capitole.application.products;

import org.springframework.stereotype.Service;
import toni.aguilera.capitole.application.mapper.ProductToDtoMapper;
import toni.aguilera.capitole.domain.products.Category;
import toni.aguilera.capitole.domain.products.OrderBy;
import toni.aguilera.capitole.domain.products.ProductQuery;
import toni.aguilera.capitole.domain.products.ProductsRepository;

import java.util.List;

@Service
public class FindProducts {

    private final ProductsRepository productsRepository;
    private final ProductToDtoMapper mapper;

    public FindProducts(ProductsRepository productsRepository, ProductToDtoMapper mapper) {
        this.productsRepository = productsRepository;
        this.mapper = mapper;
    }

    public List<ProductDto> execute(GetProductsCommand command) {
        var category = command.category() != null ? Category.valueOf(command.category()) : Category.NONE;
        var orderBy = command.orderBy() != null ? OrderBy.valueOf(command.orderBy()) : OrderBy.NONE;
        var products = productsRepository.find(new ProductQuery(category, orderBy));

        return mapper.mapList(products);
    }
}
