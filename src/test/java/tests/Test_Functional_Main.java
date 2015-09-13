package tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;
import helpers.DriverOptions;
import static tests.Test_Login.validLogin;
import static tests.Test_Login.validPass;

public class Test_Functional_Main {
	
	public static final String testSubj = "test: new message";
	public static final	String attachmentPath = "Q:\\DVDFAB\\Kaner_JobsRev6.pdf";
	public static final	String attachmentName = "Kaner_JobsRev6.pdf";
	public static final String replyText = "This is a plain text reply";
	public static final String sampleText = "test message body - "
			+ "Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
			+ " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
			+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
			+ " Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
			+ "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	
	public WebDriver driver;
	public SoftAssert sa;
	
	public Login login; 
	public Navigation navigation;	
	public Folder inbox, drafts, sent, spam, trash;
	public Message message;	
	
	
	@Parameters({"browserType"})
	@BeforeClass
	public void setup(@Optional("Firefox") String browserType) {
		driver = DriverOptions.getDriver(browserType);	
		//driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);	
		login = new Login(driver);
		navigation = new Navigation(driver);		
		inbox = new Folder(driver);
		drafts = new Folder(driver);
		sent = new Folder(driver);
		spam = new Folder(driver);
		trash = new Folder(driver);		
		message = new Message(driver);	
	}	
		
	@BeforeMethod
	private void newSoftAssertObject(){
		this.sa = new SoftAssert();			
	}
	
	//@AfterMethod
	private void logout_(){
		navigation.logout();
	}
	
		
	@AfterClass
	public void tearDownClass() throws Exception {	    
		driver.quit();
	}
	
	private boolean loggedIn(){
		String pageTitle = driver.getTitle();
		if (pageTitle.contains(validLogin+"@ukr.net")) {
			return true;
		}else {
			return false;
		}		
	}
	
	private void loginWithValidData() {
		login.load();
		if (loggedIn()) {
			return;
		} else {
			login.loginAs(validLogin, validPass);
		}				
	}
	
	private void setMessage(String subj, String bodyText) {
		message
		.to(validLogin+"@ukr.net")
		.subject(subj)
		.setBodyText(bodyText);				
	}	
			
	@Test(description = "As a User I can create a message(it's stored in Drafts automatically)")
	public void test_CreateMessage() {
		loginWithValidData();
		navigation.newMessage();
		setMessage(testSubj,sampleText);		
		navigation.goToDrafts().openMsgWithSubject(testSubj);		
		sa.assertEquals(message.bodyEditor.getText(), sampleText,"Mismatch between actual and draft bodytext!");
		navigation.logout();
		sa.assertAll();
	}	
	 		
	@Test (description = "As a User I can send a message (it's stored in Sent automatically)")
	public void test_SendMessage() {
		loginWithValidData();
		for (int i=1; i<4; i++){
			navigation.newMessage();
			setMessage(testSubj+"-"+i,sampleText);
			message.send();
		}
		navigation.goToSent().openMsgWithSubject(testSubj);		
		sa.assertEquals(message.body.getText(), sampleText,"Mismatch between actual and sent bodytext!");
		navigation.logout();
		sa.assertAll();
		
	}	
	
	@Test (description = "As a User I can open inbox message from list")//, dependsOnMethods = "testSendMessage")
	public void test_OpenInboxMessage() {
		loginWithValidData();
		navigation.goToInbox().openMsgWithSubject(testSubj);		
		sa.assertEquals(message.body.getText(), sampleText,"Text mismatch between sent/inbox message body!");
		navigation.logout();
		sa.assertAll();
	}	
	
	@Test (description = "As a User I can reply to inbox message")//, dependsOnMethods = "testOpenInboxMessage")
	public void test_ReplyToMessage() {
		loginWithValidData();
		navigation.goToInbox().openMsgWithSubject(testSubj);			
		message.reply().setBodyText(replyText).send();
		
		navigation.goToSent().openMsgWithSubject("Re: "+testSubj);			
		sa.assertTrue(message.body.getText().contains(replyText),"Reply text is not found inside Re: message!");
		
		navigation.goToInbox().openMsgWithSubject("Re: "+testSubj);			
		sa.assertTrue(message.body.getText().contains(replyText),"Reply text is not found inside Re: message!");		
		navigation.logout();
		sa.assertAll();
	}

