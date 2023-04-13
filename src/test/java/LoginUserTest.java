import api.client.UserClient;
import api.model.Credentials;
import api.model.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    private User user;
    private UserClient userClient;
    private String token;

    @Before
    public void setUp() {
        user = User.getRandomUser();
        userClient = new UserClient();
        userClient.createUser(user);
    }

    @Test
    @DisplayName("Test user login with validate data")
    public void loginUserWithValidDataTest() {
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.from(user));
        responseLogin.assertThat().statusCode(200)
                .and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Test user login with not validate email")
    public void loginUserWithInvalidEmailTest() {
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.getCredentialsWithWrongEmail(user));
        responseLogin.assertThat().statusCode(401)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Test user login with not validate password")
    public void loginUserWithInvalidPasswordTest() {
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.getCredentialsWithWrongPassword(user));
        responseLogin.assertThat().statusCode(401)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Test user login with not validate password and email")
    public void loginUserWithInvalidDataTest() {
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.getCredentialsWithWrongData(user));
        responseLogin.assertThat().statusCode(401)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void deleteUser() {
        token = userClient.loginUser(Credentials.from(user)).extract().path("accessToken");
        userClient.deleteUser(token);
    }
}