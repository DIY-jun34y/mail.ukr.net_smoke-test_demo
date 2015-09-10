package pages;

import helpers.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage extends BaseClass{

	@FindBy (css = ".signup-done__view>h2") public WebElement regDoneMsg;
	@FindBy (css = ".signup-done__view>button") public WebElement goToMailboxBtn;
	@FindBy (css = ".mailbox") public WebElement mailboxName;
	@FindBy (css = ".info") public WebElement termsHint;
	
	@FindBy (css = ".language") public WebElement langMenu;
	@FindBy (css = "[href*=\"lang=uk\"]") public WebElement langUA;
	@FindBy (css = "[href*=\"lang=ru\"]") public WebElement langRU;
	@FindBy (css = "[href*=\"lang=en\"]") public WebElement langENG;
	
	public ConfirmationPage(WebDriver driver){
		super(driver);	
	}

}
