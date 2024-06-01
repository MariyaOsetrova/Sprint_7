import Steps.OrderSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest extends BeforeTest {
    private OrderSteps order;

    @Test
    @DisplayName("Список заказов")
    @Description("В теле ответа содержится список заказов")
    public void getListOrders() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        order = new OrderSteps();
        order.getOrder()
                .then()
                .statusCode(200)
                .and()
                .body("orders", notNullValue());
    }

}
