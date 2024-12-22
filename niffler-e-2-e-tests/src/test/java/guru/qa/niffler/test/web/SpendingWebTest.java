package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.jupiter.Category;
import guru.qa.niffler.jupiter.Spending;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.UserProfilePage;
import guru.qa.niffler.utils.MenuItems;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class SpendingWebTest {

    private static final Config CFG = Config.getInstance();

    @Spending(
            username = "alex",
            category = "обучение",
            description = "Обучение Advanced 2.0",
            amount = 79990
    )
    @Test
    void categoryDescriptionShouldBeChangedFromTable(SpendJson spend) {
        final String newDescription = "Обучение Niffler Next Generation";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser("alex", "123456")
                .editSpending(spend.description())
                .setDescription(newDescription)
                .save();

        new MainPage().checkThatTableContainsSpending(newDescription);
    }

    @Category(
            username = "alex",
            archived = false
    )
    @Test
    void activeCategoryShouldBePresentedInList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser("alex", "123456")
                .openMenu()
                .clickMenuItem(MenuItems.PROFILE);
        new UserProfilePage()
                .verifyActiveCategoryInList(category.name());
    }

    @Category(
            username = "alex",
            archived = true
    )
    @Test
    void archivedCategoryShouldBePresentedInList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser("alex", "123456")
                .openMenu()
                .clickMenuItem(MenuItems.PROFILE);
        new UserProfilePage()
                .switchArchivedCategoriesToggle()
                .verifyArchivedCategoryInList(category.name());
    }
}
