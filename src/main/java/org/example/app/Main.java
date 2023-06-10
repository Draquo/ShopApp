package org.example.app;

import org.example.menu.FoodMenu;
import org.example.model.Customer;
import org.example.simulation.TakeOutSimulator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What's your name?");
        String customerName = scanner.nextLine();

        int money = 0;
        boolean validateAmount = false;

        while (!validateAmount) {
            System.out.println("How much money you have?");
            try {
                money = scanner.nextInt();
                validateAmount = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Pobierz błędne dane wejściowe, aby umożliwić kolejne próby
            }

        }
        FoodMenu menu = new FoodMenu();
        Customer customer = new Customer(customerName, money);
        TakeOutSimulator takeOutSimulator = new TakeOutSimulator(customer, menu);

        takeOutSimulator.takeOutPrompt();
    }
}
