package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class POSTOrderBOOKReadFile {
    @BeforeClass
    public void setup() {
        //Setting baseURI here once. It will be applied to all of the test cases in this class
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
    }

    @Test(description = "Given a baseURI, token, headers and path params When The user wants to order a Book Then Verify order is success and status code is 201")
    void orderBookUsingJSONFile() throws IOException {
        //Given
        //request payload, token, path params, and other headers

        //Token
        String token = Utils.bearerToken();

        // Create request payload for orderBook call
        String orderBookRequestPayload = Utils.readFile("src/test/java/code/testData/orderBook.json");
        System.out.println("Request Payload : " + orderBookRequestPayload);
        RequestSpecification orderBookRequest = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(orderBookRequestPayload);

        //When
        Response orderBookResponse = orderBookRequest.when().post("/orders");


        //Then
        orderBookResponse.then().assertThat().statusCode(201);
        String orderId = orderBookResponse.jsonPath().getString("orderId");
        System.out.println("Response Body" + orderBookResponse.getBody().asString());


        //Retrieve My order
        Utils.retrieveMyOrder(token,"Gulay");
    }
}
