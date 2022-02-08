package Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
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
     *        This TestCase will use dataProvider of TestNG
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
			Assert.assertEquals(Util.EXPECT_ALERT, actualAlert);
		} catch(NoAlertPresentException Ex) {
			String actTitle = driver.getTitle();
			Assert.assertEquals(Util.EXPECT_TITLE, actTitle);
			
			List<WebElement> l = driver.findElements(By.xpath("//td[contains(text(),'Manger Id : " + Util.USER_NAME + "')]"));
			Assert.assertEquals(l.size(), 1);
		}
	}
	
	@DataProvider(name = "test1Data")
	public Object[][] getData() {
		Object data[][] = Util.getDataFromExcel(Util.PROJECT_PATH + Util.FILE_PATH, Util.SHEET_NAME);
		return data;
	}
	
	@AfterMethod
	public static void tearDownTest() {
		driver.quit();
	}
	
}
