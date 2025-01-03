
package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitButton = $("button[type='submit']");

    private final SelenideElement
            createNewUserButton = $(".form__register"),
            errorMessageBlock = $(".form__error");

    public MainPage loginUser(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        submitButton.click();

        return new MainPage();
    }

    public RegistrationPage clickCreateNewUserButton() {
        createNewUserButton.click();
        return new RegistrationPage();
    }

    public void verifyErrorBlockVisible() {
        errorMessageBlock.shouldBe(visible);
    }
}
