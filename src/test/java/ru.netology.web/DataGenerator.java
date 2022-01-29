package ru.netology.web;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import java.util.Locale;


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
            CustomerData customerInfo = generateAuthorization("active");
            Gson gsonBuilder = new GsonBuilder().create();
            String customerData = gsonBuilder.toJson(customerInfo);
            given()
                    .spec(requestSpec)
                    .body(customerData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return customerInfo;
        }
        public static CustomerData generateAuthorization(String status) {
            Faker faker = new Faker(new Locale("en"));
            return new CustomerData(generateRandomLogin(), generateRandomPassword(), status);
        }

        public static String generateRandomPassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }

        static String generateRandomLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }
    }

}