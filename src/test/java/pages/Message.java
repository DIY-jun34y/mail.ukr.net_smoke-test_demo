package pages;

import helpers.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Message extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//���� ������

	//Top menu elements
	@FindBy (css = "[class~=\"return\"]") public WebElement backToList;			//�����
	@FindBy (css = "[class~=\"reply\"]") public WebElement replyBtn;			//��������
	@FindBy (css = "[class~=\"forward\"]") public WebElement forwardBtn;		//���������
	
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgToTrash;			//�������
	@FindBy (css = "[class~=\"spam\"]") public WebElement msgToSpam;			//����������
	@FindBy (css = "[class~=\"move\"]") public WebElement msgToFolder;			//�����������
	@FindBy (css = "[class~=\"more\"]") public WebElement moreOptions;			//���
	@FindBy (css = "[data-status~=\"1\"]") public WebElement msgIsRead;			//�������� �����������
	@FindBy (css = "[data-status~=\"0\"]") public WebElement msgIsUnread;		//�������� �������������
	
	@FindBy (css = ".pager__right") public WebElement nextMsgBtn;		//��������� ������
	@FindBy (css = ".pager__left") public WebElement prevMsgBtn;		//���������� ������
	@FindBy (css = ".readmsg__thread-count") public WebElement topicBtn;		//��������� �� ���� ����
	
	@FindBy (css = ".readmsg__head-date") public WebElement date;		//���� ������
	@FindBy (css = ".readmsg__subject") public WebElement subject;		//���� ������
	@FindBy (css = ".readmsg__body") public WebElement body;				//���� ����������� ���������
	@FindBy (css = "[class*=\"xfm_\"]") public WebElement bodyReply;			//����� reply/fwd ���������
	@FindBy (css = ".readmsg__reply-hint") public WebElement quickReplyHint;	//���� �������� ������
		
	@FindBy (css = ".controls-link.remove") public WebElement deleteDraftBtn;	//������� ��������
	
	@FindBy (css = ".default.send") public WebElement sendBtn;					//���������
	@FindBy (css = "[class~=\"cancel\"]") public WebElement cancelBtn ;			//��������
	
	@FindBy (css = ".action.attachments-file-button") public WebElement addLocalFileBtn ;	//�������� ����-��������(��������)
	@FindBy (css = ".sendmsg__attachments-edisk-files.action") public WebElement addEDiskFileBtn ;	//�������� ���� �� �����
	
	@FindBy (css = ".sendmsg__form-label-copies.cc") public WebElement  copyToLink;			//�������� ���� "�����"
	@FindBy (css = ".sendmsg__form-label-copies.bcc") public WebElement  hiddenCopyLink;	//�������� ���� "�������"
		
	@FindBy (css = "[name~=\"toInput\"]") public WebElement  toField;				//���� "����"
	@FindBy (css = "[name~=\"ccInput\"]") public WebElement  copyToField;			//���� "�����"
	@FindBy (css = "[name~=\"bccInput\"]") public WebElement  hiddenCopyField;		//���� "�������"
	@FindBy (css = "[name~=\"subject\"]") public WebElement  subjectField;		//���� "�������"
	
	@FindBy (css = ".link3.plain") public WebElement plainTextBtn;			//����������� ����� ����� �� "������ �����"
	
	@FindBy (css = ".editor__area") public WebElement bodyEditor;			//�������� ����� ������
		
		
		
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
		sendBtn.click();
	}	

}
