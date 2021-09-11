package ch.bzz.carRental.data;

import ch.bzz.carRental.model.Car;
import ch.bzz.carRental.model.Rental;
import ch.bzz.carRental.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * data handler for reading and writing the cars and rentals
 * <p>
 * M133: Car-Rental
 * <p>
 * inspired by Marcel Suter
 *
 * @author Stefan Thomsen
 */
public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Car> carMap;
    private static Map<String, Rental> rentalMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        carMap = new HashMap<>();
        rentalMap = new HashMap<>();
        Car c = new Car();
        Rental r = new Rental();

        r.setRental("Rental Volketswil");
        r.setRentalUUID("ecd86148-5341-43c6-81ed-f3f4595965dc");
        c.setRental(r);
        c.setCarUUID("3247c340-c712-402e-b400-0a23a9368c97");
        c.setBrand("Volkswagen");
        c.setModel("Golf");
        c.setCategory("Compacts");
        c.setColor("White");
        c.setHorsepower(160);
        c.setRange(1600);
        c.setFuel("Gas");
        saveRental(r);
        saveCar(c);


        Car c2 = new Car();
        Rental r2 = new Rental();

        r2.setRental("Rental Zürich");
        r2.setRentalUUID("5cc6b455-4ede-4e77-ab6a-d8e242b51595");
        c2.setRental(r);
        c2.setCarUUID("c7365af7-95be-44af-8bc5-8d35a4457d4a");
        c2.setBrand("BMW");
        c2.setModel("iX");
        c2.setCategory("SUV");
        c2.setColor("Blue");
        c2.setHorsepower(200);
        c2.setRange(605);
        c2.setFuel("Electric");
        saveRental(r2);
        saveCar(c2);
        readJSON();
    }

    /**
     * reads a single car identified by its uuid
     *
     * @param carUUID the identifier
     * @return car -object
     */
    public static Car readCar(String carUUID) {
        Car car = null;
        if (getCarMap().containsKey(carUUID)) {
            car = getCarMap().get(carUUID);
        }
        return car;
    }

    /**
     * inserts a new car into the carmap
     *
     * @param car the car to be saved
     */
    public static void insertCar(Car car) {
        getCarMap().put(car.getCarUUID(), car);
        writeJSON();
    }

    /**
     * updates the carmap
     */
    public static void updateCar() {
        writeJSON();
    }

    /**
     * removes a car from the carmap
     *
     * @param carUUID the uuid of the book to be removed
     * @return success boolean
     */
    public static boolean deleteCar(String carUUID) {
        if (getCarMap().remove(carUUID) != null) {
            writeJSON();
            return true;
        } else
            return false;
    }

    /**
     * saves a car
     *
     * @param car the car to be saved
     */
    public static void saveCar(Car car) {
        getCarMap().put(car.getCarUUID(), car);
        writeJSON();
    }


    /**
     * reads a single rental identified by its uuid
     *
     * @param rentalUUID the identifier
     * @return rental -object
     */
    public static Rental readRental(String rentalUUID) {
        Rental rental = new Rental();
        if (getRentalMap().containsKey(rentalUUID)) {
            rental = getRentalMap().get(rentalUUID);
        }
        return rental;
    }

    /**
     * Insert rental.
     *
     * @param rental the rental
     */
    public static void insertRental(Rental rental) {
        Car car = new Car();
        car.setCarUUID(UUID.randomUUID().toString());
        car.setModel("");
        car.setRental(rental);
        insertCar(car);
    }

    /**
     * Update rental boolean.
     *
     * @param rental the rental
     * @return the boolean
     */
    public static boolean updateRental(Rental rental) {
        boolean found = false;
        for (Map.Entry<String, Car> entry : getCarMap().entrySet()) {
            Car car = entry.getValue();
            if (car.getRental().getRentalUUID().equals(rental.getRentalUUID())) {
                car.setRental(rental);
                found = true;
            }
        }
        writeJSON();
        return found;
    }

    /**
     * deletes a rental, if it has no car
     *
     * @param rentalUUID the rental uuid
     * @return errorcode 0=ok, -1=referential integrity, 1=not found
     */
    public static int deleteRental(String rentalUUID) {
        int errorcode = 1;
        for (Map.Entry<String, Car> entry : getCarMap().entrySet()) {
            Car car = entry.getValue();
            if (car.getRental().getRentalUUID().equals(rentalUUID)) {
                if (car.getModel() == null || car.getModel().equals("")) {
                    deleteCar(car.getCarUUID());
                    errorcode = 0;
                } else {
                    return -1;
                }
            }
        }
        writeJSON();
        return errorcode;
    }

    /**
     * saves a rental
     *
     * @param rental the rental to be saved
     */
    public static void saveRental(Rental rental) {
        getRentalMap().put(rental.getRentalUUID(), rental);
        writeJSON();
    }

    /**
     * gets the carMap
     *
     * @return the carMap
     */
    public static Map<String, Car> getCarMap() {
        return carMap;
    }

    /**
     * gets the publisherMap
     *
     * @return the publisherMap
     */
    public static Map<String, Rental> getRentalMap() {
        return rentalMap;
    }


    /**
     * reads the cars and rentals
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("carJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Car[] cars = objectMapper.readValue(jsonData, Car[].class);
            for (Car car : cars) {
                String rentalUUID = car.getRental().getRentalUUID();
                Rental rental;
                if (getRentalMap().containsKey(rentalUUID)) {
                    rental = getRentalMap().get(rentalUUID);
                } else {
                    rental = car.getRental();
                    getRentalMap().put(rentalUUID, rental);
                }
                car.setRental(rental);
                getCarMap().put(car.getCarUUID(), car);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the cars and rentals
     *
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String carPath = Config.getProperty("carJSON");
        try {
            fileOutputStream = new FileOutputStream(carPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getCarMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
