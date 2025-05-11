package toni.aguilera.capitole.infraestructure.controller.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toni.aguilera.capitole.application.products.FindProducts;
import toni.aguilera.capitole.application.products.GetProductsCommand;
import toni.aguilera.capitole.domain.exception.ProductsNotFoundException;
import toni.aguilera.capitole.infraestructure.controller.mapper.DtoToResponseMapper;
import toni.aguilera.generated.api.ProductApi;
import toni.aguilera.generated.model.ProductResponse;

import java.util.List;

@RestController
public class GetProductsController implements ProductApi {

    private final FindProducts findProducts;
    private final DtoToResponseMapper mapper;

    public GetProductsController(FindProducts findProducts, DtoToResponseMapper mapper) {
        this.findProducts = findProducts;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "orderBy", required = false) String orderBy
    ) {
        try {
            var response = findProducts.execute(new GetProductsCommand(category, orderBy));
            if (response == null || response.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(mapper.toResponseList(response));
        } catch (ProductsNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
