package com.eliasnogueira.workshop.rest;

import com.eliasnogueira.workshop.api.SparkMockAPI;
import com.eliasnogueira.workshop.api.model.Person;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import spark.Spark;

/**
 * Run thought the Mocked API
 *
 * @author Elias Nogueira | @eliasnogueira
 * @author Edson Yanaga | @yanaga
 * 
 * @see com.eliasnogueira.workshop.api.SparkMockAPI
 */
public class SmokeAPITest {

    @BeforeMethod
    public static void setUp() {
        baseURI = "http://localhost";
        port = 4567;
        basePath = "/api/v1/";
        
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void getPersonById() {
        when().
            get("person/{id}", 1).
        then().
            statusCode(200);
    }

    @Test
    public void insertPerson() {
        given()
            .contentType(ContentType.JSON)
            .body(new Person("Yara Senger", "SP", "TDC")).
        when()
            .post("person").
        then().
            statusCode(201);

    }

    @Test
    public void deletePerson() {
        int personID = 
            given()
                .contentType(ContentType.JSON)
                .body(new Person("Yara Senger", "SP", "TDC")).
        when().
            post("person").
        then().
            extract().
                path("id");
                
        when().
            delete("person/{id}", personID).
        then().
            statusCode(202);
    }

    @Test
    public void alterPerson() {
        int personID = 
            given()
                .contentType(ContentType.JSON)
                .body(new Person("Yara Senger", "SP", "TDC")).
        when().
            post("person").
        then().
            extract().
                path("id");
        
        given().
            contentType(ContentType.JSON).
            body(new Person("Bruno Souza", "SP", "Eat")).
        when().
            put("person/{id}", personID).
        then().
            statusCode(200);
    }
}
