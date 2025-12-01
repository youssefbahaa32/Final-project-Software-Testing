package UserStory_Marketing.Pages;

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

    private void clickMenuItem(String menuText, String submenuText) {
        try {
            WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(), '" + menuText + "') or contains(@href, '" + menuText.toLowerCase() + "')]")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", menu);
            Thread.sleep(300);
            try {
                menu.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", menu);
            }
            Thread.sleep(500);
            
            WebElement submenu = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(), '" + submenuText + "') or contains(@href, '" + submenuText.toLowerCase().replace(" ", "") + "')]")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", submenu);
            Thread.sleep(300);
            try {
                submenu.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", submenu);
            }
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("⚠ Navigation failed: " + menuText + " > " + submenuText);
        }
    }

    public MarketingPage navigateToCoupons() {
        clickMenuItem("Marketing", "Coupons");
        return new MarketingPage(driver);
    }

    public MarketingPage navigateToAffiliates() {
        clickMenuItem("Marketing", "Affiliates");
        return new MarketingPage(driver);
    }

    public MarketingPage navigateToMarketing() {
        clickMenuItem("Marketing", "Marketing");
        return new MarketingPage(driver);
    }
}
