package pages;

import java.util.List;

import helpers.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Sent extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//Поле поиска

	//Message list select options
	@FindBy (css = ".msglist__checkbox") public WebElement msglistCheckbox;			//Чекбокс выбора писем:
	@FindBy (css = "[data-select~=\"all\"]") public WebElement msgSelectAll;			//Все
	@FindBy (css = "[data-select~=\"unread\"]") public WebElement msgSelectUnread;		//Непрочитанные
	@FindBy (css = "[data-select~=\"read\"]") public WebElement msgSelectRead;			//Прочитанные
	@FindBy (css = "[data-select~=\"marked\"]") public WebElement msgSelectMarked;		//Отмеченные
	@FindBy (css = "[data-select~=\"unmarked\"]") public WebElement msgSelectUnmarked;	//Неотмеченные

	//Top menu elements
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgToTrash;			//Удалить
	@FindBy (css = "[class~=\"spam\"]") public WebElement msgToSpam;			//В Спам!
	@FindBy (css = "[class~=\"move\"]") public WebElement msgToFolder;			//Переместить
	@FindBy (css = "[class~=\"more\"]") public WebElement moreOptions;			//Еще
	@FindBy (css = "[data-status~=\"1\"]") public WebElement msgIsRead;			//Пометить прочитанным
	@FindBy (css = "[data-status~=\"0\"]") public WebElement msgIsUnread;		//Пометить Непрочитанным
	
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList;  //Список писем на странице (темы)
		
	public Sent(WebDriver driver){
		super(driver);	
	}
	
	public void openMsgWithSubject(String subj) {
		for (WebElement element : msgList) {
			if (element.getText().startsWith(subj)) {
				element.click();				
				break;
			}						
		}	
	}	

}
