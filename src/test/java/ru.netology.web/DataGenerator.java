package ru.netology.web;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;

import static io.restassured.RestAssured.given;

import java.util.Locale;

@Data
@AllArgsConstructor

public class DataGenerator {
    public static class Authorization {
        private Authorization() {
        }

        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static CustomerData registrationUsers(String status) {
            CustomerData customerData = generateAuthorization("active");
            Gson gsonBuilder = new GsonBuilder().create();
            String CustomerData = gsonBuilder.toJson(customerData);
            given()
                    .spec(requestSpec)
                    .body(CustomerData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return customerData;
        }
        public static CustomerData generateAuthorization(String status) {
            Faker faker = new Faker(new Locale("en"));
            return new CustomerData(wrongLogin(), wrongPassword(), status);
        }

        private static String wrongPassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }

        private static String wrongLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }
    }

}