package org.example.services;

import jdk.jshell.execution.LoaderDelegate;
import org.example.entities.Manufacturer;
import org.example.entities.Souvenir;
import org.example.storage.DataManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing Souvenirs and Manufacturers, providing various operations on the data.
 */
public class SouvenirsService {

    private final DataManager dataManager;

    /**
     * Initializes a new instance of SouvenirsService with the provided DataManager.
     *
     * @param dataManager The DataManager responsible for loading and saving data.
     */
    public SouvenirsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Initializes a new instance of SouvenirsService with the specified filename for data storage.
     *
     * @param filename The filename for data storage.
     */
    public SouvenirsService(String filename) {
        this.dataManager = new DataManager(filename);
    }

    /**
     * Saves the provided list of manufacturers using the DataManager.
     *
     * @param manufacturerList The list of manufacturers to be saved.
     */
    public void save(List<Manufacturer> manufacturerList) {
        dataManager.saveData(manufacturerList);
    }

    /**
     * Loads a list of manufacturers from the DataManager.
     *
     * Attempts to load serialized data from the DataManager, returning a list of manufacturers.
     * If no data is found or an error occurs during loading, it returns an empty list and prints
     * a message to indicate the absence or error.
     *
     * @return A list of loaded manufacturers or an empty list if no data is found or an error occurs.
     */
    public List<Manufacturer> load() {
        List<Manufacturer> manufacturers = dataManager.loadData();
        if(manufacturers == null) {
//            System.out.println("No data found in the file or error loading data. Returning an empty list.");
            return new ArrayList<>();
        }
        return manufacturers;
    }

    /**
     * Checks if a manufacturer with the given name already exists in the list.
     *
     * @param manufacturers The list of manufacturers to check against.
     * @param name          The name of the manufacturer to check for uniqueness.
     * @return true if the name is unique, false otherwise.
     */
    private boolean isManufacturerNameUnique(List<Manufacturer> manufacturers, String name) {
        return manufacturers.stream().noneMatch(manufacturer -> manufacturer.getName().equals(name));
    }

    /**
     * Adds a new manufacturer to the existing list of manufacturers and saves the updated list,
     * if a manufacturer with the same name does not already exist.
     *
     * @param newManufacturer The new manufacturer to be added.
     */
    public void addManufacturer(Manufacturer newManufacturer) {
        List<Manufacturer> manufacturers = load();
        if (isManufacturerNameUnique(manufacturers, newManufacturer.getName())) {
            manufacturers.add(newManufacturer);
            save(manufacturers);
        } else {
            System.out.println("Manufacturer with the same name already exists: " + newManufacturer.getName());
        }
    }

    /**
     * Checks if a souvenir is unique for the specified manufacturer.
     *
     * @param manufacturer The manufacturer to check against.
     * @param newSouvenir  The new souvenir to check for uniqueness.
     * @return true if the souvenir is unique, false otherwise.
     */
    private boolean isSouvenirUnique(Manufacturer manufacturer, Souvenir newSouvenir) {
        return manufacturer.getSouvenirs().stream()
                .noneMatch(souvenir -> souvenir.getName().equals(newSouvenir.getName()));
    }

    /**
     * Adds a new souvenir to the specified manufacturer if the souvenir is not already present.
     *
     * @param manufacturerName The name of the manufacturer to add the souvenir to.
     * @param newSouvenir      The new souvenir to be added.
     */
//    public void addSouvenir(String manufacturerName, Souvenir newSouvenir) {
//        List<Manufacturer> manufacturers = load();
//        manufacturers.stream()
//                .filter(m -> m.getName().equals(manufacturerName))
//                .findFirst()
//                .ifPresent(m -> m.addSouvenir(newSouvenir));
//        save(manufacturers);
//    }
    public void addSouvenir(String manufacturerName, Souvenir newSouvenir) {
        List<Manufacturer> manufacturers = load();

        Optional<Manufacturer> manufacturerOpt = manufacturers.stream()
                .filter(m -> m.getName().equals(manufacturerName))
                .findFirst();

        if (manufacturerOpt.isEmpty()) {
            System.out.println("Manufacturer not found: " + manufacturerName);
            return;
        }

        manufacturerOpt.ifPresent(m -> {
            if (isSouvenirUnique(m, newSouvenir)) {
                System.out.println("Souvenirs before add: " + m.getSouvenirs());
                System.out.println("Souvenirs before add (file): " + load().get(0).getSouvenirs());
                m.addSouvenir(newSouvenir);
                System.out.println("Souvenirs after add: " + m.getSouvenirs());
                System.out.println("Souvenirs after add (file): " + load().get(0).getSouvenirs());
                save(manufacturers);
                System.out.println("Souvenirs after save: " + m.getSouvenirs());
                System.out.println("Souvenirs after save (file): " + load().get(0).getSouvenirs());
                System.out.println("Souvenir added to Manufacturer '" + manufacturerName + "': " + newSouvenir.getName());
            } else {
                System.out.println("Manufacturer '" + manufacturerName + "' already has the same souvenir: " + newSouvenir.getName());
            }
        });
    }

