package tests;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import component.AppPage;
import component.LoginPage;
import utilities.BrowserFactory;
import utilities.ConfigReader;
import utilities.DriverFactory;
import utilities.WaitMethods;

public class LoginTest {

	private WebDriver driver;
	private LoginPage loginPage;

	@Before
	public void setUp() {
		if (driver == null) {
			driver = DriverFactory.createDriver(BrowserFactory.port);
		}
	}

	@Test
	public void test01() {
		loginPage = new LoginPage(driver);
		WaitMethods.waitForVisibility(driver, By.xpath("//p-dropdown[@id='location']/div/div[3]/span"), 15);
		loginPage.login(loginPage.count);
		System.out.println(driver.getTitle());
	}

	@After
	public void tearDown() {
	  DriverFactory.closeDriver(driver);
	} 
}
