package component;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.WaitMethods;

public class BasePage {
	
	private WebDriver driver;
	public static boolean running = true;
	public static boolean autoGet = true;
	public static boolean next = false;
	public static boolean autoFill = false;
	public static Integer numOfApp;
	public static Integer count = 5;
	public static String day = "27";
	public static Integer profile = 1;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public void click(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void waitNClick(WebElement element) {
		WaitMethods.waitForVisibility(driver, element, 15);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	public void waitNClick(By locator) {
		WebElement element =  WaitMethods.waitForVisibility(driver, locator, 15);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void clickableWaitNClick(By locator) {
		WebElement element =  WaitMethods.waitForClickablility(driver, locator, 15);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	public void clickableWaitNClick(WebElement element) {
		WaitMethods.waitForClickablility(driver, element, 15);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	public void clickByXpath(String xpath) {
		WebElement element = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
	}

	public void input(WebElement element, String input) {
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, input);
	}

	public void refreshPage() {
		System.out.println("sayfa yenileniyor.");
		driver.navigate().refresh();
		WaitMethods.waitForPageToLoad(driver, 30);
		WaitMethods.bekle(4);
		System.out.println("sayfa yenilendi.");
	}

}
