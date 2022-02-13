package Test;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;

public class Login03 {
	static WebDriver driver = null;
	/**
     * 
     * @author Ngoc Vu
     *        ************** 
     *        This TestCase will use dataProvider of TestNG. 
     *        DataProvider get data from excel.
     *        1, Login successful
     *			2 check manager id on the page.
     */		
	@BeforeMethod
	public static void setUpTest() {
		System.setProperty("webdriver.chrome.driver", Util.PROJECT_PATH + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts()
		.implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(Util.BASE_URL + "/V4/");
	}
	
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
	
	@DataProvider(name = "test1Data")
	public Object[][] getData() {
		Object data[][] = getDataFromExcel(Util.PROJECT_PATH + Util.FILE_PATH, Util.SHEET_NAME);
		return data;
	}
	
	@Test(dataProvider = "test1Data")
	public static void login(String userName, String passWord) {
		
		System.out.println("test1: " + userName + " | " + passWord);
	
		LoginPage loginOjb = new LoginPage(driver);
		loginOjb.userName().sendKeys(userName);
		loginOjb.password().sendKeys(passWord);
		loginOjb.btnLogin().click();
		
		try {
			Alert alert = driver.switchTo().alert();
			String actualAlert = alert.getText();
			alert.accept();
			
			//check actual alert text to match expected text
			Assert.assertEquals(Util.EXPECT_ALERT, actualAlert);
		} catch(NoAlertPresentException Ex) {
			String actTitle = driver.getTitle();
			Assert.assertEquals(Util.EXPECT_TITLE, actTitle);
			
			//check Manger Id shown after login successfully.
			List<WebElement> l = driver.findElements(By.xpath("//td[contains(text(),'Manger Id : " + Util.USER_NAME + "')]"));
			Assert.assertEquals(l.size(), 1);
			
			//Take Screenshot of the output
			File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File dest = new File("./Screenshot/login.png");
	        try {
				FileUtils.copyFile(srcFile, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@AfterMethod
	public static void tearDownTest() {
		driver.quit();
	}
	
}
