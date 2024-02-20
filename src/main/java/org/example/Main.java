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

            int choice = scanner.nextInt();
            scanner.nextLine();

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

}