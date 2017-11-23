import static org.junit.Assert.*;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class EditStandupConfigTest
{
	private static WebDriver driver;

	public static String URL = "https://parkwoodgang.slack.com";

	@BeforeClass
	public static void setUp() throws Exception
	{
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
		driver.get(URL);
        assertEquals("Slack", driver.getTitle());

    	WebDriverWait wait = new WebDriverWait(driver, 30);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='signin_form']/p[1]/strong[1]")));
    	WebElement email = driver.findElement(By.xpath("//*[@id='email']"));
    	email.sendKeys("whatbot.ncsu@gmail.com");
    	WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
    	password.sendKeys("********");
    	WebElement signin = driver.findElement(By.xpath("//*[@id='signin_btn']/span[1]"));
    	signin.click();
	}

	@AfterClass
	public static void  tearDown() throws Exception
	{
		driver.close();
		driver.quit();
	}



	@Test
	public void ModifyConfig() throws Exception
	{
		driver.get("https://parkwoodgang.slack.com/messages/whatbot/");
		WebDriverWait wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.titleContains("whatbot"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msg_input")));

		// Modify standup
		WebElement bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("Modify standup.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify start time
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Start')]")));
    	List<WebElement> startButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Start')]"));
    	startButton.get(startButton.size()-1).click();
    	Thread.sleep(3000);

		// Configuring start time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'What time would you like to start the standup?']")));
		WebElement startTime = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'What time would you like to start the standup?']"));
		assertNotNull(startTime);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("10:30 am");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify standup
		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("Modify standup.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify end time
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'End')]")));
    	List<WebElement> endButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'End')]"));
    	endButton.get(endButton.size()-1).click();
    	Thread.sleep(3000);

		// Configuring end time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'When would you like the standup to end?']")));
		WebElement endTime = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'When would you like the standup to end?']"));
		assertNotNull(endTime);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("11:30 am");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify standup
		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("Modify standup.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify participants
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Participants')]")));
    	List<WebElement> participantButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Participants')]"));
    	participantButton.get(participantButton.size()-1).click();
    	Thread.sleep(3000);

		// Configuring participants
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Remove')]")));
    	List<WebElement> removeButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Remove')]"));
    	removeButton.get(removeButton.size()-1).click();
    	Thread.sleep(3000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Who would you like to remove from the standup session?']")));
		WebElement removeMsg = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Who would you like to remove from the standup session?']"));
		assertNotNull(removeMsg);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("@cfernan3");
		bot.sendKeys(Keys.RETURN);
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify standup
		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("Modify standup.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify questions
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Question')]")));
    	List<WebElement> questionButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Question')]"));
    	questionButton.get(questionButton.size()-1).click();
    	Thread.sleep(3000);

		// Enter the questions
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Ok! Give me all the questions, each on a new line, and say DONE to finish.']")));
		WebElement q1 = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Ok! Give me all the questions, each on a new line, and say DONE to finish.']"));
		assertNotNull(q1);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("What did you do yesterday?");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		bot.sendKeys("What will you do today?");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		bot.sendKeys("Done.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify standup
		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("Modify standup.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Modify report medium
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Reporting')]")));
    	List<WebElement> reportMedButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Reporting')]"));
    	reportMedButton.get(reportMedButton.size()-1).click();
    	Thread.sleep(3000);

		// Look for the buttons
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Email')]")));
    	List<WebElement> reportButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'Email')]"));
    	reportButton.get(reportButton.size()-1).click();

    	Thread.sleep(5000);
	}
}
