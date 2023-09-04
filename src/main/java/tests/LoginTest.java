package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import component.BasePage;
import component.LoginPage;
import utilities.BrowserFactory;
import utilities.DriverFactory;
import utilities.WaitMethods;

public class LoginTest {

	private WebDriver driver;
	private LoginPage loginPage;

//	@Before
	public void setUp() {
		if (driver == null) {
			BrowserFactory browserFactory = new BrowserFactory();
			driver = DriverFactory.createDriver(BrowserFactory.port[BasePage.profile]);
			System.out.println("logintest driver baslatildi.\n"+ driver);
		}
	}

//	@Test
	public void test01() {
		loginPage = new LoginPage(driver);
		WaitMethods.waitForVisibility(driver, By.xpath("//p-dropdown[@id='location']/div/div[3]/span"), 15);
		loginPage.login(LoginPage.count);
		System.out.println(driver.getTitle());
	}

//	@After
	public void tearDown() {
	  DriverFactory.closeDriver(driver);
	  System.out.println("logintest driver durduruldu.");
	} 
}
