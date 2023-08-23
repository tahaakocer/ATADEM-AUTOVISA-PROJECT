package tests;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

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
import utilities.MailSender;
import utilities.WaitMethods;
import utilities.BrowserFactory;
import utilities.DriverFactory;

public class AppTest {

	private WebDriver driver;
	private ChromeOptions options;
	private AppPage appPage;

	@Before
	public void setUp() {
		if (driver == null) {
			driver = DriverFactory.createDriver(BrowserFactory.port);
		}
	}

	@Test
	public void test01() {
		appPage = new AppPage(driver);
		
		Integer sayac = 0;
		LocalDateTime lastRefresh = LocalDateTime.now();
		appPage.refresh = true;
		
		while (appPage.running) {
			if(appPage.next == true && appPage.refresh == true) {
				appPage.clickDayOfNextMonth();
			}
 			appPage.clickDay(appPage.day);
			sayac++;
			WaitMethods.bekle(1);
			if (!driver.findElements(By.xpath(appPage.yesXpath)).isEmpty()) {
				appPage.clickByXpath(appPage.yesXpath);
			}
			if (!driver.findElements(By.xpath(appPage.headXpath)).isEmpty()) {

				LocalDateTime now = LocalDateTime.now();
				System.out.println(now + ": randevu yok");
				if (now.isAfter(lastRefresh.plusMinutes(8))) {
					lastRefresh = now;
					appPage.refreshPage();
				}

			} else if (!driver.findElements(By.xpath(appPage.appTimeXpath)).isEmpty()) {

				if(appPage.autoGet == true) {
					appPage.getAppointment(appPage.count, appPage.day);
				}
				Thread emailThread = new Thread(() -> {
					try {
						appPage.Sound();
						MailSender.sendMail(appPage.day);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				emailThread.start();
				System.out.println(AppPage.numOfApp + " adet randevu bulundu!");
				appPage.running = false;
				WaitMethods.bekle(10);
			}

		}
	}

	@After
	public void tearDown() {
		DriverFactory.closeDriver(driver);
	}
}
