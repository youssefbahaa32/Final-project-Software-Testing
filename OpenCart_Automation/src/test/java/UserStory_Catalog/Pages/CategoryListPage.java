package UserStory_Catalog.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CategoryListPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By pageHeader = By.cssSelector(".page-header h1");
    private By addNewButton = By.cssSelector("a[data-bs-toggle='tooltip']");
    private By categoryRows = By.cssSelector(".table-responsive table tbody tr");
    private By successAlert = By.cssSelector(".alert-success");

    public CategoryListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isCategoryListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCategoryCount() {
        try {
            List<WebElement> categories = driver.findElements(categoryRows);
            return categories.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public CategoryFormPage clickAddNew() {
        List<WebElement> buttons = driver.findElements(addNewButton);
        if (!buttons.isEmpty()) {
            WebElement addButton = buttons.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addButton);
            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            addButton.click();
        }
        return new CategoryFormPage(driver);
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
