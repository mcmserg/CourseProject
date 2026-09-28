package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byText;

public class MainPage {
    private SelenideElement paymentButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public PaymentPage payByCard() {
        paymentButton.click();
        return new PaymentPage();
    }

    public PaymentPage payByCredit() {
        creditButton.click();
        return new PaymentPage();
    }
}