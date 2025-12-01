package UserStory_Catalog.Pages;

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

    private By catalogMenu = By.xpath("//a[contains(text(), 'Catalog')]");
    private By productsLink = By.xpath("//a[contains(text(), 'Products')]");
    private By categoriesLink = By.xpath("//a[contains(text(), 'Categories')]");

    public AdminDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    private void clickMenuItem(By menuLocator, By submenuLocator) {
        try {
            WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(menuLocator));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", menu);
            Thread.sleep(300);
            try {
                menu.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", menu);
            }
            Thread.sleep(500);
            
            WebElement submenu = wait.until(ExpectedConditions.presenceOfElementLocated(submenuLocator));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", submenu);
            Thread.sleep(300);
            try {
                submenu.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", submenu);
            }
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("⚠ Navigation failed");
        }
    }

    public ProductListPage navigateToProducts() {
        clickMenuItem(catalogMenu, productsLink);
        return new ProductListPage(driver);
    }

    public CategoryListPage navigateToCategories() {
        clickMenuItem(catalogMenu, categoriesLink);
        return new CategoryListPage(driver);
    }
}
