package tests;

import helpers.TestData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import pages.*;

public class SmokeTest {
	
	public static final String validLogin = "tester0667804531";
	public static final String validPass = "p455word";
	public static final String testSubj = "test: create new message";	
	public static final String replyText = "This is a plain text reply";
	public static final String sampleText = "test message body - "
			+ "Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
			+ " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
			+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
			+ " Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
			+ "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	
	public WebDriver driver;
	public SoftAssert sa;
	
	public Registration regPage;	 
	public ConfirmationPage confirm;
	public Login login; 
	public Navigation navigation;	
	public Folder inbox, drafts, sent, spam, trash;
	public Message message;	
	
	@BeforeClass
	public void setup() {
		driver = new FirefoxDriver();		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				
		regPage = new Registration(driver);
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
	public void newSoftAssertObject(){
		this.sa = new SoftAssert();			
	}
		
	@AfterClass
	public void tearDown() throws Exception {	    
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
			login
			.setLanguage("RU")
			.loginAs(validLogin, validPass);
		}				
	}
	
	private void setMessage(String subj, String bodyText) {
		message
		.to(validLogin+"@ukr.net")
		.subject(subj)
		.setBodyText(bodyText);				
	}
	
		//User can register - skipped - capcha!
	//@Test (description = "UnSuccessful registration")
	public void testRegistration() {
		regPage.load()
		.setLanguage("RU")
		.setUsername("newuser99")
		.setPassword("пароль")
		.setFirstName("им€")
		.setSurName("фамили€")
		.setGender("M")
		.setDateOfBirth("22", "12", "2000")
		.setMobile("093 0000000")
		.setEmail("wrwe@erte.com");
		//set capcha...
		regPage.submitBtn.click();
		//assert success page is opened...
		sa.assertEquals(driver.getTitle(), "–егистраци€ выполнена успешно");
		sa.assertTrue(confirm.goToMailboxBtn.isDisplayed(), " 'go to Mailbox' button is not found!");
		sa.assertAll();		
	}

	@Test(description = "Checking registration links on the login page")
	public void testCheckRegistrationLinks() {
		login.load();
		if (loggedIn()) {navigation.logout();}		
		sa.assertEquals(
			login.registrationBtn.getAttribute("href"),login.registrationLink.getAttribute("href"));
		login.load().registrationBtn.click();
		sa.assertTrue(regPage.surNameField.isDisplayed(),"Can't find a 'Surname' text field ! ");
		sa.assertTrue(regPage.submitBtn.isDisplayed(),"Can't find a 'Submit' button !");			
		sa.assertAll();
	}
	
	@Test(description = "Checking error messages on the Registration page - 'Empty fields'") 
	public void testRegPageEmptyFieldsErrorHandling() throws IOException {
		regPage.load().setLanguage("RU");
		regPage.submitBtn.click();		
		BufferedReader fileIn = null;
		try {
			fileIn = new BufferedReader(new FileReader
					("src/test/java/helpers/emptyErrorMsgRU.ser"));
			for (WebElement errMsg : regPage.errorsList) {			
				sa.assertEquals(errMsg.getText(), fileIn.readLine());		
			}
		} catch (IOException e) { 
			e.printStackTrace();
		} finally { 
			if (fileIn != null) fileIn.close();	
		}
		sa.assertAll();
	}
		
	@Test(description = "As a User I can login only with valid credentials", dataProvider = "login",
			dataProviderClass =	TestData.class)
	public void testLogin(String account, String password, String errorMsg) {
		login.load()
		.setLanguage("RU")
		.loginAs(account, password);			
		if (loggedIn()) {
			sa.assertFalse(navigation.inboxLink.isDisplayed(), "Inbox folder link is missing!");
			navigation.logout();
		} else {
			sa.assertTrue(login.loginBtn.isDisplayed(), "Can't find the Login button!");
			sa.assertEquals(login.loginErrorMsg.getText(), errorMsg, "Something wrong with Error messsage!");
		}
		sa.assertAll();
	}		
		
	@Test(description = "As a User I can create a message(it's stored in Drafts automatically)")
	public void testCreateMessage() {
		loginWithValidData();
		navigation.newMsg();
		setMessage(testSubj,sampleText);		
		navigation.goToDrafts();
		drafts.openMsgWithSubject(testSubj);		
		sa.assertEquals(message.bodyEditor.getText(), sampleText,"Mismatch between actual and draft bodytext!");
		navigation.logout();
		sa.assertAll();
	}	
	 		
