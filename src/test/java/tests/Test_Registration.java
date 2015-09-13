package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static tests.Test_Login.validLogin;
import helpers.DriverOptions;
import pages.*;


public class Test_Registration {
	
	public WebDriver driver;
	public SoftAssert sa;

	public Registration regPage;	 
	public ConfirmationPage confirm;
	public Login login; 
	public Navigation navigation;
		
	@Parameters({"browserType"})
	@BeforeClass
	public void setup(@Optional("Firefox") String browserType) {
		driver = DriverOptions.getDriver(browserType);	
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				
		regPage = new Registration(driver);
		confirm = new ConfirmationPage(driver);
		login = new Login(driver);
		navigation = new Navigation(driver);
			
	}	
		
	@BeforeMethod
	public void newSoftAssertObject(){
		this.sa = new SoftAssert();			
	}
		
	@AfterClass
	public void tearDownClass() throws Exception {	    
		driver.quit();
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