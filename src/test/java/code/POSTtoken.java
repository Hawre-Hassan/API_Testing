package code;

import com.github.javafaker.Faker;
import com.sun.javafx.binding.StringFormatter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTtoken {
    //String baseURI= RestAssured.baseURI="https://simple-books-api.glitch.me";


    @BeforeClass
    public void setup(){
        //Setting baseURI here once. It will be applied to all of the test cases in this class
        RestAssured.baseURI="https://simple-books-api.glitch.me";
    }
    @Test(description = "Given baseURI when we make POST call to api/clients Then verify if access token is available")
void bookId(){
//Needed information : request payload, token, endpoint, content-type
        System.out.println(Utils.bearerToken());

}}
