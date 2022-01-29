package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.DataGenerator.Authorization.generateRandomPassword;

public class AuthorizationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldReturnHappyPath() {
        CustomerData customerData = DataGenerator.Authorization.registrationUsers("active");
        $("[data-test-id='login'] input").setValue(customerData.getLogin());
        $("[data-test-id='password'] input").setValue(customerData.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(visible);

    }

    @Test
    void shouldVerifyWrongLoginOfActiveUsers() {
        CustomerData customerData = DataGenerator.Authorization.registrationUsers("active");
        $("[data-test-id='login'] input").setValue(customerData.getLogin());
        $("[data-test-id='password'] input").setValue(generateRandomPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldVerifyWrongPasswordOfActiveUsers() {
        CustomerData customerData = DataGenerator.Authorization.registrationUsers("active");
        $("[data-test-id='login'] input").setValue(DataGenerator.Authorization.generateRandomLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.Authorization
                .generateAuthorization("active").getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldVerifyBlockedUsers() {
        CustomerData customerData = DataGenerator.Authorization.registrationUsers("blocked");
        $("[data-test-id='login'] input").setValue(customerData.getLogin());
        $("[data-test-id='password'] input").setValue(customerData.getPassword());
        $(withText("Продолжить")).click();
    }

}