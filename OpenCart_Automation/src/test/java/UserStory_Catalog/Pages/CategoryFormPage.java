package UserStory_Catalog.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoryFormPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By categoryNameInput = By.id("input-name-1");
    private By metaTagTitleInput = By.id("input-meta-title-1");
    private By statusSelect = By.id("input-status");
    private By saveButton = By.cssSelector("button[data-bs-toggle='tooltip']");
    private By successAlert = By.cssSelector(".alert-success");

    public CategoryFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void enterCategoryName(String name) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryNameInput));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterMetaTagTitle(String title) {
        WebElement metaField = wait.until(ExpectedConditions.visibilityOfElementLocated(metaTagTitleInput));
        metaField.clear();
        metaField.sendKeys(title);
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

    public void createCategory(String name, String metaTitle, String status) {
        enterCategoryName(name);
        enterMetaTagTitle(metaTitle);
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
