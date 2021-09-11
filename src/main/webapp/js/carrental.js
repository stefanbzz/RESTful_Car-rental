/**
 * view-controller for carrental.html
 *
 * M133: Car-rental
 *
 * @author  Stefan Thomsen
 */

/**
 * register listeners and load all cars
 */
$(document).ready(function () {
    loadCars();

    /**
     * listener for buttons within shelfForm
     */
    $("#shelfForm").on("click", "button", function () {
        if (confirm("Wollen Sie das Auto wirklich löschen?")) {
            deleteCar(this.value);
        }
    });



});

function loadCars() {
    $
        .ajax({
            url: "http://localhost:8080/Car-rental-0.1/resource/car/list",
            dataType: "json",
            type: "GET"
        })
        .done(showCars)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href = "./login.html";
            } else if (xhr.status == 404) {
                $("#message").text("keine Autos vorhanden");
            }else {
                $("#message").text("Fehler beim Lesen der Autos");
            }
        })

}

/**
 * shows all cars as a table
 *
 * @param carData all cars as an array
 */
function showCars(carData) {

    let table = document.getElementById("carrental");
    clearTable(table);

    $.each(carData, function (uuid, car) {
        if (car.model) {
            let row = table.insertRow(-1);
            let cell = row.insertCell(-1);
            cell.innerHTML = car.model;

            cell = row.insertCell(-1);
            cell.innerHTML = car.brand;

            cell = row.insertCell(-1);
            cell.innerHTML = car.rental.rental;

            cell = row.insertCell(-1);
            cell.innerHTML = car.category;

            cell = row.insertCell(-1);
            cell.innerHTML = car.color;

            cell = row.insertCell(-1);
            cell.innerHTML = car.horsepower;

            cell = row.insertCell(-1);
            cell.innerHTML = car.range;

            cell = row.insertCell(-1);
            cell.innerHTML = car.fuel;

            cell = row.insertCell(-1);
            cell.innerHTML = "<a href='./caredit.html?uuid=" + uuid + "'>Edit</a>";

            cell = row.insertCell(-1);
            cell.innerHTML = "<button type='button' id='delete_" + uuid + "' value='" + uuid + "'>Delete</button>";


        }
    });
}

function clearTable(table) {
    while (table.hasChildNodes()) {
        table.removeChild(table.firstChild);
    }
}

/**
 * send delete request for a car
 * @param carUUID
 */
function deleteCar(carUUID) {
    $
        .ajax({
            url: "./resource/car/delete?uuid=" + carUUID,
            dataType: "text",
            type: "DELETE",
        })
        .done(function (data) {
            loadCars();
            $("#message").text("Car deleted");

        })
        .fail(function (xhr, status, errorThrown) {
            $("#message").text("Error: Can't delete car");
        })
}