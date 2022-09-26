package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);

    @Test
    void shouldValidDeliveryCard() {
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Нижний Новгород");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Климов Иван");
        $("[name=\"phone\"]").setValue("+79081234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//*[contains(text(),\"Успешно!\")]").should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldInvalidFormOnCityNotRussia() {
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Пекин");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Климов Иван");
        $("[name=\"phone\"]").setValue("+79081234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[text()=\"Доставка в выбранный город недоступна\"]");

    }

    @Test
    void shouldInvalidFormNotData() {
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Нижний Новгород");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue("");
        $("[name=\"name\"]").setValue("Климов Иван");
        $("[name=\"phone\"]").setValue("+79081234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[text()=\"Неверно введена дата\"]");
    }

    @Test
    void shouldInvalidFormNotSurnameAndName() {
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Нижний Новгород");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("");
        $("[name=\"phone\"]").setValue("+79081234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[text()=\"Поле обязательно для заполнения\"]");
    }

    @Test
    void shouldInvalidFormSurnameAndNameOnEnglish() {
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Нижний Новгород");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Klimov Ivan");
        $("[name=\"phone\"]").setValue("+79081234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[text()=\"Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.\"]");
    }

    @Test
    void shouldInvalidFormOnTelephone() {
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Нижний Новгород");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Климов Иван");
        $("[name=\"phone\"]").setValue("89081234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $x("//span[text()=\"Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.\"]");
    }

    @Test
    void shouldInvalidFormNotClickBox() {
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Нижний Новгород");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Климов Иван");
        $("[name=\"phone\"]").setValue("+79081234567");
        $(".button__text").click();
        $x("//span[text()=\"Я соглашаюсь с условиями обработки и использования моих персональных данных\"]");
    }

}
