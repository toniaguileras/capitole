package toni.aguilera.capitole.domain.products;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductsRepository {
    List<Product> find(ProductQuery productQuery);
}
