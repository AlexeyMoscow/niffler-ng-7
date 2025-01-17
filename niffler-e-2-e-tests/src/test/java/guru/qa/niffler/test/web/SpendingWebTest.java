package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.UserProfilePage;
import guru.qa.niffler.utils.MenuItems;
import org.junit.jupiter.api.Test;

@WebTest
public class SpendingWebTest {

  private static final Config CFG = Config.getInstance();

    @User
    (       username = "alex",
            spendings = @Spending(
            category = "Музыка",
            description = "Обучение Advanced 2.0",
            amount = 79990
    ))
    @Test
    void categoryDescriptionShouldBeChangedFromTable(SpendJson spend) {
        final String newDescription = "Обучение Niffler Next Generation";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser("alex", "123456")
                .editSpending(spend.description())
                .setNewSpendingDescription(newDescription)
                .save();

        new MainPage().checkThatTableContainsSpending(newDescription);
    }

    @User(
            username = "alex",
            categories = @Category(
                    archived = false
            )
    )
    @Test
    void archivedCategoryShouldBePresentedInList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser("alex", "123456")
                .openMenu()
                .clickMenuItem(MenuItems.PROFILE);
        new UserProfilePage()
                .archiveCategory(category.name())
                .confirmArchiveCategory()
                .switchArchivedCategoriesToggle()
                .verifyArchivedCategoryInList(category.name());
    }

    @User(
            username = "alex",
            categories = @Category(
                    archived = true
            )
    )
    @Test
    void activeCategoryShouldBePresentedInListAfterRestored(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginUser("alex", "123456")
                .openMenu()
                .clickMenuItem(MenuItems.PROFILE);
        new UserProfilePage()
                .switchArchivedCategoriesToggle()
                .restoreCategory(category.name())
                .confirmRestoreCategory()
                .verifyActiveCategoryInList(category.name());
    }
}
