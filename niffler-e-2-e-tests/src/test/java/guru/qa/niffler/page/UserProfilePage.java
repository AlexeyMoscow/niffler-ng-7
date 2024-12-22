package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class UserProfilePage {

    private final ElementsCollection
                    activeCategories = $$(By.xpath("//div[contains(@class, 'MuiChip-colorPrimary')]")),
                    archivedCategories = $$(By.xpath("//div[contains(@class, 'MuiChip-colorDefault')]"));

    private final SelenideElement showArchivedCategoriesToggle = $(By.xpath("//input[contains(@class, 'MuiSwitch-input')]"));



    public void verifyActiveCategoryInList(String categoryName) {
        activeCategories.find(text(categoryName)).shouldBe(visible);
    }

    public void verifyArchivedCategoryInList(String categoryName) {
        archivedCategories.find(text(categoryName)).shouldBe(visible);
    }

    public UserProfilePage switchArchivedCategoriesToggle() {
        showArchivedCategoriesToggle.click();
        return this;
    }

}
