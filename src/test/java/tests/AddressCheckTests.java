package tests;

import io.qameta.allure.Param;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.MainPage;
import pages.components.AddressCheckPopupComponent;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class AddressCheckTests extends TestBase {
    MainPage mainPage = new MainPage();
    AddressCheckPopupComponent addressPopupComponent = new AddressCheckPopupComponent();

    @Tag("smoke")
    @ParameterizedTest(name = "Пользователь может увидеть подключенный адрес {0}")
    @ValueSource(strings = {
            "г Новосибирск, Цветной проезд, д 23", "г Новосибирск, ул Семьи Шамшиных, д 22/1",
            "г Новосибирск, Тюленина, д 19/1"
    })
    public void addressAvailabilityTest(@Param("Address") String name) {
        step("Открываем главную страницу", () -> open(baseUrl));
        step("На главной странице нажимаем на кнопку Проверить адрес", () -> mainPage.clickCheckAddressButton());
        step("Ввести адрес, на котором подключен интернет", () -> addressPopupComponent.checkAddressInput(name).checkAddressButton());
        step("Нажать на кнопку Проверить", () -> addressPopupComponent.checkAddressButton());
        step("Проверить, что заголовок содержит текст", () -> addressPopupComponent.checkPopupTitle("Узнайте, есть ли в доме интернет «Электронного города»"));
        step("Проверить, что подзаголовок содержит текст", () -> addressPopupComponent.checkPopupSubtitle("Оставьте свой номер. Мы перезвоним, ответим на вопросы и подключим услугу!!"));
        step("Проверить, что выводится сообщение о доступных услугах на адресе", () -> addressPopupComponent.checkResultMessage("\n" +
                "                                        Доступны услуги:\n" +
                "                                    "));
    }

    @Tag("smoke")
    @ParameterizedTest(name = "Пользователь не видит подключенных услуг на адресе {0}")
    @ValueSource(strings = {
            "г Новосибирск, Депутатская, д 46", "Новосибирский р-н, поселок Ложок, ул Тесла"
    })
    public void addressUnavailabilityTest(@Param("Address") String name) {
        step("Открываем главную страницу", () -> open(baseUrl));
        step("На главной странице нажимаем на кнопку Проверить адрес", () -> mainPage.clickCheckAddressButton());
        step("Ввести адрес, на котором интернет не подключен", () -> addressPopupComponent.checkAddressInput(name).checkAddressButton());
        step("Нажать на кнопку Проверить", () -> addressPopupComponent.checkAddressButton());
        step("Проверить, что заголовок содержит текст", () -> addressPopupComponent.checkPopupTitle("Узнайте, есть ли в доме интернет «Электронного города»"));
        step("Проверить, что подзаголовок содержит текст", () -> addressPopupComponent.checkPopupSubtitle("Оставьте свой номер. Мы перезвоним, ответим на вопросы и подключим услугу!!"));
        step("Проверить, что выводится сообщение о недоступности услуг на адресе", () -> addressPopupComponent.checkErrorResultMessage("Дом еще не подключили"));
    }

    @Tag("negative")
    @ParameterizedTest(name = "Пользователь не видит подключенных услуг на невалидном адресе {0}")
    @ValueSource(strings = {
            "12345", "!№%:,.."
    })
    public void invalidAddressInputTest(@Param("Invalid Address") String name) {
        step("Открываем главную страницу", () -> open(baseUrl));
        step("На главной странице нажимаем на кнопку Проверить адрес", () -> mainPage.clickCheckAddressButton());
        step("Ввести невалидный адрес", () -> addressPopupComponent.checkAddressInput(name).checkAddressButton());
        step("Нажать на кнопку Проверить", () -> addressPopupComponent.checkAddressButton());
        step("Проверить, что не отображаются сообщения об успехе или ошибке", () -> addressPopupComponent.checkNoSuccessOrErrorMessage());
    }
}
