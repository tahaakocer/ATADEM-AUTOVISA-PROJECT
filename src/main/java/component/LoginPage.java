package component;

import java.time.Duration;

import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

import org.junit.Test;
import org.junit.validator.PublicClassValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.asm.MemberSubstitution.FieldValue;
import utilities.DriverFactory;
import utilities.WaitMethods;

public class LoginPage extends BasePage {

	private WebDriver driver;
	private JavascriptExecutor js;
	private WebDriverWait wait;
	private String agency = "Atadem";
	private String email = "taha.kocer317@gmail.com";

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	}

	@FindBy(xpath = "//p-dropdown[@id='location']/div/div[3]/span")
	public WebElement locationElement;

	@FindBy(xpath = "//span[normalize-space()='Istanbul']")
	public WebElement istanbulElement;

	@FindBy(xpath = "//p-dropdown[@id='noOfApplicants']/div/div[3]/span")
	public WebElement noOfAppElement;

	@FindBy(xpath = "//input[@id='travelAgency']")
	public WebElement travelAgencyElement;

	@FindBy(xpath = "//input[@id='emailId']")
	public WebElement emailIdElement;

	@FindBy(xpath = "//input[@id='confirmEmailId']")
	public WebElement confirmEmailIdElement;

	public WebElement noElement;

	public void login(Integer num) {

		click(locationElement);
		WaitMethods.waitForVisibility(driver, By.cssSelector("li[aria-label='Istanbul']"), 15);
		click(istanbulElement);
		click(noOfAppElement);
		WaitMethods.waitForVisibility(driver, By.cssSelector("li[aria-label='20']"), 15);
		String noElementXpath = "//li[@aria-label='" + num + "']";
		WebElement noElement = driver.findElement(By.xpath(noElementXpath));
		click(noElement);
		
		travelAgencyElement.sendKeys(agency);
		input(travelAgencyElement, agency);
		
		emailIdElement.sendKeys(email);
		input(emailIdElement, email);
		
		confirmEmailIdElement.sendKeys(email);
		input(confirmEmailIdElement, email);
	}
}
