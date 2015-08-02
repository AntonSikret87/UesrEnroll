package Tests;

import Methods.FirstPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by sikretSSD on 17.03.2015.
 */
public class UserEnrollTest extends BaseTest {

    @Test(groups = "good")
    public void EnrollandSignIn() throws InterruptedException, FileNotFoundException {
////Enroll
        int count = 1;
        File logFile = new File("Files\\SSN&ID&DataBirth.csv");
        while (logFile.length() > 0) {
            goHome();
            FirstPage firstpage = new FirstPage();
            if (isElementPresent(By.id("layout-wrapper"))) {
                firstpage.Regbtn();
                firstpage.FillIdentityEstablishment();
            }
            wait(6000);
            //Verify was user enroll before
            if (getDriver().findElement(By.className("text-error")).isDisplayed()) {
                firstpage.removeFirstItemFromUsernameEnrollCSV();
                firstpage.removeFirstItemFromUsernameSignInCSV();
                continue;
            }
            firstpage.FillIdentityVerification();
            firstpage.FillContactInformation();
            firstpage.FillLoginInformation();
            firstpage.ClickContinueAccountFeatures();
            firstpage.ClickAcceptConfirm();
            firstpage.ReturnToLog();
////Sign in
            //Create new tab with https://temp-mail.ru/
            getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
            getDriver().navigate().to("https://temp-mail.ru/");
            //Create new email
            getDriver().findElement(By.id("click-to-change")).click();
            WebDriverWait wait = new WebDriverWait(getDriver(),40);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input_mail")));
            getDriver().findElement(By.id("input_mail")).clear();
            getDriver().findElement(By.id("input_mail")).sendKeys("testMail");
            getDriver().findElement(By.id("postbut")).click();
            //Take value of created email toString
            String textValue = getDriver().findElement(By.id("mail")).getAttribute("value");
            //Get back to HomePage
            getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
            getDriver().switchTo().defaultContent();
            //Start SignIn
            firstpage.SigninFirstAfterEnroll();
            firstpage.AnswerQuestion();
            firstpage.PasswordEntry();
            //Put previously created email
           // firstpage.SendMailOrLogout();
            try{
                getDriver().findElement(By.id("PrimaryEmail")).clear();
                getDriver().findElement(By.id("PrimaryEmail")).sendKeys(textValue);
                wait(1000);
                getDriver().findElement(By.xpath("//button[@type='submit']")).click();

            }
            catch (Exception e){
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".icon.icon-sign-out")));
                getDriver().findElement(By.cssSelector(".icon.icon-sign-out")).click();
                getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
                getDriver().findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
                System.out.println("How many users you enroll : " + count);
                count++;
                continue;
            }
            //Close mainPage and focus to mailPage
            getDriver().findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
            //Click Refresh
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("click-to-refresh")));
            getDriver().findElement(By.id("click-to-refresh")).click();
            //Click on receive email
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='mails']/tbody/tr[1]/td[4]/a")));
            getDriver().findElement(By.xpath(".//*[@id='mails']/tbody/tr[1]/td[4]/a")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//p/a")));
            getDriver().findElement(By.xpath(".//p/a")).click();
            System.out.println("How many users you enroll : " + count);
            count++;
        }
    }
}

