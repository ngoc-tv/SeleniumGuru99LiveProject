package Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;

public class LoginDay02 {
	static String baseURL = null;
	static WebDriver driver = null;
	/**
     * 
     * @author Ngoc Vu
     *        ************** 
     *        This TestCase will use dataProvider in TestNG
     *        
     *        1)  Go to http://www.demo.guru99.com/V4/
              2) Enter valid UserId
              3) Enter valid Password
              4) Click Login
              5) Verify the Page Title after login
     */		
	@BeforeTest
	public static void setUpTest() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		/*	System.setProperty("webdriver.ie.driver", projectPath+"//drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();*/
	}
	@Test(dataProvider = "test1Data")
	public static void login(String userName, String passWord) {
		
		System.out.println("test1: " + userName + " | " + passWord);
		
		Util util = new Util();
		baseURL = util.BASE_URL;
		driver.manage().timeouts()
		.implicitlyWait(util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(baseURL + "/V4/");
		
		LoginPage loginOjb = new LoginPage(driver);
		loginOjb.userName().sendKeys(userName);
		loginOjb.password().sendKeys(passWord);
		loginOjb.btnLogin().click();
	}
	@DataProvider(name = "test1Data")
	public Object[][] getData() {
		String projectpath = System.getProperty("user.dir");
		String excelPath = projectpath+"/excel/data.xlsx";
		Object data[][] = testData(excelPath, "Sheet1");
		return data;
	}
	
	public Object[][] testData(String excelPath, String sheetName) {
		ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
		
		int rowCount = excel.getRowCount();
		int colCount = excel.getColCount();
		
		Object data[][] = new Object[rowCount-1][colCount]; 
		
		for(int i=1 ; i < rowCount; i++) {
			for(int j=0; j< colCount; j++){
			 String cellData =	excel.getCellDataString(i, j);
			 data[i-1][j] = cellData;
			}
			System.out.println();
		}
		return data;
	}
	@AfterTest
	public static void tearDownTest() {
		driver.quit();
	}
	
}
