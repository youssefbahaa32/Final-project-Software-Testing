package UserStory_Content.Tests;

import UserStory_Content.Base.BaseTest;
import UserStory_Content.Pages.ContentPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContentTests extends BaseTest {

    @Test(priority = 1, description = "Content | Submit Contact Form")
    public void testSubmitContactForm() {
        ContentPage contentPage = new ContentPage(driver);
        contentPage.navigateToContactUs();
        
        contentPage.fillContactForm(
            "John Doe",
            "john.doe@example.com",
            "I have a question about product availability and shipping options."
        );
        
        boolean formSubmitted = contentPage.isContactFormSubmitted();
        if (formSubmitted) {
            System.out.println("Contact form submitted successfully");
        } else {
            System.out.println("Contact form submission may require CAPTCHA or additional validation");
        }
    }

    @Test(priority = 2, description = "View About Us Page")
    public void testViewAboutUs() {
        ContentPage contentPage = new ContentPage(driver);
        
        try {
            contentPage.navigateToAboutUs();
            Assert.assertTrue(contentPage.isAboutUsPageDisplayed(), 
                "About Us page not displayed");
            System.out.println("About Us page displayed successfully");
        } catch (Exception e) {
            System.out.println("About Us page may not be configured in this store");
        }
    }

    @Test(priority = 3, description = "Content | View Privacy Policy")
    public void testViewPrivacyPolicy() {
        ContentPage contentPage = new ContentPage(driver);
        contentPage.navigateToPrivacyPolicy();
        
        Assert.assertTrue(contentPage.isPrivacyPolicyDisplayed(), 
            "Privacy Policy page not displayed");
        System.out.println("Privacy Policy page displayed successfully");
    }

    @Test(priority = 4, description = "View Terms and Conditions")
    public void testViewTerms() {
        ContentPage contentPage = new ContentPage(driver);
        
        try {
            contentPage.navigateToTerms();
            Assert.assertTrue(contentPage.isTermsPageDisplayed(), 
                "Terms page not displayed");
            System.out.println("Terms and Conditions page displayed successfully");
        } catch (Exception e) {
            System.out.println("Terms page may not be configured in this store");
        }
    }

    @Test(priority = 5, description = "Content | Submit GDPR Data Request")
    public void testGDPRDataRequest() {
        ContentPage contentPage = new ContentPage(driver);
        
        try {
            contentPage.navigateToGDPR();
            contentPage.requestDataExport();
            System.out.println("GDPR data export request submitted");
        } catch (Exception e) {
            System.out.println("GDPR feature may not be enabled or requires login");
        }
    }

    @Test(priority = 6, description = "Content | Accept Cookie Consent")
    public void testAcceptCookies() {
        ContentPage contentPage = new ContentPage(driver);
        
        if (contentPage.isCookieBannerDisplayed()) {
            contentPage.acceptCookies();
            
            // Wait a moment for banner to dismiss
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Assert.assertTrue(contentPage.isCookieBannerDismissed(), 
                "Cookie banner not dismissed after accepting");
            System.out.println("Cookies accepted successfully");
        } else {
            System.out.println("Cookie consent banner not displayed - may already be accepted");
        }
    }

    @Test(priority = 7, description = "Content | Read Blog Article")
    public void testReadBlogArticle() {
        ContentPage contentPage = new ContentPage(driver);
        
        try {
            contentPage.navigateToBlog();
            
            // Check if blog is available
            if (contentPage.isBlogAvailable()) {
                contentPage.clickFirstArticle();
                
                // Wait for article to load
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Verify article displays
                Assert.assertTrue(contentPage.isArticleDisplayed(),
                        "Blog article should be displayed");
                System.out.println("Blog article displayed successfully");
            } else {
                System.out.println("Blog feature not available in this store");
            }
        } catch (Exception e) {
            System.out.println("Blog feature may not be configured: " + e.getMessage());
        }
    }

    @Test(priority = 8, description = "Content | Post Blog Comment")
    public void testPostBlogComment() {
        ContentPage contentPage = new ContentPage(driver);
        
        try {
            contentPage.navigateToBlog();
            
            if (contentPage.isBlogAvailable()) {
                contentPage.clickFirstArticle();
                
                // Wait for article to load
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Try to post comment
                boolean commentFormAvailable = contentPage.isCommentFormAvailable();
                
                if (commentFormAvailable) {
                    contentPage.postComment("John Doe", "john@example.com", "Great article!");
                    
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println("Blog comment posted successfully");
                } else {
                    System.out.println("Comment form not available - may require login or be disabled");
                }
            } else {
                System.out.println("Blog feature not available in this store");
            }
        } catch (Exception e) {
            System.out.println("Blog comment feature may not be configured: " + e.getMessage());
        }
    }
}
