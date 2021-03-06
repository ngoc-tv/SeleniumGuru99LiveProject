package Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import pages.LoginPage;

public class Login02 {
	static String baseURL = null;
	static WebDriver driver = null;
	/**
     * 
     * @author Ngoc Vu
     * ************** 
     * This TestCase will read data from Excel:
     * 1, Login successfully. Verify the title of the Homepage
     * 2, Login Fail: invalid username and valid password, a pop-up "user or password is not valid"
     * 3, Login Fail: valid username and invalid password, a pop-up "user or password is not valid"
     * 4, Login Fail: invalid username and invalid password, a pop-up "user or password is not valid"
     */		
	public static void setUpTest() {
		System.setProperty("webdriver.chrome.driver", Util.PROJECT_PATH + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		/*	System.setProperty("webdriver.ie.driver", Util.PROJECT_PATH+"//drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();*/
		
		driver.manage().timeouts()
		.implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(Util.BASE_URL + "/V4/");
	}
	public static void main(String[] args) {
		String username, password;
		
		String[][] testData = getDataFromExcel(Util.PROJECT_PATH + Util.FILE_PATH, Util.SHEET_NAME);
		
		for(int i=0; i< testData.length; i++) {
			setUpTest();
			username = testData[i][0];
			password = testData[i][1];
			
			LoginPage loginOjb = new LoginPage(driver);
			loginOjb.userName().sendKeys(username);
			loginOjb.password().sendKeys(password);
			loginOjb.btnLogin().click();
			
			try {
				Alert alert = driver.switchTo().alert();
				String actualAlert = alert.getText();
				alert.accept();
				System.out.println("Alert title: " + actualAlert); 
				if(actualAlert.equalsIgnoreCase(Util.EXPECT_ALERT)) {
					System.out.println("Test case SS[" + i + "]: Passed"); 
				} else {
				    System.out.println("Test case SS[" + i + "]: Failed");
				}
			} catch(NoAlertPresentException Ex) {
				String actTitle = driver.getTitle();
				if(actTitle.equalsIgnoreCase(Util.EXPECT_TITLE))
					System.out.println("Test case SS[" + i + "]: Passed"); 
				else 
					System.out.println("Test case SS[" + i + "]: Failed"); 
			}
			driver.quit();
		}
	}
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
