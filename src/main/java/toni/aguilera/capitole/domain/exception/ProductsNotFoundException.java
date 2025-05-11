package toni.aguilera.capitole.domain.exception;

public class ProductsNotFoundException extends RuntimeException {
    public ProductsNotFoundException() {
        super("There's no products found for these values");
    }
}
