package UserStory_Sales.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReturnListPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By pageHeader = By.cssSelector(".page-header h1");
    private By returnRows = By.cssSelector(".table-responsive table tbody tr");
    private By editButtons = By.cssSelector("a[data-bs-toggle='tooltip'][title*='Edit']");
    private By returnStatusSelect = By.id("input-return-status");
    private By returnComment = By.id("input-comment");
    private By addHistoryButton = By.id("button-history");
    private By successAlert = By.cssSelector(".alert-success");

    public ReturnListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isReturnListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getReturnCount() {
        try {
            List<WebElement> returns = driver.findElements(returnRows);
            return returns.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void editFirstReturn() {
        List<WebElement> editBtns = driver.findElements(editButtons);
        if (!editBtns.isEmpty()) {
            WebElement editBtn = editBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", editBtn);
            wait.until(ExpectedConditions.elementToBeClickable(editBtn));
            editBtn.click();
        }
    }

    public void approveReturn(String comment) {
        try {
            // Click on the status input to open dropdown
            WebElement statusInput = wait.until(ExpectedConditions.elementToBeClickable(returnStatusSelect));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", statusInput);
            statusInput.click();
            Thread.sleep(500);
            
            // Find and click the "Approved" option
            By optionLocator = By.xpath("//li[contains(@class, 'dropdown-item') and contains(text(), 'Approved')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            Thread.sleep(500);
            
            // Add comment
            WebElement commentField = wait.until(ExpectedConditions.visibilityOfElementLocated(returnComment));
            commentField.clear();
            commentField.sendKeys(comment);
            
            // Click add history button
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addHistoryButton));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addBtn);
            addBtn.click();
            
            // Wait for AJAX
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
