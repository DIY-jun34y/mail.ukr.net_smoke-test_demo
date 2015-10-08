package tests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.util.List;

import pages.*;
import helpers.BaseClass;
import static helpers.TestDataProvider.get_db_inboxList;
import static tests.Test_Login.validLogin;
import static tests.Test_Login.validPass;

public class Test_Functional_Main extends BaseClass{
	
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
	
	private SoftAssert sa;
	private Login login; 
	private Navigation navigation;	
	private Folder inbox, sent, spam, trash;
	private Message message;	
	
	@BeforeClass
	public void setup() {
		login = new Login();
		navigation = new Navigation();		
		inbox = new Folder();		
		sent = new Folder();
		spam = new Folder();
		trash = new Folder();		
		message = new Message();
		loginWithValidData();
	}	
		
	@BeforeMethod
	public void beforeMethod(){
		this.sa = new SoftAssert();		
	}
	
	/*@AfterMethod
	public void afterMethod(){		
	}*/	
	
	@AfterClass()
	public void clean_All_Folders() {
		loginWithValidData();
		navigation.goToInbox().selectAll().moveItToTrash();
		navigation.goToDrafts().selectAll().moveItToTrash();
		navigation.goToSent().selectAll().moveItToTrash();
		navigation.goToSpam().selectAll().moveItToTrash();
		navigation.goToTrash().clearAll();
		navigation.logout();
	}
			
	private boolean isLoggedIn(){
		String pageTitle = driver.getTitle();
		if (pageTitle.contains(validLogin+"@ukr.net")) {
			return true;
		}else {
			return false;
		}		
	}
	
	private void loginWithValidData() {
		login.load();
		if (isLoggedIn()) {
			return;
		} else {
			login.loginAs(validLogin, validPass);
		}				
	}
				
	@Test(priority = 0, description = "As a User I can create a message(it's stored in Drafts automatically)")
	public void test_CreateMessage() {
		navigation.newMessage();
		message.setTo(validLogin+"@ukr.net").setSubject(testSubj).setBodyText(sampleText);		
		navigation.goToDrafts().openMsgWithSubject(testSubj);
		sa.assertEquals(message.bodyEditor.getText(), sampleText,"Mismatch between actual and draft bodytext!");
		sa.assertAll();
	}	
	 		
	@Test (priority = 1, description = "As a User I can send a message (it's stored in Sent automatically)")
	public void test_SendMessage() {		
		navigation.goToInbox();
		for (int i=1; i<4; i++){
			navigation.newMessage();			
			message.setTo(validLogin+"@ukr.net").setSubject(testSubj+"-"+i).setBodyText(sampleText);
			message.send();
		}
		navigation.goToSent().openMsgWithSubject(testSubj);		
		sa.assertEquals(message.body.getText(), sampleText,"Mismatch between actual and sent bodytext!");		
		sa.assertAll();
		
	}
	
	@Test(priority = 2, description = "Check if Inbox message list is consistent with Database")
	public void test_Compare_InboxList_to_DB() throws Exception {	
		navigation.goToInbox();		
		List<String> subjList = get_db_inboxList();
		for (int i=0; i< subjList.size(); i++){
			sa.assertTrue(inbox.msgList.get(i).getText().contains(subjList.get(i)));					
		}
		sa.assertTrue(inbox.msgList.size()==subjList.size(),"Number of messages in 'Inbox' not equal to DB");
		sa.assertAll();	
	}	

	
	@Test(priority = 3, description = "As a User I can send a message with attachment file")
	public void test_Send_With_Attachment() throws Exception {		
		navigation.newMessage();		
		message.setTo(validLogin+"@ukr.net").setSubject("test_Send_With_Attachment").setBodyText(sampleText);		
		message.addLocalFile(attachmentPath).send();
		
		navigation.goToSent();
		sent.openMsgWithSubject("test_Send_With_Attachment");
		sa.assertEquals(message.attachmentName.getText(), attachmentName);
		
		navigation.goToInbox();
		sent.openMsgWithSubject("test_Send_With_Attachment");
		sa.assertEquals(message.attachmentName.getText(), attachmentName);		
		sa.assertAll();		
	}
	
	@Test (priority = 4, description = "As a User I can open inbox message from list")
	public void test_OpenInboxMessage() {		
		navigation.goToInbox().openMsgWithSubject(testSubj);		
		sa.assertEquals(message.body.getText(), sampleText,"Text mismatch between sent/inbox message body!");		
		sa.assertAll();
	}	
	
