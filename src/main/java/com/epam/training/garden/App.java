package com.epam.training.garden;

import com.epam.training.garden.domain.GardenProperties;
import com.epam.training.garden.service.GardenService;
import com.epam.training.garden.service.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static Scanner keyboard;

    public static void main(String[] args) {


        GardenService gardenService = new GardenService();
        GardenProperties gardenProperties = gardenProperties();
        printWelcomeMessage();

        if (gardenProperties.getArea() > 0 && gardenProperties.getWaterSupply() > 0) {

            gardenService.setGardenProperties(gardenProperties);
            Result result = gardenService.evaluatePlan(getPlants());

            showResults(result);
        }

    }

    private static void printWelcomeMessage() {
        System.out.println("***Welcome to Garden Planner*** \n");
    }

    private static GardenProperties gardenProperties() {
        keyboard = new Scanner(System.in);
        System.out.println("Please enter your garden properties.");
        System.out.print("Size (square meter): ");

        if (!keyboard.hasNextDouble()) {
            System.out.print("Illegal input format.");
            return new GardenProperties(0, 0);
        }
        double size = keyboard.nextDouble();

        System.out.print("Water supply (in liter): ");

        if (!keyboard.hasNextDouble()) {
            System.out.print("Illegal input format.");
            return new GardenProperties(0, 0);
        }
        double supply = keyboard.nextDouble();
        return new GardenProperties(size, supply);
    }

    private static Map<String, Integer> getPlants() {
        keyboard = new Scanner(System.in);
        Map<String, Integer> getPlants = new HashMap<>();
        System.out.println("\nKnown plant types:\n" +
                "- Corn\n" +
                "- Pumpkin\n" +
                "- Grape\n" +
                "- Tomato\n" +
                "- Cucumber\n");
        System.out.println("Please enter the plants you would like to put in your garden. Press enter when you are done.");
        String line;
        System.out.print("Enter plant (format: name,amount): ");
        while (!(line = keyboard.nextLine()).isEmpty()) {
            String[] plant = line.split(",");
            getPlants.put(plant[0], Integer.parseInt(plant[1]));
            System.out.print("Enter plant (format: name,amount): ");

        }
        return getPlants;
    }

    private static void showResults(Result result) {
        System.out.println("\n***Result***\n");
        if (result.waterAmount > 0 && result.area > 0) {
            System.out.printf("Required area: %.1f m2\n", result.area);
            System.out.printf("Water need: %.1f l\n", result.waterAmount);
            if (!result.areaOk || !result.waterOk) {
                System.out.println("Plan is NOT feasible in your garden! :(");
                if (!result.areaOk) {
                    System.out.println("- Not enough area.");
                }
                if (!result.waterOk) {
                    System.out.println("- Not enough water.");
                }
            } else System.out.println("Plan is feasible in your garden! :)");
        }
    }
}
