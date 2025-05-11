package toni.aguilera.capitole.domain.products;

import java.util.Arrays;

public enum Category {
    ELECTRONICS("Electronics", 0.15),
    HOME_KITCHEN("Home & Kitchen", 0.25),
    CLOTHING("Clothing", 0.0),
    ACCESSORIES("Accessories", 0.0),
    SPORTS("Sports", 0.0),
    MUSICAL_INSTRUMENTS("Musical Instr.", 0.0),
    FOOTWEAR("Footwear", 0.0),
    HOME_APPLIANCES("Home Appliances", 0.0),
    STATIONERY("Stationery", 0.0),
    TOYS_GAMES("Toys & Games", 0.0),
    NONE("none", 0.0);

    private final String name;
    private final double discount;

    Category(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

    public static Category findByName(String name) {
        return Arrays.stream(values())
                .filter(cat -> cat.name.equalsIgnoreCase(name))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid category " + name));
    }
}
