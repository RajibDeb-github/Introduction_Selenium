package com.selenium.learning.Test;

import java.io.IOException;
import java.net.SocketException;

import org.apache.hc.core5.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.selenium.learning.beans.Functions;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class Test extends Functions {
	// public static WebDriver driver;
	public static final String URL = "https://rahulshettyacademy.com/seleniumPractise/#/";

	public static void main(String[] args) throws InterruptedException {
		Test gk = new Test();
		SoftAssert asrt = new SoftAssert();
		gk.launchBrowser("edge", URL);
		int totalSum = 0;

		// System.out.println("Launched");

		/*
		 * veggie=//h4[contains(text(),'Brocolli')]
		 * priceVeggie=//h4[contains(text(),'Brocolli')]/following-sibling::p
		 * noOfItems=//td[text()='Items']/following-sibling::td[2]//strong
		 * totalPrice=//td[text()='Price']/following-sibling::td[2]//strong
		 * quantityVeggie=//h4[contains(text(),'Brocolli')]/following-sibling::div//
		 * input[@class='quantity'] //veggieAddToCart=
		 * h4[contains(text(),'Cucumber')]//following-sibling::div[2]//button[text()='
		 * ADD TO CART']
		 */
		int noOfItemsInitial = Integer.parseInt(gk.dynamicTotalItems());
		int totalPriceInitial = Integer.parseInt(gk.dynamicTotalPrice());
		asrt.assertEquals(noOfItemsInitial, 1, "Cart is not emply after login");
		asrt.assertEquals(totalPriceInitial, 0, "Cart is not emply after login");
		// System.out.println(noOfItemsInitial+" , "+totalPriceInitial);

		// ********Add vegetables in dynamic way************//
		try {

			int i;
			for (i = 1; i < 7; i++) {
				String veggieName = gk.loadInputPropertyFile("veggie" + i);
				int noVeggie = Integer.parseInt(gk.loadInputPropertyFile("veggie" + i + "Q"));
				// Thread.sleep(1000);
				gk.impleciteWait(1);
				int totalPriceAfterAddToCart = gk.dynamicSelectVeggie(veggieName, noVeggie);
				Thread.sleep(1000);
				gk.impleciteWait(1);
				totalSum = totalSum + totalPriceAfterAddToCart;

			}
			System.out.println("Final cost is: " + totalSum);
			System.out.println("Total items number: " + (i - 1));

		} catch (IOException e) {
			System.out.println("Error in vegetables selection process" + e.getMessage());
		}

		// *****Print the final price***********//
		int noOfItemsFinal = Integer.parseInt(gk.dynamicTotalItems());
		int totalPriceFinal = Integer.parseInt(gk.dynamicTotalPrice());

		asrt.assertEquals(totalSum, totalPriceFinal, "Incorrect calculation in the cart machine");

		System.out.println("Final quantity & cost as per shopmeter: " + noOfItemsFinal + " , " + totalPriceFinal);

		try {
			gk.click(gk.loadLocatorPropertyFile("cart"));
			gk.impleciteWait(1);
			gk.partialScreenshot(gk.loadLocatorPropertyFile("entirePage"), "proceed to check out page");
			gk.click(gk.loadLocatorPropertyFile("proceedToCheckOut"));
			Thread.sleep(2000);
			gk.impleciteWait(1);
			gk.partialScreenshot(gk.loadLocatorPropertyFile("entirePage"), "Place Order page");
			// Thread.sleep(2000);
			gk.impleciteWait(1);
			try {
				String finalAmountToPay = gk.read(gk.loadLocatorPropertyFile("discountedAmountField"));
				System.out.println("The buyer will pay finally: " + finalAmountToPay + "INR after discount");
			} catch (Exception e) {
				System.out.println("Error in final amount calculation page " + e.getMessage());
			}

			gk.click(gk.loadLocatorPropertyFile("placeOrderBtn"));
			// Thread.sleep(1000);
			gk.impleciteWait(1);
			gk.dropDwonHandle(gk.loadLocatorPropertyFile("chooseCountryField"), "India");
			Thread.sleep(1000);
			gk.impleciteWait(3);
			gk.click(gk.loadLocatorPropertyFile("terms&ConditionChkBox"));
			gk.impleciteWait(3);
			gk.partialScreenshot(gk.loadLocatorPropertyFile("entirePage"), "Proceed page");
			gk.click(gk.loadLocatorPropertyFile("proceedBtn"));
			gk.readExcel();
			gk.writeExcel();
			// Thread.sleep(2000);
			gk.impleciteWait(3);
			try {
				asrt.assertAll();
			} catch (AssertionError e) {
				System.out.println(e.getMessage());

			}

			/*
			 * try { gk.closeBrowser(); System.out.println("Thanks!:)"); }catch(Exception e)
			 * { System.out.println("Error!"+e.getMessage()); }
			 */
			
			/*
			 * git init git add .
			 * git commit -m "first commit" 
			 * git branch -M main
			 * git remote add origin
			 * https://github.com/RajibDeb-github/Introduction_Selenium.git git push -u
			 * origin main
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in cart process " + e.getMessage());
		}

	}

}
