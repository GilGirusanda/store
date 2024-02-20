package org.example.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Manufacturer with information such as name, country, and a list of souvenirs.
 * Implements Serializable to support object serialization.
 */
public class Manufacturer implements Serializable {
    private String name;
    private String country;
    private ArrayList<Souvenir> souvenirs;

    /**
     * Constructs a Manufacturer with the specified name and country.
     *
     * @param name    The name of the manufacturer.
     * @param country The country where the manufacturer is located.
     */
    public Manufacturer(String name, String country) throws Exception {
        setName(name);
        setCountry(country);
        this.souvenirs = new ArrayList<>();
    }

    // Getters

    /**
     * Gets the name of the manufacturer.
     *
     * @return The name of the manufacturer.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the country where the manufacturer is located.
     *
     * @return The country of the manufacturer.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the list of souvenirs produced by the manufacturer.
     *
     * @return The list of souvenirs produced by the manufacturer.
     */
    public ArrayList<Souvenir> getSouvenirs() {
        return souvenirs;
    }

    // Setters

    /**
     * Sets the name of the manufacturer.
     *
     * @param name The name to set for the manufacturer.
     */
    public void setName(String name) throws Exception {
        if(name.isEmpty()) {
            throw new Exception("Manufacturer name can't be empty");
        }
        this.name = name;
    }

    /**
     * Sets the country of the manufacturer.
     *
     * @param country The country to set for the manufacturer.
     */
    public void setCountry(String country) throws Exception {
        if(country.isEmpty()) {
            throw new Exception("Manufacturer country can't be empty");
        }
        this.country = country;
    }

    /**
     * Adds a souvenir to the list of souvenirs produced by the manufacturer.
     *
     * @param souvenir The souvenir to add.
     */
    public void addSouvenir(Souvenir souvenir) {
        Optional<Souvenir> souvenirOpt = Optional.of(souvenir);
        souvenirOpt.ifPresentOrElse(this.souvenirs::add, () -> {
            System.out.println("Can't add a souvenir. Value is absent.");
        });
    }

    /**
     * Adds a list of souvenirs to the list of souvenirs produced by the manufacturer.
     *
     * @param souvenirs The list of souvenirs to add.
     */
    public void addSouvenirs(List<Souvenir> souvenirs) {
        for(Souvenir souvenir: souvenirs) {
            addSouvenir(souvenir);
        }
    }

    /**
     * Returns a string representation of the Manufacturer.
     *
     * @return A string containing the name and country of the manufacturer.
     */
    @Override
    public String toString() {
        return String.format("Manufacturer(\nname = %s, \ncountry = %s)", this.name, this.country);
    }

    /**
     * Generates a hash code for the Manufacturer based on its attributes.
     *
     * @return The hash code for the Manufacturer.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

    /**
     * Checks if the Manufacturer is equal to another object based on its attributes.
     *
     * @param o The object to compare with the Manufacturer.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Manufacturer manufacturer = (Manufacturer) o;
        return Objects.equals(name, manufacturer.name)
                && (country.equals(manufacturer.country));
    }
}
