package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement spendingTable = $("#spendings");
    private final SelenideElement statisticsFigure = $("#stat");
    private final SelenideElement headerBlock = $("#root header");

    private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");

    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).$("td", 5).click();

        return new EditSpendingPage();
    }


    public void checkThatTableContainsSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).shouldBe(visible);
    }

    public void verifyMainPageOpened() {
        spendingTable.shouldBe(visible);
        statisticsFigure.shouldBe(visible);
        headerBlock.shouldBe(visible);
    }
}
