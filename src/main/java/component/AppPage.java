package component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.WaitMethods;

public class AppPage extends BasePage {

	private WebDriver driver;
	private JavascriptExecutor js;
	private WebDriverWait wait;
	private Map<String, WebElement> dateMap;

	public String appTimeXpath = "//span[@class='ui-button-text ui-unselectable-text']";
	public String headXpath = "//h2[normalize-space()='No slots available']";
	public String appNextXpath = "//span[@class='ui-button-text ui-clickable']";
	public String OKxpath = "//span[normalize-space()='OK']";
	public String yesXpath = "//span[normalize-space()='Yes']";
	public String nextMonthXpath = "";
	public boolean next = false;
	public boolean refresh;
	public boolean running = true;
	public boolean autoGet = true;
	public static Integer numOfApp;

	public AppPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		dateMap = new HashMap<>();
		setDateMap();
	}

	@FindBy(xpath = "//h2[normalize-space()='No slots available']")
	public WebElement headElement;

	@FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td/a")
	public List<WebElement> dateElements;

	@FindBy(xpath = "//span[@class='ui-button-text ui-unselectable-text']")
	public List<WebElement> appTimeElements;
	
	@Override
	public void refreshPage() {
		System.out.println("sayfa yenileniyor.");
		driver.navigate().refresh();
		WaitMethods.waitForPageToLoad(driver, 30);
		WaitMethods.bekle(4);
		System.out.println("sayfa yenilendi.");
		refresh = true;
	}

	public void setDateMap() {
		for (WebElement element : dateElements) {
			String day = element.getText();
			dateMap.put(day, element);
		}
	}

	public WebElement getDateElement(String day) {
		return dateMap.get(day);
	}

	public void clickDay(String day) {
		WebElement dateElement = getDateElement(day);
		click(dateElement);
	}

	public boolean checkIt() {
		return !driver.findElements(By.xpath("//span[@class='ui-button-text ui-unselectable-text']")).isEmpty();
	}

	public void getAppointment(Integer count, String day) {

		List<WebElement> AppElements = driver.findElements(By.xpath(appTimeXpath));
		numOfApp = AppElements.size();

		if (count <= numOfApp) {
			for (int i = numOfApp - 1; i >= numOfApp - count; i--) {
				click(AppElements.get(i));
			}
			WaitMethods.bekle(1);
			clickByXpath(appNextXpath);

			if (!driver.findElements(By.xpath(OKxpath)).isEmpty()) {
				clickByXpath(OKxpath);
				getAppointment(count, day);

			} else if (driver.findElements(By.xpath(OKxpath)).isEmpty()) {
				System.out.println("Otomatik randevu alindi.");

			}

		} else {

			clickDay(day);
			System.out.println("Butona tiklandi, 1 saniye bekletilecek");
			WaitMethods.bekle(1);
			getAppointment(count, day);
		}
	}
	
	public void Sound() throws LineUnavailableException {
		float frequency = 240; // 440 Hz
		int durationMs = 500; // 1 saniye
		byte[] buffer = new byte[durationMs * 8 * 2]; // PCM verileri için buffer boyutunu hesaplar

		for (int i = 0; i < buffer.length; i++) {
			double angle = i / (8000.0 / frequency) * 2.0 * Math.PI;
			short amplitude = (short) (Math.sin(angle) * 32767);
			buffer[i++] = (byte) (amplitude & 0xFF);
			buffer[i] = (byte) (amplitude >> 8);
		}

		AudioFormat format = new AudioFormat(8000, 16, 1, true, true);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

		line.open(format);
		line.start();
		line.write(buffer, 0, buffer.length);
		line.drain();
		line.close();
	}
}
