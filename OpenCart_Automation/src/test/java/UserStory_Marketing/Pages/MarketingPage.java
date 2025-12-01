package UserStory_Marketing.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MarketingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public MarketingPage(WebDriver driver) {
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

    public int getItemCount() {
        try {
            List<WebElement> items = driver.findElements(By.cssSelector(".table-responsive table tbody tr"));
            return items.size();
        } catch (Exception e) {
            return 0;
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

    public void createCoupon(String code, String discount) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-name"))).sendKeys(code);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-code"))).sendKeys(code);
        
        Select typeSelect = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-type"))));
        typeSelect.selectByValue("P");
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-discount"))).sendKeys(discount);
        
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-bs-toggle='tooltip']")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveBtn);
        saveBtn.click();
    }

    public void editFirstItem() {
        List<WebElement> editBtns = driver.findElements(By.cssSelector("a[data-bs-toggle='tooltip'][title*='Edit']"));
        if (!editBtns.isEmpty()) {
            WebElement editBtn = editBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", editBtn);
            wait.until(ExpectedConditions.elementToBeClickable(editBtn));
            editBtn.click();
        }
    }

    public void approveFirstAffiliate() {
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
