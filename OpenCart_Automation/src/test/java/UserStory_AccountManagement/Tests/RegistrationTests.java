package UserStory_AccountManagement.Tests;

import UserStory_AccountManagement.Base.BaseTest;
import UserStory_AccountManagement.Pages.HomePage;
import UserStory_AccountManagement.Pages.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * - Registration | Successful Registration
 * - Registration | Registration with Existing Email
 * - Registration | Registration with Invalid Data
 */
public class RegistrationTests extends BaseTest {

    // Static email to use for "existing email" test
    private static final String EXISTING_EMAIL = "existing@example.com";
    private static boolean existingUserCreated = false;

    /**
     * CSV Test Case: Registration | Successful Registration
     * Action: Navigate to homepage then Click Register then Fill all required fields then Accept Privacy Policy then Submit
     * Data: Email: john@example.com and Password: Test123
     * Expected: Registration successful then User logged in then Welcome email sent then Account created
     */
    @Test(priority = 1, description = "Registration | Successful Registration")
    public void testSuccessfulRegistration() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = homePage.navigateToRegistration();

        // Generate unique email to avoid conflicts
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "john" + timestamp + "@example.com";

        registrationPage.registerUser("John", "Doe", email, "Test123");

        // Wait for redirect or success
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify registration successful - should redirect to success page or account page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("success") || currentUrl.contains("account"),
                "Registration should redirect to success or account page. Current URL: " + currentUrl);
    }

    /**
     * Registration | Registration with Existing Email
     * Action: Navigate to Register then Enter existing email then Submit
     * Data: Email: existing@example.com
     * Expected: Error message displayed then Registration fails then User remains on page
     * Note: This test creates a user first, then tries to register with the same email
     */
    @Test(priority = 2, description = "Registration | Registration with Existing Email")
    public void testRegistrationWithExistingEmail() {
        // First, create a user if not already created
        if (!existingUserCreated) {
            HomePage homePage = new HomePage(driver);
            RegistrationPage registrationPage = homePage.navigateToRegistration();
            registrationPage.registerUser("Existing", "User", EXISTING_EMAIL, "Test123");
            
            // Wait for registration
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            existingUserCreated = true;
            
            // Navigate back to home
            driver.get(baseUrl);
        }

        // Now try to register with the same email
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = homePage.navigateToRegistration();
        registrationPage.registerUser("Another", "User", EXISTING_EMAIL, "Test123");

        // Wait for error
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify error message displayed OR still on registration page
        boolean hasError = registrationPage.isErrorDisplayed();
        boolean stillOnRegisterPage = driver.getCurrentUrl().contains("register");
        
        Assert.assertTrue(hasError || stillOnRegisterPage,
                "Error message should be displayed OR should remain on registration page for existing email. " +
                "Current URL: " + driver.getCurrentUrl());
    }

    /**
     * CSV Test Case: Registration | Registration with Invalid Data
     * Action: Navigate to Register then Enter invalid email or weak password then Submit
     * Data: Email: invalid.com or Password: 123
     * Expected: Validation errors shown then Registration blocked then Fields highlighted
     */
    @Test(priority = 3, description = "Registration | Registration with Invalid Data")
    public void testRegistrationWithInvalidData() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = homePage.navigateToRegistration();

        // Try with invalid email format
        registrationPage.registerUser("Test", "User", "invalid.com", "Test123");

        // Wait for validation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify still on registration page (not redirected)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("register"),
                "Should remain on registration page with invalid data. Current URL: " + currentUrl);
    }
}
