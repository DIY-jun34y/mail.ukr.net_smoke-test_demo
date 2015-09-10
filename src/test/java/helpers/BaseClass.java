package helpers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

public abstract class BaseClass {
	
	public WebDriver driver;
			
	public BaseClass(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@BeforeSuite
	public void setUp() {
		setupFirefox();
		//setUpChrome();
		
	}

	@AfterSuite
	public void tearDown() {	    
	    driver.quit();
	}	
	
	private void setupFirefox() {
		String Property = "webdriver.firefox.bin";
		String Value = "Q:\\Mozilla Firefox\\firefox.exe";
		System.setProperty(Property,Value);
	}

	public void load(String url){
	 	driver.get(url);
		//implicitlyWait(3);
	}
	
	public WebElement hoover(WebElement element){
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		return element;
	}
	
	public void implicitlyWait(int sec){
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}		
		
}