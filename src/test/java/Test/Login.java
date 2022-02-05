package Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.LoginPage;

public class Login {

	public static void main(String[] args) {
		login();
	}
	public static void login() {
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath + "\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.demo.guru99.com/V4/");
		
		/*
		 * LoginPage.userName(driver).sendKeys("mngr384511");
		 * LoginPage.password(driver).sendKeys("yjehesA");
		 * LoginPage.btnLogin(driver).click();
		 */
		
		LoginPage loginOjb = new LoginPage(driver);
		loginOjb.userName().sendKeys("mngr384511");
		loginOjb.password().sendKeys("yjehesA");
		loginOjb.btnLogin().click();
		
		driver.quit();
	}
}
