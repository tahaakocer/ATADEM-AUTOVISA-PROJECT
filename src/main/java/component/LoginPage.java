package component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigReader;
import utilities.WaitMethods;

public class LoginPage extends BasePage {

	private WebDriver driver;
	private String agency = "Atadem";
	private String[] email;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		email = new String[3];
		email[0] = ConfigReader.getProperty("email1");
		email[1] = ConfigReader.getProperty("email2");
		email[2] = ConfigReader.getProperty("email3");

		
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

		emailIdElement.sendKeys(email[BasePage.profile]);
		input(emailIdElement, email[BasePage.profile]);

		confirmEmailIdElement.sendKeys(email[BasePage.profile]);
		input(confirmEmailIdElement, email[BasePage.profile]);
	}
}
