package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	
	private static WebElement element = null;
	private static WebDriver driver = null;
	public LoginPage(WebDriver driver1) {
		driver = driver1;
	}
	public WebElement userName() {
			element = driver.findElement(By.name("uid"));
			return element;
		}
	
	public WebElement password() {
		element = driver.findElement(By.name("password"));
		return element;
	}
	public WebElement btnLogin() {
		element = driver.findElement(By.name("btnLogin"));
		return element;
	}
}