	@Test (priority = 4, description = "As a User I can reply to inbox message")
	public void test_ReplyToMessage() {		
		navigation.goToInbox();
		inbox.openAnyFromList();					
		message.reply().setBodyText(replyText).send();
		
		navigation.goToSent().openMsgWithSubject("Re: "+inbox.msgSubj);			
		sa.assertTrue(message.body.getText().contains(replyText),"Reply text is not found inside Re: message![Sent folder]");
		
		navigation.goToInbox().openMsgWithSubject("Re: "+inbox.msgSubj);			
		sa.assertTrue(message.body.getText().contains(replyText),"Reply text is not found inside Re: message![Inbox folder]");		
		sa.assertAll();
	}

	@Test (priority = 4, description = "As a User I can forward inbox message")
	public void test_ForwardInboxMessage() {		
		navigation.goToInbox();		
		inbox.openAnyFromList();		
		message.forwardTo(validLogin+"@ukr.net").send();
		
		navigation.goToSent().openMsgWithSubject("Fw: "+inbox.msgSubj);		
		sa.assertTrue(message.body.getText().contains(inbox.msgBody), "Original text is not found inside Forwarded message! [Sent folder]");
		
		navigation.goToInbox().openMsgWithSubject("Fw: "+inbox.msgSubj);
		sa.assertTrue(message.body.getText().contains(inbox.msgBody), "Original text is not found inside Forwarded message! [Inbox folder]");
		
		sa.assertAll();
	}
	
	@Test (priority = 4, description = "As a User I can move opened Inbox message to Trash immediately)")
	public void test_Read_And_Move_ToTrash() {		
		navigation.goToInbox();
		inbox.openAnyFromList();	
		message.moveToTrash();
		navigation.goToTrash();
		trash.openMessageWithId(inbox.msgId);
		sa.assertEquals(message.subject.getText(), inbox.msgSubj,"Mismatch of Subject in original/deleted message!");		
		sa.assertAll();
	}	
		
	@Test (priority = 4, description = "As a User I can move a message to Trash from Inbox-list ")
	public void test_Move_FromInboxList_ToTrash() {
		navigation.goToInbox();
		inbox.selectAnyFromList().moveItToTrash();				
		navigation.goToTrash();
		sa.assertTrue(trash.getMessageWithId(inbox.msgId).isDisplayed(), "Deleted message not found in Trash!");		
		sa.assertAll();
	}
	
	@Test (priority = 4, description = "As a User I can recover message from Trash back to Inbox")
	public void test_Recover_FromTrashList_ToInbox() {
		navigation.goToTrash();
		trash.selectAnyFromList().moveItToInbox();				
		navigation.goToInbox();
		sa.assertTrue(inbox.getMessageWithId(trash.msgId).isDisplayed(), "Recovered message not found inside Inbox!");		
		sa.assertAll();
	}
		
	@Test (priority = 4, description = "As a User I can move a message to Spam directly from Inbox")
	public void test_Move_FromInboxList_ToSpam() {
		navigation.goToInbox();
		inbox.selectAnyFromList().moveItToSpam();				
		navigation.goToSpam();
		sa.assertTrue(spam.getMessageWithId(inbox.msgId).isDisplayed(), "Moved message not found in Spam!");
		sa.assertAll();
	}
	
	@Test (priority = 4, description = "As a User I can recover message from Spam folder (back to Inbox)")
	public void test_Recover_FromSpamList_ToInbox() {
		navigation.goToSpam();
		spam.selectAnyFromList().itIsNotSpam();				
		navigation.goToInbox();
		sa.assertTrue(inbox.getMessageWithId(spam.msgId).isDisplayed(), "Recovered message not found inside Inbox!");
		sa.assertAll();
	}
	
	@Test (priority = 4, description = "As a User I can drag-n-drop selected message from Inbox to Spam folder)")
	public void test_DragAndDrop_InboxToSpam() throws InterruptedException {
		navigation.goToInbox();		
		inbox.selectAnyFromList();
		WebElement selected = inbox.getMessageWithId(inbox.msgId);
		WebElement target = navigation.spamLink;
		inbox.dragNDrop(selected, target);					
		
		navigation.goToSpam();
		sa.assertTrue(spam.getMessageWithId(inbox.msgId).isDisplayed(), "drag-n-dropped message not found inside target folder!");
		sa.assertAll();
	}
}
		
	
	

	//prev-next message	
	//password recovery
	//login in public place