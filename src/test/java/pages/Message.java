package pages;

import helpers.BaseClass;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Message extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//Поле поиска

	//Top menu elements
	@FindBy (css = "[class~=\"return\"]") public WebElement backToList;			//Назад
	@FindBy (css = "[class~=\"reply\"]") public WebElement replyBtn;			//Ответить
	@FindBy (css = "[class~=\"forward\"]") public WebElement forwardBtn;		//Переслать
	
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgToTrash;			//Удалить
	@FindBy (css = "[class~=\"spam\"]") public WebElement msgToSpam;			//Отписаться
	@FindBy (css = "[class~=\"move\"]") public WebElement msgToFolder;			//Переместить
	@FindBy (css = "[class~=\"more\"]") public WebElement moreOptions;			//Еще
	@FindBy (css = "[data-status~=\"1\"]") public WebElement msgIsRead;			//Пометить прочитанным
	@FindBy (css = "[data-status~=\"0\"]") public WebElement msgIsUnread;		//Пометить Непрочитанным
	
	@FindBy (css = ".pager__right") public WebElement nextMsgBtn;		//Следующее письмо
	@FindBy (css = ".pager__left") public WebElement prevMsgBtn;		//Предыдущее письмо
	@FindBy (css = ".readmsg__thread-count") public WebElement topicBtn;		//Переписка по этой теме
	
	@FindBy (css = ".readmsg__head-date") public WebElement date;		//Дата письма
	@FindBy (css = ".readmsg__subject") public WebElement subject;		//Тема письма
	@FindBy (css = ".readmsg__body") public WebElement body;				//Тело полученного сообщения
	@FindBy (css = "[class*=\"xfm_\"]") public WebElement bodyReply;			//Текст reply/fwd сообщения
	@FindBy (css = ".readmsg__reply-hint") public WebElement quickReplyHint;	//Поле быстрого ответа
		
	@FindBy (css = ".controls-link.remove") public WebElement deleteDraftBtn;	//Удалить черновик
	
	@FindBy (css = ".default.send") public WebElement sendBtn;					//Отправить
	@FindBy (css = "[class~=\"cancel\"]") public WebElement cancelBtn ;			//Отменить
	
	@FindBy (css = ".action.attachments-file-button") public WebElement addLocalFileBtn ;	//Добавить файл-вложение(локально)
	@FindBy (css = ".sendmsg__attachments-edisk-files.action") public WebElement addEDiskFileBtn ;	//Добавить файл из еДиск
	@FindBy (css = ".sendmsg__attachment-preview") public WebElement attachmentIcon;	//Иконка добавленного файла
	@FindBy (css = ".attachment__foot-name") public WebElement attachmentName;  //Имя файла вложения
	@FindBy (css = ".sendmsg__ads-ready") public WebElement sentOK; //Блок сообщения об успешной отправке письма 
		
	
	@FindBy (css = ".sendmsg__form-label-copies.cc") public WebElement  copyToLink;			//Показать поле "Копия"
	@FindBy (css = ".sendmsg__form-label-copies.bcc") public WebElement  hiddenCopyLink;	//Показать поле "Скрытая"
		
	@FindBy (css = "[name~=\"toInput\"]") public WebElement  toField;				//Поле "Кому"
	@FindBy (css = "[name~=\"ccInput\"]") public WebElement  copyToField;			//Поле "Копия"
	@FindBy (css = "[name~=\"bccInput\"]") public WebElement  hiddenCopyField;		//Поле "Скрытая"
	@FindBy (css = "[name~=\"subject\"]") public WebElement  subjectField;		//Поле "Скрытая"
	
	@FindBy (css = ".link3.plain") public WebElement plainTextBtn;			//Переключить режим ввода на "просто текст"
	
	@FindBy (css = ".editor__area") public WebElement bodyEditor;			//Вводимый текст письма
		
		
		
	public Message(WebDriver driver){
		super(driver);
	}
	
	public Message to(String string){
		toField.sendKeys(string);	
		return this;
	}
	
	public Message subject(String string){
		subjectField.sendKeys(string);
		return this;
	}
	
	public Message setBodyText(String string){
		bodyEditor.sendKeys(string);
		return this;
	}
	
	public Message reply(){
		replyBtn.click();
		return this;
	}
	
	public Message forwardTo(String string){
		forwardBtn.click();
		toField.sendKeys(string);
		return this;
	}
	
	public void moveToTrash(){
		msgToTrash.click();
	}
	
	public void send(){
		WebDriverWait wait = new WebDriverWait(driver, 15);
		sendBtn.click();
		wait.until(ExpectedConditions.visibilityOf(sentOK));		
	}	

	public Message addLocalFile(String abs_path) throws Exception{		
		WebDriverWait wait = new WebDriverWait(driver, 60);
		StringSelection str = new StringSelection(abs_path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
		addLocalFileBtn.click();
		Robot robot = new Robot(){};
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);		
		wait.until(ExpectedConditions.visibilityOf(attachmentIcon));
		return this;
	}	
}
