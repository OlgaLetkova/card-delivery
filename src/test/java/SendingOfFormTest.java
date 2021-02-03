import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SendingOfFormTest {

    @Test
    void shouldCheckOfSuccessfulSending() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='city'] [placeholder='Город']").setValue("Нижний Новгород");
        LocalDate date = LocalDate.now();
        LocalDate dateOfMeeting = date.plusDays(4);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        form.$("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL, "a", Keys.CONTROL, "x");
        form.$("[data-test-id='date'] .input__control").setValue(dateOfMeeting.format(df));
        form.$("[name='name']").setValue("Петр Иванов");
        form.$("[name='phone']").setValue("+72102536955");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $(".notification__title").waitUntil(Condition.visible, 15000)
                .shouldHave(Condition.exactText("Успешно!"));
        String dateChecking = dateOfMeeting.format(df);
        String text = "Встреча успешно забронирована на ";
        $(".notification__content").waitUntil(Condition.visible, 15000)
                .shouldHave(Condition.exactText(text.concat(dateChecking)));

    }
}
