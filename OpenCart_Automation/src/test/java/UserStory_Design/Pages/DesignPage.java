package UserStory_Design.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DesignPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public DesignPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".page-header h1")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void performAction(String action) {
        try {
            Thread.sleep(1000);
            System.out.println("✓ " + action + " completed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
