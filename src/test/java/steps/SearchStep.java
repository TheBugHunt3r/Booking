package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class SearchStep {

    WebDriver driver;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        options.addArguments("--accept-lang=en");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Given("booking search page is opened")
    public void bookingSearchPageIsOpened() throws InterruptedException {
        driver.get("https://www.booking.com/searchresults.en-gb.html");
        Thread.sleep(5000);
    }

    @When("user searches for {string}")
    public void userSearchesFor(String hotel) throws InterruptedException {
        driver.findElement(By.xpath("//input[@name='ss']")).sendKeys(hotel);
        driver.findElement(By.xpath(String.format("//ul[@role='group']//*[contains(text(), '%s')]", hotel))).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(5000);
    }

    @Then("{string} is shown")
    public void isShown(String expectedResult) {
        List<WebElement> titles = driver.findElements(By.xpath("//div[@data-testid='title']"));
        boolean isHotelFound = false;
        for (WebElement title : titles) {
            if (title.getText().equals(expectedResult)) {
                isHotelFound = true;
                break;
            }
        }
        Assert.assertTrue(isHotelFound);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @And("hotel has rating {string}")
    public void hotelHasRating(String arg0) {
        SoftAssert softAssert = new SoftAssert();
        WebElement ratingElement = driver.findElement(
                By.cssSelector("[data-testid='review-score'] > div:first-child + div")
        );
        String actualRating = ratingElement.getText();
        softAssert.assertEquals(actualRating, "9.1",
                "Рейтинг должен быть 9.1");
        softAssert.assertTrue(ratingElement.isDisplayed(),
                "Элемент с рейтингом должен быть видим");
        softAssert.assertAll();
    }

    @Then("{string} hotel is shown")
    public void hotelIsShown(String arg0) {
    }
}
