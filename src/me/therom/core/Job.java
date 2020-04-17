package me.therom.core;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Job
{
	private String amount;
	private String exitAmount;
	private int rounds;
	private WebDriver driver;

	protected Job(String amount, String exitAmount, int rounds)
	{
		this.amount = amount;
		this.exitAmount = exitAmount;
		this.rounds = rounds;
		this.driver = new ChromeDriver();
	}

	protected void startJob() throws InterruptedException
	{
		System.out.println("[Game] Starting...");

		driver.get("https://www.csgoroll.com/en/crash");

		Thread.sleep(5000);

		int counter = 0;

		while (true)
		{
			List<WebElement> created = driver.findElements(By.className("created"));

			if (created.size() == 1 && created.get(0).getText().contains("Next round in...") && counter <= this.rounds)
			{
				try
				{
					System.out.println(startRound());
				}
				catch (Exception ex)
				{
					System.out.println(ex);
					System.exit(0);
				}

				counter++;
			}
		}
	}

	private String startRound() throws InterruptedException
	{
		WebElement searchBox = driver.findElement(By.className("cdk-text-field-autofill-monitored"));

		searchBox.sendKeys(String.valueOf(this.amount));
		Thread.sleep(50);
		searchBox.submit();

		boolean won = false;

		while (true)
		{
			try
			{
				if (driver.findElement(By.className("started")).getText().contains(this.exitAmount + "x"))
				{
					driver.findElements(By.className("mat-button-lg")).get(1).click();
					won = true;
					break;
				}
				
				driver.findElement(By.className("finished"));
				//If no exception is thrown the finished exists which mean lost.
				break;
			}
			catch (Exception ex) 
			{
				continue;
			}
		}
		
		return won ? "[Game] Round won!" : "[Game] Round lost!";
	}
}