package pages;

import java.util.List;

import helpers.BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Drafts extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//���� ������
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList; //������ ����� �� �������� (����)

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
	
	public void openMsgWithSubject(String subj) {
		for (WebElement element : msgList) {
			if (element.getText().startsWith(subj)) {
				element.click();				
				break;
			}						
		}	
	}	

}
