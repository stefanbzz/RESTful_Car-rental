/**
 * view-controller for login.html
 *
 *Javascript datei für das Login/out
 *
 * @author  Stefan Thomsen
 * @since 31.3.2021
 * @version 0.1
 *
 * inspiriert: Ghwalin
 */

$(document).ready(function () {

    //sendet formparam zum login service
    $("#login").submit(sendLogin);
    /**
     * listener for button [Abmelden]
     */
    $("#logout").click(sendLogout);

});

/**
 * sendet und üperprüft eine gültige Login request
 *
 * @param form param mit Username + password
 */
function sendLogin(form) {
    form.preventDefault();
    $
        .ajax({
            url: "./resource/user/login",
            type: "POST",
            data: $("#login").serialize()
        })
        .done(function (){
            alert("Erfolgreich eingeloggt!");
            window.location.href ="./carrental.html"
        })
        .fail(function (xhr,status,errorThrown){
            if(xhr.status ==404) {
                $("#message").text("Benutzername/Passwort unbekannt");
            }else if(xhr.status=403){
                $("#message").text("Sie sind nicht autorisiert!")
            } else {
                $("#message").text("Es ist ein Fehler aufgetreten");
            }
        })
}

/**
 * meldet den benutzer mittes request zum Userservice
 * ab
 */
function sendLogout() {
    $
        .ajax({
            url: "./resource/user/logout",
            dataType:"text",
            type: "DELETE",
        })
        .done(function (){
            window.location.href ="./login.html"
        })
        .fail(function (xhr,status,errorThrown){

        })
}
