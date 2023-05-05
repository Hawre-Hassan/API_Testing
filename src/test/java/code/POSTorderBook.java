package code;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTorderBook {
    @BeforeClass
    public void setup(){
        //Setting baseURI here once. It will be applied to all of the test cases in this class
        RestAssured.baseURI="https://simple-books-api.glitch.me";
    }

        @Test(description = "Given a baseURI and token When user wants to order book Then Verify status code is 201 ")
        void orderBook(){
            //First Call - Order Book
            //Needed information : request payload, token, endpoint and content-type

            //Given

            //Token
            String token=Utils.bearerToken();

            //Payload
            Faker faker=new Faker();
            String customerName= faker.name().fullName();
            String bookId=Utils.bookId();

            JSONObject object= new JSONObject();
            object.put("bookId",bookId);
            object.put("customerName",customerName);

            String requestPayload=object.toString();

            //Provide neccessary information

            RequestSpecification orderBookRequest= given()
                    .header("Content-Type","application/json")
                    .header("Authorization",token)
                    .body(requestPayload);

            //When

            Response orderBookResponse=orderBookRequest.when().post("/orders");

            //Then
            orderBookResponse.then().assertThat().statusCode(201);
            System.out.println("Response Payload for OrderBook:"+orderBookResponse.getBody().asString());

            String orderId=orderBookResponse.jsonPath().getString("orderId");

            //RETRIEVE ORDER(S)
            // Make Second Call : List of The Ordered Book(s)

            //Given
            RequestSpecification listOfOrdersRequest=given()
                    .header("Authorization",token);

            //When
            Response listOfOrdersResponse=listOfOrdersRequest.when().get("/orders");

            //Then
            listOfOrdersResponse.then().assertThat().statusCode(200);

            System.out.println(listOfOrdersResponse.getBody().asString());

            String actualCustomerName=listOfOrdersResponse.jsonPath().getString("customerName");
            Assert.assertTrue(actualCustomerName.contains(customerName));

            //UPDATE ORDER

            // Token, conten-type, path param, request body(customerName)

            String newCustomerName="Jason";

            JSONObject objectNewName=new JSONObject();
            objectNewName.put("customerName",newCustomerName);
            String newNameRequestPayload=objectNewName.toString();

            //Given
            RequestSpecification updateOrderRequest= given()
                    .pathParam("orderId",orderId)
                    .header("Content-Type","application/json")
                    .header("Authorization",token)
                    .body(newNameRequestPayload);

            //When
            Response updateOrderResponse= updateOrderRequest.when().patch("/orders/{orderId}");

            updateOrderResponse.then().assertThat().statusCode(204);

            //List of the Ordered Book(s)

            listOfOrdersRequest=given()
                    .header("Authorization",token);

            listOfOrdersResponse=listOfOrdersRequest.when().get("/orders");
            listOfOrdersResponse.then().statusCode(200);

            System.out.println(listOfOrdersResponse.getBody().asString());

            String actualNewCustomerName=listOfOrdersResponse.jsonPath().getString("customerName");

            Assert.assertTrue(actualNewCustomerName.contains(newCustomerName));

            //DELETE ORDER

            //Given
            // Token, path params, Content-Type, Delete(HTTP), Body

            RequestSpecification deleteOrderRequest=given()
                    .pathParam("orderId",orderId)
                    .header("Content-Type","application/json")
                    .header("Authorization",token)
                    .body("{}");

            //When
            Response deleteOrderResponse=deleteOrderRequest.when().delete("/orders/{orderId}");

            //Then
            deleteOrderResponse.then().assertThat().statusCode(204);


            //List the Ordered Book(s) - Verify if they got deleted

            listOfOrdersRequest=given()
                    .header("Authorization", token);

            listOfOrdersResponse=listOfOrdersRequest.when().get("/orders");

            listOfOrdersResponse.then().statusCode(200);


            System.out.println(listOfOrdersResponse.getBody().asString());
            String listOfOrdersResponseBody=listOfOrdersResponse.getBody().asString();
            Assert.assertTrue(!listOfOrdersResponseBody.contains(orderId));

        }

}