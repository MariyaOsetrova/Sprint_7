import Model.Order;
import Steps.OrderSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BeforeTest {
    private OrderSteps orderSteps = new OrderSteps();
    private Order order;
    private String track;
    private List<String> colour;

    @Parameterized.Parameters
    public static Object[][] date() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("GREY", "BLACK")},
                {List.of()},
        };
    }

    public CreateOrderTest(List<String> colour) {
        this.colour = colour;
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа в зависимости от цвета")
    public void createOrderOfColour() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        order = new Order();
        order.setFirstName("Иван");
        order.setLastName("Иванов");
        order.setAddress("Урицкого д.9 кв. 9");
        order.setMetroStation("Черкизовская");
        order.setPhone("89999999999");
        order.setRentTime(5);
        order.setDeliveryDate("05.06.2024");
        order.setComment("Хочу доставку");
        order.setColor(colour);

        Response response = orderSteps.createOrder(order);
        track = response
                .then()
                .extract()
                .path("track").toString();
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());

    }

    @After
    public void delOrder() {
        orderSteps.cancelOrder(track);
    }
}
