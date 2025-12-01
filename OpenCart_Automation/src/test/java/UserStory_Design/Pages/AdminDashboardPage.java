package UserStory_Design.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AdminDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public AdminDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public DesignPage navigateToDesign(String section) {
        try {
            // Click on Design menu
            WebElement designMenu = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(), 'Design') or contains(@href, 'design')]")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", designMenu);
            Thread.sleep(500);
            
            try {
                designMenu.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", designMenu);
            }
            
            Thread.sleep(1000); // Wait for submenu to expand
            
            // Click on the specific section
            WebElement sectionLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(), '" + section + "') or contains(@href, '" + section.toLowerCase().replace(" ", "") + "')]")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sectionLink);
            Thread.sleep(500);
            
            try {
                sectionLink.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", sectionLink);
            }
            
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("⚠ Could not navigate to Design > " + section + ": " + e.getMessage());
        }
        
        return new DesignPage(driver);
    }
}
