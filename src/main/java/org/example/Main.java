package org.example;

import org.example.entities.Manufacturer;
import org.example.entities.Souvenir;
import org.example.services.SouvenirsService;
import org.example.storage.DataManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        DataManager dataManager = new DataManager("souvenirs_data.txt");
        SouvenirsService souvenirsService = new SouvenirsService(dataManager);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Manufacturer");
            System.out.println("2. Add Souvenir");
            System.out.println("3. Display All Souvenirs and Manufacturers");
            System.out.println("4. Exit");
            System.out.println("5. Find Souvenirs by Manufacturer");
            System.out.println("6. Find Souvenirs by Country");
            System.out.println("7. Find Manufacturers by Price");
            System.out.println("8. Display All Manufacturers with Souvenirs");
            System.out.println("9. Display Manufacturers of Souvenir in Year");
            System.out.println("10. Display Souvenirs by Year");
            System.out.println("11. Edit Manufacturer");
            System.out.println("12. Remove Manufacturer and Souvenirs");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addManufacturer(scanner, souvenirsService);
                case 2 -> addSouvenir(scanner, souvenirsService);
                case 3 -> souvenirsService.displayAllSouvenirsAndManufacturers();
                case 4 -> {
                    System.out.println("Goodbye!\nExiting...");
                    System.exit(0);
                }
                case 5 -> displaySouvenirsByManufacturer(scanner, souvenirsService);
                case 6 -> displaySouvenirsByCountry(scanner, souvenirsService);
                case 7 -> displayManufacturersByPrice(scanner, souvenirsService);
                case 8 -> displayAllManufacturersWithSouvenirs(scanner, souvenirsService);
                case 9 -> displayManufacturersOfSouvenirInYear(scanner, souvenirsService);
                case 10 -> displaySouvenirsByYear(scanner, souvenirsService);
                case 11 -> editManufacturer(scanner, souvenirsService);
                case 12 -> removeManufacturerAndSouvenirs(scanner, souvenirsService);
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addManufacturer(Scanner scanner, SouvenirsService souvenirsService) throws Exception {
        System.out.println("Enter Manufacturer name:");
        String name = scanner.nextLine();

        System.out.println("Enter Manufacturer country:");
        String country = scanner.nextLine();

        Manufacturer newManufacturer = new Manufacturer(name, country);
        souvenirsService.addManufacturer(newManufacturer);

        System.out.println("Manufacturer added:\n" + newManufacturer);
    }

    private static void addSouvenir(Scanner scanner, SouvenirsService souvenirsService) throws Exception {
        System.out.println("Enter Manufacturer name:");
        String manufacturerName = scanner.nextLine();

        if (Objects.isNull(souvenirsService.findManufacturerByName(manufacturerName))) {
            System.out.println("No such a manufacturer.\n");
            return;
        }

        System.out.println("Enter Souvenir name:");
        String souvenirName = scanner.nextLine();
        if (!Objects.isNull(souvenirsService.findSouvenirByManufacturerAndName(manufacturerName, souvenirName))) {
            System.out.println("Such a souvenir name is already taken.\n");
            return;
        }

        System.out.println("Enter Souvenir manufacturer details:");
        String manufacturerDetails = scanner.nextLine();

        System.out.println("Enter Souvenir release date (dd.MM.yyyy):");
        String releaseDateStr = scanner.nextLine();

        System.out.println("Enter Souvenir price:");
        double price = scanner.nextDouble();

        Souvenir newSouvenir = new Souvenir(souvenirName, manufacturerDetails, releaseDateStr, price);
        souvenirsService.addSouvenir(manufacturerName, newSouvenir);
        System.out.println("Souvenir details:\n" + newSouvenir);
    }

    private static void displaySouvenirsByManufacturer(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter Manufacturer name:");
        String manufacturerName = scanner.nextLine();
        souvenirsService.findSouvenirsByManufacturerName(manufacturerName)
                .forEach(System.out::println);
    }

    private static void displaySouvenirsByCountry(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter Manufacturer country:");
        String country = scanner.nextLine();
        souvenirsService.findSouvenirsByManufacturerCountry(country)
                .forEach(System.out::println);
    }

    private static void displayManufacturersByPrice(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter maximum price:");
        double priceLimit = scanner.nextDouble();
        souvenirsService.findManufacturersByPriceLowerThan(priceLimit)
                .forEach(System.out::println);
    }

    private static void displayAllManufacturersWithSouvenirs(Scanner scanner, SouvenirsService souvenirsService) {
        souvenirsService.displayAllSouvenirsAndManufacturers();
    }

    private static void displayManufacturersOfSouvenirInYear(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter Souvenir name:");
        String souvenirName = scanner.nextLine();
        System.out.println("Enter manufacturing year:");
        int year = scanner.nextInt();
        souvenirsService.findManufacturersBySouvenirAndReleaseDate(souvenirName, year)
                .forEach(System.out::println);
    }

    private static void displaySouvenirsByYear(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter manufacturing year:");
        int year = scanner.nextInt();
        souvenirsService.findSouvenirsByReleaseDate(year)
                .forEach(System.out::println);
    }

    private static void removeManufacturerAndSouvenirs(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter Manufacturer name to delete:");
        String manufacturerNameToDelete = scanner.nextLine();
        souvenirsService.removeManufacturerAndSouvenirs(manufacturerNameToDelete);
        System.out.println("Manufacturer and associated souvenirs removed.");
    }

    private static void editManufacturer(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter manufacturer name to edit:");
        String manufacturerName = scanner.next();

        System.out.println("Select an option to edit:");
        System.out.println("1. Update manufacturer name");
        System.out.println("2. Update manufacturer country");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> updateManufacturerName(scanner, souvenirsService, manufacturerName);
            case 2 -> updateManufacturerCountry(scanner, souvenirsService, manufacturerName);
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void updateManufacturerName(Scanner scanner, SouvenirsService souvenirsService, String manufacturerName) {
        System.out.println("Enter new manufacturer name:");
        String newManufacturerName = scanner.next();
        souvenirsService.updateManufacturerName(manufacturerName, newManufacturerName);
    }

    private static void updateManufacturerCountry(Scanner scanner, SouvenirsService souvenirsService, String manufacturerName) {
        System.out.println("Enter new manufacturer country:");
        String newManufacturerCountry = scanner.next();
        souvenirsService.updateManufacturerCountry(manufacturerName, newManufacturerCountry);
    }

}