	@Test (description = "As a User I can forward inbox message")//, dependsOnMethods = "testOpenInboxMessage")
	public void test_ForwardInboxMessage() {
		loginWithValidData();
		navigation.goToInbox().openMsgWithSubject(testSubj);				
		String bodyText = message.body.getText();
		
		message.forwardTo(validLogin+"@ukr.net").send();
		navigation.goToSent().openMsgWithSubject("Fw: "+testSubj);
		
		sa.assertTrue(message.body.getText().contains(bodyText), "Original text is not found inside Forwarded message!");
		navigation.logout();
		sa.assertAll();
	}
	
	@Test (description = "As a User I can move opened Inbox message to Trash immediately)")//, dependsOnMethods = "testForwardMessage")
	public void test_Read_And_Move_ToTrash() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.openAnyFromList();	
		message.moveToTrash();
		navigation.goToTrash();
		trash.openMessageWithId(inbox.msgId);
		sa.assertEquals(message.subject.getText(), inbox.msgSubj,"Mismatch of Subject in original/deleted message!");
		navigation.logout();
		sa.assertAll();
	}	
		
	@Test (description = "As a User I can move a message to Trash from Inbox-list ")//, dependsOnMethods = "")
	public void test_Move_FromInboxList_ToTrash() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.selectAnyFromList().moveItToTrash();				
		navigation.goToTrash();
		sa.assertTrue(trash.getMessageWithId(inbox.msgId).isDisplayed(), "Deleted message not found in Trash!");
		navigation.logout();
		sa.assertAll();
	}
	
	@Test (description = "As a User I can recover message from Trash back to Inbox")//, dependsOnMethods = "testForwardMessage")
	public void test_Recover_FromTrashList_ToInbox() {
		loginWithValidData();		
		navigation.goToTrash();
		trash.selectAnyFromList().moveItToInbox();				
		navigation.goToInbox();
		sa.assertTrue(inbox.getMessageWithId(trash.msgId).isDisplayed(), "Recovered message not found inside Inbox!");
		navigation.logout();
		sa.assertAll();
	}
		
	@Test (description = "As a User I can move a message to Spam directly from Inbox")
	public void test_Move_FromInboxList_ToSpam() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.selectAnyFromList().moveItToSpam();				
		navigation.goToSpam();
		sa.assertTrue(spam.getMessageWithId(inbox.msgId).isDisplayed(), "Moved message not found in Spam!");
		navigation.logout();
		sa.assertAll();
	}
	
	@Test (description = "As a User I can recover message from Spam folder (back to Inbox)")
	public void test_Recover_FromSpamList_ToInbox() {
		loginWithValidData();		
		navigation.goToSpam();
		spam.selectAnyFromList().ItIsNotSpam();				
		navigation.goToInbox();
		sa.assertTrue(inbox.getMessageWithId(spam.msgId).isDisplayed(), "Recovered message not found inside Inbox!");
		navigation.logout();
		sa.assertAll();
	}
	
	@Test (description = "As a User I can drag-n-drop selected message from Inbox to Spam folder)")
	public void test_DragAndDrop_InboxToSpam() throws InterruptedException {
		loginWithValidData();		
		navigation.goToInbox();		
		inbox.selectAnyFromList();
		WebElement selected = inbox.getMessageWithId(inbox.msgId);
		WebElement target = navigation.spamLink;
		inbox.dragNDrop(selected, target);					
		
		navigation.goToSpam();
		sa.assertTrue(spam.getMessageWithId(inbox.msgId).isDisplayed(), "drag-n-dropped message not found inside target folder!");
		navigation.logout();
		sa.assertAll();
	}	
	
	@Test()
	public void test_Send_With_Attachment() throws Exception {
		loginWithValidData();
		navigation.newMessage();
		setMessage("test_Send_With_Attachment",sampleText);		
		message.addLocalFile(attachmentPath).send();
		navigation.goToSent();
		sent.openMsgWithSubject("test_Send_With_Attachment");
		sa.assertEquals(message.attachmentName.getText(), attachmentName);
		
		navigation.goToInbox();
		sent.openMsgWithSubject("test_Send_With_Attachment");
		sa.assertEquals(message.attachmentName.getText(), attachmentName);
		
		navigation.logout();
		sa.assertAll();		
	}

	
}
	/*
	 * 									  			
					 <!-- include name=""/-->
								 
						
					
				  	<include name=""/>
				  	<include name=""/>
				  	<include name="testRegPageEmptyFieldsErrorHandling" />
					<include name="testCheckRegistrationLinks"/>
					<include name="testRegistration"/>
					 <!-- include name=""/-->		
				  
	 */
	
	
	//As a User I can attach small file (up to 10MB) to a message
	//prev-next message
		
//DB:
	//As a User I can see a list of inbox messages
	//As a User I can see a list of sent messages
	
	//password recovery
	//login in public place (no cookies)