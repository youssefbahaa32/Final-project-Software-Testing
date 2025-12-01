package UserStory_AccountManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends BasePage {

    // Locators based on actual OpenCart register.twig template
    private By firstNameInput = By.id("input-firstname");
    private By lastNameInput = By.id("input-lastname");
    private By emailInput = By.id("input-email");
    private By passwordInput = By.id("input-password");
    private By agreeCheckbox = By.name("agree");
    private By continueButton = By.xpath("//button[@type='submit' and contains(@class, 'btn-primary')]");
    private By errorAlert = By.xpath("//div[contains(@class, 'alert-danger')]");
    private By successMessage = By.xpath("//div[contains(@class, 'alert-success')]");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void checkAgreeTerms() {
        WebElement agreeElement = wait.until(ExpectedConditions.presenceOfElementLocated(agreeCheckbox));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", agreeElement);
        wait.until(ExpectedConditions.elementToBeClickable(agreeCheckbox));
        try {
            agreeElement.click();
        } catch (Exception e) {
            // Use JavaScript click if regular click fails
            js.executeScript("arguments[0].click();", agreeElement);
        }
    }

    public void clickContinue() {
        WebElement continueBtn = wait.until(ExpectedConditions.presenceOfElementLocated(continueButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", continueBtn);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        try {
            continueBtn.click();
        } catch (Exception e) {
            // Use JavaScript click if regular click fails
            js.executeScript("arguments[0].click();", continueBtn);
        }
    }

    // CSV Test Case: Registration | Successful Registration
    public void registerUser(String firstName, String lastName, String email, String password) {
        try {
            enterFirstName(firstName);
            enterLastName(lastName);
            enterEmail(email);
            enterPassword(password);
            checkAgreeTerms();
            clickContinue();
            
            // Wait for page to process (either success or error)
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    public boolean isErrorDisplayed() {
        try {
            return driver.findElements(errorAlert).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert)).getText();
    }

    public boolean isSuccessDisplayed() {
        try {
            return driver.findElements(successMessage).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
