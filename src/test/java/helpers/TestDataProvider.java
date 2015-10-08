package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
	
	public static final String xlsDataFile = "src/test/java/helpers/testDataFile.xls";
	
	private static final Map<String,String> dbCol_inboxSubject;
	static {
		 dbCol_inboxSubject = new HashMap<String, String>();
		 dbCol_inboxSubject.put("dbUrl","jdbc:mysql://localhost:3306/mail_ukr_net");
		 dbCol_inboxSubject.put("query","SELECT * FROM inbox_list ORDER BY id DESC");
		 dbCol_inboxSubject.put("user","root");
		 dbCol_inboxSubject.put("password","123");
		 dbCol_inboxSubject.put("column","subject");		
	}
	
	
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
	}	
	
	@DataProvider(name="loginData")
	public static Object[][] loginData() throws IOException{
		return readExcel(xlsDataFile,"loginData");
		
	}
	
	public static List<String> get_db_inboxList() throws Exception{
		return read_db_ColumnToList(dbCol_inboxSubject);		
	}
	
	private static List<String> read_db_ColumnToList(Map<String,String> db_param) throws Exception{		 
		String dbUrl = db_param.get("dbUrl");
		String query = db_param.get("query");
		String username = db_param.get("user");
		String password = db_param.get("password");
		String dbColumn = db_param.get("column");	
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();	
		Connection con = DriverManager.getConnection(dbUrl+"?user="+username+"&password="+password);
		Statement stmt = con.createStatement();        
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<String> list = new ArrayList<String>();
        while (rs.next()){
        	list.add(rs.getString(dbColumn));
        }
        con.close();
        return list;   		
	}

		
	private static Object[][] readExcel(String filePath, String sheetName) throws IOException{		
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
	        for (int j = 0; j < cols; j++) { //Cells shouldn't be empty!
	            obj[i-1][j] = currentRow.getCell(j).getStringCellValue();	        		 
	        }
	    }
		return obj;
	}		
}