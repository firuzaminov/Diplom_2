import api.client.UserClient;
import api.model.Credentials;
import api.model.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    private UserClient userClient;
    private User user;
    private String token;
    private int statusCode;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandomUser();
    }

    @Test
    @DisplayName("Test create new user")
    public void createUserTest() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        responseCreate.assertThat().statusCode(200)
                .and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Test create existing user")
    public void createExistingUserTest() {
        userClient.createUser(user);
        ValidatableResponse responseCreate = userClient.createUser(user);
        responseCreate.assertThat().statusCode(403)
                .and().body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Test create user without email")
    public void createUserWithoutEmailTest() {
        user = User.getUserWithoutEmail();
        ValidatableResponse responseCreate = userClient.createUser(user);
        responseCreate.assertThat().statusCode(403)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Test create user without password")
    public void createUserWithoutPasswordTest() {
        user = User.getUserWithoutPassword();
        ValidatableResponse responseCreate = userClient.createUser(user);
        responseCreate.assertThat().statusCode(403)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Test create user without name")
    public void createUserWithoutNameTest() {
        user = User.getUserWithoutName();
        ValidatableResponse responseCreate = userClient.createUser(user);
        responseCreate.assertThat().statusCode(403)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void deleteUser() {
        statusCode = userClient.loginUser(Credentials.from(user)).extract().statusCode();
        if(statusCode == 200) {
            token = userClient.loginUser(Credentials.from(user)).extract().path("accessToken");
            userClient.deleteUser(token);
        }
    }
}