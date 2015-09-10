package pages;

import helpers.BaseClass;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BaseClass{
	
	public static final String REGISTER_URL = "https://mail.ukr.net/q/reg";
	
	@FindBy (css = ".language") public WebElement langMenu;
	@FindBy (css = "[href*=\"lang=uk\"]") public WebElement langUA;
	@FindBy (css = "[href*=\"lang=ru\"]") public WebElement langRU;
	@FindBy (css = "[href*=\"lang=en\"]") public WebElement langENG;
	
	@FindBy (css = "[name~=\"username\"]") public WebElement loginField;
	@FindBy (css = "[name~=\"secret\"]") public WebElement passwordField;
	@FindBy (css = "[name~=\"name\"]") public WebElement fNameField;
	@FindBy (css = "[name~=\"surname\"]") public WebElement surNameField;
	
	@FindBy (css = "[class~=\"radio\"]") public WebElement genderMale;
	@FindBy (css = "[value~=\"F\"]") public WebElement genderFemale;
	
	@FindBy (css = "[name~=\"year\"]") public WebElement bYear;
	@FindBy (css = "[name~=\"day\"]") public WebElement bDay;
	@FindBy (css = "[name~=\"month\"]") public WebElement bMonthList;
	@FindBy (css = "[data-month~=\"12\"]") public WebElement bMonth;
		
	@FindBy (css = "[name~=\"mobile\"]") public WebElement mobileNumber;
	@FindBy (css = "[name~=\"email\"]") public WebElement email;
	
	@FindBy (css = "[name~=\"capcha\"]") public WebElement capcha;
	@FindBy (css = ".form__footer>button") public WebElement submitBtn;
	
	//сообщения об ошибках заполнения
	@FindBy (css = ".form__error.show") public List<WebElement> errorsList;
	
		
	public RegisterPage(WebDriver driver){
		super(driver);	
	}
	
	public RegisterPage load() {
		load(REGISTER_URL);
		return this;
	}
	public RegisterPage setLanguage(String lang){
		langMenu.click();
		switch(lang){
		case "UA":			
			langUA.click();
			break;
		case "RU":
			langRU.click();
			break;
		case "ENG":
			langENG.click();
			break;
		}
		return this;
	}	
	
	public RegisterPage setUsername(String string) {
		loginField.sendKeys(string);
		return this;
	}

	public RegisterPage setPassword(String string) {
		passwordField.sendKeys(string);
		return this;
	}
	
	public RegisterPage setFirstName(String string) {
		fNameField.sendKeys(string);
		return this;
	}
	
	public RegisterPage setSurName(String string) {
		surNameField.sendKeys(string);
		return this;
	}
	
	public RegisterPage setGender(String string) {
		switch(string){
		case "F": 
			genderFemale.click();
			break;
		case "M": 
			genderMale.click();
			break;
		}
		return this;
	}
		
	public RegisterPage setDateOfBirth(String day, String month, String year){
		bDay.sendKeys(day);
		bYear.sendKeys(year);
		bMonthList.click();
		bMonth.click();
		return this;
	}
			
	public RegisterPage setMobile(String string){
		mobileNumber.sendKeys(string);
		return this;
	}
	
	public RegisterPage setEmail(String string){
		email.sendKeys(string);
		return this;
	}

}
