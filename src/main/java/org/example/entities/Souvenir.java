package org.example.entities;

import javax.swing.text.DateFormatter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Objects;

/**
 * Represents a Souvenir with information such as name, manufacturer details, release date, and price.
 * Implements Serializable to support object serialization.
 */
public class Souvenir implements Serializable {
    private String name;
    private String manufacturerDetails;
    private LocalDateTime releaseDate;
    private double price;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Constructs a Souvenir with the specified attributes.
     *
     * @param name               The name of the souvenir.
     * @param manufacturerDetails The details of the manufacturer.
     * @param releaseDate        The release date of the souvenir.
     * @param price              The price of the souvenir.
     */
    public Souvenir(String name, String manufacturerDetails, LocalDateTime releaseDate, double price) throws Exception {
        setName(name);
        setManufacturerDetails(manufacturerDetails);
        setReleaseDate(releaseDate);
        setPrice(price);
    }

    public Souvenir(String name, String manufacturerDetails, String releaseDate, double price) throws Exception {
        setName(name);
        setManufacturerDetails(manufacturerDetails);
        setReleaseDate(releaseDate);
        setPrice(price);
    }

    // Getters

    /**
     * Gets the name of the souvenir.
     *
     * @return The name of the souvenir.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the manufacturer details of the souvenir.
     *
     * @return The manufacturer details of the souvenir.
     */
    public String getManufacturerDetails() {
        return manufacturerDetails;
    }

    /**
     * Gets the release date of the souvenir.
     *
     * @return The release date of the souvenir.
     */
    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets the price of the souvenir.
     *
     * @return The price of the souvenir.
     */
    public double getPrice() {
        return price;
    }

    // Setters

    /**
     * Sets the name of the souvenir.
     *
     * @param name The name to set for the souvenir.
     */
    public void setName(String name) throws Exception {
        if(name.isEmpty()) {
            throw new Exception("Souvenir name can't be empty");
        }
        this.name = name;
    }

    /**
     * Sets the manufacturer details of the souvenir.
     *
     * @param manufacturerDetails The manufacturer details to set for the souvenir.
     */
    public void setManufacturerDetails(String manufacturerDetails) throws Exception {
        if(name.isEmpty()) {
            throw new Exception("Souvenir manufacturer details can't be empty");
        }
        this.manufacturerDetails = manufacturerDetails;
    }

    /**
     * Sets the release date of the souvenir using a LocalDateTime object.
     *
     * @param releaseDate The release date to set for the souvenir.
     */
    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Sets the release date of the souvenir using a string in the format "dd.MM.yyyy".
     *
     * @param releaseDateString The release date string to set for the souvenir.
     */
    public void setReleaseDate(String releaseDateString) {
        try {
            LocalDate dt = LocalDate.parse(releaseDateString, DATE_FORMATTER);
            this.releaseDate = dt.atStartOfDay();

//            this.releaseDate = LocalDateTime.parse(releaseDateString, DATE_FORMATTER);

//            this.releaseDate = date.atStartOfDay();

//            this.releaseDate = LocalDateTime.of(date, LocalTime.MIDNIGHT);

//            this.releaseDate = LocalDateTime.parse(releaseDateString, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            System.out.println("Can't set release date. Wrong format. Should be `dd.MM.yyyy`");
            ex.printStackTrace();
        }
    }

    /**
     * Sets the price of the souvenir.
     *
     * @param price The price to set for the souvenir.
     */
    public void setPrice(double price) throws Exception {
        if(price < 0) {
            throw new Exception("Souvenir price can't be negative");
        }
        this.price = price;
    }

    /**
     * Returns a string representation of the Souvenir.
     *
     * @return A string containing the name, manufacturer details, release date, and price of the souvenir.
     */
    @Override
    public String toString() {
        return String.format("Souvenir(\n\t\tname = %s, \n\t\tmanufacturerDetails = %s, \n\t\treleaseDate = %s, \n\t\tprice = %s)", this.name, this.manufacturerDetails, this.releaseDate, this.price);
    }

    /**
     * Generates a hash code for the Souvenir based on its attributes.
     *
     * @return The hash code for the Souvenir.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturerDetails, releaseDate, price);
    }

    /**
     * Checks if the Souvenir is equal to another object based on its attributes.
     *
     * @param o The object to compare with the Souvenir.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Souvenir souvenir = (Souvenir) o;
        return Objects.equals(name, souvenir.name)
                && (manufacturerDetails.equals(souvenir.manufacturerDetails)
                && releaseDate.equals(souvenir.releaseDate)
                && price == souvenir.price);
    }
}
