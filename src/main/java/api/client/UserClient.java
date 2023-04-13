package api.client;
import api.model.Credentials;
import api.model.User;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    @Step("Create new user")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(USER_REGISTER)
                .then();
    }

    @Step("Login user")
    public ValidatableResponse loginUser(Credentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(USER_LOGIN)
                .then().log().all();
    }

    @Step("Update user data")
    public ValidatableResponse updateUserData(User user, String token) {
        return given()
                .header("Authorization", token)
                .spec(getSpec())
                .body(user)
                .when()
                .patch(USER_AUTH)
                .then().log().all();
    }

    @Step("Update user information without authentication")
    public ValidatableResponse updateUserDataWithoutAuth(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .patch(USER_AUTH)
                .then().log().all();
    }

    @Step("Deleting user")
    public ValidatableResponse deleteUser(String token) {
        return given()
                .header("Authorization", token)
                .spec(getSpec())
                .when()
                .delete(USER_AUTH)
                .then();
    }
}