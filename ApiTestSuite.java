package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class ApiTestSuite {

    String baseUrl = "https://automationexercise.com";

    @Test
    public void getProductList() {
        RestAssured
            .given()
                .baseUri(baseUrl)
            .when()
                .get("/api/productsList")
            .then()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("products", not(empty()));
    }

    @Test
    public void createUserAccount() {
        String payload = "{"
                + "\"name\":\"John\","
                + "\"email\":\"john@example.com\","
                + "\"password\":\"123456\""
                + "}";

        RestAssured
            .given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/createAccount")
            .then()
                .statusCode(anyOf(is(200), is(201)));
    }

    @Test
    public void userLogin() {
        String payload = "{"
                + "\"email\":\"john@example.com\","
                + "\"password\":\"123456\""
                + "}";

        RestAssured
            .given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/login")
            .then()
                .statusCode(200)
                .body(containsString("token"));
    }

    @Test
    public void searchProduct() {
        String payload = "{"
                + "\"search_product\":\"top\""
                + "}";

        RestAssured
            .given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/searchProduct")
            .then()
                .statusCode(200)
                .body(containsString("top"));
    }

    @Test
    public void invalidLogin() {
        String payload = "{"
                + "\"email\":\"wrong@example.com\","
                + "\"password\":\"wrongpass\""
                + "}";

        RestAssured
            .given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/login")
            .then()
                .statusCode(400)
                .body(containsString("error"));
    }
}
