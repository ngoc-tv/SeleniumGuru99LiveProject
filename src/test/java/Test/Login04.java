package Test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;

public class Login04 {
	static WebDriver driver = null;
	/**
     * 
     * @author Ngoc Vu
     *        ************** 
     *        This TestCase will use dataProvider of TestNG. 
     *        DataProvider DO NOT get data from excel. Create data for DataProvider
     *        1, Login successful
     *			2, check the dynamic text manager id on the page.
     */		
	@BeforeMethod
	public static void setUpTest() {
		System.setProperty("webdriver.chrome.driver", Util.PROJECT_PATH + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts()
		.implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(Util.BASE_URL + "/V4/");
	}
	
	@DataProvider(name = "test1Data")
	public Object[][] getData() {
		Object[][] data = new Object[4][2];

		// 1st row
		data[0][0] = Util.USER_NAME;
		data[0][1] = Util.PASSWD;
		//2nd row
		data[1][0] = "invalid";
		data[1][1] = "valid";
		//3rd row
		data[2][0] = "valid";
		data[2][1] = "invalid";
		//4th row
		data[3][0] = "invalid";
		data[3][1] = "invalid";
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
			// Get text displayes on login page 
			String pageText = driver.findElement(By.tagName("tbody")).getText();

			// Extract the dynamic text mngrXXXX on page		
			String[] parts = pageText.split(Util.PATTERN);
			String dynamicText = parts[1];

			// Check that the dynamic text is of pattern mngrXXXX
			// First 4 characters must be "mngr"
			assertTrue(dynamicText.substring(1, 5).equals(Util.FIRST_PATTERN));
			// remain stores the "XXXX" in pattern mngrXXXX
			String remain = dynamicText.substring(dynamicText.length() - 4);
			// Check remain string must be numbers;
			assertTrue(remain.matches(Util.SECOND_PATTERN));
			
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
