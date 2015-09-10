package pages;

import helpers.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
	@FindBy (css = ".sidebar__profile") public WebElement profileMenu;		//���� ������� ������������
	
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
		
					
	public Navigation(WebDriver driver){
		super(driver);	
	}	
	
	public Inbox goToFolder(Inbox inbox){
		inboxLink.click();
		return inbox;
	}
	
	public Drafts goToFolder(Drafts drafts){
		draftsLink.click();
		return drafts;
	}
	
	public Sent goToFolder(Sent sent){
		sentLink.click();
		return sent;
	}
	
	public Trash goToFolder(Trash trash){
		trashLink.click();
		return trash;
	}
	
	public Spam goToFolder(Spam spam){
		spamLink.click();
		return spam;
	}	
	
	public void newMsg() {
		newMsgBtn.click();		
	}
			
	public void logout() {		
		hoover(profileMenu).click();;
		implicitlyWait(3);
		logoutLink.click();		
	}

}