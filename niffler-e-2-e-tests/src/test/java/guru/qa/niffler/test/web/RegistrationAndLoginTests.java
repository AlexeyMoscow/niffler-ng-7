package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.Test;

@WebTest
public class RegistrationAndLoginTests {

    private static final Config CFG = Config.getInstance();

    LoginPage loginPage = new LoginPage();

    @Test
    void shouldRegisterNewUser() {

        String userName = RandomDataUtils.randomUserName();
        String password = RandomDataUtils.randomPassword();

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUser(userName, password, password)
                .verifyUserCreated();
    }


    @Test
    void shouldNotRegisterNewUserWithExistedUserName() {

        String userName = "alex";
        String password = RandomDataUtils.randomPassword();
        String errorMessage = "Username `" + userName + "` already exists";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewUserButton()
                .registerNewUser(userName, password, password)
                .verifyErrorText(errorMessage);
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreDifferent() {

        String userName = RandomDataUtils.randomUserName();
        String password = RandomDataUtils.randomPassword();
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
