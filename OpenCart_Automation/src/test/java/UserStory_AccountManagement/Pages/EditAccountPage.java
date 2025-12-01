package UserStory_AccountManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EditAccountPage extends BasePage {

    // Locators based on actual edit.twig template
    private By firstNameInput = By.id("input-firstname");
    private By lastNameInput = By.id("input-lastname");
    private By emailInput = By.id("input-email");
    private By telephoneInput = By.id("input-telephone");
    private By continueButton = By.xpath("//button[@type='submit' and contains(@class, 'btn-primary')]");
    private By successAlert = By.xpath("//div[contains(@class, 'alert-success')]");

    public EditAccountPage(WebDriver driver) {
        super(driver);
    }

    public void clearAndEnterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void clearAndEnterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void clearAndEnterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void clearAndEnterTelephone(String telephone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(telephoneInput)).clear();
        driver.findElement(telephoneInput).sendKeys(telephone);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    // CSV Test Case: Profile | Update Profile Information
    public void updateProfile(String firstName, String lastName, String telephone) {
        clearAndEnterFirstName(firstName);
        clearAndEnterLastName(lastName);
        clearAndEnterTelephone(telephone);
        clickContinue();
    }

    // CSV Test Case: Profile | Change Email Address
    public void changeEmail(String newEmail) {
        clearAndEnterEmail(newEmail);
        clickContinue();
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
