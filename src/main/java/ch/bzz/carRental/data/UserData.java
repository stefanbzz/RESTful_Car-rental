package ch.bzz.carRental.data;

import ch.bzz.carRental.model.User;
import ch.bzz.carRental.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * data handler für das lesen/finden von Benutzer aus JSON files
 *
 * @author Stefan Thomsen
 * @since 31.3.2021
 * @version 1.0
 *
 * inspiriert durch Marcel Suter (Ghwalin)
 */

public class UserData {
    private static final UserData instance = new UserData();

    /**
     * findet den User anhand des Benutzernamens und des passwortes
     * @param username des Users
     * @param password des Users
     * @return User object / null=not found
     */
    public static User findUser(String username, String password) {
        User user = new User();
        List<User> userList = readJson();

        for (int i = 0; i <userList.size() ; i++) {
            if(userList.get(i).getUsername().equals(username) &&
                    userList.get(i).getPassword().equals(password))
                user = userList.get(i);
        }

        return user;
    }

    /**
     * list die JSON daten aus der Datei
     * @return die Liste mit den daten
     */
    private static List<User> readJson() {
        List<User> userListe = new ArrayList<>();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("userJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            User[] users = objectMapper.readValue(jsonData, User[].class);
            for (User user : users) {
                userListe.add(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userListe;
    }
}