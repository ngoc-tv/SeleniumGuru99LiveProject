package Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.LoginPage;
import Test.Util;

public class Login {

	static String baseURL = null;
	static WebDriver driver = null;
	/**
     * 
     * @author Ngoc Vu
     *        ************** 
     *        This TC will perform following Test Steps
     *        
     *        1)  Go to http://www.demo.guru99.com/V4/
              2) Enter valid UserId
              3) Enter valid Password
              4) Click Login
              5) Verify the Page Title after login
     */		
	
	public static void setUpTest() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		/*	System.setProperty("webdriver.ie.driver", projectPath+"//drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();*/
		
		baseURL = Util.BASE_URL;
		driver.manage().timeouts()
		.implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(baseURL + "/V4/");
	}
	public static void main(String[] args) {
		
		setUpTest();
		
		LoginPage loginOjb = new LoginPage(driver);
		loginOjb.userName().sendKeys(Util.USER_NAME);
		loginOjb.password().sendKeys(Util.PASSWD);
		loginOjb.btnLogin().click();
		
		String actTitle = driver.getTitle();
		Assert.assertEquals(actTitle, Util.EXPECT_TITLE);
		
		driver.quit();
	}
	
}
