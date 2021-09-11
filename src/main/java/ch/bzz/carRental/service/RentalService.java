package ch.bzz.carRental.service;

import ch.bzz.carRental.data.DataHandler;
import ch.bzz.carRental.model.Rental;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the carrental
 * <p>
 * M133: Car-rental
 *
 * @author Stefan Thomsen
 */
@Path("rental")
public class RentalService {
    /**
     * List rental response.
     *
     * @return the response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listRentals(
    ) {
        Map<String, Rental> rentalMap = DataHandler.getRentalMap();
        Response response = Response
                .status(200)
                .entity(rentalMap)
                .build();
        return response;

    }

    /**
     * Read rental response.
     *
     * @param rentalUUID the rental uuid
     * @return the response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readRental(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String rentalUUID
    ) {
        Rental rental = null;
        int httpStatus;

        rental = DataHandler.readRental(rentalUUID);
        if (rental.getRental() != null) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }


        Response response = Response
                .status(httpStatus)
                .entity(rental)
                .build();
        return response;
    }

    /**
     * creates a new rental shop without cars
     *
     * @param rental the rental
     * @return Response response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createRental(
            @Valid @BeanParam Rental rental
    ) {
        int httpStatus = 200;
        rental.setRentalUUID(UUID.randomUUID().toString());
        DataHandler.insertRental(rental);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates the rental shop in all it's cars
     *
     * @param rental the new rental name
     * @return Response response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateRental(
            @Valid @BeanParam Rental rental
    ) {
        int httpStatus = 200;

        if (DataHandler.updateRental(rental)) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * Deletes rental.
     *
     * @param rentalUUID the rental uuid
     * @return the response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteRental(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String rentalUUID
    ) {
        int httpStatus;

        int errorcode = DataHandler.deleteRental(rentalUUID);
        if (errorcode == 0) httpStatus = 200;
        else if (errorcode == -1) httpStatus = 409;
        else httpStatus = 404;

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

}
