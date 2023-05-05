package code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTOrderBookPOJO {
    @BeforeClass
    public void setup(){
        //Setting baseURI here once. It will be applied to all of the test cases in this class
        RestAssured.baseURI="https://simple-books-api.glitch.me";
    }

    //This is how we made API calls without using POJO classes
    @Test(description = "Given a baseURI and token When user wants to order book Then Verify status code is 201 ")
    void orderBook() {
        //First Call - Order Book
        //Needed information : request payload, token, endpoint and content-type

        //Given

        //Token
        String token =Utils.bearerToken();

        //Payload
        Faker faker = new Faker();
        String customerName = faker.name().fullName();
        String bookId = Utils.bookId();

        //
        JSONObject object = new JSONObject();
        object.put("bookId", bookId);
        object.put("customerName", customerName);

        String requestPayload = object.toString();

        //Provide necessary information

        RequestSpecification orderBookRequest = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(requestPayload);

        //When

        Response orderBookResponse = orderBookRequest.when().post("/orders");

        //Then
        orderBookResponse.then().assertThat().statusCode(201);
        System.out.println("Response Payload for OrderBook:" + orderBookResponse.getBody().asString());

        String orderId = orderBookResponse.jsonPath().getString("orderId");
    }
    @Test(description = "Given a baseURI and Authorization token and headers When the user wants to Order a book Then Verify if status code is 201")
    void orderBookPOJO() throws JsonProcessingException {
        //Given
        // request payload, token, endpoint, any required headers(Content-Type)

        //Token
        String authorizationToken=Utils.bearerToken();

        //Request payload - bookId, customerName
        Faker faker=new Faker();
        String customerName=faker.name().fullName();
        String bookId=Utils.bookId();

        //Create pojo class, set value as you wish
        orderBookPOJO requestBody=new orderBookPOJO(bookId,customerName);

        //Converting a JAVA class object to JSON payload as string(Serialization)
        ObjectMapper objectMapper=new ObjectMapper();
        String orderJSONPayload=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody);


        RequestSpecification orderBookRequest=given()
                .header("Content-Type","application/json")
                .header("Authorization",authorizationToken)
                .body(orderJSONPayload);


        //When
        Response orderBookResponsePayload=orderBookRequest.when().post("/orders");

        //Then
        orderBookResponsePayload.then().assertThat().statusCode(201);

        String orderId=orderBookResponsePayload.jsonPath().getString("orderId");
        System.out.println(orderBookResponsePayload.getBody().asString());


        //UPDATE ORDER

        //Create a new customer name
        String newName="Alican";

        orderBookPOJO orderBookPOJO=new orderBookPOJO("1",newName);

        ObjectMapper objectMapper1=new ObjectMapper();
        String updatedOrderJSON=objectMapper1.writerWithDefaultPrettyPrinter().writeValueAsString(orderBookPOJO);

        RequestSpecification updateBookRequest=given()
                .header("Content-Type","application/json")
                .header("Authorization",authorizationToken)
                .pathParam("orderId",orderId)
                .body(updatedOrderJSON);

        Response updateOrderResponse=updateBookRequest.when().patch("/orders/{orderId}");

        updateOrderResponse.then().assertThat().statusCode(204);

        //List of the Ordered Book(s)

        RequestSpecification listOfOrdersRequest=given()
                .header("Authorization",authorizationToken);

        Response listOfOrdersResponse=listOfOrdersRequest.when().get("/orders");
        listOfOrdersResponse.then().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualNewCustomerName=listOfOrdersResponse.jsonPath().getString("customerName");

        Assert.assertTrue(actualNewCustomerName.contains(newName));


    }



}

