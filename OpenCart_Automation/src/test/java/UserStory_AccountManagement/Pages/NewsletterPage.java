package UserStory_AccountManagement.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsletterPage extends BasePage {

    @FindBy(xpath = "//input[@name='newsletter' and @value='1']")
    private WebElement subscribeRadio;

    @FindBy(xpath = "//input[@name='newsletter' and @value='0']")
    private WebElement unsubscribeRadio;

    @FindBy(xpath = "//button[@type='submit' and contains(@class, 'btn-primary')]")
    private WebElement continueButton;

    @FindBy(xpath = "//a[contains(@class, 'btn-light')]")
    private WebElement backButton;

    public NewsletterPage(WebDriver driver) {
        super(driver);
    }

    public void selectSubscribe() {
        clickElement(subscribeRadio);
    }

    public void selectUnsubscribe() {
        clickElement(unsubscribeRadio);
    }

    public void clickContinue() {
        clickElement(continueButton);
    }

    public void clickBack() {
        clickElement(backButton);
    }

    public void updateNewsletterSubscription(boolean subscribe) {
        if (subscribe) {
            selectSubscribe();
        } else {
            selectUnsubscribe();
        }
        clickContinue();
    }
}
