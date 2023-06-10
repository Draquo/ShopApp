package org.example.model;

import org.example.common.PricedItem;

public class Food implements PricedItem<Integer> {
    private final String name;
    private final String description;
    private final int price;

    public Food(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Enjoy " + name + ": " + description + "    Cost: " + price;
    }
}
