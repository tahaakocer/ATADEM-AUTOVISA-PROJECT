package component;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Person;
import utilities.WaitMethods;

public class FormPage extends BasePage {
	private WebDriver driver;
	private String proceedXpath = "//span[normalize-space()='Proceed']";
	private String turkeyXpath = "//li[@aria-label='Turkey']";
	private String businessXpath = "//li[@aria-label='Business']";

	public FormPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//p-dropdown[starts-with(@id, 'nationality')]/div/div[3]/span")
	public List<WebElement> nationalityElements;

	@FindBy(xpath = "//input[starts-with(@id, 'lastName')]")
	public List<WebElement> lastNameElements;

	@FindBy(xpath = "//input[starts-with(@id, 'firstAndMiddleName')]")
	public List<WebElement> firstNameElements;

	@FindBy(xpath = "//input[starts-with(@id, 'passportNumber')]")
	public List<WebElement> passNumElements;

	@FindBy(xpath = "//p-calendar[starts-with(@id, 'passportExpiryDate')]/span/input")
	public List<WebElement> dateElements;

	@FindBy(xpath = "//p-dropdown[starts-with(@id, 'visaType')]/div/div[3]/span")
	public List<WebElement> visaTypeElements;

	@FindBy(xpath = "//input[starts-with(@id, 'contactNo.')]")
	public List<WebElement> phoneNumElements;

	@FindBy(xpath = "//input[starts-with(@id, 'emailAddress')]")
	public List<WebElement> emailAddressElements;

	@FindBy(xpath = "//div[starts-with(@id, 'ui-accordiontab-')]/div/div/div[5]/div[2]/button")
	public List<WebElement> saveElements;

	@FindBy(xpath = "//span[normalize-space()='Proceed']")
	public WebElement proceedElement;

	public void fillAppointment() {
		WaitMethods.waitForVisibility(driver, By.xpath(proceedXpath), 15);
		System.out.println("XPathler listelere aktarıldı.");
		System.out.println("Form doldurma islemi Baslatildi. 5 saniye bekletilecek.");
		
		List<Person> persons = new ArrayList<Person>();
		for (int say = 0; say < count; say++) {
			persons.add(new Person());
		}
		
		WaitMethods.bekle(5);

		for (int i = 0; i < count; i++) {
			click(nationalityElements.get(i));
			clickableWaitNClick(By.xpath(turkeyXpath));
			WaitMethods.msBekle(200);
			
			lastNameElements.get(i).sendKeys(persons.get(i).getLastName());
			input(lastNameElements.get(i), persons.get(i).getLastName());
			WaitMethods.msBekle(200);
			
			firstNameElements.get(i).sendKeys(persons.get(i).getFirstName());
			input(firstNameElements.get(i), persons.get(i).getFirstName());
			WaitMethods.msBekle(200);
			
			passNumElements.get(i).sendKeys(persons.get(i).getPassNum());
			input(passNumElements.get(i), persons.get(i).getPassNum());
			WaitMethods.msBekle(200);
			
			dateElements.get(i).sendKeys(persons.get(i).getDate());
			input(dateElements.get(i), persons.get(i).getDate());
			WaitMethods.msBekle(200);
			
			click(visaTypeElements.get(i));
			clickableWaitNClick(By.xpath(businessXpath));
			WaitMethods.msBekle(200);
			
			phoneNumElements.get(i).sendKeys(persons.get(i).getPhoneNum());
			input(phoneNumElements.get(i), persons.get(i).getPhoneNum());
			WaitMethods.msBekle(200);
			
			emailAddressElements.get(i).sendKeys(persons.get(i).getMailAddress(driver));
			input(emailAddressElements.get(i), persons.get(i).getMailAddress(driver));
			WaitMethods.bekle(1);
			
			click(saveElements.get(i));
			WaitMethods.bekle(2);
			System.out.println("Form dolduruldu. 2.5 saniye bekletilecek");
			
		}
		clickableWaitNClick(proceedElement);
	}

}
