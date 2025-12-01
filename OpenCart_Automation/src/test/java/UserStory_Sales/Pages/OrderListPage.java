package UserStory_Sales.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderListPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By pageHeader = By.cssSelector(".page-header h1");
    private By orderRows = By.cssSelector(".table-responsive table tbody tr");
    private By orderIdFilter = By.name("filter_order_id");
    private By filterButton = By.id("button-filter");
    private By viewButtons = By.cssSelector("a[data-bs-toggle='tooltip'][title*='View']");
    private By editButtons = By.cssSelector("a[data-bs-toggle='tooltip'][title*='Edit']");
    private By printInvoiceButtons = By.cssSelector("a[data-bs-toggle='tooltip'][title*='Invoice']");
    private By successAlert = By.cssSelector(".alert-success");

    public OrderListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isOrderListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getOrderCount() {
        try {
            List<WebElement> orders = driver.findElements(orderRows);
            return orders.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void filterByOrderId(String orderId) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderIdFilter)).clear();
        driver.findElement(orderIdFilter).sendKeys(orderId);
        driver.findElement(filterButton).click();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public OrderDetailPage viewFirstOrder() {
        List<WebElement> viewBtns = driver.findElements(viewButtons);
        if (!viewBtns.isEmpty()) {
            WebElement viewBtn = viewBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewBtn);
            wait.until(ExpectedConditions.elementToBeClickable(viewBtn));
            viewBtn.click();
            return new OrderDetailPage(driver);
        }
        return null;
    }

    public OrderDetailPage editFirstOrder() {
        List<WebElement> editBtns = driver.findElements(editButtons);
        if (!editBtns.isEmpty()) {
            WebElement editBtn = editBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", editBtn);
            wait.until(ExpectedConditions.elementToBeClickable(editBtn));
            editBtn.click();
            return new OrderDetailPage(driver);
        }
        return null;
    }

    public void printInvoiceForFirstOrder() {
        List<WebElement> printBtns = driver.findElements(printInvoiceButtons);
        if (!printBtns.isEmpty()) {
            WebElement printBtn = printBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", printBtn);
            wait.until(ExpectedConditions.elementToBeClickable(printBtn));
            
            // Store current window handle
            String mainWindow = driver.getWindowHandle();
            
            printBtn.click();
            
            // Wait for new window/tab
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Switch back to main window if new window opened
            driver.switchTo().window(mainWindow);
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
