package toni.aguilera.capitole.domain.products;

import lombok.Getter;

@Getter
public enum OrderBy {
    SKU("SKU"), PRICE("PRICE"), DESCRIPTION("DESCRIPTION"), CATEGORY("CATEGORY"), NONE("NONE");

    private final String value;

    OrderBy(String value) {
        this.value = value;
    }
}
