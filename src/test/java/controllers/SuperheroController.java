package controllers;

import configs.TestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.marvel.models.Superhero;

import static io.restassured.RestAssured.given;
import static org.marvel.Constants.BASE_URL;
import static org.marvel.TestData.DEFAULT_HERO;

public class SuperheroController {
    TestPropertiesConfig testPropertiesConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
    RequestSpecification requestSpecification = given();

    public SuperheroController() {
        requestSpecification.baseUri(testPropertiesConfig.getBaseUrl());
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add a new hero")
    public Response addHero() {
        return this.requestSpecification
                .body(DEFAULT_HERO)
                .when()
                .post("/superheroes")
                .then()
                .extract().response();
    }

    @Step("Parsing to obj")
    public Superhero parseHero(Response response) {
        return response.as(Superhero.class);
    }

    @Step("Get all heroes")
    public Response getHeroes() {
        return this.requestSpecification
                .when()
                .get("/superheroes")
                .then()
                .extract().response();
    }

    public Response getHeroesWithCookie() {
        return this.requestSpecification
                .when()
                .get("/superheroes")
                .then()
                .extract().response();
    }
}
