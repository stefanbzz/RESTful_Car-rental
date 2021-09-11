package ch.bzz.carRental.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;

/**
 * Class for a Car
 *
 * @author Stefan Thomsen
 * @version 1.0
 * @since 8.3.2021
 */
public class Car {

    private Rental rental;

    @FormParam("carUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String carUUID;

    @FormParam("brand")
    @NotEmpty
    @Size(min = 1, max = 40)
    private String brand;

    @FormParam("model")
    @NotEmpty
    @Size(min = 2, max = 40)
    private String model;

    @FormParam("category")
    @NotEmpty
    @Size(min = 2, max = 40)
    private String category;

    @FormParam("color")
    @NotEmpty
    @Size(min = 3, max = 30)
    private String color;

    @FormParam("horsepower")
    @Min(100)
    @Max(500)
    private int horsepower;

    @FormParam("range")
    @Min(100)
    @Max(10000)
    private int range;

    @FormParam("fuel")
    @NotEmpty
    @Size(min = 2, max = 20)
    private String fuel;

    /**
     * Instantiates a new Car.
     */
    public Car() {
        setCarUUID(null);
        setBrand(null);
        setModel(null);
        setCategory(null);
        setColor(null);
        setFuel(null);

    }

    /**
     * Gets rental.
     *
     * @return the rental
     */
    public Rental getRental() {
        return rental;
    }

    /**
     * Sets rental.
     *
     * @param rental the rental
     */
    public void setRental(Rental rental) {
        this.rental = rental;
    }

    /**
     * Gets car uuid.
     *
     * @return the car uuid
     */
    public String getCarUUID() {
        return carUUID;
    }

    /**
     * Sets car uuid.
     *
     * @param carUUID the car uuid
     */
    public void setCarUUID(String carUUID) {
        this.carUUID = carUUID;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets horsepower.
     *
     * @return the horsepower
     */
    public int getHorsepower() {
        return horsepower;
    }

    /**
     * Sets horsepower.
     *
     * @param horsepower the horsepower
     */
    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets range.
     *
     * @param range the range
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Gets fuel.
     *
     * @return the fuel
     */
    public String getFuel() {
        return fuel;
    }

    /**
     * Sets fuel.
     *
     * @param fuel the fuel
     */
    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
}