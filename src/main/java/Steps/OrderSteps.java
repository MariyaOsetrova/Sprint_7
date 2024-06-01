package Steps;

import Model.Order;
import Utils.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static Utils.EndPoints.CANCEL_ORDER;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(order)
                .when()
                .post(EndPoints.LIST_ORDERS);

    }

    @Step("Получение списка заказов")
    public Response getOrder() {
        return given()
                .header("Content-Type", "application/json")
                .get(EndPoints.LIST_ORDERS);

    }

    @Step("Отмена заказа")
    public Response cancelOrder(String track) {
        return given()
                .header("Content-Type", "application/json")
                .put(CANCEL_ORDER + "{track}", track);

    }
}
