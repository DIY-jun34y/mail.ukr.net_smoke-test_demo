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
	@FindBy (css = ".controls-link.remove") public WebElement msgDelete;			//Удалить/Удалить навсегда	
	@FindBy (css = ".controls-link.spam") public WebElement msgToSpam;			//В Спам!
	@FindBy (css = ".controls-link.move") public WebElement msgToFolder;			//Переместить
	@FindBy (xpath = "//div[1]/div[2]/div/div/div[1]/div[2]/div[2]/div/div[1]/a[1]") 
	public WebElement toInbox;			//Восстановить во входящие
	
	@FindBy (css = ".controls-link.more") public WebElement moreOptions;			//Еще
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

	public void moveItToTrash() {
		msgDelete.click();		
	}
	
	public void moveItToSpam() {
		msgToSpam.click();		
	}
	
	public void ItIsNotSpam() {
		msgToSpam.click();		
	}

	public void moveItToInbox() {
		msgToFolder.click();		
		hoover(toInbox).click();
	}
	
	public Folder selectAll(){
		hoover(msglistCheckbox).click();
		return this;
	}
	
	public void clearAll(){
		hoover(msglistCheckbox).click();
		msgDelete.click();
	}
	
	public WebElement getMessageWithId(String message_id){		
		return driver.findElement(By.id(message_id));
	}
	
	public void openMessageWithId(String message_id) {
		driver.findElement(By.cssSelector("[href*=\""+message_id.substring(3)+"\"]")).click();
	}
	
	public void openMsgWithSubject(String subj){
		try {
			Thread.sleep(1000);
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
	
	public Folder selectAnyFromList(){
		int s = msgList.size();
		int n = (int) (1+Math.round((s-1)*Math.random()));
		msgId = driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]")).getAttribute("id");
		hoover(driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]/td[1]/label/input"))).click();
		return this;
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
