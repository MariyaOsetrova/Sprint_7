package Steps;

import Model.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static Utils.EndPoints.*;
import static io.restassured.RestAssured.given;


public class CourierSteps {
    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER);

    }

    @Step("Авторизация курьера")
    public Response loginCourier(Courier courier) {
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN);
    }

    @Step("Удаление курьера по id")
    public Response delCourier(Courier courier) {
        return given()
                .header("Content-Type", "application/json")
                .pathParam("id", courier.getId())
                .when()
                .delete(COURIER_DEL);

    }

}