package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import helpers.DriverOptions;
import helpers.TestData;
import pages.Login;
import pages.Navigation;

public class Test_Login {
	
	public static final String validLogin = "tester0667804531";
	public static final String validPass = "p455word";
	private WebDriver driver;
	private Login login; 
	private Navigation navigation;
	private SoftAssert sa;
	
	@Parameters({"browserType"})
	@BeforeClass
	public void setup(@Optional("Firefox") String browserType) {
		driver = DriverOptions.getDriver(browserType);	
		//driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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
	
	@Test(description = "As a User I can login only with valid credentials", dataProvider = "login", dataProviderClass =	TestData.class)	
	public void test_Login(String account, String password, Boolean state, String errorMsg) {
		login.load().setLanguage("RU").loginAs(account, password);
		sa.assertTrue(loggedIn()==state);
		if (!loggedIn()) {sa.assertEquals(login.loginErrorMsg.getText(), errorMsg);}		
		else {navigation.logout();}
		sa.assertAll();
	}		
}
