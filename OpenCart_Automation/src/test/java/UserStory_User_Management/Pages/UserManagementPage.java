package UserStory_User_Management.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class UserManagementPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public UserManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".page-header h1")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
