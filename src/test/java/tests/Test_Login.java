package tests;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import helpers.BaseClass;
import helpers.TestData;
import pages.Login;
import pages.Navigation;

public class Test_Login extends BaseClass{
	
	public static final String validLogin = "tester0667804531";
	public static final String validPass = "p455word";
	//private WebDriver driver;
	private Login login; 
	private Navigation navigation;
	private SoftAssert sa;
	
	
	@BeforeClass
	public void setup() {
		//driver = BaseClass.getDriver();//DriverOptions.getDriver(browserType);				
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
	
	@Test(description = "As a User I can login only with valid credentials", dataProvider = "login", dataProviderClass =	TestData.class)	
	public void test_Login(String account, String password, Boolean state, String errorMsg) {
		login.load().setLanguage("RU").loginAs(account, password);
		sa.assertTrue(loggedIn()==state);
		if (!loggedIn()) {sa.assertEquals(login.loginErrorMsg.getText(), errorMsg);}		
		else {navigation.logout();}
		sa.assertAll();
	}		
}
