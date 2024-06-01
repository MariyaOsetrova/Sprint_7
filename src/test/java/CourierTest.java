import Model.Courier;
import Steps.CourierSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CourierTest extends BeforeTest {
    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;
    //  private String id;

    @Before
    public void SetUp() {
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("Создание курьера со всеми  полями")
    @Description("Курьера можно создать. Успешный ответ")
    public void createCourier() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier.setFirstName(RandomStringUtils.randomAlphabetic(10));
        Response response = courierSteps
                .createCourier(courier);
        courierSteps.loginCourier(courier).then().extract().path("id").toString();
        response.then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Ошибка при создании двух одинаковых курьера")
    @Description("Нельзя создать двух одинаковых курьера")
    public void createDoubleCourier() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courierSteps.createCourier(courier);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера со всеми обязательными полями полями")
    @Description("Для создания курьера нужно заполнить обязательные поля")
    public void createRequiredFieldsCourier() {
        courierSteps
                .createCourier(courier)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Создание курьера возвращает ответ ok: true")
    @Description("В ответе возвращается ok: true")
    public void createCourierTrue() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courierSteps
                .createCourier(courier)
                .then()
                .assertThat().body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля: логин")
    @Description("Проверка ошибки при отсутсвтии логина при создании")
    public void createCourierWithoutLogin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier.setLogin(null);
        courierSteps
                .createCourier(courier)
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля: пароль")
    @Description("Проверка ошибки при отсутствии пароля при создании")
    public void createCourierWithoutPassword() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier.setPassword("");
        courierSteps
                .createCourier(courier)
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Проверка ошибки при создании с существующим логином курьера")
    public void createCourierDoubleLogin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier.setLogin("LoginOne6");
        courier.setPassword("123456789");
        courierSteps
                .createCourier(courier)
                .then()
                .assertThat().body("ok", is(true));
        courier.setLogin("LoginOne6");
        courier.setPassword("123456789");
        courierSteps
                .createCourier(courier)
                .then()
                .statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @After
    public void delCourier() {
        Integer id = courierSteps
                .loginCourier(courier)
                .then()
                .extract()
                .body()
                .path("id");
        courier.setId(id);
        if (id != null)
            courierSteps.delCourier(courier);
    }
}