	@Test (description = "As a User I can send a message (it's stored in Sent automatically)")
	public void testSendMessage() {
		loginWithValidData();
		for (int i=1; i<4; i++){
			navigation.newMsg();
			setMessage(testSubj+"-"+i,sampleText);
			message.send();
		}
		navigation.goToSent();
		sent.openAnyFromList();		
		sa.assertEquals(message.body.getText(), sampleText,"Mismatch between actual and sent bodytext!");
		navigation.logout();
		sa.assertAll();
		
	}	
	
	@Test (description = "As a User I can open inbox message from list")//, dependsOnMethods = "testSendMessage")
	public void testOpenInboxMessage() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.openMsgWithSubject(testSubj);		
		sa.assertEquals(message.body.getText(), sampleText,"Text mismatch between sent/inbox message body!");
		navigation.logout();
		sa.assertAll();
	}	
	
	@Test (description = "As a User I can reply to inbox message")//, dependsOnMethods = "testOpenInboxMessage")
	public void testReplyToMessage() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.openMsgWithSubject(testSubj);			
		message.reply().setBodyText(replyText).send();
		
		navigation.goToSent();
		sent.openMsgWithSubject("Re: "+testSubj);			
		sa.assertTrue(message.body.getText().contains(replyText),"Reply text is not found inside Re: message!");
		
		navigation.goToInbox();
		inbox.openMsgWithSubject("Re: "+testSubj);			
		sa.assertTrue(message.body.getText().contains(replyText),"Reply text is not found inside Re: message!");		
		navigation.logout();
		sa.assertAll();
	}

	@Test (description = "As a User I can forward inbox message")//, dependsOnMethods = "testOpenInboxMessage")
	public void testForwardInboxMessage() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.openMsgWithSubject(testSubj);				
		String bodyText = message.body.getText();
		
		message.forwardTo(validLogin+"@ukr.net").send();
		navigation.goToSent();
		sent.openMsgWithSubject("Fw: "+testSubj);
		sa.assertTrue(message.body.getText().contains(bodyText), "Original text is not found inside Forwarded message!");
		
		navigation.logout();
		sa.assertAll();
	}
	
	@Test (description = "As a User I can delete inbox message to Trash while it's opened)")//, dependsOnMethods = "testForwardMessage")
	public void testReadAndMoveToTrash() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.openAnyFromList();	
		message.moveToTrash();
		navigation.goToTrash();
		trash.openMessage(inbox.msgId);
		sa.assertEquals(message.subject.getText(), inbox.msgSubj,"Mismatch of Subject in original/deleted message!");
		navigation.logout();
		sa.assertAll();
	}	
		
	@Test (description = "As a User I can move a message to Trash strait from Inbox-list ")//, dependsOnMethods = "")
	public void testMoveToTrashFromInboxList() {
		loginWithValidData();
		navigation.goToInbox();
		inbox.selectAnyFromList();
		inbox.moveSelectedToTrash();				
		navigation.goToTrash();
		sa.assertTrue(trash.getMessageWithId(inbox.msgId).isDisplayed(), "Deleted message not found in Trash!");
		navigation.logout();
		sa.assertAll();
	}
	
	@Test (description = "As a User I can recover message from Trash-list back to Inbox")//, dependsOnMethods = "testForwardMessage")
	public void testRecoverFromTrashToInbox() {
		loginWithValidData();		
		navigation.goToTrash();
		trash.selectAnyFromList();
		trash.moveSelectedToInbox();				
		navigation.goToInbox();
		sa.assertTrue(inbox.getMessageWithId(trash.msgId).isDisplayed(), "Recovered message not found inside Inbox!");
		navigation.logout();
		sa.assertAll();
	}
}

	/*
	 * 				<include name="testCheckRegistrationLinks"/>
					<include name="testRegistration"/>
					 <!-- include name=""/-->
					<include name="testLogin"/>
					<include name="testCreateMessage" />				
					<include name="testSendMessage" />
					<include name="testOpenInboxMessage" />
					<include name="testReplyToMessage" />
				  	<include name="testForwardInboxMessage" />					
				   	<include name="testMoveToTrashFromInboxList" />
				   	<include name="testReadAndMoveToTrash" />				  
				  	<include name="testRecoverFromTrashToInbox" />
				  
	 */
	
	//As a User I can move a message to spam folder
	//As a User I can recover a message from spam
	
	//As a User I can attach small file (up to 10MB) to a message
	
	//login in public place (no cookies)
	
//DB:
	//As a User I can see a list of inbox messages
	//As a User I can see a list of sent messages
	
	//password recovery}