
package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {

    private final SelenideElement
            registerForm = $("#register-form"),
            usernameInput = $("#username"),
            passwordInput = $("#password"),
            submitPasswordInput = $("#passwordSubmit"),
            submitButton = $(".form__submit"),
            congratulationMessage = $(".form__paragraph_success");


    public RegistrationPage registerNewUser(String username, String password, String confirmPassword) {
        usernameInput.shouldBe(visible).setValue(username);
        passwordInput.shouldBe(visible).setValue(password);
        submitPasswordInput.shouldBe(visible).setValue(confirmPassword);
        submitButton.click();
        return this;
    }

    public  void verifyUserCreated() {
        congratulationMessage.shouldHave(text("Congratulations"));
    }

    public  void verifyErrorText(String errorText) {
        registerForm.shouldHave(text(errorText));
    }
}
