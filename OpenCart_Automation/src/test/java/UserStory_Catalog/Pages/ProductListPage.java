package UserStory_Catalog.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductListPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By pageHeader = By.cssSelector(".page-header h1");
    private By addNewButton = By.cssSelector("a[data-bs-toggle='tooltip']");
    private By productRows = By.cssSelector(".table-responsive table tbody tr");
    private By productNameFilter = By.name("filter_name");
    private By filterButton = By.id("button-filter");
    private By editButtons = By.cssSelector("a[data-bs-toggle='tooltip'][title*='Edit']");
    private By deleteButtons = By.cssSelector("button[data-bs-toggle='tooltip'][title*='Delete']");
    private By successAlert = By.cssSelector(".alert-success");
    private By checkboxes = By.cssSelector("input[type='checkbox'][name='selected[]']");

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isProductListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        try {
            List<WebElement> products = driver.findElements(productRows);
            return products.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public ProductFormPage clickAddNew() {
        List<WebElement> buttons = driver.findElements(addNewButton);
        if (!buttons.isEmpty()) {
            WebElement addButton = buttons.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addButton);
            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            addButton.click();
        }
        return new ProductFormPage(driver);
    }

    public void filterByName(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameFilter)).clear();
        driver.findElement(productNameFilter).sendKeys(productName);
        driver.findElement(filterButton).click();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isProductInList(String productName) {
        try {
            By productLink = By.linkText(productName);
            return driver.findElements(productLink).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public ProductFormPage editFirstProduct() {
        List<WebElement> editBtns = driver.findElements(editButtons);
        if (!editBtns.isEmpty()) {
            WebElement editBtn = editBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", editBtn);
            wait.until(ExpectedConditions.elementToBeClickable(editBtn));
            editBtn.click();
            return new ProductFormPage(driver);
        }
        return null;
    }

    public void deleteFirstProduct() {
        List<WebElement> checkboxList = driver.findElements(checkboxes);
        if (!checkboxList.isEmpty()) {
            WebElement checkbox = checkboxList.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        
        List<WebElement> deleteBtns = driver.findElements(deleteButtons);
        if (!deleteBtns.isEmpty()) {
            WebElement deleteBtn = deleteBtns.get(0);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", deleteBtn);
            wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
            deleteBtn.click();
            
            try {
                Thread.sleep(1000);
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                // No alert
            }
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
