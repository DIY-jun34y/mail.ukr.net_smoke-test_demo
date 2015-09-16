package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.WebClient;

public abstract class DriverOptions {
	
	public static WebDriver getDriver(String string){		
		WebDriver driver;
		switch(string.toLowerCase()){
			case "firefox":
				driver = newFirefoxDriver();
				break;
			case "chrome":
				driver = newChromeDriver();
				break;
			case "htmlunit":
				driver = newHtmlUnitDriver();
				break;			
			default:
				driver = newFirefoxDriver();		
		}		
		return driver;		
	}
	
	private static WebDriver newHtmlUnitDriver() {
		//((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		WebDriver driver = new HtmlUnitDriver(true){
		@Override
        protected WebClient getWebClient() {
            WebClient c = super.getWebClient();
            c.getCache().setMaxSize(0);
            return c;
			}
		};
		return driver;
	}

	//if Firefox installed to non-default location:
	private static FirefoxDriver newFirefoxDriver() {		
		System.setProperty("webdriver.firefox.bin", "Q:\\Mozilla Firefox\\firefox.exe");
		return new FirefoxDriver();
	}
	
	private static ChromeDriver newChromeDriver() {
		System.setProperty("webdriver.chrome.driver", "Q:/eclipse/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";		
		options.setBinary(chromePath);
		return new ChromeDriver(options);
	}	
}
