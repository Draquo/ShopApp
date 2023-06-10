package org.example.menu;

import org.example.model.Food;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Create FoodMenu class here
public class FoodMenu {
    private final List<Food> menu;

    public FoodMenu() {
        this.menu = new ArrayList<>();
        initializeMenu();

    }

    private void initializeMenu() {
        Food food = new Food("Ananas", "Słodki świeży ananas.", 34);
        Food food1 = new Food("Milk", "Mleko roślinne.", 4);
        Food food2 = new Food("Bread", "Świeży, chrupiący chleb.", 14);
        menu.add(food);
        menu.add(food1);
        menu.add(food2);
    }

    public Food getFood(int index) {
        if (index > 0 && index <= menu.size()) {
            return menu.get(index - 1);
        } else {
            return null;
        }
    }

    public Food getLowestCostFood() {
        return menu
                .stream()
                .min(Comparator.comparingInt(Food::getPrice))
                .orElse(null);
    }

    public String toString() {
        String result = "";
        AtomicInteger index = new AtomicInteger(1);

        for (Food food : menu) {
            result += index.getAndIncrement() + ". " + food.getName() + ": " + food.getDescription() + "    " + food.getPrice() + "\n";
        }
        return result;
    }

}

