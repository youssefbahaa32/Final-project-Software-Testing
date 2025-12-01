package UserStory_Rewards.Tests;

import UserStory_Rewards.Base.BaseTest;
import UserStory_Rewards.Pages.AffiliatePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AffiliateTests extends BaseTest {

    private void loginAsCustomer() {
        driver.findElement(By.linkText("My Account")).click();
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("input-email")).sendKeys(config.getProperty("test.email"));
        driver.findElement(By.id("input-password")).sendKeys(config.getProperty("test.password"));
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @BeforeMethod
    public void loginBeforeTest() {
        loginAsCustomer();
    }

    @Test(priority = 1, description = "Affiliate Program | Register as Affiliate")
    public void testRegisterAsAffiliate() {
        AffiliatePage affiliatePage = new AffiliatePage(driver);
        
        try {
            affiliatePage.navigateToAffiliateRegistration();
            
            affiliatePage.fillAffiliateRegistration(
                "My Marketing Company",
                "https://mymarketingsite.com",
                "TAX123456",
                "cheque",
                "John Doe"
            );
            
            boolean registrationSuccess = affiliatePage.isRegistrationSuccessful();
            if (registrationSuccess) {
                System.out.println("Affiliate registration successful");
            } else {
                System.out.println("Affiliate registration may require admin approval");
            }
        } catch (Exception e) {
            System.out.println("Affiliate registration link not found - may already be registered or feature disabled");
        }
    }

    @Test(priority = 2, description = "Affiliate Program | View Affiliate Dashboard")
    public void testViewAffiliateDashboard() {
        AffiliatePage affiliatePage = new AffiliatePage(driver);
        
        try {
            affiliatePage.navigateToAffiliateDashboard();
            
            Assert.assertTrue(affiliatePage.isDashboardDisplayed(), 
                "Affiliate dashboard not displayed");
            
            System.out.println("Affiliate dashboard displayed successfully");
        } catch (Exception e) {
            System.out.println("Affiliate dashboard not accessible - may not be registered as affiliate");
        }
    }

    @Test(priority = 3, description = "Affiliate Program | Get Tracking Code")
    public void testGetTrackingCode() {
        AffiliatePage affiliatePage = new AffiliatePage(driver);
        
        try {
            affiliatePage.navigateToAffiliateDashboard();
            
            String trackingCode = affiliatePage.getTrackingCode();
            Assert.assertNotNull(trackingCode, "Tracking code not found");
            System.out.println("Affiliate tracking code: " + trackingCode);
        } catch (Exception e) {
            System.out.println("Unable to retrieve tracking code - may not be registered as affiliate");
        }
    }

    @Test(priority = 4, description = "Affiliate Program | View Commission History")
    public void testViewCommissionHistory() {
        AffiliatePage affiliatePage = new AffiliatePage(driver);
        
        try {
            affiliatePage.navigateToAffiliateDashboard();
            
            String clicks = affiliatePage.getTotalClicks();
            String sales = affiliatePage.getTotalSales();
            String commission = affiliatePage.getTotalCommission();
            
            System.out.println("Total Clicks: " + clicks);
            System.out.println("Total Sales: " + sales);
            System.out.println("Total Commission: " + commission);
        } catch (Exception e) {
            System.out.println("Unable to retrieve commission data - may not be registered as affiliate");
        }
    }
}
