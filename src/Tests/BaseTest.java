package Tests;

import data.Settings;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Created with IntelliJ IDEA.
 * User: Дом
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest extends Settings {

    @BeforeMethod(groups = {"good", "bad"})
    public void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
        setDriver(new ChromeDriver());
        getDriver().manage().window().maximize();
    }

    @AfterMethod(groups = {"good", "bad"})
    public void after() {
        getDriver().close();
    }

}