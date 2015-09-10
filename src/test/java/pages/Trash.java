package pages;

import java.util.List;

import helpers.BaseClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Trash extends BaseClass{

	//Message list select options
	@FindBy (css = ".msglist__checkbox") public WebElement msglistCheckbox;			//Чекбокс выбора писем:
	@FindBy (css = "[data-select~=\"all\"]") public WebElement msgSelectAll;			//Все
	@FindBy (css = "[data-select~=\"unread\"]") public WebElement msgSelectUnread;		//Непрочитанные
	@FindBy (css = "[data-select~=\"read\"]") public WebElement msgSelectRead;			//Прочитанные
	@FindBy (css = "[data-select~=\"marked\"]") public WebElement msgSelectMarked;		//Отмеченные
	@FindBy (css = "[data-select~=\"unmarked\"]") public WebElement msgSelectUnmarked;	//Неотмеченные

	//Top menu elements
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgDelete;			//Удалить навсегда
	@FindBy (css = "[class~=\"spam\"]") public WebElement msgToSpam;			//В Спам!
	@FindBy (css = "[class~=\"move\"]") public WebElement msgToFolder;			//Восстановить
	
	@FindBy (xpath = "//div[1]/div[2]/div/div/div[1]/div[2]/div[2]/div/div[1]/a[1]") 
	public WebElement toInbox;			//Восстановить во входящие
	
	@FindBy (css = "[class~=\"more\"]") public WebElement moreOptions;			//Еще
	@FindBy (css = "[data-status~=\"1\"]") public WebElement msgIsRead;			//Пометить прочитанным
	@FindBy (css = "[data-status~=\"0\"]") public WebElement msgIsUnread;		//Пометить Непрочитанным
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//Поле поиска	
	
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList; //Список писем на странице (темы)
	
	public String msgID;
	
	public Trash(WebDriver driver){
		super(driver);			
	}
		
	public void openAnyWithSubject(String subj) {
		for (WebElement element : msgList) {
			if (element.getText().startsWith(subj)) {
				element.click();				
				break;
			}						
		}	
	}
	
	public void selectAnyFromList() {
		int s = msgList.size();
		int n = (int) (1+Math.round((s-1)*Math.random()));
		msgID = driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]")).getAttribute("id");
		hoover(driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]/td[1]/label/input"))).click();		
	}
	
	public void openMessage(String message_id) {
		driver.findElement(By.cssSelector("[href*=\""+message_id.substring(3)+"\"]")).click();
	}
	
	public void moveSelectedToInbox() {
		msgToFolder.click();		
		hoover(toInbox).click();
	}
	
	public WebElement getMessageWithId(String string){		
		return driver.findElement(By.id(string));
	}
	
	public void clear(){
		hoover(msglistCheckbox).click();
		msgDelete.click();
	}
	
}
