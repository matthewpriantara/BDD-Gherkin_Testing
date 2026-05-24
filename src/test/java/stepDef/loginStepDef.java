package stepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.loginPage;
import pages.inventoryPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class loginStepDef {
    private WebDriver driver;
    private loginPage login;
    private inventoryPage inventory;

    // Cucumber Hooks: @Before runs before each scenario
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        login = new loginPage(driver);
        inventory = new inventoryPage(driver);
    }

    // Cucumber Hooks: @After runs after each scenario
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("User is on the login page")
    public void userIsOnLoginPage() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("User enter username {string} and password {string}")
    public void userEnterUsernameAndPassword(String username, String password) {
        login.enterUsername(username);
        login.enterPassword(password);
    }

    @And("User click the login button")
    public void userClickLoginButton() {
        login.clickLogin();
    }

    @Then("User should be redirected to the inventory page")
    public void userShouldBeRedirectedToInventoryPage() {
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        assertEquals(expectedUrl, inventory.getCurrentPageUrl());
    }

    @Then("User should see error message {string}")
    public void userShouldSeeErrorMessage(String expectedError) {
        String actualError = login.getErrorMessageText();
        assertTrue(actualError.contains(expectedError),
                "Error message should contain: " + expectedError + " but got: " + actualError);
    }

    @And("First inventory item image source should contain {string}")
    public void firstInventoryItemImageSourceShouldContain(String keyword) {
        String imageSrc = inventory.getFirstItemImageSrc();
        assertTrue(imageSrc != null && imageSrc.contains(keyword),
                "Image src should contain: " + keyword + " but got: " + imageSrc);
    }

    @Then("User should be redirected to the inventory page with delay")
    public void userShouldBeRedirectedToInventoryPageWithDelay() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        assertEquals("https://www.saucedemo.com/inventory.html", inventory.getCurrentPageUrl());
    }

    @And("Inventory list should be displayed")
    public void inventoryListShouldBeDisplayed() {
        assertTrue(inventory.isInventoryListDisplayed(), "Inventory list should be displayed");
    }
}
