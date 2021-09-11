package ch.bzz.carRental.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * The type Rental.
 */
public class Rental {

    @FormParam("rental")
    @NotEmpty
    @Size(min=5, max=50)
    private String rental;

    @FormParam("rentaluuid")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String rentalUUID;

    /**
     * Default constructor
     */
    public Rental() {
        setRental(null);
    }

    /**
     * Gets rental.
     *
     * @return the rental
     */
    public String getRental() {
        return rental;
    }

    /**
     * Sets rental.
     *
     * @param rental the rental
     */
    public void setRental(String rental) {
        this.rental = rental;
    }

    /**
     * Gets the rentalUUID
     *
     * @return value of rental
     */
    public String getRentalUUID() {
        return rentalUUID;
    }

    /**
     * Sets the rentalUUID
     *
     * @param rentalUUID the value to set
     */
    public void setRentalUUID(String rentalUUID) {
        this.rentalUUID = rentalUUID;
    }
}
