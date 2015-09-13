package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import helpers.BaseClass;

import static tests.Test_Login.validLogin;
import pages.*;


public class Test_Registration extends BaseClass{
	
	private SoftAssert sa;
	private Registration regPage;	 
	private ConfirmationPage confirm;
	private Login login; 
	private Navigation navigation;		
	
	@BeforeClass
	public void setup() {			
		regPage = new Registration();
		confirm = new ConfirmationPage();
		login = new Login();
		navigation = new Navigation();
			
	}	
		
	@BeforeMethod
	public void newSoftAssertObject(){
		this.sa = new SoftAssert();			
	}
			
	public boolean loggedIn(){
		String pageTitle = driver.getTitle();
		if (pageTitle.contains(validLogin+"@ukr.net")) {
			return true;
		}else {
			return false;
		}		
	}
	
	//User can register - skipped - capcha!
	@Test (description = "(Un)Successful registration")
	public void testRegistration() {
		regPage.load()
		.setLanguage("RU")
		.setUsername("newuser99")
		.setPassword("пароль")
		.setFirstName("им€")
		.setSurName("фамили€")
		.setGender("M")
		.setDateOfBirth("22", "12", "2000")
		.setMobile("093 0000000")
		.setEmail("wrwe@erte.com");
		//set capcha...
		regPage.submitBtn.click();
		//assert success page is opened...
		sa.assertEquals(driver.getTitle(), "–егистраци€ выполнена успешно");
		sa.assertTrue(confirm.goToMailboxBtn.isDisplayed(), " 'go to Mailbox' button is not found!");
		sa.assertAll();		
	}
	
	@Test(description = "Checking registration links on the login page")
	public void testCheckRegistrationLinks() {
		login.load();
		if (loggedIn()) {navigation.logout();}		
		sa.assertEquals(
			login.registrationBtn.getAttribute("href"),login.registrationLink.getAttribute("href"));
		login.load().registrationBtn.click();
		sa.assertTrue(regPage.surNameField.isDisplayed(),"Can't find a 'Surname' text field ! ");
		sa.assertTrue(regPage.submitBtn.isDisplayed(),"Can't find a 'Submit' button !");			
		sa.assertAll();
	}
	
	@Test(description = "Checking error messages on the Registration page - 'Empty fields'") 
	public void testRegPageEmptyFieldsErrorHandling() throws IOException {
		regPage.load().setLanguage("RU");
		regPage.submitBtn.click();		
		BufferedReader fileIn = null;
		try {
			fileIn = new BufferedReader(new FileReader("src/test/java/helpers/emptyErrorMsgRU.ser"));
			for (WebElement errMsg : regPage.errorsList) {			
				sa.assertEquals(errMsg.getText(), fileIn.readLine());		
			}
		} catch (IOException e) {e.printStackTrace();
		} finally { 
			if (fileIn != null) fileIn.close();	
		}
		sa.assertAll();
	}	
		
}