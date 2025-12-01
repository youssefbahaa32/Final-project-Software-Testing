package UserStory_Customer.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CustomerListPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CustomerListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isCustomerListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".page-header h1")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCustomerCount() {
        try {
            List<WebElement> customers = driver.findElements(By.cssSelector(".table-responsive table tbody tr"));
            return customers.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public CustomerFormPage editFirstCustomer() {
        List<WebElement> editBtns = driver.findElements(By.cssSelector("a[data-bs-toggle='tooltip'][title*='Edit']"));
        if (!editBtns.isEmpty()) {
            WebElement editBtn = editBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", editBtn);
            wait.until(ExpectedConditions.elementToBeClickable(editBtn));
            editBtn.click();
            return new CustomerFormPage(driver);
        }
        return null;
    }

    public void approveFirstCustomer() {
        List<WebElement> approveBtns = driver.findElements(By.cssSelector("button[data-bs-toggle='tooltip'][title*='Approve']"));
        if (!approveBtns.isEmpty()) {
            WebElement approveBtn = approveBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", approveBtn);
            wait.until(ExpectedConditions.elementToBeClickable(approveBtn));
            approveBtn.click();
            try {
                Thread.sleep(1000);
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                // No alert
            }
        }
    }
}
