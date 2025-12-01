package UserStory_Catalog.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductFormPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators
    private By productNameInput = By.id("input-name-1");
    private By metaTagTitleInput = By.id("input-meta-title-1");
    private By modelInput = By.id("input-model");
    private By priceInput = By.id("input-price");
    private By quantityInput = By.id("input-quantity");
    private By stockStatusSelect = By.id("input-stock-status");
    private By statusSelect = By.id("input-status");
    private By saveButton = By.cssSelector("button[data-bs-toggle='tooltip']");
    private By dataTab = By.linkText("Data");
    private By imageTab = By.linkText("Image");
    private By optionTab = By.linkText("Option");
    private By successAlert = By.cssSelector(".alert-success");

    public ProductFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void enterProductName(String name) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameInput));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterMetaTagTitle(String title) {
        WebElement metaField = wait.until(ExpectedConditions.visibilityOfElementLocated(metaTagTitleInput));
        metaField.clear();
        metaField.sendKeys(title);
    }

    public void clickDataTab() {
        try {
            WebElement dataTabElement = wait.until(ExpectedConditions.elementToBeClickable(dataTab));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", dataTabElement);
            dataTabElement.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickImageTab() {
        try {
            WebElement imageTabElement = wait.until(ExpectedConditions.elementToBeClickable(imageTab));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", imageTabElement);
            imageTabElement.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOptionTab() {
        try {
            WebElement optionTabElement = wait.until(ExpectedConditions.elementToBeClickable(optionTab));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", optionTabElement);
            optionTabElement.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterModel(String model) {
        WebElement modelField = wait.until(ExpectedConditions.visibilityOfElementLocated(modelInput));
        modelField.clear();
        modelField.sendKeys(model);
    }

    public void enterPrice(String price) {
        WebElement priceField = wait.until(ExpectedConditions.visibilityOfElementLocated(priceInput));
        priceField.clear();
        priceField.sendKeys(price);
    }

    public void enterQuantity(String quantity) {
        WebElement quantityField = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        quantityField.clear();
        quantityField.sendKeys(quantity);
    }

    public void selectStockStatus(String status) {
        try {
            // Click on the stock status input to open dropdown
            WebElement stockStatusInput = wait.until(ExpectedConditions.elementToBeClickable(stockStatusSelect));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", stockStatusInput);
            stockStatusInput.click();
            Thread.sleep(500);
            
            // Find and click the dropdown option
            By optionLocator = By.xpath("//li[contains(@class, 'dropdown-item') and contains(text(), '" + status + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectStatus(String status) {
        try {
            // Click on the status input to open dropdown
            WebElement statusInput = wait.until(ExpectedConditions.elementToBeClickable(statusSelect));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", statusInput);
            statusInput.click();
            Thread.sleep(500);
            
            // Find and click the dropdown option
            By optionLocator = By.xpath("//li[contains(@class, 'dropdown-item') and contains(text(), '" + status + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickSave() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveBtn);
        saveBtn.click();
    }

    public void createProduct(String name, String metaTitle, String model, String price, String quantity, String status) {
        enterProductName(name);
        enterMetaTagTitle(metaTitle);
        clickDataTab();
        enterModel(model);
        enterPrice(price);
        enterQuantity(quantity);
        selectStatus(status);
        clickSave();
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
