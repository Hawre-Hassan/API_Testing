package code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;


public class Deserialization {

    public static void main(String[] args) throws JsonProcessingException {


    //We are going to Deserialize JSON object below
//            "bookId" : "1",
//                "customerName" : "Yaseen"}

    String jsonObject = "{\n" + "\"bookId\" : \"1\",\n" + "\"customerName\" : \"Yaseen\"\n" + "}";

    //create an object of ObjectMapper class
    ObjectMapper objectMapper = new ObjectMapper();

    //Deserialize Json object to POJO
    serializationAndDeserialization deserialization = objectMapper.readValue(jsonObject, serializationAndDeserialization.class);

    //once we get deserialization object we can use to get all available variables in that POJO class.
        System.out.println("bookId; " +deserialization.getBookId());
        System.out.println("customerName:" +deserialization.getCustomerName());


        //Deserialize JSON object to Map Object
        Map<String, Object> orderAsMap=objectMapper.readValue(jsonObject,Map.class);
        System.out.println(orderAsMap);

        //Once we get map Object we can use keys to fetch values
        System.out.println("bookId is: "+orderAsMap.get("bookId"));
        System.out.println("customerName is: "+orderAsMap.get("customerName"));


    }
}

