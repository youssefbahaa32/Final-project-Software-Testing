package UserStory_AccountManagement.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordPage extends BasePage {

    @FindBy(id = "input-password")
    private WebElement passwordInput;

    @FindBy(id = "input-confirm")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(@class, 'btn-primary')]")
    private WebElement continueButton;

    @FindBy(xpath = "//a[contains(@class, 'btn-light')]")
    private WebElement backButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]")
    private WebElement errorMessage;

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        enterText(confirmPasswordInput, confirmPassword);
    }

    public void clickContinue() {
        clickElement(continueButton);
    }

    public void clickBack() {
        clickElement(backButton);
    }

    public void changePassword(String newPassword, String confirmPassword) {
        enterPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickContinue();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getElementText(errorMessage);
    }
}
