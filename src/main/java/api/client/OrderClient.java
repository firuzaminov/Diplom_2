package api.client;
import api.model.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    @Step("Order create")
    public ValidatableResponse createOrder(Order order, String token) {
        return given()
                .spec(getSpec())
                .header("Authorization", token)
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Create order without authentication")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Get users order")
    public ValidatableResponse getUserOrders(String token) {
        return given()
                .spec(getSpec())
                .header("Authorization", token)
                .when()
                .get(ORDERS)
                .then().log().all();
    }

    @Step("Get user order without authentication")
    public ValidatableResponse getUserOrdersWithoutAuth() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDERS)
                .then().log().all();
    }
}