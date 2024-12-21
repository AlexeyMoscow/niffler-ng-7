package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

public class RegistrationTests {

    private static final Config CFG = Config.getInstance();

    Faker faker = new Faker();

    LoginPage loginPage = new LoginPage();

    @Test
    void shouldRegisterNewUser() {

        String userName = faker.name().firstName();
        String password = faker.internet().password(6,10);

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUser(userName, password)
                .verifyUserCreated();
    }


    @Test
    void shouldNotRegisterNewUserWithExistedUserName() {

        String userName = "alex";
        String password = faker.internet().password(6,10);
        String errorMessage = "Username `" + userName + "` already exists";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUser(userName, password)
                .verifyErrorText(errorMessage);
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreDifferent() {

        String userName = "alex";
        String password = faker.internet().password(6,10);
        String errorMessage = "Passwords should be equal";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUserWithErrorPasswordInput(userName, password)
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
