package tests;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.SwingWorker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import component.BasePage;
import component.FormPage;
import utilities.BrowserFactory;
import utilities.DriverFactory;

public class FormTest {

	private WebDriver driver;
	private FormPage formPage;
	
	@Before
	public void setUp() {
		if (driver == null) {
	
			BrowserFactory browserFactory = new BrowserFactory();
			driver = DriverFactory.createDriver(BrowserFactory.port[BasePage.profile]);
			System.out.println("formtest driver baslatildi.\n" + driver);

		}
	}

	@Test
	public void test() {
	

			formPage = new FormPage(driver);
			formPage.fillAppointment();

	}

	@After
	public void tearDown() {
		DriverFactory.closeDriver(driver);
	}

}
