package code;

public class serializationAndDeserialization {
    // Specific to rest assured. It is related to programming language - java
    // What is serialization and Deserialization
    // S is conversion of state of a Java object byte stream, BUT Deserialization is the reverse flow
    //POJO - Plain Old Java Object
    //Converting a POJO object to a JSON object >>> Serialization
    //Converting a JSON Object to a POJO object >>> Deserialization
    // Jackson, Gson etc.

    //bookId and customerName

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private String bookId;
    private String customerName;
    }
