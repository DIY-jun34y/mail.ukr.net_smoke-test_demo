package pages;

import java.util.List;

import helpers.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Drafts extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//Поле поиска
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList; //Список писем на странице (темы)
	@FindBy (css = ".msglist__row-subject>a>strong") public List<WebElement> snippetList; //Body snippet(First 255 chars)
	
	//Message list select options
	@FindBy (css = ".msglist__checkbox") public WebElement msglistCheckbox;			//Чекбокс-фильтр выбора писем:
	@FindBy (css = "[data-select~=\"all\"]") public WebElement msgSelectAll;			//Все
	@FindBy (css = "[data-select~=\"unread\"]") public WebElement msgSelectUnread;		//Непрочитанные
	@FindBy (css = "[data-select~=\"read\"]") public WebElement msgSelectRead;			//Прочитанные
	@FindBy (css = "[data-select~=\"marked\"]") public WebElement msgSelectMarked;		//Отмеченные
	@FindBy (css = "[data-select~=\"unmarked\"]") public WebElement msgSelectUnmarked;	//Неотмеченные

	//Top menu elements
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgDelete;			//Удалить навсегда
	
	public Drafts(WebDriver driver){
		super(driver);	
	}
		
	public void openMsgWithSubject(String targetSubj) {
		for (int i=0; i<msgList.size(); i++) {
			String wholeStr = msgList.get(i).getText();
			String snippet = snippetList.get(i).getText();			
			int positionOfSnippet = wholeStr.indexOf(snippet);
			String subject = wholeStr.substring(0, positionOfSnippet-1);
			
			if (subject.equals(targetSubj)) {
				msgList.get(i).click();			
				break;
			}			
		}	
	}	

}
