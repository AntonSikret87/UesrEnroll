package Methods;

import org.openqa.selenium.By;

import java.io.*;
import java.util.Scanner;

/**
 * Created by sikretSSD on 17.03.2015.
 */
public class FirstPage extends PageBase {
    //Read/type SSN,ID and BirthDate
    String fileName1 = "Files\\SSN&ID&DataBirth.csv";
    File file1 = new File(fileName1);
    //Read/Type user name during enroll
    String fileName2 = "Files\\UserNameEnroll.csv";
    File file2 = new File(fileName2);
    //Read/Type user name during Login
    String fileName3 = "Files\\UserNameSignIn.csv";
    File file3 = new File(fileName3);


    public FirstPage() {
        super();

    }

    public void Init() {
    }
    public void Regbtn() throws InterruptedException {
        getDriver().findElement(By.linkText(getProperty("RegisterBTN_linkText"))).click();
    }
    //Метод удаляет /строку из тхт файла
    public void removeLineFromFile(String file, String lineToRemove) {
        try {
            File inFile = new File(file);
            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;
            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void FillIdentityEstablishment() throws InterruptedException, FileNotFoundException {
        getDriver().findElement(By.id(getProperty("SocialSecurityNumberField_id"))).clear();
        try {
            Scanner inputStream = new Scanner(file1);
          //  inputStream.hasNext();
            String data = inputStream.next();
           // data = data.replace("\"", "");
            String[] values = data.split(",");
            String str = values[0];
            getDriver().findElement(By.id(getProperty("SocialSecurityNumberField_id"))).sendKeys(str);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getDriver().findElement(By.id(getProperty("MemberAccountNumberField_id"))).clear();
        try {
            Scanner inputStream = new Scanner(file1);
            inputStream.hasNext();
            String data = inputStream.next();
            data = data.replace("\"", "");
            String[] values = data.split(",");
            String str1 = values[1];
            getDriver().findElement(By.id(getProperty("MemberAccountNumberField_id"))).sendKeys(str1);
            inputStream.close();

//            //Переиминует ЦСВ в ТХТ
//            File newFile = new File("Files\\SSN&ID&DataBirth.txt");
//            file1.renameTo(newFile) ;
//            //Удаляет первое значение
//            FirstPage operationWithFile = new FirstPage();
//            System.out.print(data);
//            operationWithFile.removeLineFromFile("Files\\SSN&ID&DataBirth.txt", data);
//            //Переиминует ТХТ в ЦСВ
//            newFile.renameTo(file1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getDriver().findElement(By.id(getProperty("BirthdateField_id"))).clear();
        try {
            Scanner inputStream = new Scanner(file1);
            inputStream.hasNext();
            String data = inputStream.next();
            data = data.replace("\"", "");
            String[] values = data.split(",");
            String str2 = values[2];
            getDriver().findElement(By.id(getProperty("BirthdateField_id"))).sendKeys(str2);
            inputStream.close();

            //Переиминует ЦСВ в ТХТ
            File newFile = new File("Files\\SSN&ID&DataBirth.txt");
            file1.renameTo(newFile) ;
            //Удаляет первое значение
            FirstPage operationWithFile = new FirstPage();
            System.out.print(data);
            operationWithFile.removeLineFromFile("Files\\SSN&ID&DataBirth.txt", data);
            //Переиминует ТХТ в ЦСВ
            newFile.renameTo(file1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getDriver().findElement(By.xpath(getProperty("ContinueBTN_xpath"))).click();

        }



    public void FillIdentityVerification() throws InterruptedException {
        wait(1000);
        getDriver().findElement(By.xpath(getProperty("FirstRadioButonNONE_xpath"))).click();
        wait(1000);
        getDriver().findElement(By.xpath(getProperty("SecondRadioButonNONE_xpath"))).click();
        wait(1000);
        getDriver().findElement(By.xpath(getProperty("ThirdRadioButonNONE_xpath"))).click();
        getDriver().findElement(By.xpath(getProperty("ContinueBtn_xpath"))).click();

//        getDriver().findElement(By.id("AmountOfLastDeposit")).sendKeys(getProperty("AmmountOfLastDeposit.value"));
//        getDriver().findElement(By.cssSelector("span.switch")).click();
//       // getDriver().findElement(By.xpath("//form[@id='widget-container']/div[2]/div[3]/div/div[3]/label/span")).click();//For Line of Credit
//        getDriver().findElement(By.xpath("//form[@id='widget-container']/div[2]/div[3]/div/div[8]/label/span")).click();//For None
//        getDriver().findElement(By.xpath("//form[@id='widget-container']/div[4]/div/button")).click();
    }

    //unmark in AdminTool EnrollmentWorkflow->"Email address" "Require response" checkboxes
    public void FillContactInformation() throws InterruptedException {
        wait(1000);
        if(getDriver().findElement(By.name(getProperty("EmailAddressField_name"))).isDisplayed()) //If user never enroll before
        {
            getDriver().findElement(By.name(getProperty("EmailAddressField_name"))).clear();
            getDriver().findElement(By.name(getProperty("EmailAddressField_name"))).sendKeys(getProperty("SendEmail.value"));
            getDriver().findElement(By.name(getProperty("ConfirmEmailAddressField_name"))).clear();
            getDriver().findElement(By.name(getProperty("ConfirmEmailAddressField_name"))).sendKeys(getProperty("SendEmail.value"));
            getDriver().findElement(By.xpath(getProperty("ContinueBTN_xpath"))).click();

        }
        else // if user was enrolled before
        {
            getDriver().findElement(By.cssSelector(getProperty("ChangeEmailBtn_css"))).click(); //тут может упасть (поменять на By.name)
            wait(2000);
            getDriver().findElement(By.name(getProperty("EmailAddressField_name"))).clear();
            getDriver().findElement(By.name(getProperty("EmailAddressField_name"))).sendKeys(getProperty("SendEmail.value"));
            getDriver().findElement(By.name(getProperty("ConfirmEmailAddressField_name"))).clear();
            wait(2000);
            getDriver().findElement(By.name(getProperty("ConfirmEmailAddressField_name"))).sendKeys(getProperty("SendEmail.value"));
            wait(2000);
            getDriver().findElement(By.xpath(getProperty("OKBTNDialog_xpath"))).click();
            wait(2000);
            getDriver().findElement(By.xpath(getProperty("ContinueBtn_xpath"))).click();

        }
    }
    public void removeFirstItemFromUsernameEnrollCSV() throws InterruptedException, FileNotFoundException {
        Scanner inputStream = new Scanner(file2);
        inputStream.hasNext();
        String data1 = inputStream.next();
        inputStream.close();

        //Переиминует ЦСВ в ТХТ
        File newFile = new File("Files\\UserNameEnroll.txt");
        file2.renameTo(newFile) ;
        //Удаляет первое значение
        FirstPage operationWithFile = new FirstPage();
        System.out.print(data1);
        operationWithFile.removeLineFromFile("Files\\UserNameEnroll.txt", data1);
        //Переиминует ТХТ в ЦСВ
        newFile.renameTo(file2);
   }
    public void removeFirstItemFromUsernameSignInCSV() throws InterruptedException, FileNotFoundException {
        Scanner inputStream = new Scanner(file3);
        inputStream.hasNext();
        String data3 = inputStream.next();
        inputStream.close();

        //Переиминует ЦСВ в ТХТ
        File newFile = new File("Files\\UserNameSignIn.txt");
        file3.renameTo(newFile) ;
        //Удаляет первое значение
        FirstPage operationWithFile = new FirstPage();
        System.out.print(data3);
        operationWithFile.removeLineFromFile("Files\\UserNameSignIn.txt", data3);
        //Переиминует ТХТ в ЦСВ
        newFile.renameTo(file3);
    }
    public void FillLoginInformation() throws InterruptedException {
        getDriver().findElement(By.id(getProperty("UserNameField_id"))).clear();
        try {
            Scanner inputStream = new Scanner(file2);
            inputStream.hasNext();
            String data = inputStream.next();
            data = data.replace("\"", "");
            String[] values = data.split(",");
            String str = values[0];
            getDriver().findElement(By.id(getProperty("UserNameField_id"))).sendKeys(str);
            inputStream.close();
            //Переиминует ЦСВ в ТХТ
            File newFile = new File("Files\\UserNameEnroll.txt");
            file2.renameTo(newFile) ;
            //Удаляет первое значение
            FirstPage operationWithFile = new FirstPage();
            System.out.print(data);
            operationWithFile.removeLineFromFile("Files\\UserNameEnroll.txt", data);
            //Переиминует ТХТ в ЦСВ
            newFile.renameTo(file2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getDriver().findElement(By.id(getProperty("NewPasswordField_id"))).clear();
        getDriver().findElement(By.id(getProperty("NewPasswordField_id"))).sendKeys(getProperty("Password.value"));
        getDriver().findElement(By.id(getProperty("NewPasswordConfirmationField_id"))).clear();
        getDriver().findElement(By.id(getProperty("NewPasswordConfirmationField_id"))).sendKeys(getProperty("Password.value"));
        getDriver().findElement(By.id(getProperty("PersonalPhraseField_id"))).clear();
        getDriver().findElement(By.id(getProperty("PersonalPhraseField_id"))).sendKeys(getProperty("PersonalPhrase.value"));
        wait(2000);
        getDriver().findElement(By.xpath(getProperty("Image2_xpath"))).click(); //вторая картинка
        getDriver().findElement(By.id(getProperty("PoolNumber1AnswerField_id"))).clear();
        getDriver().findElement(By.id(getProperty("PoolNumber1AnswerField_id"))).sendKeys(getProperty("PersonalPhrase.value"));
        getDriver().findElement(By.id(getProperty("PoolNumber2AnswerField_id"))).clear();
        getDriver().findElement(By.id(getProperty("PoolNumber2AnswerField_id"))).sendKeys(getProperty("PersonalPhrase.value"));
        getDriver().findElement(By.id(getProperty("PoolNumber3AnswerField_id"))).clear();
        getDriver().findElement(By.id(getProperty("PoolNumber3AnswerField_id"))).sendKeys(getProperty("PersonalPhrase.value"));
        getDriver().findElement(By.xpath(getProperty("ContinueBtn_xpath"))).click();
        wait(2000);
    }
    public void ClickContinueAccountFeatures() throws InterruptedException {
        getDriver().findElement(By.xpath(getProperty("ContinueBtn_xpath"))).click();
    }
    public void ClickAcceptConfirm() throws InterruptedException {
        wait(3000);
        getDriver().findElement(By.xpath(getProperty("checkboxaccept_xpath"))).click();
        wait(2000);
        getDriver().findElement(By.xpath(getProperty("ContinueBtn_xpath"))).click();
        wait(7000);
    }
    public void ReturnToLog() throws InterruptedException {
        getDriver().findElement(By.linkText(getProperty("ReturnToLog_linkText"))).click();
        wait(4000);
    }
    public void SigninFirstAfterEnroll() throws InterruptedException {
        getDriver().findElement(By.id(getProperty("UNameField_id"))).clear();
        try {
            Scanner inputStream = new Scanner(file3);
            inputStream.hasNext();
            String data = inputStream.next();
            data = data.replace("\"", "");
            String[] values = data.split(",");
            String str = values[0];
            getDriver().findElement(By.id(getProperty("UNameField_id"))).sendKeys(str);
            inputStream.close();
            //Переиминует ЦСВ в ТХТ
            File newFile = new File("Files\\UserNameSignIn.txt");
            file3.renameTo(newFile) ;
            //Удаляет первое значение
            FirstPage operationWithFile = new FirstPage();
            System.out.print(data);
            operationWithFile.removeLineFromFile("Files\\UserNameSignIn.txt", data);
            //Переиминует ТХТ в ЦСВ
            newFile.renameTo(file3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        wait(1000);
        getDriver().findElement(By.name(getProperty("SubmitBTN_name"))).click();
    }
    public void AnswerQuestion() throws InterruptedException {
        wait(1000);
        getDriver().findElement(By.id(getProperty("ChallengeQuestionField_id"))).clear();
        getDriver().findElement(By.id(getProperty("ChallengeQuestionField_id"))).sendKeys(getProperty("AnswerQuestion.value"));
        wait(1000);
        getDriver().findElement(By.xpath(getProperty("SubmitAnswerBTN_xpath"))).click();
    }
    public void PasswordEntry() throws InterruptedException {
        getDriver().findElement(By.id(getProperty("PasswordField_id"))).clear();
        getDriver().findElement(By.id(getProperty("PasswordField_id"))).sendKeys(getProperty("Password.value"));
        getDriver().findElement(By.xpath(getProperty("ContinueBtn_xpath"))).click();
        wait(1000);
    }
    public void SendMailOrLogout() throws InterruptedException {
        try{
            getDriver().findElement(By.id(getProperty("PrimaryEmailField_id"))).clear();
            getDriver().findElement(By.id(getProperty("PrimaryEmailField_id"))).sendKeys(getProperty("SendEmail.value"));
            wait(1000);
            getDriver().findElement(By.xpath(getProperty("ContinueBtn_xpath"))).click();

        }
        catch (Exception e){
            wait(5000);
            getDriver().findElement(By.cssSelector(getProperty("LOgoutBTN_css"))).click();
        }
    }
}

