package org.example.storage;

import org.example.entities.Manufacturer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Manages the data by providing methods to save and load a list of manufacturers using object serialization.
 */
public class DataManager {

    private final String filename;

    /**
     * Constructs a DataManager with the specified filename.
     *
     * @param filename The name of the file to be used for saving and loading data.
     */
    public DataManager(String filename) {
        this.filename = filename;
        checkAndCreateFile();
    }

    /**
     * Checks if the file exists and creates it if it doesn't.
     */
    private void checkAndCreateFile() {
        if (!fileExists(filename)) {
            createFile(filename);
            System.out.println("New file created: " + filename);
        } else {
            System.out.println("File already exists, opening: " + filename);
        }
    }

    /**
     * Checks if a file exists.
     *
     * @param filename The name of the file to be checked.
     * @return true if the file exists, false otherwise.
     */
    private boolean fileExists(String filename) {
        Path path = Paths.get(filename);
        return Files.exists(path);
    }

    /**
     * Creates a file.
     *
     * @param filename The name of the file to be created.
     */
    private void createFile(String filename) {
        Path path = Paths.get(filename);
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a list of manufacturers to the specified file using object serialization.
     *
     * @param manufacturers The list of manufacturers to be saved.
     */
    public void saveData(List<Manufacturer> manufacturers) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(manufacturers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a list of manufacturers from the specified file using object deserialization.
     *
     * @return The list of manufacturers loaded from the file.
     */
    public List<Manufacturer> loadData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Manufacturer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            return null;
        }
    }
}

