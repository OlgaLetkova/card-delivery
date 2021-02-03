package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.codeborne.selenide.Selenide.*;

public class HighSpeedFillingOutFormTest {

    @Test
    void shouldCheckOfSuccessfulHighSpeedSending() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='city'] [placeholder='Город']").setValue("ка");
        $$(".menu-item").findBy(Condition.text("Казань")).click();
        LocalDate date = LocalDate.now();
        LocalDate dateOfMeeting = date.plusDays(7);
        int day = dateOfMeeting.getDayOfMonth();
        int month = dateOfMeeting.getMonthValue();
        List<String> Months;
        Months = Arrays.asList(new String[]{
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь",
                "Ноябрь", "Декабрь"});
        String nameOfMonth = Months.get(month - 1);
        $(".icon-button").click();
        String txt = $(".calendar__name").getText();
        boolean start = txt.startsWith(nameOfMonth);
        if (!start) {
            $(".calendar__arrow_direction_right").click();
        }
        $$("td, .calendar__day").findBy(Condition.text(String.valueOf(day))).click();
        form.$("[name='name']").setValue("Петр Иванов");
        form.$("[name='phone']").setValue("+72102536955");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $(".notification__title").waitUntil(Condition.visible, 15000)
                .shouldHave(Condition.exactText("Успешно!"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateChecking = dateOfMeeting.format(df);
        String text = "Встреча успешно забронирована на ";
        $(".notification__content").waitUntil(Condition.visible, 15000)
                .shouldHave(Condition.exactText(text.concat(dateChecking)));
    }
}
