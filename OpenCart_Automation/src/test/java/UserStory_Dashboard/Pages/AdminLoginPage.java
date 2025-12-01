package UserStory_Dashboard.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators based on admin/view/template/common/login.twig
    private By usernameInput = By.id("input-username");
    private By passwordInput = By.id("input-password");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By errorAlert = By.xpath("//div[contains(@class, 'alert-danger')]");

    public AdminLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public AdminDashboardPage login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("route=common/dashboard"),
            ExpectedConditions.urlContains("dashboard")
        ));
        
        return new AdminDashboardPage(driver);
    }

    public boolean isErrorDisplayed() {
        try {
            return driver.findElements(errorAlert).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
