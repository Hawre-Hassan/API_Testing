package code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Serialization {
    public static void main(String[] args) throws JsonProcessingException {
        //1.Create an object of POJO class and declare required variables
        //Create object of pojo class and set values

        serializationAndDeserialization serializationExample=new serializationAndDeserialization();

        //Set Values of properties of pojo class
        serializationExample.setBookId("1");
        serializationExample.setCustomerName("Yaseen");

        //ObjectMAPPER class to serialize POJO object to JSON
        //Create an object of ObjectMAPPER class provided by Jackson dependancy
        ObjectMapper objectMapper= new ObjectMapper();

        String jsonPayload= objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serializationExample);

        System.out.println("JSON Object is;");
        System.out.println(jsonPayload);


//        JSON Object is;
//        {
//            "bookId" : "1",
//                "customerName" : "Yaseen"
//        }
    }
}

