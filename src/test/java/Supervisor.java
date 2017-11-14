import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by SChubuk on 10.11.2017.
 */
public class Supervisor {
    static Data data;
    static WebDriver driver;
    static WebDriver driver2;
    static boolean fast = false;
    static int delay = 2;

    @Test
    public static void call() throws InterruptedException, IOException, FindFailed {

    }
    @Test
    public static void listen() throws InterruptedException, IOException, FindFailed {


        //OPEN IE
        driver2 = Methods.openWebphoneLoginPage(driver2, "ie", "http://172.21.24.109/gbwebphone/");
        Methods.login(driver2, "usual", "81049", "\\!test_group5_5220");
        Methods.checkStatus(driver2, "Available", 60);


        //OPEN CHROME
        CallOnTwoLines.call();
        driver = CallOnTwoLines.driver;
        data = CallOnTwoLines.data;
        Thread.sleep(1000);

        //LISTEN FROM IE CALL IN CHROME
        Methods.switchFocus();
        WebElement userName = driver2.findElement(By.xpath("//*[text()='81016']"));
        userName.click();
        WebElement listen = driver2.findElement(By.cssSelector("#tabView\\3a supervisorPanel > tbody > tr > td:nth-child(2)"));
        JavascriptExecutor js = (JavascriptExecutor) driver2;
        js.executeScript("runSupervisorAction('silent');PrimeFaces.ab({source:'tabView:supervisorListen',update:'growl'});");


        //END LISTENING IN IE
        Methods.checkStatus(driver2, "Listening", 5);
        Methods.agentHangup(driver2, 1);
        Methods.checkStatus(driver2, "Available", 5);

        //END CALL IN CHROME
        Methods.switchFocus();
        Methods.agentHangup(driver, 1);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
    }


    @Test
    public static void talkToUser() throws InterruptedException, IOException, FindFailed {


        //OPEN IE
        driver2 = Methods.openWebphoneLoginPage(driver2, "ie", "http://172.21.24.109/gbwebphone/");
        Methods.login(driver2, "usual", "81049", "\\!test_group5_5220");
        Methods.checkStatus(driver2, "Available", 60);


        //OPEN CHROME
        CallOnTwoLines.call();
        driver = CallOnTwoLines.driver;
        data = CallOnTwoLines.data;
        Thread.sleep(1000);

        //LISTEN FROM IE CALL IN CHROME
        Methods.switchFocus();
        WebElement userName = driver2.findElement(By.xpath("//*[text()='81016']"));
        userName.click();
        WebElement listen = driver2.findElement(By.cssSelector("#tabView\\3a supervisorPanel > tbody > tr > td:nth-child(2)"));
        JavascriptExecutor js = (JavascriptExecutor) driver2;
        js.executeScript("runSupervisorAction('whisper');PrimeFaces.ab({source:'tabView:supervisorTalk',update:'growl'});");
        System.out.println("JavaScript has been executed.");
        //listen.click();
        //Methods.clickIEelement(driver2, listen);

        //END LISTENING IN IE
        Methods.checkStatus(driver2, "Whispering", 5);
        Methods.agentHangup(driver2, 1);
        Methods.checkStatus(driver2, "Available", 5);

        //END CALL IN CHROME
        Methods.switchFocus();
        Methods.agentHangup(driver, 1);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();

        //Agent is in Available stauts. Status button disabled.


        /*WebElement button_transfer = driver.findElement(By.cssSelector("#btn_transfer"));
        button_transfer.click();
        WebElement selectTransfer = driver.findElement(By.cssSelector("#transfer_type > div.ui-selectonemenu-trigger.ui-state-default.ui-corner-right"));
        selectTransfer.click();
        if (!fast)
            Thread.sleep(1000);
        WebElement attendedTransfer = driver.findElement(By.cssSelector("#transfer_type_panel > div > ul > li:nth-child(2)"));
        attendedTransfer.click();
        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.sendKeys("81049");
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();

        //SWITCH TO IE

        Methods.switchFocus();

        Methods.checkStatus(driver2, "Ringing", 10);
        Screen screen = new Screen();
        org.sikuli.script.Pattern button_Accept = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_Accept.png");
        screen.wait(button_Accept, 10);
        screen.click(button_Accept);
        Methods.checkStatus(driver2, "Ringing", 5);

        //AGENT WHO MADE TRANSFER ENDS THE CALL
        //SWITCH TO CHROME AND SAVE
        Thread.sleep(5000);
        Methods.switchFocus();
       *//**//*
        Thread.sleep(2000);
        Methods.agentHangup(driver, 1);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();

        //SWITCH TO IE
        //END THE CALL WITH CLIENT
        Methods.switchFocus();
        Thread.sleep(5000);
        Methods.agentHangup(driver2, 1);

        Methods.setWebphoneResultCode(driver2);
        Methods.checkStatus(driver2, "Available", 3);*/

    }

    @Test
    public static void bargeIn() throws InterruptedException, IOException, FindFailed {

    }

    @Test
    public static void logUserOut() throws InterruptedException, IOException, FindFailed {

    }

    @Test
    public static void sendNotification() throws InterruptedException, IOException, FindFailed {

    }
    @Test
    public static void assist() throws InterruptedException, IOException, FindFailed {

    }

    @Test
    public static void changeStatus() throws InterruptedException, IOException, FindFailed {

    }


    @BeforeMethod
    public void open() throws InterruptedException, FindFailed, IOException {
        Methods.openCXphone(100);
        //before groups to launch ie browser
    }

    //alailability schedule for transfer point - not really needed
    @AfterMethod
    public void teardown() throws IOException {
        try {
            boolean isIE = Methods.isIE(driver);
            driver.quit();
            driver2.quit();

            if (isIE) {
                Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("taskkill /F /IM 3CXPhone.exe");
    }

}
