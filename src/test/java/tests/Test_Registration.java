package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import helpers.BaseClass;
import helpers.TestDataProvider;

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
	
	@Test (description = "(Un)Successful registration - CAPCHA!",
			dataProvider = "regData", dataProviderClass = TestDataProvider.class)
	public void test_Registration
			(String testCase, String loginName, String password, String fname, String surname, 
			String gender, String bDay, String bMonth, String bYear, String mobile, String email) {
		regPage
		.load().setLanguage("RU")
		.setLoginName(loginName).setPassword(password)
		.setFirstName(fname).setSurName(surname)
		.setGender(gender)
		.setDateOfBirth(bDay, bMonth, bYear)
		.setMobile(mobile).setEmail(email)
		//.setCapcha("capcha")
		.submit();
		//assert success page is opened...
		sa.assertEquals(driver.getTitle(), "Регистрация выполнена успешно");		
		sa.assertAll();		
	}
	
	@Test(description = "Checking registration links on the login page")
	public void test_CheckRegistrationLinks() {
		login.load();
		if (loggedIn()) {navigation.logout();}		
		sa.assertEquals(
			login.registrationBtn.getAttribute("href"),login.registrationLink.getAttribute("href"));
		login.load().registrationBtn.click();
		sa.assertTrue(regPage.surNameField.isDisplayed(),"Can't find a 'Surname' text field ! ");
		sa.assertTrue(regPage.submitBtn.isDisplayed(),"Can't find a 'Submit' button !");			
		sa.assertAll();
	}
	
	
	@Test(description = "Checking error messages on the Registration page - 'Empty fields' case",
			dataProvider = "regEmptyFieldsErrorMessages", dataProviderClass = TestDataProvider.class) 
	public void test_RegPage_EmptyFieldsErrorHandling(String lang, String errMsgFile) throws IOException {
		regPage.load().setLanguage(lang);
		regPage.submitBtn.click();		
		BufferedReader fileIn = null;
		try {
			fileIn = new BufferedReader(new FileReader(errMsgFile));
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