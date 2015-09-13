package helpers;

import org.testng.annotations.DataProvider;

public class TestData {
	
	@DataProvider(name="login")
	public static Object[][] login(){
		return new Object[][]{
				{"tester0667804531","p455word",true, "Valid credentials"},
				{"tester0667804531","wrong_pass",false,"Неверно указан логин или пароль. Подробнее"},
				{"wrong_login_name","p455word",false,"Неверно указан логин или пароль. Подробнее"}
		};
	}
						
	

}
