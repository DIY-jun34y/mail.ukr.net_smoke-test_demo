package pages;

import helpers.BaseClass;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login extends BaseClass{
	
	public final String LoginURL = "https://mail.ukr.net";	
		
	@FindBy (id = "login") public WebElement userNameField;
	@FindBy (id = "password") public WebElement passwordField;	
	@FindBy (css = ".login__foot>button") public WebElement loginBtn;	
	@FindBy (css = "#login-form>label>input") public  WebElement publicPlaceCheckbox;	
	@FindBy (id = "signup-link") public WebElement registrationLink;	
	@FindBy (id = "recovery-link") public WebElement recoveryLink;	
	@FindBy (css = ".reg") public WebElement registrationBtn;
	
	@FindBy (css = ".login__error.show") public WebElement loginErrorMsg;
		
	@FindBy (css = "[href*=\"lang=uk\"]") public WebElement langUALink;
	@FindBy (css = "[href*=\"lang=ru\"]") public WebElement langRULink;
	@FindBy (css = "[href*=\"lang=en\"]") public WebElement langENGLink;
	@FindBy (linkText = "support@ukr.net") public WebElement supportLink;
	@FindBy (linkText = "Угода конфіденційності") public WebElement privacyTermsLink;
	@FindBy (linkText = "Умови використання") public WebElement usageTermsLink;
	@FindBy (linkText = "Мобільна версія") public WebElement mobileVersionLink;
	
	public Login(){
		//super(driver);WebDriver driver
		//this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Login load() {
		load(LoginURL);
		return this;
	}
	
	public Login setLanguage(String lang){
		switch(lang){
		case "UA": 
			langUALink.click();
			break;
		case "RU":
			langRULink.click();
			break;
		case "ENG":
			langENGLink.click();
			break;
		}
		return this;
	}
	
		
	public void setPublicPlace(){
		if (!publicPlaceCheckbox.isSelected()) publicPlaceCheckbox.click();				
	}
	
	public void setUserName(String username) {
		userNameField.sendKeys(username);	
	}

	public void setPassword(String password) {
		passwordField.sendKeys(password);		
	}
	
	public void submitLoginBtn() {
		loginBtn.click();
	}
		
	public void loginAs(String username, String password) {
		setUserName(username);
		setPassword(password);
		submitLoginBtn();
    }
}
