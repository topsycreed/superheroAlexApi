import controllers.SuperheroController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.marvel.models.Superhero;

import static io.restassured.RestAssured.given;
import static org.marvel.Constants.BASE_URL;
import static org.marvel.TestData.*;

class BaseTests {

    SoftAssertions softly = new SoftAssertions();
    SuperheroController controller = new SuperheroController();

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
        Response response = given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .when().get("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.asPrettyString());
    }

    @Test
    void simpleGetControllerTest() {
        Response response = controller.getHeroes();
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    void postControllerTest() {
        SuperheroController controller = new SuperheroController();
//        System.out.println(System.getProperty("env"));
//        System.setProperty("env", "dev");
//        System.out.println(System.getProperty("env"));
        Response response = controller.addHero();
        int status = response.getStatusCode();
        Superhero superhero = controller.parseHero(response);
        Assertions.assertEquals(200, status);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(superhero.getPhone())
                    .as("Phone value should be <%s>, but was <%s>", "+9959550431", superhero.getPhone())
                    .isEqualTo("+9959550431");
            softly.assertThat(superhero.getFullName()).isEqualTo("Blade");
            softly.assertThat(superhero.getBirthDate()).isEqualTo("2000-03-15");
            // no need to call assertAll, it is done by assertSoftly.
        });
    }

    @Test
    void simplePostCheckJavaObjTest() {
        Response response = RestAssured.given().baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(DEFAULT_HERO)
                .when().post("/superheroes")
                .then().extract().response();
        System.out.println(response.asPrettyString());
        Superhero createdHero = response.as(Superhero.class);
        System.out.println(createdHero);
        Assertions.assertEquals(200, response.statusCode());
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(createdHero.getPhone())
                    .as("Phone value should be <%s>, but was <%s>", "+9959550431", createdHero.getPhone())
                    .isEqualTo("+9959550431");
            softly.assertThat(createdHero.getFullName()).isEqualTo("Blade");
            softly.assertThat(createdHero.getBirthDate()).isEqualTo("2000-03-15");
            // no need to call assertAll, it is done by assertSoftly.
        });

//        softly.assertThat(createdHero.getPhone()).as("Phone value should be <%s>, but was <%s>", "+9959550431", createdHero.getPhone()).isEqualTo("+9959550431");
//        softly.assertThat(createdHero.getFullName()).isEqualTo("Blade1");
//        softly.assertThat(createdHero.getBirthDate()).isEqualTo("2000-03-16");
//        softly.assertAll();
    }

    @Test
    void simplePostCheckJsonTest() {
        Response response = RestAssured.given().baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(DEFAULT_HERO)
                .when().post("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println(response.asPrettyString());
        JsonPath json = response.jsonPath();
        String phone = json.get("phone");
        String fn = json.get("fullName");
        softly.assertThat(phone).isEqualTo("+9959550431");
        softly.assertThat(fn).isEqualTo("Blade");
        softly.assertAll();
    }

    @Test
    @Tags({@Tag("Failed"), @Tag("Flaky")})
    void simplePutHeroDataUpdate() {
        Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(DEFAULT_HERO)
                .when().post("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println(response.asPrettyString());
        Superhero createdHeroObj = response.as(Superhero.class);
        DEFAULT_HERO.setId(1);

        System.out.println(DEFAULT_HERO.getId());
        //int createdHeroId = response.path("id");
        int createdHeroId = createdHeroObj.getId();
        Superhero createdHero = getUpdatedHero2(createdHeroId);

        given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(createdHero)
                .when().put("/superheroes/" + createdHero.getId())
                .then()
                .statusCode(200)
                .extract().response();

        Response getResponse = given().baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .when().get("/superheroes/" + createdHero.getId())
                .then().statusCode(200).extract().response();
        Superhero updHero = getResponse.as(Superhero.class);
        System.out.println(getResponse.asPrettyString());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(updHero.getPhone()).isEqualTo(createdHero.getPhone());
            softly.assertThat(updHero.getFullName()).isEqualTo(createdHero.getFullName());
            softly.assertThat(updHero.getBirthDate()).isEqualTo(createdHero.getBirthDate());
            // no need to call assertAll, it is done by assertSoftly.
        });
    }
}
