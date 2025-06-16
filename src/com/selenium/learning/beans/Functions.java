package com.selenium.learning.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Functions {
	public WebDriver driver;
	public WebElement element;
	public static Properties prop = new Properties();
	public Random random=new Random();
	public String dataSheetName="fruits_list.xlsx";
	public HashMap<String, String> map=new HashMap();

	public String loadInputPropertyFile(String str) throws IOException {
		String propHome = System.getProperty("user.dir");
		// System.out.println(propHome);
		FileInputStream fis = new FileInputStream(propHome + "/input.properties");
		prop.load(fis);
		String value = prop.getProperty(str);
		return value;

	}
	
	public HashMap readExcel() throws IOException {
		String excelPath= System.getProperty("user.dir")+"/dataset/fruits_list.xlsx";
		FileInputStream fis=new FileInputStream(new File(excelPath));
		Workbook workbook= new XSSFWorkbook(fis);
		Sheet sheet=workbook.getSheet("Sheet1");
		int lastRowCount=sheet.getLastRowNum();
		//Row row=sheet.getRow(0);
		/*
		 * String firstColumnAttribute=sheet.getRow(0).getCell(0).getStringCellValue();
		 * System.out.println(firstColumnAttribute);
		 */
		for(int i=1;i<=lastRowCount;i++) {		
		map.put(sheet.getRow(i).getCell(0).getStringCellValue(), sheet.getRow(i).getCell(1).getStringCellValue());
		}
		
		  for (String key : map.keySet()) { System.out.println("These fruits are not yet purchased "+key + " = " +
		  map.get(key)); }
		 
		return map;
		
	}
	
	public void writeExcel() throws IOException {
		String excelPath= System.getProperty("user.dir")+"/dataset/fruits_list.xlsx";
		FileInputStream fis=new FileInputStream(new File(excelPath));
		Workbook workbook= new XSSFWorkbook(fis);
		Sheet sheet=workbook.getSheet("Sheet1");
		int lastRowCount=sheet.getLastRowNum();
		sheet.getRow(1).getCell(2).setCellValue("120");
		fis.close();  // Always close input stream before writing

        // Now write to file
        FileOutputStream fos = new FileOutputStream(excelPath);
        workbook.write(fos);
        fos.close();
        workbook.close();
		
	}

	public String loadLocatorPropertyFile(String str) throws IOException {
		String propHome = System.getProperty("user.dir");
		// System.out.println(propHome);
		FileInputStream fis = new FileInputStream(propHome + "/locator.properties");
		prop.load(fis);
		String value = prop.getProperty(str);
		return value;

	}

	public WebElement elementSearch(String str) {
		element = driver.findElement(By.xpath(str));
		return element;

	}

	public void dropDwonHandle(String loc, String text) {
		element = driver.findElement(By.xpath(loc));
		Select select = new Select(element);
		select.selectByVisibleText(text);

	}

	public void launchBrowser(String browserName, String url) {
		/*
		 * System.setProperty("webdriver.edge.driver",
		 * "D:\\Business\\Java\\Drivers\\msedgedriver.exe"); No need to use as selenium
		 * manager will create by own
		 */
		switch (browserName) {
		case "edge":
			/* open in incognito mode */
			System.setProperty("webdriver.edge.driver", "D:\\Business\\Selenium\\Driver\\msedgedriver.exe");
			/*
			 * WebDriverManager.edgedriver().setup(); EdgeOptions edgeoption = new
			 * EdgeOptions(); edgeoption.addArguments("-inprivate");
			 */
			driver = new EdgeDriver();
			break;
		case "chrome":
			/* open in incognito mode */
			ChromeOptions chromeoption = new ChromeOptions();
			chromeoption.addArguments("--incognito");
			driver = new ChromeDriver(chromeoption);
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("Incorrect browser name");

		}
		driver.get(url);
		driver.manage().window().maximize();
		System.out.println("Title: " + driver.getTitle());
	}// End launchBrowser(String name)

	public void Get(String url) {
		driver.get(url);

	}

	public String dynamicTotalItems() {
		String totalItemsXpath = "//td[text()='Items']/following-sibling::td[2]//strong";
		String totalItems = driver.findElement(By.xpath(totalItemsXpath)).getText();
		try {
			int number=random.nextInt(100);
			partialScreenshot(totalItemsXpath, "Total item"+number);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return totalItems;

	}// End dynamicTotalItems

	public String dynamicTotalPrice() {
		String totalPriceXpath = "//td[text()='Price']/following-sibling::td[2]//strong";
		String totalPrice = driver.findElement(By.xpath(totalPriceXpath)).getText();
		try {
			int number=random.nextInt(100);
			partialScreenshot(totalPriceXpath, "Total price"+number);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return totalPrice;

	}// End dynamicTotalPrice

	/*
	 * public String dynamicVeggie(String veggieName) { String xpath =
	 * "//h4[contains(text(),'" + veggieName + "')]"; return xpath;
	 * 
	 * } // End DynamicVeggie(String veggie)
	 */
	public String dynamicVeggiePrice(String veggieName) {
		String veggiePriceXpath = "//h4[contains(text(),'" + veggieName + "')]/following-sibling::p";
		String veggiePrice = driver.findElement(By.xpath(veggiePriceXpath)).getText();
		return veggiePrice;

	}

	public int dynamicSelectVeggie(String veggieName, int veggieQuantity) {
		String veggiePriceXpath = "//h4[contains(text(),'" + veggieName + "')]/following-sibling::p";
		String oneUnitVeggiePrice = driver.findElement(By.xpath(veggiePriceXpath)).getText();
		int veggiePrice = Integer.parseInt(oneUnitVeggiePrice);
		// System.out.println(veggiePrice);

		String veggieQuantityInputPath = "//h4[contains(text(),'" + veggieName
				+ "')]/following-sibling::div//input[@class='quantity']";
		driver.findElement(By.xpath(veggieQuantityInputPath)).clear();
		driver.findElement(By.xpath(veggieQuantityInputPath)).sendKeys(Integer.toString(veggieQuantity));

		String addToCart = "//h4[contains(text(),'" + veggieName
				+ "')]//following-sibling::div[2]//button[text()='ADD TO CART']";
		driver.findElement(By.xpath(addToCart)).click();
		impleciteWait(3);
		String str="//h4[contains(text(),'"+veggieName+"')]//parent::div";
		try {
			partialScreenshot(str, veggieName);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		int totalPrice = veggiePrice * veggieQuantity;

		return totalPrice;

	}
	

	public void click(String str) {
		driver.findElement(By.xpath(str)).click();
	}

	public String read(String str) {
		String value;
		value = driver.findElement(By.xpath(str)).getText();
		return value;
	}
	
	public void impleciteWait(int time) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
	}
	
	public WebDriverWait expliciteWait(int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait;
	}
	
	public void partialScreenshot(String loc, String screenshotName) throws IOException {
	File screehShot=driver.findElement(By.xpath(loc)).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(screehShot, new File("Greenkart-"+screenshotName+".png"));
		
	}

	public void closeBrowser() {
		driver.quit();;
	}

}
