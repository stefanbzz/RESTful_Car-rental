/**
 * view-controller for caredit.html
 *
 * M133: Car-rental
 *
 * @author  Stefan Thomsen
 */

/**
 * register listeners and load the car data
 */
$(document).ready(function () {

    loadRentals();
    loadCar();

    /**
     * listener for submitting the form
     */
    $("#careditForm").submit(saveCar);


});

/**
 *  loads the data of this car
 *
 */
function loadCar() {
    let carUUID = $.urlParam("carUUID");
    if (carUUID != null) {
        $
            .ajax({
                url: "./resource/car/read?uuid=" + carUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showCar)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 403) {
                    window.location.href = "./login.html";
                } else if (xhr.status == 404) {
                    $("#message").text("Kein Auto gefunden");
                } else {
                    window.location.href = "./carrental.html";
                }
            })
    }

}

/**
 * shows the data of this car
 * @param  car  the car data to be shown
 */
function showCar(car) {
    $("#carUUID").val(car.carUUID);
    $("#brand").val(car.brand);
    $("#model").val(car.model);
    $("#rental").val(car.rental.rentalUUID);
    $("#category").val(car.category);
    $("#color").val(car.color);
    $("#horsepower").val(car.horsepower);
    $("#range").val(car.range);
    $("#fuel").val(car.fuel);

}

/**
 * sends the car data to the webservice
 * @param form the form being submitted
 */
function saveCar(form) {
    let url = "./resource/car/";

    let carUUID =$("#carUUID").val();

    if(carUUID){
        url +="update";
    }else {
        url += "create";
    }
    form.preventDefault();
    $
        .ajax({
            url: url,
            dataType: "text",
            type: "POST",
            data: $("#careditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./carrental.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("This car does not exist");
            } else {
                $("#message").text("Error when saving the car");
            }
        })
}

function loadRentals() {
    $
        .ajax({
            url: "./resource/rental/list",
            dataType: "json",
            type: "GET"
        })
        .done(showRentals)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("No rentals found");
            } else {
                window.location.href = "./carrental.html";
            }
        })
}

function showRentals(rentals) {

    $.each(rentals, function (uuid, rental) {
        $('#rental').append($('<option>', {
            value: rental.rentalUUID,
            text: rental.rental
        }));
    });
}

/**
 *schaltet die Funktion der eingabe von Eingaben aus
 *kommt auf die Benutzerrolle an
 */
function inputDisabled(){
    let cookie = wertAusCookie();
    if(cookie=="guest") {
        document.getElementById("model").readOnly = true;
        document.getElementById("garage").setAttribute("disabled", "disabled");
        document.getElementById("plaetze").readOnly = true;
        document.getElementById("motorZylinder").readOnly = true;
        document.getElementById("preis").readOnly = true;
        document.getElementById("speichern").disabled = true;
        document.getElementById("message").innerText = "Nicht autorisiert um zu adden";
    }
}
