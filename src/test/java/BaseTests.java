import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class BaseTests {

    @Test
    void newTest() {
        int a = 2;
        int b = 3;
        int sum = a + b;
        System.out.println(sum);
    }

//    @BeforeAll
//    public static void setup() {
//        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
//    }
//
//    @Test
//    public void getRequest() {
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/posts")
//                .then()
//                .extract().response();
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals("qui est esse", response.jsonPath().getString("title[1]"));
//    }

    @Test
    void simpleGetTest() {
        Response response = given().baseUri("https://superhero.qa-test.csssr.com").contentType(ContentType.JSON)
                .when().get("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.asPrettyString());
    }
}
