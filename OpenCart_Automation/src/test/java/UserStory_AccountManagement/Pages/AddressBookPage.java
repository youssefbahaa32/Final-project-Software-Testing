package UserStory_AccountManagement.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddressBookPage extends BasePage {

    @FindBy(xpath = "//a[contains(@class, 'btn-primary') and contains(text(), 'New Address')]")
    private WebElement newAddressButton;

    @FindBy(xpath = "//a[contains(@href, 'address.edit')]")
    private WebElement editAddressLink;

    @FindBy(xpath = "//a[contains(@href, 'address.delete')]")
    private WebElement deleteAddressLink;

    @FindBy(xpath = "//div[contains(@class, 'alert-success')]")
    private WebElement successMessage;

    public AddressBookPage(WebDriver driver) {
        super(driver);
    }

    public void clickNewAddress() {
        clickElement(newAddressButton);
    }

    public void clickEditAddress() {
        clickElement(editAddressLink);
    }

    public void clickDeleteAddress() {
        clickElement(deleteAddressLink);
    }

    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(successMessage);
    }

    public String getSuccessMessage() {
        return getElementText(successMessage);
    }
}
