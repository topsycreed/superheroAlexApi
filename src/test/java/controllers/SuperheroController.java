package controllers;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;
import static org.marvel.Constants.BASE_URL;
import static org.marvel.TestData.DEFAULT_HERO;

public class SuperheroController {
    RequestSpecification requestSpecification = given();

    public SuperheroController() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.contentType(ContentType.JSON);
    }

    public Response addHero() {
        return this.requestSpecification
                .body(DEFAULT_HERO)
                .when()
                .post("/superheroes")
                .then()
                .extract().response();
    }

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
