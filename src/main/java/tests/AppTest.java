package tests;

import java.time.LocalDateTime;

import javax.swing.SwingWorker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import component.AppPage;
import component.BasePage;
import main.Main;
import utilities.MailSender;
import utilities.WaitMethods;
import utilities.BrowserFactory;
import utilities.DriverFactory;

public class AppTest {

	private WebDriver driver;
	private AppPage appPage;

//	@Before
	public void setUp() {
		if (driver == null) {
			BrowserFactory browserFactory = new BrowserFactory();
			driver = DriverFactory.createDriver(BrowserFactory.port[BasePage.profile]);
			System.out.println("apptest driver baslatildi. \n" + driver);
			Main.lblInfo.setText("Randevu Aranıyor..");
		}
	}

//	@Test
	public void test01() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				appPage = new AppPage(driver);

				Integer sayac = 0;
				LocalDateTime lastRefresh = LocalDateTime.now();
				appPage.refresh = true;

				while (AppPage.running) {
					if (AppPage.next == true && appPage.refresh == true) {
						appPage.clickDayOfNextMonth();
					}
					appPage.clickDay(AppPage.day);
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
							test01();
						}

					} else if (!driver.findElements(By.xpath(appPage.appTimeXpath)).isEmpty()) {

						if (AppPage.autoGet == true) {
							appPage.getAppointment(AppPage.count, AppPage.day);
						}
						Thread emailThread = new Thread(() -> {
							try {
								appPage.Sound();
								MailSender.sendMail(AppPage.day);
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
						emailThread.start();

						System.out.println(AppPage.numOfApp + " adet randevu bulundu!");
						AppPage.running = false;
						tearDown();
						
						WaitMethods.bekle(5);
						if (AppPage.autoFill == true) {
							FormTest formTest = new FormTest();
							formTest.setUp();
							formTest.test();
							formTest.tearDown();
						}
					}

				}

				return null;
			}

			@Override
			protected void done() {
			}
		};
		worker.execute();

	}

//	@After
	public void tearDown() {
		DriverFactory.closeDriver(driver);
		System.out.println("apptest driver durduruldu.");
		Main.lblInfo.setText("Durduruldu.");
	}
}
