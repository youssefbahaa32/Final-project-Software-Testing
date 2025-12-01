package UserStory_AccountManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class AddressFormPage extends BasePage {

    // Locators based on actual address_form.twig template
    private By firstNameInput = By.id("input-firstname");
    private By lastNameInput = By.id("input-lastname");
    private By address1Input = By.id("input-address-1");
    private By cityInput = By.id("input-city");
    private By postcodeInput = By.id("input-postcode");
    private By countrySelect = By.id("input-country");
    private By zoneSelect = By.id("input-zone");
    private By defaultYesRadio = By.id("input-default-yes");
    private By defaultNoRadio = By.id("input-default-no");
    private By continueButton = By.xpath("//button[@type='submit' and contains(@class, 'btn-primary')]");
    private By successAlert = By.xpath("//div[contains(@class, 'alert-success')]");

    public AddressFormPage(WebDriver driver) {
        super(driver);
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void enterAddress1(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(address1Input)).clear();
        driver.findElement(address1Input).sendKeys(address);
    }

    public void enterCity(String city) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityInput)).clear();
        driver.findElement(cityInput).sendKeys(city);
    }

    public void enterPostcode(String postcode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(postcodeInput)).clear();
        driver.findElement(postcodeInput).sendKeys(postcode);
    }

    public void selectCountry(String countryName) {
        Select country = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(countrySelect)));
        country.selectByVisibleText(countryName);
        
        // Wait for zones to load (AJAX call)
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(zoneSelect, 0));
    }

    public void selectZone(String zoneName) {
        Select zone = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(zoneSelect)));
        zone.selectByVisibleText(zoneName);
    }

    public void setAsDefault(boolean isDefault) {
        if (isDefault) {
            wait.until(ExpectedConditions.elementToBeClickable(defaultYesRadio)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(defaultNoRadio)).click();
        }
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    // CSV Test Case: Address | Add New Address
    public void addAddress(String firstName, String lastName, String address, String city, 
                          String postcode, String country, String zone, boolean setDefault) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterAddress1(address);
        enterCity(city);
        enterPostcode(postcode);
        selectCountry(country);
        selectZone(zone);
        setAsDefault(setDefault);
        clickContinue();
    }

    public boolean isSuccessDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
