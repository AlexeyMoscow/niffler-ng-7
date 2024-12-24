package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.UserRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class RegistrationAndLoginTests {

    private static final Config CFG = Config.getInstance();

    Faker faker = new Faker();

    LoginPage loginPage = new LoginPage();

    @Test
    void shouldRegisterNewUser() {

        String userName = UserRandom.getUserName();
        String password = UserRandom.getPassword();

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUser(userName, password, password)
                .verifyUserCreated();
    }


    @Test
    void shouldNotRegisterNewUserWithExistedUserName() {

        String userName = "alex";
        String password = UserRandom.getPassword();
        String errorMessage = "Username `" + userName + "` already exists";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUser(userName, password, password)
                .verifyErrorText(errorMessage);
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreDifferent() {

        String userName = UserRandom.getUserName();
        String password = UserRandom.getPassword();
        String errorMessage = "Passwords should be equal";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUser(userName, password, "123456")
                .verifyErrorText(errorMessage);
    }

    @Test
    void mainPageShouldBeVisibleAfterSuccessLogin() {
        String userName = "alex";
        String password = "123456";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser(userName, password)
                .verifyMainPageOpened();
    }

    @Test
    void shouldStayOnLoginPageAfterUnsuccessfulLogin() {
        String userName = "alex";
        String password = "1234561";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser(userName, password);
        loginPage.verifyErrorBlockVisible();
    }
}
