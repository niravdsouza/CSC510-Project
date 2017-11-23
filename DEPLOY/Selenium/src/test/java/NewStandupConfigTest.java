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

public class NewStandupConfigTest
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
    	password.sendKeys("**********");
    	WebElement signin = driver.findElement(By.xpath("//*[@id='signin_btn']/span[1]"));
    	signin.click();
	}

	@AfterClass
	public static void  tearDown() throws Exception
	{
		// driver.close();
		// driver.quit();
	}



	@Test
	public void StartConfig() throws Exception
	{
		WebElement bot;
		driver.get("https://parkwoodgang.slack.com/messages/whatbot/");
		WebDriverWait wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.titleContains("whatbot"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msg_input")));

		// Type help
		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("Help");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'All I need is the standup window, participant list, question set, and reporting medium (Slack channel / Email).']")));
		WebElement help = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'All I need is the standup window, participant list, question set, and reporting medium (Slack channel / Email).']"));
		assertNotNull(help);
		Thread.sleep(3000);

		// Type schedule
		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("I want to setup a standup meeting.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Configuring wrong start time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'What time would you like to start the standup?']")));
		WebElement startTime = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'What time would you like to start the standup?']"));
		assertNotNull(startTime);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("Start at 25");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Configuring start time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'What time would you like to start the standup?']")));
		startTime = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'What time would you like to start the standup?']"));
		assertNotNull(startTime);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("Start the meeting at 9:30 am");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Configuring end time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'When would you like the standup to end?']")));
		WebElement endTime = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'When would you like the standup to end?']"));
		assertNotNull(endTime);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("End at 10:30 am");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Configuring participants
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Who would you like to invite for the standup session?']")));
		WebElement users = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Who would you like to invite for the standup session?']"));
		assertNotNull(users);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("#general");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Configuring questions
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Would you like to give your own question set?']")));
		WebElement questions = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Would you like to give your own question set?']"));
		assertNotNull(questions);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("Yes, I would like to.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Enter the questions
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Ok! Give me all the questions, each on a new line, and say DONE to finish.']")));
		WebElement q1 = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Ok! Give me all the questions, each on a new line, and say DONE to finish.']"));
		assertNotNull(q1);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("How much did you accomplish yesterday?");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		bot.sendKeys("How much did you plan to complete today?");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		bot.sendKeys("Is any issue blocking your tasks today?");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		bot.sendKeys("I'm done giving the questions.");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Look for the buttons
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'channel')]")));
	  	List<WebElement> reportButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'channel')]"));
	  	reportButton.get(reportButton.size()-1).click();

    	// Configure channel name that doesn't exist.
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Which slack channel do you want to use? E.g. #general']")));
		WebElement channelName = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Which slack channel do you want to use? E.g. #general']"));
		assertNotNull(channelName);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("#abrade");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(5000);

		// Validate error message for invalid channel.
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[contains(@class,'message_body') and text() = 'This channel does not exist.']")));
		
		// Look for the buttons
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'channel')]")));
	  	reportButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'channel')]"));
	  	reportButton.get(reportButton.size()-1).click();

    	// Configure channel name in which the bot is not present.
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Which slack channel do you want to use? E.g. #general']")));
		channelName = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Which slack channel do you want to use? E.g. #general']"));
		assertNotNull(channelName);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("#random");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(8000);

		// Validate error message for channel in which bot is not present.
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[contains(@class,'message_body') and text() = 'I am not a member of this channel.']")));

		// Look for the buttons
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn_attachment'and contains(., 'channel')]")));
	  	reportButton = driver.findElements(By.xpath("//button[@class='btn btn_attachment'and contains(., 'channel')]"));
	  	reportButton.get(reportButton.size()-1).click();

    	// Configure valid channel name.
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Which slack channel do you want to use? E.g. #general']")));
		channelName = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Which slack channel do you want to use? E.g. #general']"));
		assertNotNull(channelName);
		Thread.sleep(3000);

		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div")); // ("msg_input").);
		bot.sendKeys("#general");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		// Type show
		bot = driver.findElement(By.xpath("//div[@id='msg_input']/div"));
		bot.sendKeys("Show config");
		bot.sendKeys(Keys.RETURN);
		Thread.sleep(3000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'message_body') and text() = 'Let me show you the current configuration...']")));
		WebElement showConfig = driver.findElement(
				By.xpath("//span[contains(@class,'message_body') and text() = 'Let me show you the current configuration...']"));
		assertNotNull(showConfig);
		Thread.sleep(10000);
	}
}
