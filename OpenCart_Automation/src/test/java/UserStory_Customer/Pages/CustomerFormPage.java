package UserStory_Customer.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CustomerFormPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CustomerFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isCustomerFormDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateCustomerEmail(String newEmail) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email")));
        emailField.clear();
        emailField.sendKeys(newEmail);
    }

    public void clickSave() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-bs-toggle='tooltip']")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveBtn);
        saveBtn.click();
    }

    public void clickOrdersTab() {
        try {
            WebElement ordersTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Orders")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", ordersTab);
            ordersTab.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickAddressesTab() {
        try {
            WebElement addressesTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Addresses")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addressesTab);
            addressesTab.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
