import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class Transfer {
    static Data data;
    static WebDriver driver;
    static WebDriver driver2;
    static boolean fast = false;
    static int delay = 2;

    public static void createCallAndOpenTransferWindow() throws InterruptedException, FindFailed, IOException {
        CallOnTwoLines.call();
        driver = CallOnTwoLines.driver;
        data = CallOnTwoLines.data;
        Thread.sleep(1000);
        WebElement button_transfer = driver.findElement(By.cssSelector("#btn_transfer"));
        button_transfer.click();
    }

    public static void openIE() throws InterruptedException, FindFailed, IOException {
        driver2 = Methods.openWebphoneLoginPage(driver2, "ie", "http://172.21.24.109/gbwebphone/");
        Methods.login(driver2, "usual", "81049", "\\!test_group5_5220");
        Methods.checkStatus(driver2, "Available", 60);
    }

    public static void selectAttendedTransfer() throws InterruptedException {
        WebElement selectTransfer = driver.findElement(By.cssSelector("#transfer_type > div.ui-selectonemenu-trigger.ui-state-default.ui-corner-right"));
        selectTransfer.click();
        if (!fast)
            Thread.sleep(1000);
        WebElement attendedTransfer = driver.findElement(By.cssSelector("#transfer_type_panel > div > ul > li:nth-child(2)"));
        attendedTransfer.click();
    }


    @Test
    public static void blindTransferToNumber() throws InterruptedException, IOException, FindFailed {
        createCallAndOpenTransferWindow();
        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.sendKeys("94948");
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        Methods.cxAnswer();
        Methods.clientHangup(driver, 1);
       /* if(fast == false){
        Methods.clientHangup(driver, 1);
        Thread.sleep(delay);
        Methods.clientHangup(driver, 2);
        Thread.sleep(delay);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        } else{
            Methods.clientHangup(driver, 1);
            Methods.clientHangup(driver, 2);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        }*/

    }

     @Test
    public static void attendedTransferToNumber() throws InterruptedException, IOException, FindFailed {
         createCallAndOpenTransferWindow();
        if (!fast)
            Thread.sleep(1000);
        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.sendKeys("94948");
        if (!fast)
            Thread.sleep(1000);
        WebElement selectTransfer = driver.findElement(By.cssSelector("#transfer_type > div.ui-selectonemenu-trigger.ui-state-default.ui-corner-right"));
        selectTransfer.click();
        if (!fast)
            Thread.sleep(1000);
        WebElement attendedTransfer = driver.findElement(By.cssSelector("#transfer_type_panel > div > ul > li:nth-child(2)"));
        attendedTransfer.click();
        if (!fast)
            Thread.sleep(1000);
        /*WebElement transferType = driver.findElement(By.cssSelector("#transfer_number"));
        Select select = new Select(transferType);
        select.selectByVisibleText("Attended");*/


        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();
        Methods.cxAnswer();
        Thread.sleep(5000);

        Methods.agentHangup(driver, 1);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        if (!fast)
            Thread.sleep(1000);
        Methods.clientHangup(driver, 1);
        //Methods.clientHangup(driver, 2);

       /* if(fast == false){
        Methods.clientHangup(driver, 1);
        Thread.sleep(delay);
        Methods.clientHangup(driver, 2);
        Thread.sleep(delay);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        } else{
            Methods.clientHangup(driver, 1);
            Methods.clientHangup(driver, 2);
            CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        }*/
    }

    @Test
    public static void blindTransferToAgent() throws InterruptedException, IOException, FindFailed {
        //OPEN IE
        openIE();
        //OPEN CHROME
        createCallAndOpenTransferWindow();

        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.sendKeys("81049");
        if (!fast)
            Thread.sleep(1000);
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();
        if (!fast)
            Thread.sleep(2000);
        //SWITCH TO IE
        Methods.switchFocus();

        Methods.checkStatus(driver2, "Ringing", 10);
        Methods.agentAcceptCall(driver2, 20);
        System.out.println("CALL ACCEPTED.");

        Methods.checkStatus(driver2, "Ringing", 5);
        Thread.sleep(5000);
        Methods.agentHangup(driver2, 1);

        Methods.setWebphoneResultCode(driver2);
        Methods.checkStatus(driver2, "Available", 3);

        //SWITCH TO CHROME AND SAVE
        Methods.switchFocus();
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        //Thread.sleep(100000);
    }

    @Test
    public static void attendedTransferToAgent() throws InterruptedException, IOException, FindFailed {
        //OPEN IE
        openIE();
        //OPEN CHROME
        createCallAndOpenTransferWindow();
        selectAttendedTransfer();

        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.sendKeys("81049");
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();

        //SWITCH TO IE

        Methods.switchFocus();

        Methods.checkStatus(driver2, "Ringing", 10);
        Methods.agentAcceptCall(driver2, 10);
        Methods.checkStatus(driver2, "Ringing", 5);

        //AGENT WHO MADE TRANSFER ENDS THE CALL
        //SWITCH TO CHROME AND SAVE
        Thread.sleep(5000);
        Methods.switchFocus();
       /**/
        Thread.sleep(2000);
        Methods.agentHangup(driver, 1);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();

        //SWITCH TO IE
        //END THE CALL WITH CLIENT
        Methods.switchFocus();
        Thread.sleep(5000);
        Methods.agentHangup(driver2, 1);

        Methods.setWebphoneResultCode(driver2);
        Methods.checkStatus(driver2, "Available", 3);

    }

    //call to queue 33333
    @Test
    public static void blindTransferToQueue() throws InterruptedException, IOException, FindFailed {
        createCallAndOpenTransferWindow();
        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.sendKeys("33333");
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();

        //CALL WILL ARRIVE AGAIN
        Methods.checkStatus(driver, "Ringing", 10);
        Screen screen = new Screen();
        org.sikuli.script.Pattern button_Accept = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_Accept.png");
        screen.wait(button_Accept, 10);
        screen.click(button_Accept);
        Methods.checkStatus(driver, "Incall", 5);
        Thread.sleep(5000);
        Methods.agentHangup(driver, 1);

        Methods.setWebphoneResultCode(driver);
        Methods.checkStatus(driver, "Available", 3);
        /*Methods.cxAnswer();
        Methods.clientHangup(driver, 1);*/
        //Thread.sleep(1000000);
    }

    //ATTENDED TRANSFER TO QUEUE SHOULD BE THE SAME AS TRANSFER TO AGENT EXCEPT 33333
    @Test
    public static void attendedTransferToQueue() throws InterruptedException, IOException, FindFailed {
        //OPEN IE

        openIE();

        //OPEN CHROME
        createCallAndOpenTransferWindow();
        WebElement selectTransfer = driver.findElement(By.cssSelector("#transfer_type > div.ui-selectonemenu-trigger.ui-state-default.ui-corner-right"));
        selectTransfer.click();
        if (!fast)
            Thread.sleep(1000);
        WebElement attendedTransfer = driver.findElement(By.cssSelector("#transfer_type_panel > div > ul > li:nth-child(2)"));
        attendedTransfer.click();
        WebElement transferNumber = driver.findElement(By.cssSelector("#transfer_number"));
        transferNumber.sendKeys("33333");
        WebElement button_form_transfer = driver.findElement(By.cssSelector("#btn_trnsfr"));
        button_form_transfer.click();

        //SWITCH TO IE

        Methods.switchFocus();

        Methods.checkStatus(driver2, "Ringing", 15);
        Screen screen = new Screen();
        org.sikuli.script.Pattern button_Accept = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_Accept.png");
        screen.wait(button_Accept, 10);
        screen.click(button_Accept);
        Methods.checkStatus(driver2, "Ringing", 5);

        //AGENT WHO MADE TRANSFER ENDS THE CALL
        //SWITCH TO CHROME AND SAVE
        Thread.sleep(5000);
        Methods.switchFocus();
       /**/
        Thread.sleep(2000);
        Methods.agentHangup(driver, 1);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();

        //SWITCH TO IE
        //END THE CALL WITH CLIENT
        Methods.switchFocus();
        Thread.sleep(5000);
        Methods.agentHangup(driver2, 1);

        Methods.setWebphoneResultCode(driver2);
        Methods.checkStatus(driver2, "Available", 3);
    }


    @Test
    public static void blindTransferToPoint() throws InterruptedException, IOException, FindFailed {
        createCallAndOpenTransferWindow();

        WebElement transfer_point = driver.findElement(By.cssSelector("#peerTransferTable_data"));
        transfer_point.click();

        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();

        //BUG: CALL HANGUP ON CLIENT SIDE.

        //CALL WILL ARRIVE AGAIN
        Methods.checkStatus(driver, "Ringing", 10);
        Screen screen = new Screen();
        org.sikuli.script.Pattern button_Accept = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_Accept.png");
        screen.wait(button_Accept, 10);
        screen.click(button_Accept);
        Methods.checkStatus(driver, "Incall", 5);
        Thread.sleep(5000);
        Methods.agentHangup(driver,1);

        Methods.setWebphoneResultCode(driver);
        Methods.checkStatus(driver, "Available", 3);
    }

    //Threre is no guarantee of transfer to othrer agent, not to yourself

   // @Test
    public static void attendedTransferToPoint() throws InterruptedException, IOException, FindFailed {

        openIE();

        //OPEN CHROME
        createCallAndOpenTransferWindow();
        WebElement selectTransfer = driver.findElement(By.cssSelector("#transfer_type > div.ui-selectonemenu-trigger.ui-state-default.ui-corner-right"));
        selectTransfer.click();
        if (!fast)
            Thread.sleep(1000);
        WebElement attendedTransfer = driver.findElement(By.cssSelector("#transfer_type_panel > div > ul > li:nth-child(2)"));
        attendedTransfer.click();
        WebElement transfer_point = driver.findElement(By.cssSelector("#peerTransferTable_data"));
        transfer_point.click();

        System.out.println("OPEN CHROME");
        //SWITCH TO IE

        Methods.switchFocus();

        Methods.checkStatus(driver2, "Ringing", 15);
        Screen screen = new Screen();
        org.sikuli.script.Pattern button_Accept = new org.sikuli.script.Pattern("C:\\SikuliImages\\button_Accept.png");
        screen.wait(button_Accept, 10);
        screen.click(button_Accept);
        Methods.checkStatus(driver2, "Ringing", 5);
        System.out.println("SWITCH TO IE");

        //AGENT WHO MADE TRANSFER ENDS THE CALL
        //SWITCH TO CHROME AND SAVE
        Thread.sleep(5000);
        Methods.switchFocus();
       /**/
        Thread.sleep(2000);
        Methods.agentHangup(driver, 1);
        CallOnTwoLines.setResultCodeAndCheckAvailableStatus();
        System.out.println("SWITCH TO CHROME AND SAVE");

        //SWITCH TO IE
        //END THE CALL WITH CLIENT
        Methods.switchFocus();
        Thread.sleep(5000);
        Methods.agentHangup(driver2, 1);

        Methods.setWebphoneResultCode(driver2);
        Methods.checkStatus(driver2, "Available", 3);
        System.out.println("END THE CALL WITH CLIENT");
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


// time on each test 2h