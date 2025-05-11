package toni.aguilera.capitole.infraestructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import toni.aguilera.capitole.domain.exception.ProductsNotFoundException;
import toni.aguilera.capitole.domain.products.Category;
import toni.aguilera.capitole.domain.products.OrderBy;
import toni.aguilera.capitole.domain.products.Product;
import toni.aguilera.capitole.domain.products.ProductQuery;
import toni.aguilera.capitole.domain.products.ProductsRepository;
import toni.aguilera.capitole.domain.products.SkuId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class H2ProductsRepository implements ProductsRepository {
    @Autowired
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ProductsRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> find(ProductQuery query) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT SKU, PRICE, DESCRIPTION, CATEGORY FROM PRODUCTS");
        Map<String, Object> parameters = new HashMap<>();

        if (query.category() != Category.NONE) {
            sqlBuilder.append(" WHERE CATEGORY = :category");
            parameters.put("category", query.category().getName());
        }

        if (query.orderBy() != OrderBy.NONE) {
            sqlBuilder.append(" ORDER BY ").append(query.orderBy().getValue());
        }

        try {
            return jdbcTemplate.query(sqlBuilder.toString(), parameters, mapResultSetToProduct());
        } catch (EmptyResultDataAccessException ex) {
            throw new ProductsNotFoundException();
        }
    }

    private RowMapper<Product> mapResultSetToProduct() {
        return (rs, rowNum) -> {
            var skuId = new SkuId(rs.getString("sku"));
            var price = rs.getDouble("price");
            var description = rs.getString("description");
            var category = Category.findByName(rs.getString("category"));

            return Product.create(skuId, price, description, category);
        };
    }
}
