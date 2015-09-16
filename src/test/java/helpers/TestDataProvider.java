package helpers;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
	
	public static String xlsDataFile = "src/test/java/helpers/testDataFile.xls";
		
	@DataProvider(name="regEmptyFieldsErrorMessages")
	public static Object[][] regEmptyFieldsErrorMessages(){
		return new Object[][]{
				{"RU","src/test/java/helpers/emptyErrorMsgRU.txt"},
				{"UA","src/test/java/helpers/emptyErrorMsgUA.txt"},
				{"ENG","src/test/java/helpers/emptyErrorMsgENG.txt"}
			};
	}	
	
	
	@DataProvider(name="regData")
	public static Object[][] regData() throws IOException{
		return readExcel(xlsDataFile,"regData");
	};
	
	
	@DataProvider(name="loginData")
	public static Object[][] loginData() throws IOException{
		return readExcel(xlsDataFile,"loginData");
		
	}
	
	public static Object[][] readExcel(String filePath, String sheetName) throws IOException{		
		File file = new File(filePath);
	    FileInputStream inputStream = new FileInputStream(file);	 
	    Workbook workbook = null;	    
	    String fileExtension = filePath.substring(filePath.lastIndexOf("."));
	  	 
	    if(fileExtension.equalsIgnoreCase(".xlsx")){
	    	workbook = new XSSFWorkbook(inputStream);
	     }
	    else if(fileExtension.equalsIgnoreCase(".xls")){
	    	 workbook = new HSSFWorkbook(inputStream);
	     }
	 	 
	    Sheet sheet = workbook.getSheet(sheetName);
	    int cols = sheet.getRow(0).getPhysicalNumberOfCells();
	    int rows = sheet.getPhysicalNumberOfRows();
	    Object[][] obj = new Object[rows-1][cols];
	    
	    for (int i = 1; i < rows; i++) {//skip header row 
	        Row currentRow = sheet.getRow(i);	        	 
	        for (int j = 0; j < cols; j++) { //Cells can't be empty!
	            obj[i-1][j] = currentRow.getCell(j).getStringCellValue();	        		 
	        }
	    }
		return obj;
	}	
	
}
	 


