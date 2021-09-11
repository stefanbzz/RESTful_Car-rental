package ch.bzz.carRental.service;

import ch.bzz.carRental.data.UserData;
import ch.bzz.carRental.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * provides services for the user
 * <p>
 * M133: Bookshelf
 *
 * @author Marcel Suter
 */
@Path("user")
public class UserService {

    /**
     * Testet die Person existiert und ihr Login gültig ist
     *
     * @param username der ameldenten Person
     * @param password der Person
     * @return Leerer Text und optional status 200
     */
    @Path("login")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ){
        int httpsStatus;
        User user = UserData.findUser(username, password);
        if (user == null || user.getRole() ==null ||user.getRole().equals("guest")) {
            httpsStatus = 403;
        } else {
            httpsStatus = 200;
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login_Cookie",
                600,
                false
        );

        Response response = Response
                .status(httpsStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

    /**
     * Testen das ausloggen eines Benutzer aus der Applikation
     *
     * @return leerer String mit optional Status 200
     */
    @Path("logout")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoff(){
        NewCookie cookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "logout-Cookie",
                1,
                false
        );

        Response response = Response
                .status(200)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }
}
