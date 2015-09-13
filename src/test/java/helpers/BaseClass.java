package helpers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

public abstract class BaseClass {
	
	public static WebDriver driver;
	/*
	public BaseClass(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}	
	*/	
	
	@Parameters({"browserType"})
	@BeforeSuite
	private void setupDriver(@Optional("Firefox") String browserType){
		if (driver==null){
			driver = DriverOptions.getDriver(browserType);	
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}		
	}	
		
	@AfterSuite
	public void tearDownSuite() throws Exception {	    
		driver.quit();
	}
	
	public void load(String url){
	 	driver.get(url);		
	}
	
	public WebElement hoover(WebElement element){
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		return element;
	}
	
	public void dragNDrop(WebElement source, WebElement target) throws InterruptedException{ 
		Actions action = new Actions(driver);		
		//hoover(source);
		action.clickAndHold(source).build().perform();		
		Thread.sleep(1000);
		action.moveToElement(target).release(target).build().perform();
	}			
	
	public void implicitlyWait(int sec){
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}		
		
}