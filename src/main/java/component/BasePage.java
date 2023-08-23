package component;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.WaitMethods;

public class BasePage {
	private WebDriver driver;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void click(WebElement element) {
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
	}
	
	public void waitNClick(WebElement element) {
		WaitMethods.waitForVisibility(driver, element, 15);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
	}
	
	public void clickByXpath(String xpath) {
		WebElement element = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
	}
	
	public void input(WebElement element,String input) {
		((JavascriptExecutor)driver).executeScript("arguments[0].value = arguments[1];", element, input);
	}
	

}
