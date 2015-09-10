package helpers;

import org.testng.annotations.DataProvider;

public class TestData {
	
	@DataProvider(name="login")
	public static Object[][] login(){
		return new Object[][]{
				{"tester0667804531","p455word","Valid credentials"},
				{"tester0667804531","wrong_pass","������� ������ ����� ��� ������. ���������"},
				{"wrong_login_name","p455word","������� ������ ����� ��� ������. ��������� "}
		};
	}
						
	

}
