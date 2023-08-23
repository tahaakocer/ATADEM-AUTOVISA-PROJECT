package component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public boolean next = false;
	public boolean refresh = false;
	public boolean running = true;
	public Integer numOfApp;

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
	public void refreshPage() {
		driver.navigate().refresh();
		refresh = true;
		WaitMethods.bekle(5);
	}
}
