package com.manipal.khanAcadamy;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class DataDrivenTest {

	public AndroidDriver driver;

	@BeforeMethod

	public void setUp() throws MalformedURLException, InterruptedException {

		DesiredCapabilities capability = new DesiredCapabilities();
		// capability.setCapability("deviceName", "Manzoor");
		capability.setCapability(MobileCapabilityType.DEVICE_NAME, "Suresh");
		capability.setCapability(MobileCapabilityType.APPLICATION_NAME, "Android");
		// capability.setCapability(MobileCapabilityType.NO_RESET,"true");//it will stay
		// on homescreen
		capability.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, "true"); // it wil not ask any
																								// permission once we
																								// open app
		capability.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "org.khanacademy.android");
		capability.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
				"org.khanacademy.android.ui.library.MainActivity");
		driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capability);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Dismiss\")")).click();
		Thread.sleep(4000);
		driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Sign in\")")).click();
		Thread.sleep(10000);
		driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Sign in\")")).click();

	}

	@Test
	public void dataDriven() throws IOException, InterruptedException {

		File file = new File("C:\\Users\\LakshmiSureshThakkel\\Desktop\\Manipal\\DataDriven.xlsx");
		System.out.println(file.exists());

		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Suresh");

		System.out.println(sheet.getLastRowNum());

		for (int row = 1; row <= sheet.getLastRowNum(); row++) {

			String email = sheet.getRow(row).getCell(0).getStringCellValue();
			String password = sheet.getRow(row).getCell(1).getStringCellValue().toString();

			System.out.println(email + "and the password is " + password);
			driver.findElement(MobileBy.className("android.widget.EditText")).clear();
			driver.findElement(MobileBy.className("android.widget.EditText")).sendKeys(email);
			driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Password\")")).clear();
			driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Password\")")).sendKeys(password);
			driver.hideKeyboard();
			Thread.sleep(5000);
			
			driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Sign in\"]/android.widget.TextView")).click();
			
			Thread.sleep(10000);
			 String Actual = "Invalid password";

			 String Expected = driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"Invalidpassword\")")).getText();
			 System.out.println(Expected);

			assertEquals(Actual, Expected);

		}

	}

}
