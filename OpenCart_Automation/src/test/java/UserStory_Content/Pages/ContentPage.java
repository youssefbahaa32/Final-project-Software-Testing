package UserStory_Content.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators
    private By contactUsLink = By.linkText("Contact Us");
    private By aboutUsLink = By.linkText("About Us");
    private By privacyPolicyLink = By.linkText("Privacy Policy");
    private By termsLink = By.linkText("Terms & Conditions");
    
    // Contact Form
    private By nameField = By.id("input-name");
    private By emailField = By.id("input-email");
    private By enquiryField = By.id("input-enquiry");
    private By submitButton = By.cssSelector("button[type='submit']");
    private By successMessage = By.cssSelector(".alert-success");
    
    // GDPR
    private By gdprLink = By.linkText("GDPR");
    private By requestDataButton = By.linkText("Request My Data");
    private By exportDataRadio = By.cssSelector("input[value='export']");
    private By removeDataRadio = By.cssSelector("input[value='remove']");
    private By gdprSubmitButton = By.cssSelector("button[type='submit']");
    
    // Cookie Consent
    private By cookieBanner = By.id("cookie-consent");
    private By acceptCookiesButton = By.id("accept-cookies");
    private By rejectCookiesButton = By.id("reject-cookies");
    private By cookieSettingsLink = By.linkText("Cookie Settings");
    
    public ContentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void navigateToContactUs() {
        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(contactUsLink));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(contactUsLink)).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillContactForm(String name, String email, String enquiry) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(enquiryField).sendKeys(enquiry);
        driver.findElement(submitButton).click();
    }

    public boolean isContactFormSubmitted() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return driver.findElement(successMessage).getText().contains("Success");
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToAboutUs() {
        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(aboutUsLink));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(aboutUsLink)).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAboutUsPageDisplayed() {
        return driver.getTitle().toLowerCase().contains("about") || 
               driver.getCurrentUrl().contains("about");
    }

    public void navigateToPrivacyPolicy() {
        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(privacyPolicyLink));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(privacyPolicyLink)).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPrivacyPolicyDisplayed() {
        return driver.getTitle().toLowerCase().contains("privacy") || 
               driver.getCurrentUrl().contains("privacy");
    }

    public void navigateToTerms() {
        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(termsLink));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(termsLink)).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTermsPageDisplayed() {
        return driver.getTitle().toLowerCase().contains("terms") || 
               driver.getCurrentUrl().contains("terms");
    }

    public void navigateToGDPR() {
        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(gdprLink));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", link);
            Thread.sleep(500);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(gdprLink)).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestDataExport() {
        wait.until(ExpectedConditions.elementToBeClickable(exportDataRadio)).click();
        driver.findElement(gdprSubmitButton).click();
    }

    public void requestDataRemoval() {
        wait.until(ExpectedConditions.elementToBeClickable(removeDataRadio)).click();
        driver.findElement(gdprSubmitButton).click();
    }

    public boolean isCookieBannerDisplayed() {
        try {
            return driver.findElement(cookieBanner).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptCookies() {
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
    }

    public void rejectCookies() {
        wait.until(ExpectedConditions.elementToBeClickable(rejectCookiesButton)).click();
    }

    public boolean isCookieBannerDismissed() {
        try {
            return !driver.findElement(cookieBanner).isDisplayed();
        } catch (Exception e) {
            return true; // Banner not found means it's dismissed
        }
    }

    public void navigateToBlog() {
        try {
            driver.findElement(By.linkText("Blog")).click();
        } catch (Exception e) {
            // Try alternative navigation
            driver.get(driver.getCurrentUrl().replace("index.php", "index.php?route=blog/blog"));
        }
    }

    public boolean isBlogAvailable() {
        try {
            return driver.findElements(By.cssSelector(".blog-post, .article, [class*='blog']")).size() > 0 ||
                   driver.getCurrentUrl().contains("blog");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstArticle() {
        try {
            WebElement firstArticle = driver.findElement(By.cssSelector(".blog-post a, .article a, [class*='blog'] a"));
            firstArticle.click();
        } catch (Exception e) {
            System.out.println("Blog article link not found: " + e.getMessage());
        }
    }

    public boolean isArticleDisplayed() {
        try {
            return driver.findElements(By.cssSelector(".article-content, .blog-content, [class*='article']")).size() > 0 ||
                   driver.getPageSource().contains("article");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCommentFormAvailable() {
        try {
            return driver.findElements(By.cssSelector("textarea[name*='comment'], #comment-form")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void postComment(String name, String email, String comment) {
        try {
            driver.findElement(By.id("input-name")).sendKeys(name);
            driver.findElement(By.id("input-email")).sendKeys(email);
            driver.findElement(By.cssSelector("textarea[name*='comment']")).sendKeys(comment);
            driver.findElement(By.cssSelector("button[type='submit']")).click();
        } catch (Exception e) {
            System.out.println("Comment form fields not found: " + e.getMessage());
        }
    }
}
