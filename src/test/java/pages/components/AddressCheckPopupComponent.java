package pages.components;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class AddressCheckPopupComponent {
    private final SelenideElement popupTitle = $(".form__title"),
            popupSubtitle = $(".callme__subtitle"),
            addressInput = $("[data-action='https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address']"),
            checkAddressButton = $("[data-action='/local/ajax/internetAddressCheck.php?check=Y&site_id=s2']"),
            popupCloseButton = $(".popup__close"),
            successResultMessage = $(".callme__address-result__text"),
            errorResultMessage = $(".callme__address-result__error .callme__address-result__text");

    public AddressCheckPopupComponent checkPopupTitle(String value) {
        popupTitle.shouldHave(exactText(value));
        return this;
    }

    public AddressCheckPopupComponent checkPopupSubtitle(String value) {
        popupSubtitle.shouldHave(exactText(value));
        return this;
    }

    public AddressCheckPopupComponent checkAddressInput(String value) {
        addressInput.shouldBe(visible).click();
        Actions actions = new Actions(Selenide.webdriver().object());
        actions.sendKeys(value).perform();
        sleep(1000);
        return this;
    }

    public AddressCheckPopupComponent checkAddressButton() {
        checkAddressButton.shouldBe(visible).click();
        return this;
    }

    public AddressCheckPopupComponent checkPopupCloseButton() {
        popupCloseButton.click();
        return this;
    }

    public AddressCheckPopupComponent checkResultMessage(String value) {
        successResultMessage.shouldHave(text(value));
        return this;
    }

    public AddressCheckPopupComponent checkErrorResultMessage(String value) {
        errorResultMessage.shouldHave(text(value));
        return this;
    }

    public AddressCheckPopupComponent checkNoSuccessOrErrorMessage() {
        successResultMessage.shouldNotBe(visible);
        errorResultMessage.shouldNotBe(visible);
        return this;
    }
}