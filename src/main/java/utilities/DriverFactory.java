package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
	public static WebDriver createDriver(Integer port) {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		ChromeDriverService service = new ChromeDriverService.Builder().withLogOutput(System.out).build();
		options.setExperimentalOption("debuggerAddress", "localhost:" + port); // açık pencere portu
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--priority=high");

		return new ChromeDriver(service, options);
		
	}
	
	public static void closeDriver(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}
	
	
}
