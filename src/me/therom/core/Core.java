package me.therom.core;

public class Core
{
	public static void main(String[] args) throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver",args[0]);
		Job job = new Job(args[1], args[2], Integer.parseInt(args[3]));
		job.startJob();
	}
}
