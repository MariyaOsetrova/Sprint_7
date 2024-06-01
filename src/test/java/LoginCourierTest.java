import Model.Courier;
import Steps.CourierSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends BeforeTest {
    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;
    private Integer id;

    @Before
    public void SetUp() {
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Курьера можно создать. Успешный ответ возвращает id")
    public void authorizatioLoginCourier() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courierSteps
                .createCourier(courier);
        id = courierSteps.loginCourier(courier)
                .then()
                .extract()
                .path("id");

        courierSteps.loginCourier(courier)
                .then()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера c неправильным логином")
    @Description("Передается неправильный логин")
    public void authorizationWithAnIncorrectLogin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courierSteps
                .createCourier(courier);
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .then()
                .statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера c неправильным паролем")
    @Description("Передается неправильный пароль")
    public void authorizationWithAnIncorrectPassword() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courierSteps
                .createCourier(courier);
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .then()
                .statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("При авторизации не вводится обязательное поле - логин")
    public void authorizationWithoutLogin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier.setLogin(null);
        courierSteps.loginCourier(courier)
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("При авторизации не вводится обязательное поле - пароль")
    public void authorizationWithoutPassword() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier.setPassword("");
        courier.setFirstName(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера c несуществующими данными")
    @Description("При авторизации вводятся несуществующие данные")
    public void authorizationWithAnIncorrectFields() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier.setFirstName(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .then()
                .statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void delCourier() {
        courier.setId(id);
        if (id != null)
            courierSteps.delCourier(courier);
    }
}
