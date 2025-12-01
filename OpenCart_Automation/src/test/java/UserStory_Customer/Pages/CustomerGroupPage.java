package UserStory_Customer.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CustomerGroupPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CustomerGroupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isCustomerGroupListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".page-header h1")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddNew() {
        List<WebElement> buttons = driver.findElements(By.cssSelector("a[data-bs-toggle='tooltip']"));
        if (!buttons.isEmpty()) {
            WebElement addButton = buttons.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addButton);
            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            addButton.click();
        }
    }

    public void createCustomerGroup(String groupName) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-name-1")));
        nameField.clear();
        nameField.sendKeys(groupName);
        
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-bs-toggle='tooltip']")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveBtn);
        saveBtn.click();
    }
}
