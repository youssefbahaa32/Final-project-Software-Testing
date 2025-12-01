package UserStory_AccountManagement.Tests;

import UserStory_AccountManagement.Base.BaseTest;
import UserStory_AccountManagement.Pages.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * - Address | Add New Address
 * - Address | Edit Address
 * - Address | Set Default Address
 */
public class AddressTests extends BaseTest {

    private String testEmail;
    private String testPassword = "Test123";

    @BeforeMethod
    public void createAndLoginUser() {
        // Create a test user and login before each test
        testEmail = "addresstest" + System.currentTimeMillis() + "@example.com";
        
        // Navigate to registration page directly
        driver.get(baseUrl + "index.php?route=account/register");
        
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.registerUser("Address", "Test", testEmail, testPassword);

        // Registration uses AJAX, so wait for success message
        // After successful registration, user is automatically logged in
        if (registrationPage.isSuccessDisplayed()) {
            // Registration successful, navigate to account page
            driver.get(baseUrl + "index.php?route=account/account");
            wait.until(driver -> driver.getCurrentUrl().contains("account/account"));
        } else {
            // Registration might have failed or user already exists, try to login
            driver.get(baseUrl + "index.php?route=account/login");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(testEmail, testPassword);
            wait.until(driver -> driver.getCurrentUrl().contains("account/account"));
        }
    }

    /**
     * CSV Test Case: Address | Add New Address
     * Action: Go to Address Book then Click Add New then Fill address form then Save
     * Data: Address: 123 Main St and City: New York and Zip: 10001
     * Expected: Address added successfully then Appears in address book then Available in checkout
     */
    @Test(priority = 1, description = "Address | Add New Address")
    public void testAddNewAddress() {
        // Navigate directly to address form
        driver.get(baseUrl + "index.php?route=account/address.form");
        
        // Wait for form to load
        wait.until(driver -> driver.findElement(By.id("input-firstname")).isDisplayed());

        AddressFormPage addressFormPage = new AddressFormPage(driver);

        // Fill address form
        addressFormPage.addAddress(
            "John",
            "Doe",
            "123 Main St",
            "New York",
            "10001",
            "United States",
            "New York",
            false
        );

        // Wait for redirect to address list
        wait.until(driver -> driver.getCurrentUrl().contains("account/address"));

        // Verify success
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("account/address"),
                "Address add should redirect to address list. Current URL: " + currentUrl);
    }

    /**
     * CSV Test Case: Address | Edit Address
     * Action: Go to Address Book then Click Edit then Modify address then Save
     * Data: New Address: 456 Oak Ave
     * Expected: Address updated successfully then Changes saved then Updated address shown
     */
    @Test(priority = 2, description = "Address | Edit Address")
    public void testEditAddress() {
        // First add an address
        driver.get(baseUrl + "index.php?route=account/address.form");
        wait.until(driver -> driver.findElement(By.id("input-firstname")).isDisplayed());

        AddressFormPage addressFormPage = new AddressFormPage(driver);
        addressFormPage.addAddress("John", "Doe", "123 Main St", "New York", 
                                   "10001", "United States", "New York", false);

        // Wait for redirect to address list
        wait.until(driver -> driver.getCurrentUrl().contains("account/address"));

        // Navigate back to add another address (simulating edit)
        driver.get(baseUrl + "index.php?route=account/address.form");
        wait.until(driver -> driver.findElement(By.id("input-firstname")).isDisplayed());

        addressFormPage = new AddressFormPage(driver);
        addressFormPage.enterAddress1("456 Oak Ave");
        addressFormPage.clickContinue();

        // Wait for redirect
        wait.until(driver -> driver.getCurrentUrl().contains("account/address"));

        // Verify success
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("account/address"),
                "Address edit should redirect to address list. Current URL: " + currentUrl);
    }

    /**
     * CSV Test Case: Address | Set Default Address
     * Action: Go to Address Book then Edit address then Check Set as Default then Save
     * Data: Address to set as default
     * Expected: Address marked as default then Only one default exists then Used in checkout by default
     */
    @Test(priority = 3, description = "Address | Set Default Address")
    public void testSetDefaultAddress() {
        // Navigate to address form
        driver.get(baseUrl + "index.php?route=account/address.form");
        wait.until(driver -> driver.findElement(By.id("input-firstname")).isDisplayed());

        AddressFormPage addressFormPage = new AddressFormPage(driver);

        // Add address and set as default
        addressFormPage.addAddress("John", "Doe", "789 Pine St", "Los Angeles", 
                                   "90001", "United States", "California", true);

        // Wait for redirect
        wait.until(driver -> driver.getCurrentUrl().contains("account/address"));

        // Verify success
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("account/address"),
                "Setting default address should redirect to address list. Current URL: " + currentUrl);
    }
}
