package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement checkAddressButton = $("[data-popup_id='proverka-interneta']");

    public MainPage clickCheckAddressButton() {
        checkAddressButton.shouldBe(visible).click();
        return this;
    }
}
