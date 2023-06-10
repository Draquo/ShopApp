package org.example.simulation;

import org.example.menu.FoodMenu;
import org.example.input.IntUserInputRetriever;
import org.example.model.Customer;
import org.example.model.Food;
import org.example.model.ShoppingBag;

import java.util.Scanner;

public class TakeOutSimulator {
    private final Customer customer;
    private final FoodMenu menu;
    private final Scanner input;

    public TakeOutSimulator(Customer customer, FoodMenu menu) {
        this.customer = customer;
        this.menu = menu;
        this.input = new Scanner(System.in);
    }

    private <T> T getOutputOnIntInput(String userInputPrompt, IntUserInputRetriever<T> intUserInputRetriever) {
        while (true) {
            System.out.println(userInputPrompt);
            if (input.hasNextInt()) {
                int userInput = input.nextInt();
                input.nextLine(); // Pobierz znak nowej linii po wczytaniu liczby całkowitej
                try {
                    return intUserInputRetriever.produceOutputOnIntUserInput(userInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Podana wartość nie jest prawidłowym wyborem. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Podaj wartość całkowitą. Spróbuj ponownie.");
                input.next(); // Pobierz błędne dane wejściowe, aby umożliwić kolejne próby
            }
        }
    }

    public boolean shouldSimulate() {
        Food lowestPrice = menu.getLowestCostFood();
        if (customer.getMoney() < lowestPrice.getPrice()) {
            System.out.println("Nie masz wystarczająco pieniędzy na    najtańsze jedzenie.");
            return false;
        }

        String userPrompt = "Enter 1 to CONTINUE simulation or 0 to EXIT program: ";

        IntUserInputRetriever<Boolean> intUserInputRetriever = selection -> {
            if (selection == 1) {
                return true;
            } else if (selection == 0) {
                return false;
            } else {
                throw new IllegalArgumentException("Nieprawidłowy wybór. Wybierz 1 lub 0.");
            }
        };
        return getOutputOnIntInput(userPrompt, intUserInputRetriever);
    }

    public Food getMenuSelection() {
        String welcomePrompt = "Today's Menu Options!";
        System.out.println(welcomePrompt);
        System.out.println(menu.toString());

        String userPrompt = "Choose a menu item!: ";

        IntUserInputRetriever<Food> intUserInputRetriever = selection -> {
            Food selectedFood = menu.getFood(selection);
            if (selectedFood != null) {
                return selectedFood;
            } else {
                throw new IllegalArgumentException("Invalid selection. Please choose a valid food item number.");
            }


        };
        return getOutputOnIntInput(userPrompt, intUserInputRetriever);
    }

    public Boolean isStillOrderingFood() {
        String userPrompt = "Enter 1 to CONTINUE shopping or 0 to CHECKOUT: ";
        IntUserInputRetriever<Boolean> intUserInputRetriever = selection -> {
            if (selection == 1) {
                return true;
            } else if (selection == 0) {
                return false;
            } else {
                throw new IllegalArgumentException("Nieprawidłowy wybór. Wybierz 1 lub 0.");
            }
        };
        return getOutputOnIntInput(userPrompt, intUserInputRetriever);
    }

    public void checkoutCustomer(ShoppingBag<Food> shoppingBag) {
        System.out.println("Processing payment...");
        int totalCost = shoppingBag.getTotalPrice();
        int remainingMoney = customer.getMoney() - totalCost;

        customer.setMoney(remainingMoney);
        System.out.println("Total cost: " + totalCost);

        System.out.println("Your remaining money: " + customer.getMoney());
    }

    public void takeOutPrompt() {
        ShoppingBag<Food> shoppingBag = new ShoppingBag<>();
        int customerMoneyLeft = customer.getMoney();

        System.out.println("You have $" + customerMoneyLeft + " left to spend.");

        while (true) {
            Food selectedFood = getMenuSelection();
            if (selectedFood.getPrice() <= customerMoneyLeft) {
                shoppingBag.addItem(selectedFood);
                customerMoneyLeft -= selectedFood.getPrice();
                System.out.println("Added " + selectedFood.getName() + " to your shopping bag.");
            } else {
                System.out.println("Oops! Looks like you don't have enough for that. Choose another item or checkout.");
            }

            if (Boolean.FALSE.equals(isStillOrderingFood())) {
                checkoutCustomer(shoppingBag);
                break;
            }
        }
    }


    public void startTakeOutSimulator() {
        System.out.println("Hello, welcome to my restaurant!");

        while (true) {
            System.out.println("\nWelcome " + customer.getName() + "!");
            takeOutPrompt();

            if (!shouldSimulate()) {
                System.out.println("Thank you for using the take-out system. Goodbye!");
                break;
            }
        }
    }

}