    /**
     * Adds a list of new souvenirs to the manufacturer with the specified name and saves the updated list.
     *
     * @param manufacturerName The name of the manufacturer to which souvenirs will be added.
     * @param newSouvenirList  The list of new souvenirs to be added.
     */
//    public void addAllSouvenirs(String manufacturerName, List<Souvenir> newSouvenirList) {
//        List<Manufacturer> manufacturers = load();
//        manufacturers.stream()
//                .filter(m -> m.getName().equals(manufacturerName))
//                .findFirst()
//                .ifPresent(m -> m.addSouvenirs(newSouvenirList));
//        save(manufacturers);
//    }
    public void addAllSouvenirs(String manufacturerName, List<Souvenir> newSouvenirList) {
        List<Manufacturer> manufacturers = load();

        Optional<Manufacturer> manufacturerOpt = manufacturers.stream()
                .filter(m -> m.getName().equals(manufacturerName))
                .findFirst();

        if (manufacturerOpt.isEmpty()) {
            System.out.println("Manufacturer not found: " + manufacturerName);
            return;
        }

        manufacturerOpt.ifPresent(manufacturer -> {
            for (Souvenir newSouvenir : newSouvenirList) {
                if (isSouvenirUnique(manufacturer, newSouvenir)) {
                    manufacturer.addSouvenir(newSouvenir);
                    System.out.println("Souvenir added to Manufacturer '" + manufacturerName + "': " + newSouvenir.getName());
                } else {
                    System.out.println("Manufacturer '" + manufacturerName + "' already has the same souvenir: " + newSouvenir.getName());
                    return;
                }
            }
        });

        save(manufacturers);
    }

