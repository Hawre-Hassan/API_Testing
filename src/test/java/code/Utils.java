package code;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Utils {
    public static String bookId() {
        Response response = get("/books");
        response.then().assertThat().statusCode(200);
        String bookId = response.jsonPath().getString("[0].id");
        return bookId;
    }

    public static String bearerToken() {

        Faker faker = new Faker();

        String clientName = faker.name().fullName();
        String clientEmail = faker.internet().emailAddress();

        JSONObject object = new JSONObject();

        object.put("clientName", clientName);
        object.put("clientEmail", clientEmail);

        String payload = object.toString();

        RequestSpecification generateTokenRequest = given()
                .header("Content-Type", "application/json")
                .body(payload);
        Response generateTokenResponse = generateTokenRequest.when().post("/api-clients");

        generateTokenResponse.then().assertThat().statusCode(201);

        return "Bearer " + generateTokenResponse.jsonPath().getString("accessToken");
    }

    public static String readFile(String path) throws IOException { // src/test/java/code/orderBook.json
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static void retrieveMyOrder(String authorizationToken, String customerName) {
        RequestSpecification listOfOrdersRequest = given()
                .header("Authorization", authorizationToken);

        Response listOfOrdersResponse = listOfOrdersRequest.when().get("/orders");
        listOfOrdersResponse.then().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualNewCustomerName = listOfOrdersResponse.jsonPath().getString("customerName");

        Assert.assertTrue(actualNewCustomerName.contains(customerName));
    }
}

