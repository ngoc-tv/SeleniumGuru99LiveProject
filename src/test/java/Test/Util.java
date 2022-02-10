package Test;

/*
 * Declare some common parameters for scripts
 */
public class Util {
	public static final String PROJECT_PATH = System.getProperty("user.dir");
	
	//For excel file/excel/data.xlsx
	public static final String FILE_PATH = "/excel/data.xlsx"; // File Path
    public static final String SHEET_NAME = "Sheet1"; // Sheet name
    
	public static final String FIREFOX_PATH ="C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe";
	public static final String BASE_URL = "http://www.demo.guru99.com/";
	
	public static final int WAIT_TIME = 30; 
	
	public static final String USER_NAME ="mngr384511";
	public static final String PASSWD ="yjehesA";
	
	//Expected Outputs
	public static final String EXPECT_TITLE ="Guru99 Bank Manager HomePage";
	public static final String EXPECT_ALERT ="User or Password is not valid";
	
	//For Dynamic text in Homepage
	public static final String PATTERN = ":";
    public static final String FIRST_PATTERN = "mngr";
    public static final String SECOND_PATTERN = "[0-9]+";
	
	/**
	 * @author: Ngoc Vu
	 * This method reads the data from the Sheet name "Sheet1" of file
	 * "excel/data.xls"
	 * 
	 * @return a 2 dimensions array to store all the test data read from excel
	 *
	 */
	
	public static String[][] getDataFromExcel(String excelPath, String sheetName) {
		ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
		
		int rowCount = excel.getRowCount();
		int colCount = excel.getColCount();
		
		String data[][] = new String[rowCount-1][colCount]; 
		
		for(int i=1 ; i < rowCount; i++) {
			for(int j=0; j< colCount; j++){
			 String cellData =	excel.getCellDataString(i, j);
			 //System.out.print("testData " + cellData + " | ");
			 data[i-1][j] = cellData;
			}
			System.out.println();
		}
		return data;
		
	}
}
