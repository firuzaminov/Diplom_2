import api.client.OrderClient;
import api.client.UserClient;
import api.model.Order;
import api.model.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest {
    private User user;
    private UserClient userClient;
    private ValidatableResponse responseUserCreate;
    private OrderClient orderClient;
    private String token;

    @Before
    public void setUp() {
        user = User.getRandomUser();
        userClient = new UserClient();
        responseUserCreate = userClient.createUser(user);
        token = responseUserCreate.extract().path("accessToken");
    }

    @Test
    @DisplayName("Test create order with authorisation and ingredients")
    public void createOrderWithAuthTest() {
        orderClient = new OrderClient();
        ValidatableResponse responseOrderCreate = orderClient.createOrder(Order.getCorrectOrder("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"), token);
        responseOrderCreate.assertThat().statusCode(200)
                .and().body("success", equalTo(true))
                .and().body("name", notNullValue())
                .and().body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Test create order without authorisation")
    public void createOrderWithoutAuthTest() {
        orderClient = new OrderClient();
        ValidatableResponse responseOrderCreate = orderClient.createOrderWithoutAuth(Order.getCorrectOrder("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        responseOrderCreate.assertThat().statusCode(401)
                .and().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Test create order without ingredients")
    public void createOrderWithoutIngredientsTest() {
        orderClient = new OrderClient();
        ValidatableResponse responseOrderCreate = orderClient.createOrder(Order.getEmptyOrder(), token);
        responseOrderCreate.assertThat().statusCode(400)
                .and().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Test create order with wrong ingredients hash")
    public void createOrderWithWrongHashTest() {
        orderClient = new OrderClient();
        ValidatableResponse responseOrderCreate = orderClient.createOrder(Order.getOrderWithWrongHash("32,.1433154gfg","6fgdfdg22&1.,&?"), token);
        responseOrderCreate.assertThat().statusCode(500);
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(token);
    }
}