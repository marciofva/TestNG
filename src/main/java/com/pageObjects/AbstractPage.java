package com.pageObjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPage {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected WebElement element;

	private static final Logger logger = LoggerFactory.getLogger(AbstractPage.class);

	public AbstractPage(WebDriver driver) {
		this.driver = driver;
		this.wait = (new WebDriverWait(driver, 15));
		logger.trace("Constructor: {}", this.getClass());
	}

	public WebElement getUniqueElement(By selector) {

		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
		} catch (NoSuchElementException e) {
			logger.error("getUniqueElement: {} " + e, this.getClass());
		}

		List<WebElement> elements = driver.findElements(selector);
		assertTrue("Unique Element \'" + selector.toString() + "\' not found for " + this.getClass().getSimpleName(),
				(elements.size() == 1));
		return elements.get(0);
	}

	public boolean isElementClickable(By locator) {
		logger.trace("Check whether Element '" + locator + "' is clickable - {}.", this.getClass());
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementPresent(By locator) {
		logger.trace("Check whether Element '" + locator + "' is present in the page - {}.", this.getClass());
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
