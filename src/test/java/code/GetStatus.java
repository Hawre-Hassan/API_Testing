package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetStatus {
    @Test
    public void happyPathTest() {
        Response response = RestAssured.get("https://simple-books-api.glitch.me/status");
        System.out.println("response :" + response.asString());
        System.out.println("Status Code:" + response.getStatusCode());
        System.out.println("Taken Time to call:" + response.getTime());
        System.out.println("Content-Type:" + response.getHeader("Content-Type"));
        System.out.println("Content-Length:" + response.getHeader("Content-Length"));
        System.out.println("Date:" + response.getHeader("Date"));
        System.out.println("Test:" + response.getHeader("Test"));
    }

        @Test(description = "Given BaseUrl we make the get call to/Status Then verify status code is 200")
                public void  ValidateStatusCode(){
        //Given

            //When
            Response response=RestAssured.get("https://simple-books-api.glitch.me/status");
            int actualStatusCode= response.getStatusCode();
            int expectedStatusCode=404;

            System.out.println("Actual Status Code:"+actualStatusCode);
            System.out.println("Expected Status Code:"+expectedStatusCode);
            //Then
            Assert.assertEquals(actualStatusCode,expectedStatusCode,"status code should be 200 but is:"+actualStatusCode);
    }


@Test(description = "Given BaseUrl when we make get call to status/endpoint this is to verify time of response is 2000ms")
    public void ValidateTime(){

        //Given and When
        Response response=RestAssured.get("https://simple-books-api.glitch.me/status");
        long actualResponseTime=response.getTime();
        int expectedResponseTime=2000;

        //Then
        Assert.assertEquals(actualResponseTime,expectedResponseTime,"response time should be 2000s bu it is");



    }
}
