package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.MainPage;
import page.PaymentPage;
import page.PaymentPage.*;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.CardInfo.*;


public class PaymentPageTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

//    Позитивные сценарии

    // Passed
    @Test
    void shouldGetPaymentPage() {
        val mainPage = new MainPage();
        mainPage.payByCard();
    }

    // Passed
    @Test
    void shouldGetCreditPage() {
        val mainPage = new MainPage();
        mainPage.payByCredit();
    }

    // Passed
    @Test
    void shouldPayByCardSuccessfully() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfullPayment();
    }

    //   Not passed. TODO Issue
    @Test
    void shouldNotPayWithDeclinedCard() {
        val cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
    }

//    Негативные сценарии

    //    Passed
    @Test
    void shouldNotPayByShortCard() {
        val cardInfo = new DataHelper.CardInfo(getShortCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    //   Passed
    @Test
    void shouldNotPayByUnknownCard() {
        val cardInfo = new DataHelper.CardInfo(getUnknownCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
    }

    //   Passed
    @Test
    void shouldNotPayByCardWithSigns() {
        val cardInfo = new DataHelper.CardInfo(getCardNumberWithSigns(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayByCardWithLetters() {
        val cardInfo = new DataHelper.CardInfo(getCardNumberWithLetters(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithoutCard() {
        val cardInfo = new DataHelper.CardInfo(null, getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithMonthOver12() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthOver12(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    //   Passed
    @Test
    void shouldNotPayWithMonthWithLetters() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithLetters(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    //   Passed
    @Test
    void shouldNotPayWithMonthWithSigns() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithSigns(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithMonthWithOneDigit() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithOneDigit(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Not passed TODO Issue
    @Test
    void shouldNotPayWithMonthWithNulls() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithNulls(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithoutMonth() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), null, getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Passed
    @Test
    void shouldNotPayWithPastYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getPastYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.expiredCardErrorVisible();
    }

    // Passed
    @Test
    void shouldNotPayWithYearWithLetters() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithLetters(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Passed
    @Test
    void shouldNotPayWithYearWithSigns() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithSigns(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Passed
    @Test
    void shouldNotPayWithYearWithOneDigit() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithOneDigit(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Passed
    @Test
    void shouldNotPayWithoutYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), null, getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Not passed TODO Issue
    @Test
    void shouldNotPayWithFirstName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerFirstName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Not passed TODO Issue
    @Test
    void shouldNotPayWithNameInRussian() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameInRussia(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Not Passed TODO Issue
    @Test
    void shouldNotPayWithNameWithDigits() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithDigits(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Not passed TODO Issue
    @Test
    void shouldNotPayWithNameWithSigns() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithSigns(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Not passed TODO Issue
    @Test
    void shouldNotPayWithShortName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameShort(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Not passed TODO Issue
    @Test
    void shouldNotPayWithLongName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameLong(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Passed
    @Test
    void shouldPayWithDoubleName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithDoubleName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfullPayment();
    }

    // Passed
    @Test
    void shouldNotPayWithoutName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), null, getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Passed
    @Test
    void shouldNotPayWithCVCwithLetters() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCwithLetters());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithCVCwithSigns() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCwithSigns());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithCVCwithOneDigit() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCshort());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithoutCVC() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), null);
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    //    Passed
    @Test
    void shouldNotPayWithoutData() {
        val mainPage = new MainPage();
        mainPage.payByCard();
        val paymentPage = new PaymentPage();
        paymentPage.notFilledForm();
    }

}