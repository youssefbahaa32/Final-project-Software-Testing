package UserStory_AccountManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountPage extends BasePage {

    // Locators based on actual account.twig template
    private By myAccountHeading = By.xpath("//h1[contains(text(), 'My Account')]");
    private By editAccountLink = By.xpath("//a[contains(@href, 'account/edit')]");
    private By addressBookLink = By.xpath("//a[contains(@href, 'account/address')]");
    private By successAlert = By.xpath("//div[contains(@class, 'alert-success')]");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isMyAccountPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(myAccountHeading));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public EditAccountPage navigateToEditAccount() {
        WebElement editLink = wait.until(ExpectedConditions.presenceOfElementLocated(editAccountLink));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", editLink);
        return new EditAccountPage(driver);
    }

    public AddressListPage navigateToAddressBook() {
        WebElement addressLink = wait.until(ExpectedConditions.presenceOfElementLocated(addressBookLink));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", addressLink);
        return new AddressListPage(driver);
    }

    public boolean isSuccessDisplayed() {
        try {
            return driver.findElements(successAlert).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
