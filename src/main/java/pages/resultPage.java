package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class resultPage {
    private WebDriver driver;

    public resultPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle()
    {
        return driver.getTitle();
    }

    By links;
    public void setLinks(String query) {
        links = By.partialLinkText(query);
    }

    public WebElement getLinks() {
        return driver.findElement(links);
    }
}
