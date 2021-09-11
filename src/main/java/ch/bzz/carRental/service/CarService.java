package ch.bzz.carRental.service;

import ch.bzz.carRental.data.DataHandler;
import ch.bzz.carRental.model.Car;
import ch.bzz.carRental.model.Rental;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the bookshelf
 * <p>
 * M133: Bookshelf
 *
 * @author Stefan Thomsen
 */
@Path("car")
public class CarService {

    /**
     * produces a map of all books
     *
     * @return Response response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listCars(
            @CookieParam("userRole") String userRole
    ) {
        Map<String, Car> carMap = null;
        int httpStatus;

        if (userRole == null || userRole.equals("") || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            carMap = DataHandler.getCarMap();
        }

        NewCookie cookie = new NewCookie(
                "role",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity(carMap)
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * reads a single book identified by the bookId
     *
     * @param carUUID the carUUID in the URL
     * @return Response response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readCar(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String carUUID,
            @CookieParam("userRole") String userRole
    ) {
        Car car = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            car = DataHandler.readCar(carUUID);
            if (car.getModel() != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        }

        NewCookie cookie = new NewCookie(
                "role",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        Response response = Response
                .status(httpStatus)
                .entity(car)
                .cookie(cookie)
                .build();
        return response;
    }


    /**
     * creates a new car
     *
     * @param car        a valid car
     * @param rentalUUID the uuid of the rental
     * @return Response response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createCar(
            @Valid @BeanParam Car car,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("rentalUUID") String rentalUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            car.setCarUUID(UUID.randomUUID().toString());
            Rental rental = DataHandler.readRental(rentalUUID);
            if (rental != null) {
                car.setRental(rental);
                DataHandler.insertCar(car);
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        }

        NewCookie cookie = new NewCookie(
                "role",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

    /**
     * updates an existing book
     *
     * @param car        a valid car
     * @param rentalUUID the uuid of the rental
     * @return Response response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCar(
            @Valid @BeanParam Car car,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("rentalUUID") String rentalUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;

        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            Car oldCar = DataHandler.readCar(car.getCarUUID());
            if (oldCar.getModel() != null) {
                httpStatus = 200;
                oldCar.setBrand(car.getBrand());
                oldCar.setModel(car.getModel());
                oldCar.setCategory(car.getCategory());
                oldCar.setColor(car.getColor());
                oldCar.setHorsepower(car.getHorsepower());
                oldCar.setRange(car.getRange());
                oldCar.setFuel(car.getFuel());

                Rental rental = DataHandler.readRental(rentalUUID);
                oldCar.setRental(rental);
                DataHandler.updateCar();
            } else {
                httpStatus = 404;
            }
        }
        NewCookie cookie = new NewCookie(
                "role",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

    /**
     * deletes an existing car identified by its uuid
     *
     * @param carUUID the unique key for the car
     * @return Response response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCar(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String carUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            if (DataHandler.deleteCar(carUUID)) {
                httpStatus = 200;

            } else {
                httpStatus = 404;
            }
        }

        NewCookie cookie = new NewCookie(
                "role",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

}