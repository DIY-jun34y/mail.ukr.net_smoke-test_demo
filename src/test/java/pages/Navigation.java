package pages;

import helpers.BaseClass;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Navigation extends BaseClass {
	
	//left-side navigation
	@FindBy (css = ".default.compose") public WebElement newMsgBtn;			//�������� ������
	@FindBy (css = "[class~=\"inbox\"]") public WebElement inboxLink;			//��������
	@FindBy (css = "[class~=\"drafts\"]") public WebElement draftsLink;	    	//���������
	@FindBy (css = "[class~=\"sent\"]") public WebElement sentLink;				//������������
	@FindBy (css = "[class~=\"spam\"]") public WebElement spamLink;				//����
	@FindBy (css = "[class~=\"trash\"]") public WebElement trashLink;			//���������
	@FindBy (css = "[class~=\"folders\"]") public WebElement customFoldersLink;	//������ �����
	@FindBy (css = "[class~=\"unread\"]") public WebElement unreadLink;			//�������������
	@FindBy (css = "[class~=\"marked\"]") public WebElement markedLink;			//���������
	@FindBy (css = "[class~=\"files\"]") public WebElement attachmentsLink; 	//��������
	@FindBy (css = ".sidebar__profile-user") public WebElement profileMenu;		//���� ������� ������������
	
	//pop-up profile menu
	@FindBy (css = "[href*=\"logout\"]") public WebElement logoutLink;		//����� (Logout)
	@FindBy (css = "[href*=\"terms\"]") public WebElement termsLink;			//������������������
	@FindBy (css = "[class*=\"link__help\"]") public WebElement helpLink;		//������
	@FindBy (css = "[class*=\"popup-hotkeys\"]") public WebElement hotkeysLink;	//������� �������	
	@FindBy (css = "[href*=\"security\"]") public WebElement securityLink;		//������������
	@FindBy (css = "[href*=\"account\"]") public WebElement accountLink;		//������� ������
	@FindBy (css = "[href*=\"interface\"]") public WebElement interfaceLink;	//���������
	@FindBy (css = "[href*=\"identities\"]") public WebElement otherEmailsLink;	//�������������� ������
	@FindBy (css = "[href*=\"forward\"]") public WebElement forwardingLink;			//��������� �����
	@FindBy (css = "[href*=\"autoresponder\"]") public WebElement autoresponderLink;	//������������
	@FindBy (css = "[href*=\"filters\"]") public WebElement filtersLink;			//�������
	@FindBy (css = "[href*=\"protocols\"]") public WebElement mailProtocolsLink;	//�������� ���������
	@FindBy (css = "[href*=\"password\"]") public WebElement changePasswordLink;	//����� ������
		
					
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