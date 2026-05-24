package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class inventoryPage extends basePage {
    private WebDriver driver;

    public inventoryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // Locators
    private final By firstItemImage = By.cssSelector(".inventory_item_img img");
    private final By inventoryList = By.className("inventory_list");

    // Actions & Validations
    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    public String getFirstItemImageSrc() {
        WebElement image = waitUntil(firstItemImage);
        return image.getAttribute("src");
    }

    public boolean isInventoryListDisplayed() {
        return waitUntil(inventoryList).isDisplayed();
    }
}
