package pages;

import helpers.BaseClass;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Navigation extends BaseClass {
	
	//left-side navigation
	@FindBy (css = ".default.compose") public WebElement newMsgBtn;			//Написать письмо
	@FindBy (css = "[class~=\"inbox\"]") public WebElement inboxLink;			//Входящие
	@FindBy (css = "[class~=\"drafts\"]") public WebElement draftsLink;	    	//Черновики
	@FindBy (css = "[class~=\"sent\"]") public WebElement sentLink;				//Отправленные
	@FindBy (css = "[class~=\"spam\"]") public WebElement spamLink;				//Спам
	@FindBy (css = "[class~=\"trash\"]") public WebElement trashLink;			//Удаленные
	@FindBy (css = "[class~=\"folders\"]") public WebElement customFoldersLink;	//Другие папки
	@FindBy (css = "[class~=\"unread\"]") public WebElement unreadLink;			//Непрочитанные
	@FindBy (css = "[class~=\"marked\"]") public WebElement markedLink;			//Отмченные
	@FindBy (css = "[class~=\"files\"]") public WebElement attachmentsLink; 	//Вложения
	@FindBy (css = ".sidebar__profile-user") public WebElement profileMenu;		//Меню Профиля пользователя
	
	//pop-up profile menu
	@FindBy (css = "[href*=\"logout\"]") public WebElement logoutLink;		//Выход (Logout)
	@FindBy (css = "[href*=\"terms\"]") public WebElement termsLink;			//Конфиденциальность
	@FindBy (css = "[class*=\"link__help\"]") public WebElement helpLink;		//Помощь
	@FindBy (css = "[class*=\"popup-hotkeys\"]") public WebElement hotkeysLink;	//Быстрые клавиши	
	@FindBy (css = "[href*=\"security\"]") public WebElement securityLink;		//Безопасность
	@FindBy (css = "[href*=\"account\"]") public WebElement accountLink;		//Учетная запись
	@FindBy (css = "[href*=\"interface\"]") public WebElement interfaceLink;	//Интерфейс
	@FindBy (css = "[href*=\"identities\"]") public WebElement otherEmailsLink;	//Дополнительные адреса
	@FindBy (css = "[href*=\"forward\"]") public WebElement forwardingLink;			//Пересылка писем
	@FindBy (css = "[href*=\"autoresponder\"]") public WebElement autoresponderLink;	//Автоответчик
	@FindBy (css = "[href*=\"filters\"]") public WebElement filtersLink;			//Фильтра
	@FindBy (css = "[href*=\"protocols\"]") public WebElement mailProtocolsLink;	//Почтовые программы
	@FindBy (css = "[href*=\"password\"]") public WebElement changePasswordLink;	//Смена пароля
		
					
	public Navigation(){		
		PageFactory.initElements(driver, this);
	}	
	
	public Folder goToInbox(){
		inboxLink.click();
		return new Folder();
	}
	
	public Folder goToDrafts(){
		draftsLink.click();
		return new Folder();
	}
	
	public Folder goToSent(){
		sentLink.click();
		return new Folder();
	}
	
	public Folder goToTrash(){
		trashLink.click();
		return new Folder();
	}
	
	public Folder goToSpam(){
		spamLink.click();
		return new Folder();
	}	
	
	public void newMessage(){
		newMsgBtn.click();		
	}
			
	public void logout(){		
		hoover(profileMenu).click();;
		implicitlyWait(3);
		logoutLink.click();		
	}

}