package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import sun.awt.image.GifImageDecoder;

import javax.print.attribute.standard.RequestingUserName;

import static io.restassured.RestAssured.given;

public class GETbooks {
    //This is the first way to provide baseURI
    //String url="https://simple-books-api.glitch.me/books";

   //This is the second way to provide Base URI
   // String baseURI=RestAssured.baseURI="https://simple-books-api.glitch.me";

    @BeforeClass
    public void setup(){
        //Setting baseURI here once. It will be applied to all of the test cases in this class
        RestAssured.baseURI="https://simple-books-api.glitch.me";
    }

    @Test(description = "Given a baseURI when we make GET CALL with books then verify status code")
    public void ValidateStatusCode(){
        Response response= RestAssured.get("/books");
        int actualStatusCode= response.statusCode();
        int expectedStatusCode=200;

        System.out.println(response.getBody().asString());
        Assert.assertEquals(actualStatusCode,expectedStatusCode,"Expected status Code is 200 but it is"+actualStatusCode);
    }
    @Test(description = "Given a baseURI when we make GET CALL to/books and use query param limit=2 Then Verify ststus code is 200")
void userRetrieveListOfTheBookslimit2(){
        //Given
        //we are providing a query param called "limit" using request specification class
        RequestSpecification request=given().queryParam("limit",2);
        //When
        //we make a call using variable above we created to have query parameters
        Response response=request.when().get("/books");

        //Then
        response.then().assertThat().statusCode(200);
        //print response body
        System.out.println(response.getBody().asString());
String secondBookName=response.jsonPath().getString("[1].name");
String secondBookID=response.jsonPath().getString("[1].id");
String expectedSecondBookName="Just as I Am";
String expectedSecondBookID="2";
        System.out.println("Second book's ID and Name is: "+ secondBookName+secondBookID);

        Assert.assertEquals(expectedSecondBookID,secondBookID);
        Assert.assertTrue(expectedSecondBookName.contains(secondBookName));

        //we can use this line as wee to perform same action as above
       // given().queryParam("limit",2).when().get("https://simple-books-api.glitch.me/books").
        //        then().assertThat().statusCode(200);
    }

    @Test(description = "Given baseURI when we make get Call to/books and query parameters as a type=ficition and limit=1")
 void userRetrieveListOfTheBooksTypeFiction(){
        //Given
        RequestSpecification requestBody=given().queryParams("type","fiction","limit",1);
        //When
        Response response=requestBody.when().get("/books");
        //Then
        response.then().assertThat().statusCode(200);
        System.out.println(response.getBody().asString());

        String type=response.jsonPath().getString("[0].type");
        System.out.println(type);
        Assert.assertEquals(type,"fiction");

       String bookId=response.jsonPath().getString("[0].id");
        Assert.assertEquals(bookId,"1");

        String isAvailable=response.jsonPath().getString("[0].available");
        Assert.assertEquals(isAvailable,"true");

        String bookName=response.jsonPath().getString("[0].name");
        Assert.assertEquals(bookName,"The Russian");
    }

    @Test(description = "Given baseURI when we make get call to/books and type crime=crime(doesn't exist) Then verify status code is 400")
void userRetrieveListOfBooksNotExistingType() {
//Given
        RequestSpecification requestSpecification = given().queryParam("type", "crime");
        //When
        Response response = requestSpecification.when().get("/books");

        //Then
        response.then().assertThat().statusCode(400);
    }

    @Test(description = "Given a baseURI when we make GET CALL to/books and use query param, limit=220(out of limit) Then verify status code is 400")
void userRetrieveListOfTheBooksOutOfLimit(){
        //Given
        RequestSpecification requestSpecification=given().queryParam("limit","220");
        //When
        Response response=requestSpecification.when().get("/books");
        //Then
        response.then().assertThat().statusCode(400);

    }
    @Test(description = "Given a baseURI we make GET CAll to/books/booksID Then verify status code is 200 and get book information")
    void userRetrieveOnSingleBookInfo(){
        String bookId="1";
        String bookIdFromUtils=Utils.bookId();
        //Given
        RequestSpecification request=given().pathParam("bookId",bookId);
        //When
        Response response=request.when().get("/books/{bookId}");
        //Then
        response.then().statusCode(200);
        System.out.println(response.getBody().asString());


    }
}
