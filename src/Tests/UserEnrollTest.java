package Tests;

import Methods.FirstPage;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by sikretSSD on 17.03.2015.
 */
public class UserEnrollTest extends BaseTest {


    @Test(groups = "good")
    public void EnrollandSignIn() throws InterruptedException {
//enroll
        File logFile = new File("Files\\SSN&ID&DataBirth.csv");
        while (logFile.length() > 0) {
            goHome();
            wait(2000);
            FirstPage firstpage = new FirstPage();
            wait(2000);
            firstpage.Regbtn();
            wait(4000);
            firstpage.FillIdentityEstablishment();
            wait(6000);
            firstpage.FillIdentityVerification();
            wait(6000);
            firstpage.FillContactInformation();
            wait(4000);
            firstpage.FillLoginInformation();
            wait(2000);
            firstpage.ClickContinueAccountFeatures();
            wait(4000);
            firstpage.ClickAcceptConfirm();
            wait(4000);
            firstpage.ReturnToLog();
////Sign in if need to send email
            wait(2000);
            firstpage.SigninFirstAfterEnroll();
            wait(4000);
            firstpage.AnswerQuestion();
            wait(3000);
            firstpage.PasswordEntry();
            wait(2000);
            firstpage.SendMailOrLogout();
            wait(2000);
            }
        }
    }

