package utilities;

import java.io.IOException;

public class BrowserFactory {
	
	final public static Integer port = 9222;
	final private static String cmd = "cmd.exe";
	final private static String parameter = "/c";
	final private static String commandRunBrowser = "chrome.exe -remote-debugging-port=" + port
			+ " --user-data-dir=\"C:\\Selenium\\Chrome_Test_Profile\"";
	final private static String commandPriority1 = "wmic process where name=\"chrome.exe\" CALL setpriority realtime";
	final private static String commandPriority2 = "wmic process where name=\"chromedriver.exe\" CALL setpriority realtime";
	final private static String commandTaskKill = "Taskkill /f /im \"chromedriver.exe\"";
	
	public static void runBrowser() {
		ProcessBuilder debuggerBuilder;
		Process process;
		try {
			debuggerBuilder = new ProcessBuilder(cmd, parameter, commandRunBrowser);
			process = debuggerBuilder.start();
			System.out.println("chrome.exe debugger mod'ta baslatildi. port=" + port);
			Thread.sleep(2000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void setPriority() {
		ProcessBuilder priorityBuilder1, priorityBuilder2;
		Process process;
		priorityBuilder1 = new ProcessBuilder(cmd, parameter, commandPriority1);
		try {
			process = priorityBuilder1.start();
			Thread.sleep(2000);
			priorityBuilder2 = new ProcessBuilder(cmd, parameter, commandPriority2);
			process = priorityBuilder2.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Priorty is set.");
	}

	public static void taskKill() {
		ProcessBuilder processBuilder;
		Process process;
		try {
			processBuilder = new ProcessBuilder(cmd, parameter, commandTaskKill);
			process = processBuilder.start();
			System.out.println("Tum \"chromedriver.exe\" yazilimlari durduruldu.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