    /**
     * Finds and returns a Manufacturer with the specified name from the loaded list of manufacturers.
     *
     * @param manufacturerName The name of the manufacturer to be found.
     * @return The found Manufacturer or null if not found.
     */
    public Manufacturer findManufacturerByName(String manufacturerName) {
        List<Manufacturer> manufacturers = load();
        return manufacturers.stream()
                .filter(m -> m.getName().equals(manufacturerName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds and returns a Souvenir with the specified manufacturer and souvenir names from the loaded list of manufacturers.
     *
     * @param manufacturerName The name of the manufacturer.
     * @param souvenirName     The name of the souvenir to be found.
     * @return The found Souvenir or null if not found.
     */
    public Souvenir findSouvenirByManufacturerAndName(String manufacturerName, String souvenirName) {
        List<Manufacturer> manufacturers = load();

        Optional<Souvenir> souvenirOpt = manufacturers.stream()
                .filter(m -> m.getName().equals(manufacturerName))
                .flatMap(m -> m.getSouvenirs().stream())
                .filter(s -> s.getName().equals(souvenirName))
                .findFirst();

        return souvenirOpt.orElse(null);
    }

    /**
     * Finds and returns the list of souvenirs produced by the manufacturer with the specified name.
     *
     * @param existingManufacturerName The name of the manufacturer to search for.
     * @return The list of souvenirs produced by the specified manufacturer.
     */
    public List<Souvenir> findSouvenirsByManufacturerName(String existingManufacturerName) {
        List<Manufacturer> manufacturers = load();
        Optional<Manufacturer> foundManufacturer = manufacturers.stream()
                .filter(m -> m.getName().equals(existingManufacturerName))
                .findFirst();
        return foundManufacturer.isPresent() ? foundManufacturer.get().getSouvenirs() : new ArrayList<>();
    }

    /**
     * Finds and returns the list of souvenirs produced by manufacturers from the specified country.
     *
     * @param existingManufacturerCountry The country of the manufacturer to search for.
     * @return The list of souvenirs produced by manufacturers from the specified country.
     */
    public List<Souvenir> findSouvenirsByManufacturerCountry(String existingManufacturerCountry) {
        List<Manufacturer> manufacturers = load();
        Optional<Manufacturer> foundManufacturer = manufacturers.stream()
                .filter(m -> m.getCountry().equals(existingManufacturerCountry))
                .findFirst();
        return foundManufacturer.isPresent() ? foundManufacturer.get().getSouvenirs() : new ArrayList<>();
    }

    /**
     * Finds and returns the list of souvenirs with prices lower than the specified limit.
     *
     * @param priceLimit The price limit to filter souvenirs.
     * @return The list of souvenirs with prices lower than the specified limit.
     */
    public List<Souvenir> findSouvenirsByPriceLowerThan(double priceLimit) {
        List<Manufacturer> manufacturers = load();
        return manufacturers.stream()
                .flatMap(manufacturer -> manufacturer.getSouvenirs().stream())
                .filter(souvenir -> souvenir.getPrice() < priceLimit)
                .toList();
    }

    /**
     * Finds and returns the list of manufacturers with souvenirs having prices lower than the specified limit.
     *
     * @param priceLimit The price limit to filter manufacturers.
     * @return The list of manufacturers with souvenirs having prices lower than the specified limit.
     */
    public List<Manufacturer> findManufacturersByPriceLowerThan(double priceLimit) {
        List<Manufacturer> manufacturers = load();
        return manufacturers.stream()
                .filter(manufacturer -> manufacturer.getSouvenirs().stream().anyMatch(souvenir -> souvenir.getPrice() < priceLimit))
                .toList();
    }

    /**
     * Displays information about all souvenirs and their manufacturers.
     */
    public void displayAllSouvenirsAndManufacturers() {
        List<Manufacturer> manufacturers = load();
        if(manufacturers.isEmpty()) {
            System.out.println("\n-------------\n(Empty)");
        }
        manufacturers.forEach(m -> {
            System.out.println("\n-------------\n" + m.toString() + "\nSouvenir list:");
            m.getSouvenirs().forEach(s -> System.out.println("\t" + s));
        });
    }

    /**
     * Finds and returns the list of manufacturers that produced the specified souvenir in the given year.
     *
     * @param souvenirName The name of the souvenir to search for.
     * @param year         The release year of the souvenir.
     * @return The list of manufacturers that produced the specified souvenir in the given year.
     */
    public List<Manufacturer> findManufacturersBySouvenirAndReleaseDate(String souvenirName, int year) {
        List<Manufacturer> manufacturers = load();
        return manufacturers.stream()
                .filter(m -> m.getSouvenirs().stream()
                        .anyMatch(s -> s.getName().equals(souvenirName) &&
                                s.getReleaseDate().getYear() == year))
                .collect(Collectors.toList());
    }

    /**
     * Finds and returns the list of souvenirs released in the specified year.
     *
     * @param year The release year to filter souvenirs.
     * @return The list of souvenirs released in the specified year.
     */
    public List<Souvenir> findSouvenirsByReleaseDate(int year) {
        List<Manufacturer> manufacturers = load();
        return manufacturers.stream()
                .flatMap(manufacturer -> manufacturer.getSouvenirs().stream())
                .filter(souvenir -> souvenir.getReleaseDate().getYear() == year)
                .collect(Collectors.toList());
    }

    /**
     * Removes the manufacturer with the specified name and its associated souvenirs from the list and saves the updated list.
     *
     * @param manufacturerNameToDelete The name of the manufacturer to be removed.
     */
    public void removeManufacturerAndSouvenirs(String manufacturerNameToDelete) {
        List<Manufacturer> manufacturers = load();
        manufacturers = manufacturers.stream().filter(m -> !m.getName().equals(manufacturerNameToDelete)).collect(Collectors.toList());
        save(manufacturers);
    }

    /**
     * Updates the name of the manufacturer with the specified old name to the new name and saves the updated list.
     *
     * @param oldManufacturerName The current name of the manufacturer.
     * @param newManufacturerName The new name for the manufacturer.
     */
    public void updateManufacturerName(String oldManufacturerName, String newManufacturerName) {
        List<Manufacturer> manufacturers = load();
        manufacturers.stream()
                .filter(m -> m.getName().equals(oldManufacturerName))
                .findFirst()
                .ifPresent(m -> {
                    try {
                        m.setName(newManufacturerName);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        save(manufacturers);
    }

    /**
     * Updates the country of the manufacturer with the specified old country to the new country and saves the updated list.
     *
     * @param oldManufacturerCountry The current country of the manufacturer.
     * @param newManufacturerCountry The new country for the manufacturer.
     */
    public void updateManufacturerCountry(String oldManufacturerCountry, String newManufacturerCountry) {
        List<Manufacturer> manufacturers = load();
        manufacturers.stream()
                .filter(m -> m.getCountry().equals(oldManufacturerCountry))
                .findFirst()
                .ifPresent(m -> {
                    try {
                        m.setCountry(newManufacturerCountry);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        save(manufacturers);
    }

    /**
     * Updates the name of a specific souvenir for the specified manufacturer.
     *
     * @param manufacturerName  The name of the manufacturer.
     * @param oldSouvenirName   The current name of the souvenir to be updated.
     * @param newSouvenirName   The new name for the souvenir.
     */
//    public void updateSouvenirName(String manufacturerName, String oldSouvenirName, String newSouvenirName) {
//        List<Manufacturer> manufacturers = load();
//        manufacturers.forEach(m -> {
//            if (m.getName().equals(manufacturerName)) {
//                m.getSouvenirs().forEach(s -> {
//                    if (s.getName().equals(oldSouvenirName)) {
//                        s.setName(newSouvenirName);
//                    }
//                });
//            }
//        });
//        save(manufacturers);
//    }
    public void updateSouvenirName(String manufacturerName, String oldSouvenirName, String newSouvenirName) {
        List<Manufacturer> manufacturers = load();

        manufacturers.forEach(manufacturer -> {
            if (manufacturer.getName().equals(manufacturerName)) {
                boolean isSouvenirNameUnique = manufacturer.getSouvenirs().stream()
                        .filter(s -> !s.getName().equals(oldSouvenirName))
                        .noneMatch(s -> s.getName().equals(newSouvenirName));

                if (isSouvenirNameUnique) {
                    manufacturer.getSouvenirs().forEach(souvenir -> {
                        if (souvenir.getName().equals(oldSouvenirName)) {
                            try {
                                souvenir.setName(newSouvenirName);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Souvenir name updated for Manufacturer '" + manufacturerName +
                                    "', Souvenir '" + oldSouvenirName + "' to '" + newSouvenirName + "':\n" + souvenir);
                        }
                    });
                } else {
                    System.out.println("Cannot update to the same name. Souvenir with name '" + newSouvenirName + "' already exists.");
                }
            }
        });

        save(manufacturers);
    }

    /**
     * Updates the manufacturer details of the specified souvenir produced by the specified manufacturer and saves the updated list.
     *
     * @param manufacturerName        The name of the manufacturer.
     * @param souvenirName            The name of the souvenir.
     * @param newManufacturerDetails The new manufacturer details for the souvenir.
     */
    public void updateSouvenirManufacturerDetails(String manufacturerName, String souvenirName, String newManufacturerDetails) {
        List<Manufacturer> manufacturers = load();
        manufacturers.forEach(m -> {
            if (m.getName().equals(manufacturerName)) {
                m.getSouvenirs().forEach(s -> {
                    if (s.getName().equals(souvenirName)) {
                        try {
                            s.setManufacturerDetails(newManufacturerDetails);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        save(manufacturers);
    }

    /**
     * Updates the release date of the specified souvenir produced by the specified manufacturer to the new date and saves the updated list.
     *
     * @param manufacturerName The name of the manufacturer.
     * @param souvenirName     The name of the souvenir.
     * @param newDate          The new release date for the souvenir.
     */
    public void updateSouvenirReleaseDate(String manufacturerName, String souvenirName, LocalDateTime newDate) {
        List<Manufacturer> manufacturers = load();
        manufacturers.forEach(m -> {
            if (m.getName().equals(manufacturerName)) {
                m.getSouvenirs().forEach(s -> {
                    if (s.getName().equals(souvenirName)) {
                        s.setReleaseDate(newDate);
                    }
                });
            }
        });
        save(manufacturers);
    }

    /**
     * Updates the release date of the specified souvenir produced by the specified manufacturer to the new date and saves the updated list.
     *
     * @param manufacturerName The name of the manufacturer.
     * @param souvenirName     The name of the souvenir.
     * @param newDate          The new release date for the souvenir as a string in the format "dd.MM.yyyy".
     */
    public void updateSouvenirReleaseDate(String manufacturerName, String souvenirName, String newDate) {
        List<Manufacturer> manufacturers = load();
        manufacturers.forEach(m -> {
            if (m.getName().equals(manufacturerName)) {
                m.getSouvenirs().forEach(s -> {
                    if (s.getName().equals(souvenirName)) {
                        s.setReleaseDate(newDate);
                    }
                });
            }
        });
        save(manufacturers);
    }

    /**
     * Updates the price of the specified souvenir produced by the specified manufacturer to the new price and saves the updated list.
     *
     * @param manufacturerName The name of the manufacturer.
     * @param souvenirName     The name of the souvenir.
     * @param newPrice         The new price for the souvenir.
     */
    public void updateSouvenirPrice(String manufacturerName, String souvenirName, double newPrice) {
        List<Manufacturer> manufacturers = load();
        manufacturers.forEach(m -> {
            if (m.getName().equals(manufacturerName)) {
                m.getSouvenirs().forEach(s -> {
                    if (s.getName().equals(souvenirName)) {
                        try {
                            s.setPrice(newPrice);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        save(manufacturers);
    }
}
