package UserStory_AccountManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddressListPage extends BasePage {

    // Locators for address list page
    private By newAddressButton = By.xpath("//a[contains(@class, 'btn-primary') and contains(@href, 'address.form')]");
    private By editAddressLink = By.xpath("//a[contains(@href, 'address.form') and contains(text(), 'Edit')]");
    private By successAlert = By.xpath("//div[contains(@class, 'alert-success')]");

    public AddressListPage(WebDriver driver) {
        super(driver);
    }

    public AddressFormPage clickNewAddress() {
        WebElement newButton = wait.until(ExpectedConditions.presenceOfElementLocated(newAddressButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", newButton);
        return new AddressFormPage(driver);
    }

    public AddressFormPage clickEditAddress() {
        WebElement editLink = wait.until(ExpectedConditions.presenceOfElementLocated(editAddressLink));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", editLink);
        return new AddressFormPage(driver);
    }

    public boolean isSuccessDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
