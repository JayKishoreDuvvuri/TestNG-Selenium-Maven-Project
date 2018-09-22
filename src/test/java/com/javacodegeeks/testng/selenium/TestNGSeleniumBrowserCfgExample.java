package com.javacodegeeks.testng.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNGSeleniumBrowserCfgExample {
   private WebDriver driver;

	@Parameters("browser")
	@BeforeTest
	public void setUp(String browser) {
		System.out.println("You are testing on browser " + browser);
		browser = browser.toLowerCase();

		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "C:/geckodriver/geckodriver.exe");
			driver = new FirefoxDriver();
		}
	}

	@Test(dataProvider = "searchStrings")
	public void searchGoogle(final String searchKey) {
		System.out.println("Search " + searchKey + " in google");
		driver.navigate().to("http://www.google.com");
		WebElement element = driver.findElement(By.name("q"));
		System.out.println("Enter " + searchKey);
		element.sendKeys(searchKey);
		System.out.println("submit");
		element.submit();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith(searchKey.toLowerCase());
			}
		});
		System.out.println("Got " + searchKey + " results");
	}

	@DataProvider
	private Object[][] searchStrings() {
		return new Object[][] { { "TestNG" }, { "Selenium" } };
	}

	@AfterTest
	public void quitDriver() throws Exception {
		driver.quit();
	}
}
