package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.MenuItems;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.StaticUser;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType;
import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.UserType.Type.*;


@ExtendWith({UsersQueueExtension.class, BrowserExtension.class})
public class FriendsWebTest {

  private static final Config CFG = Config.getInstance();

   @Test
    void friendShouldBeDisplayedInFriendsTable(@UserType(WITH_FRIENDS) StaticUser user) {
       Selenide.open(CFG.frontUrl(), LoginPage.class)
               .loginUser(user.username(), user.password())
               .openMenu()
               .clickMenuItem(MenuItems.FRIENDS);

       new FriendsPage()
               .setValueIntoSearch(user.friend())
               .verifyNameInTableByType("friends",user.friend());
   }

    @Test
    void friendsTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser(user.username(), user.password())
                .openMenu()
                .clickMenuItem(MenuItems.FRIENDS);

        new FriendsPage()
                .verifyFriendsTableEmpty();
    }

    @Test
    void incomeInvitationShouldBePresentedInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser(user.username(), user.password())
                .openMenu()
                .clickMenuItem(MenuItems.FRIENDS);

        new FriendsPage()
                .verifyNameInTableByType("requests",user.income_requests());
    }

    @Test
    void outcomeInvitationShouldBePresentedInFriendsTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser(user.username(), user.password())
                .openMenu()
                .clickMenuItem(MenuItems.FRIENDS);

        new FriendsPage()
                .switchTab()
                .setValueIntoSearch(user.outcome_requests())
                .verifyUserPresentInAllPeopleTableAndCheckStatus(user.outcome_requests(), "Waiting");
    }
}
