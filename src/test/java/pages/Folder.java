package pages;

import java.util.List;

import helpers.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Folder extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//Поле поиска

	//Message list select options
	@FindBy (css = ".msglist__checkbox") public WebElement msglistCheckbox;			//Чекбокс выбора писем:
	@FindBy (css = "[data-select~=\"all\"]") public WebElement msgSelectAll;			//Все
	@FindBy (css = "[data-select~=\"unread\"]") public WebElement msgSelectUnread;		//Непрочитанные
	@FindBy (css = "[data-select~=\"read\"]") public WebElement msgSelectRead;			//Прочитанные
	@FindBy (css = "[data-select~=\"marked\"]") public WebElement msgSelectMarked;		//Отмеченные
	@FindBy (css = "[data-select~=\"unmarked\"]") public WebElement msgSelectUnmarked;	//Неотмеченные

	//Top menu elements
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgDelete;			//Удалить/Удалить навсегда	
	@FindBy (css = "[class~=\"spam\"]") public WebElement msgToSpam;			//В Спам!
	@FindBy (css = "[class~=\"move\"]") public WebElement msgToFolder;			//Переместить
	@FindBy (xpath = "//div[1]/div[2]/div/div/div[1]/div[2]/div[2]/div/div[1]/a[1]") 
	public WebElement toInbox;			//Восстановить во входящие
	
	@FindBy (css = "[class~=\"more\"]") public WebElement moreOptions;			//Еще
	@FindBy (css = "[data-status~=\"1\"]") public WebElement msgIsRead;			//Пометить прочитанным
	@FindBy (css = "[data-status~=\"0\"]") public WebElement msgIsUnread;		//Пометить Непрочитанным
	
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList; //Список писем на странице (темы)
	@FindBy (css = ".msglist__row-subject>a>strong") public List<WebElement> snippetList; //Body snippet(First 255 chars)

	public String msgId;
	public String msgDate;
	public String msgSubj;
		
	public Folder(WebDriver driver){
		super(driver);			
	}
			
	public WebElement getMessageWithId(String string){		
		return driver.findElement(By.id(string));
	}

	public void moveSelectedToTrash() {
		msgDelete.click();		
	}
	
	public void openMessage(String message_id) {
		driver.findElement(By.cssSelector("[href*=\""+message_id.substring(3)+"\"]")).click();
	}
	
	public void moveSelectedToInbox() {
		msgToFolder.click();		
		hoover(toInbox).click();
	}
	
	public void clear(){
		hoover(msglistCheckbox).click();
		msgDelete.click();
	}	
	
	public void openMsgWithSubject(String subj){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (WebElement element : msgList) {
			if (element.getText().startsWith(subj)) {
				element.click();				
				break;
			}						
		}	
	}
	
	public void selectAnyFromList(){
		int s = msgList.size();
		int n = (int) (1+Math.round((s-1)*Math.random()));
		msgId = driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]")).getAttribute("id");
		hoover(driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]/td[1]/label/input"))).click();		
	}
	
	public void openAnyFromList(){
		int s = msgList.size();
		int n = (int) (1+Math.round((s-1)*Math.random()));
		msgId = driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]")).getAttribute("id");
		driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]/td[4]/a")).click();
		msgDate = driver.findElement(By.cssSelector(".readmsg__head-date")).getText();
		msgSubj = driver.findElement(By.cssSelector(".readmsg__subject")).getText();		
	}
	
}
