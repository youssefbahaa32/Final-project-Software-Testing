package UserStory_AccountManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // Locators based on actual OpenCart login.twig template
    private By emailInput = By.id("input-email");
    private By passwordInput = By.id("input-password");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By errorAlert = By.xpath("//div[contains(@class, 'alert-danger')]");
    private By forgottenPasswordLink = By.linkText("Forgotten Password");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    // CSV Test Case: Login | Successful Login
    public AccountPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return new AccountPage(driver);
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

    public void clickForgottenPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgottenPasswordLink)).click();
    }

    public void enterEmailForReset(String email) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email"))).sendKeys(email);
        } catch (Exception e) {
            System.out.println("Email field for password reset not found: " + e.getMessage());
        }
    }

    public void submitPasswordReset() {
        try {
            driver.findElement(By.cssSelector("button[type='submit']")).click();
        } catch (Exception e) {
            System.out.println("Submit button for password reset not found: " + e.getMessage());
        }
    }

    public boolean isPasswordResetConfirmationDisplayed() {
        try {
            return driver.findElements(By.cssSelector(".alert-success")).size() > 0 ||
                   driver.getPageSource().toLowerCase().contains("email has been sent");
        } catch (Exception e) {
            return false;
        }
    }
}
