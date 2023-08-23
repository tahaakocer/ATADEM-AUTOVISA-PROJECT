package tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import component.LoginPage;
import utilities.BrowserFactory;
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
