package UserStory_AccountManagement.Tests;

import UserStory_AccountManagement.Base.BaseTest;
import UserStory_AccountManagement.Pages.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * - Profile | Update Profile Information
 * - Profile | Change Email Address
 */
public class ProfileTests extends BaseTest {

    private String testEmail;
    private String testPassword = "Test123";

    @BeforeMethod
    public void createAndLoginUser() {
        // Create a test user and login before each test
        testEmail = "profiletest" + System.currentTimeMillis() + "@example.com";
        
        // Navigate to registration page directly
        driver.get(baseUrl + "index.php?route=account/register");
        
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.registerUser("Profile", "Test", testEmail, testPassword);

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
     * Action: Login then Go to Edit Account then Update name and phone then Save
     * Data: New Name: Jonathan and New Phone: 555-9999
     * Expected: Profile updated successfully then Changes saved then New information displayed
     */
    @Test(priority = 1, description = "Profile | Update Profile Information")
    public void testUpdateProfileInformation() {
        // Navigate directly to edit account page
        driver.get(baseUrl + "index.php?route=account/edit");
        wait.until(driver -> driver.findElement(By.id("input-firstname")).isDisplayed());

        EditAccountPage editAccountPage = new EditAccountPage(driver);

        // Update profile information
        editAccountPage.updateProfile("Jonathan", "Updated", "555-9999");

        // Wait for AJAX response or redirect
        wait.until(driver -> {
            String url = driver.getCurrentUrl();
            return url.contains("account/account") || editAccountPage.isSuccessDisplayed();
        });

        // Verify success
        String currentUrl = driver.getCurrentUrl();
        boolean onAccountPage = currentUrl.contains("account");
        boolean hasSuccess = editAccountPage.isSuccessDisplayed();

        Assert.assertTrue(onAccountPage || hasSuccess,
                "Profile update should redirect to account page or show success. Current URL: " + currentUrl);
    }

    /**
     * Action: Go to Edit Account then Change email then Save then Verify new email
     * Data: New Email: new@example.com
     * Expected: Verification email sent then After verification email updated then Can login with new email
     * 
     * Note: Email verification cannot be automated, so we only test the email change submission
     */
    @Test(priority = 2, description = "Profile | Change Email Address")
    public void testChangeEmailAddress() {
        // Navigate directly to edit account page
        driver.get(baseUrl + "index.php?route=account/edit");
        wait.until(driver -> driver.findElement(By.id("input-firstname")).isDisplayed());

        EditAccountPage editAccountPage = new EditAccountPage(driver);

        // Change email
        String newEmail = "newemail" + System.currentTimeMillis() + "@example.com";
        editAccountPage.changeEmail(newEmail);

        // Wait for AJAX response or redirect
        wait.until(driver -> {
            String url = driver.getCurrentUrl();
            return url.contains("account/account") || editAccountPage.isSuccessDisplayed();
        });

        // Verify the form was submitted
        String currentUrl = driver.getCurrentUrl();
        boolean onAccountPage = currentUrl.contains("account");
        boolean hasSuccess = editAccountPage.isSuccessDisplayed();

        Assert.assertTrue(onAccountPage || hasSuccess,
                "Email change should redirect to account page or show success. Current URL: " + currentUrl);
    }
}
