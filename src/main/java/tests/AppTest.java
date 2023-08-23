package tests;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import component.AppPage;
import component.LoginPage;
import utilities.DriverFactory;

public class AppTest {
	
	private WebDriver driver;
	private ChromeOptions options;
	private WebDriverWait wait;
	private AppPage appPage;
	private String day = "31";
	
	@Before
	public void setUp() {
		if (driver == null) {
			driver = DriverFactory.createDriver(9300);
			wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		}
	}
	
	@Test
	public void test01() {
		appPage = new AppPage(driver);
		LocalDateTime lastRefresh = LocalDateTime.now();
		while (appPage.running) {
			appPage.clickDay(day);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!driver.findElements(By.xpath(appPage.yesXpath)).isEmpty()) {
				appPage.clickByXpath(appPage.yesXpath);
			}
			if (!driver.findElements(By.xpath(appPage.headXpath)).isEmpty()) {
				
				LocalDateTime now = LocalDateTime.now();
				System.out.println(now + ": randevu yok");
				if(now.isAfter(lastRefresh.plusMinutes(8))) {
					lastRefresh = now;
					appPage.refreshPage();
				}
				
			} else if(!driver.findElements(By.xpath(appPage.appTimeXpath)).isEmpty()) {
				
				System.out.println("randevu var");
				appPage.getAppointment(null, day);
			}

		}
	}
	
	@After
	public void tearDown() {
	  DriverFactory.closeDriver(driver);
	} 
}
