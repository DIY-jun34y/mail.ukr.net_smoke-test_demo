package pages;

import java.util.List;

import helpers.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Inbox extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//���� ������

	//Message list select options
	@FindBy (css = ".msglist__checkbox") public WebElement msglistCheckbox;			//������� ������ �����:
	@FindBy (css = "[data-select~=\"all\"]") public WebElement msgSelectAll;			//���
	@FindBy (css = "[data-select~=\"unread\"]") public WebElement msgSelectUnread;		//�������������
	@FindBy (css = "[data-select~=\"read\"]") public WebElement msgSelectRead;			//�����������
	@FindBy (css = "[data-select~=\"marked\"]") public WebElement msgSelectMarked;		//����������
	@FindBy (css = "[data-select~=\"unmarked\"]") public WebElement msgSelectUnmarked;	//������������

	//Top menu elements
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgToTrash;			//�������
	@FindBy (css = "[class~=\"spam\"]") public WebElement msgToSpam;			//� ����!
	@FindBy (css = "[class~=\"move\"]") public WebElement msgToFolder;			//�����������
	@FindBy (css = "[class~=\"more\"]") public WebElement moreOptions;			//���
	@FindBy (css = "[data-status~=\"1\"]") public WebElement msgIsRead;			//�������� �����������
	@FindBy (css = "[data-status~=\"0\"]") public WebElement msgIsUnread;		//�������� �������������
	
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList; //������ ����� �� �������� (����)

	public String msgId;
	public String msgDate;
	public String msgSubj;
		
	public Inbox(WebDriver driver){
		super(driver);			
	}
			
	public WebElement getMessageWithId(String string){		
		return driver.findElement(By.id(string));
	}

	public void moveSelectedToTrash() {
		msgToTrash.click();		
	}
	
	public void clear(){
		hoover(msglistCheckbox).click();
		msgToTrash.click();
	}	
	
	public void openMsgWithSubject(String subj){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (WebElement element : msgList) {
			if (element.getText().startsWith(subj)) {
				element.click();				
				break;
			}						
		}	
	}
	
	public void selectAnyFromList(){
		int s = msgList.size();
		int n = (int) (1+Math.round((s-1)*Math.random()));
		msgId = driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]")).getAttribute("id");
		hoover(driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]/td[1]/label/input"))).click();		
	}
	
	public void openAnyFromList(){
		int s = msgList.size();
		int n = (int) (1+Math.round((s-1)*Math.random()));
		msgId = driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]")).getAttribute("id");
		driver.findElement(By.xpath("//section/table/tbody/tr["+n+"]/td[4]/a")).click();
		msgDate = driver.findElement(By.cssSelector(".readmsg__head-date")).getText();
		msgSubj = driver.findElement(By.cssSelector(".readmsg__subject")).getText();		
	}
	
}
