package tests;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import helpers.BaseClass;
import helpers.TestDataProvider;
import pages.Login;
import pages.Navigation;

public class Test_Login extends BaseClass{
	
	public static final String validLogin = "tester0667804531";
	public static final String validPass = "p455word";
	private Login login; 
	private Navigation navigation;
	private SoftAssert sa;
	
	
	@BeforeClass
	public void setup() {			
		login = new Login();
		navigation = new Navigation();
	}
					
	public boolean loggedIn(){
		String pageTitle = driver.getTitle();
		if (pageTitle.contains(validLogin+"@ukr.net")) {
			return true;
		}else {
			return false;
		}		
	}	
	
	@Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class)	
	public void test_Login(String testCase,String account,String password,String status,String errorMsg){	
		login
		.load()
		.setLanguage("RU")
		.loginAs(account, password);
		
		this.sa = new SoftAssert();
		sa.assertEquals(loggedIn()+"", status);		
		
		if (loggedIn()) {			
			navigation.logout();
		}else {			
			sa.assertEquals(login.loginErrorMsg.getText(), errorMsg);
		}
		sa.assertAll();
	}	
	
}