package toni.aguilera.capitole.domain.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private SkuId sku;
    private Double price;
    private String description;
    private Category category;
    private Double discount;

    private static final String SKU_LAST_NUMBER = "5";
    private static final Double SKU_DISCOUNT = 0.3;


    public Product(SkuId sku, Double price, String description, Category category) {
        this.sku = sku;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public static Product create(SkuId sku, Double price, String description, Category category) {
        var product = new Product(sku, price, description, category);
        product.calculateDiscount();
        product.applyDiscount();
        return product;
    }

    private void calculateDiscount() {
        var categoryDiscount = category.getDiscount();
        var skuDiscount = sku.value().endsWith(SKU_LAST_NUMBER) ? SKU_DISCOUNT : 0;
        this.discount = Math.max(categoryDiscount, skuDiscount);
    }

    private void applyDiscount() {
        this.price = Math.floor(this.price * (1 - this.discount) * 100 + 0.5) / 100;
    }
}
