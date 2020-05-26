package com.atqc;

import com.atqc.models.PetModel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import static com.atqc.framework.Config.restApiBaseUri;
import static io.restassured.RestAssured.given;

public class RestAPIBaseTest {

    static final RequestSpecification REQUEST_SPEC =
            new RequestSpecBuilder()
                    .setContentType("application/json")
                    .setBaseUri(restApiBaseUri)
                    .setBasePath("/pet")
                    .build();

    @BeforeSuite
    public void addFilters() {

        RestAssured.filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );

    }

    public static long getPetID(){
        return given()
                .spec(REQUEST_SPEC)
                .body(PetModel.positiveCreatePet())
        .when()
                .post("/pet")
        .then()
                .statusCode(200)
                .extract().jsonPath().getLong("id");
    }
}
