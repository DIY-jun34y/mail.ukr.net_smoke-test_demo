package pages;

import java.util.List;

import helpers.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Drafts extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//���� ������
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList; //������ ����� �� �������� (����)
	@FindBy (css = ".msglist__row-subject>a>strong") public List<WebElement> snippetList; //Body snippet(First 255 chars)
	
	//Message list select options
	@FindBy (css = ".msglist__checkbox") public WebElement msglistCheckbox;			//�������-������ ������ �����:
	@FindBy (css = "[data-select~=\"all\"]") public WebElement msgSelectAll;			//���
	@FindBy (css = "[data-select~=\"unread\"]") public WebElement msgSelectUnread;		//�������������
	@FindBy (css = "[data-select~=\"read\"]") public WebElement msgSelectRead;			//�����������
	@FindBy (css = "[data-select~=\"marked\"]") public WebElement msgSelectMarked;		//����������
	@FindBy (css = "[data-select~=\"unmarked\"]") public WebElement msgSelectUnmarked;	//������������

	//Top menu elements
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgDelete;			//������� ��������
	
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
