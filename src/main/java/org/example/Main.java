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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dataManager = new DataManager("souvenirs_data.txt");
        SouvenirsService souvenirsService = new SouvenirsService(dataManager);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Manufacturer");
            System.out.println("2. Add Souvenir");
            System.out.println("3. Display All Souvenirs and Manufacturers");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addManufacturer(scanner, souvenirsService);
                    break;
                case 2:
                    addSouvenir(scanner, souvenirsService);
                    break;
                case 3:
                    souvenirsService.displayAllSouvenirsAndManufacturers();
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addManufacturer(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter Manufacturer name:");
        String name = scanner.nextLine();

        System.out.println("Enter Manufacturer country:");
        String country = scanner.nextLine();

        Manufacturer newManufacturer = new Manufacturer(name, country);
        souvenirsService.addManufacturer(newManufacturer);

        System.out.println("Manufacturer added:\n" + newManufacturer);
    }

    private static void addSouvenir(Scanner scanner, SouvenirsService souvenirsService) {
        System.out.println("Enter Manufacturer name:");
        String manufacturerName = scanner.nextLine();

        System.out.println("Enter Souvenir name:");
        String name = scanner.nextLine();

        System.out.println("Enter Souvenir manufacturer details:");
        String manufacturerDetails = scanner.nextLine();

        System.out.println("Enter Souvenir release date (dd.MM.yyyy):");
        String releaseDateStr = scanner.nextLine();
//        LocalDateTime releaseDate = LocalDate.parse(releaseDateStr, Souvenir.DATE_FORMATTER).atStartOfDay();

        System.out.println("Enter Souvenir price:");
        double price = scanner.nextDouble();

//        Souvenir newSouvenir = new Souvenir(name, manufacturerDetails, releaseDate, price);
        Souvenir newSouvenir = new Souvenir(name, manufacturerDetails, releaseDateStr, price);
        souvenirsService.addSouvenir(manufacturerName, newSouvenir);

        if (!Objects.isNull(souvenirsService.findSouvenirByManufacturerAndName(manufacturerName, newSouvenir.getName())))
            System.out.println("Souvenir added:\n" + newSouvenir);
    }

//    public static void main(String[] args) {
//        // Create DataManager and SouvenirsService instances
//        DataManager dataManager = new DataManager("storage.txt");
//        SouvenirsService souvenirsService = new SouvenirsService(dataManager);
//
//        // Create sample data
//        Manufacturer manufacturer1 = new Manufacturer("Manufacturer1", "Country1");
//        Manufacturer manufacturer2 = new Manufacturer("Manufacturer2", "Country2");
//
//        Souvenir souvenir1 = new Souvenir("Souvenir1", "Details1", LocalDateTime.now(), 20.5);
//        Souvenir souvenir2 = new Souvenir("Souvenir2", "Details2", LocalDateTime.now(), 15.0);
//
//        // Add data using SouvenirsService methods
//        souvenirsService.addManufacturer(manufacturer1);
//        souvenirsService.addManufacturer(manufacturer2);
//
//        souvenirsService.addSouvenir("Manufacturer1", souvenir1);
//        souvenirsService.addSouvenir("Manufacturer2", souvenir2);
//
//        // Test various methods
//        testFindManufacturersBySouvenirAndReleaseDate(souvenirsService, "Souvenir1", LocalDateTime.now().getYear());
//        testUpdateSouvenirName(souvenirsService, "Manufacturer1", "Souvenir1", "NewSouvenirName");
//
//        // Display all souvenirs and manufacturers
//        souvenirsService.displayAllSouvenirsAndManufacturers();
//    }
//
//    private static void testFindManufacturersBySouvenirAndReleaseDate(SouvenirsService souvenirsService, String souvenirName, int year) {
//        List<Manufacturer> manufacturers = souvenirsService.findManufacturersBySouvenirAndReleaseDate(souvenirName, year);
//
//        System.out.println("\nManufacturers for Souvenir '" + souvenirName + "' released in " + year + ":");
//        manufacturers.forEach(System.out::println);
//    }
//
//    private static void testUpdateSouvenirName(SouvenirsService souvenirsService, String manufacturerName, String oldSouvenirName, String newSouvenirName) {
//        souvenirsService.updateSouvenirName(manufacturerName, oldSouvenirName, newSouvenirName);
//
//        System.out.println("\nSouvenir name updated for Manufacturer '" + manufacturerName + "', Souvenir '" + oldSouvenirName +
//                "' to '" + newSouvenirName + "':");
//
//        // Display all souvenirs and manufacturers after update
//        souvenirsService.displayAllSouvenirsAndManufacturers();
//    }
}