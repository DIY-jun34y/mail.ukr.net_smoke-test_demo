package pages;

import java.util.List;

import helpers.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Folder extends BaseClass{
	
	@FindBy (css = ".search>input") public WebElement searchBox;		//���� ������

	//Message list select options
	@FindBy (css = ".msglist__checkbox") public WebElement msglistCheckbox;			//������� ������ �����:
	@FindBy (css = "[data-select~=\"all\"]") public WebElement msgSelectAll;			//���
	@FindBy (css = "[data-select~=\"unread\"]") public WebElement msgSelectUnread;		//�������������
	@FindBy (css = "[data-select~=\"read\"]") public WebElement msgSelectRead;			//�����������
	@FindBy (css = "[data-select~=\"marked\"]") public WebElement msgSelectMarked;		//����������
	@FindBy (css = "[data-select~=\"unmarked\"]") public WebElement msgSelectUnmarked;	//������������

	//Top menu elements
	@FindBy (css = "[class~=\"remove\"]") public WebElement msgDelete;			//�������/������� ��������	
	@FindBy (css = "[class~=\"spam\"]") public WebElement msgToSpam;			//� ����!
	@FindBy (css = "[class~=\"move\"]") public WebElement msgToFolder;			//�����������
	@FindBy (xpath = "//div[1]/div[2]/div/div/div[1]/div[2]/div[2]/div/div[1]/a[1]") 
	public WebElement toInbox;			//������������ �� ��������
	
	@FindBy (css = "[class~=\"more\"]") public WebElement moreOptions;			//���
	@FindBy (css = "[data-status~=\"1\"]") public WebElement msgIsRead;			//�������� �����������
	@FindBy (css = "[data-status~=\"0\"]") public WebElement msgIsUnread;		//�������� �������������
	
	@FindBy (css = ".msglist__row-subject>a") public List<WebElement> msgList; //������ ����� �� �������� (����)
	@FindBy (css = ".msglist__row-subject>a>strong") public List<WebElement> snippetList; //Body snippet(First 255 chars)

	public String msgId;
	public String msgDate;
	public String msgSubj;
		
	public Folder(WebDriver driver){
		super(driver);			
	}
			
	public WebElement getMessageWithId(String string){		
		return driver.findElement(By.id(string));
	}

	public void moveSelectedToTrash() {
		msgDelete.click();		
	}
	
	public void openMessage(String message_id) {
		driver.findElement(By.cssSelector("[href*=\""+message_id.substring(3)+"\"]")).click();
	}
	
	public void moveSelectedToInbox() {
		msgToFolder.click();		
		hoover(toInbox).click();
	}
	
	public void clear(){
		hoover(msglistCheckbox).click();
		msgDelete.click();
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
