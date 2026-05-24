package stepDef;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.resultPage;
import pages.searchPage;

public class searchStepDef {
    static WebDriver driver;
    searchPage search;
    @BeforeAll
    public static void before_or_after_all()
    {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Given("User is on the search page")
    public void navigateToSearchPage()
    {
        driver.get("https://www.bing.com/");
    }
    @When("User search for a {string}")
    public void search(String query)
    {
        search = new searchPage(driver);
        search.enterQuery(query);
        search.submitForm();
    }
    @Then("User redirect to the result page")
    public void searchSuccess()
    {
        resultPage result = new resultPage(driver);
        String title = result.getPageTitle();;
    }

    @And("User click search")
    public void userClickSearch() {
        search.submitForm();
    }

    @When("User write a query")
    public void userWriteAQuery() {
        search = new searchPage(driver);
        search.enterQuery("soobin");
    }

    @AfterAll
    public static void after_all() {
        if (driver != null) {
            driver.quit();
        }
    }

}
