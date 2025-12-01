package UserStory_Marketing.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AdminLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public AdminLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public AdminDashboardPage login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-username"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-password"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("route=common/dashboard"),
            ExpectedConditions.urlContains("dashboard")
        ));
        return new AdminDashboardPage(driver);
    }
}
