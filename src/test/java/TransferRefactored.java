/**
 * Created by SChubuk on 15.11.2017.
 */
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
public class TransferRefactored {
    static Data data;
    static WebDriver transferInitiator;
    static WebDriver transferReceiver;
    static String callToNumber = "94949";
    static String transferToNumber = "94948";
    static String transferToAgent = "81049";
    static String transferFromAgent = "81016";

    static String transferToQueue = "33333";

    static boolean fast = false;
    static int delay = 2;


    @Test
    public static void blindTransferToNumber() throws InterruptedException, IOException, FindFailed {
        transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "blind", transferToNumber);
        Methods.cxAnswer();
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        Methods.focusCXphone(1);
        Thread.sleep(5000);
        Methods.clientHangup(transferInitiator, 1);
    }

    @Test
    public static void attendedTransferToNumber() throws InterruptedException, IOException, FindFailed {
        transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "attended", transferToNumber);
        Methods.cxAnswer();
        Methods.agentHangup(transferInitiator,1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        Methods.focusCXphone(1);
        Thread.sleep(5000);
        Methods.clientHangup(transferInitiator, 1);
    }


    @Test
    public static void blindTransferToAgent() throws InterruptedException, IOException, FindFailed {
        transferReceiver = STMethods.loginReceiver(transferReceiver, transferToAgent);
        transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "blind", transferToAgent);
        STMethods.switchWindow();
        STMethods.acceptTransfer(transferReceiver);
        STMethods.switchWindow();
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        STMethods.switchWindow();
        Thread.sleep(5000);
        Methods.agentHangup(transferReceiver,1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
    }

    @Test
    public static void attendedTransferToAgent() throws InterruptedException, IOException, FindFailed {
        transferReceiver = STMethods.loginReceiver(transferReceiver, transferToAgent);
        transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "attended", transferToAgent);
        STMethods.switchWindow();
        STMethods.acceptTransfer(transferReceiver);
        STMethods.switchWindow();
        Thread.sleep(5000);
        Methods.agentHangup(transferInitiator,1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        Thread.sleep(5000);
        STMethods.switchWindow();
        Methods.agentHangup(transferReceiver,1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
    }

/*    //call to queue 33333
    @Test
    public static void blindTransferToQueue() throws InterruptedException, IOException, FindFailed {
        transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "blind", transferToQueue);
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        Thread.sleep(10000);
        transferReceiver = transferInitiator;
        STMethods.acceptTransfer(transferReceiver);
        Thread.sleep(5000);
        Methods.clientHangup(transferReceiver, 1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
    }

    //ATTENDED TRANSFER TO QUEUE SHOULD BE THE SAME AS TRANSFER TO AGENT EXCEPT 33333
    @Test
    public static void attendedTransferToQueue() throws InterruptedException, IOException, FindFailed {
        STMethods.login(transferReceiver);
        STMethods.login(transferInitiator);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "attended", transferToAgent);
        STMethods.switchWindow();
        STMethods.acceptTransfer(transferReceiver);
        Thread.sleep(5000);
        Methods.agentHangup(transferInitiator,1);
        STMethods.switchWindow();
        Thread.sleep(5000);
        Methods.agentHangup(transferReceiver,1);
        STMethods.setResultCodeAndCheckAvailableStatus(transferReceiver);
    }*/


    @Test
    public static void blindTransferToPoint() throws InterruptedException, IOException, FindFailed {
        transferInitiator = STMethods.loginInitiator(transferInitiator, transferFromAgent);
        STMethods.call(transferInitiator, callToNumber);
        STMethods.makeTransfer(transferInitiator, "point", transferToNumber);
        Methods.cxAnswer();
        STMethods.setResultCodeAndCheckAvailableStatus(transferInitiator);
        Methods.focusCXphone(1);
        Thread.sleep(5000);
        Methods.clientHangup(transferInitiator, 1);
    }

    //Threre is no guarantee of transfer to othrer agent, not to yourself

    // @Test
    public static void attendedTransferToPoint() throws InterruptedException, IOException, FindFailed {


    }

   /* @BeforeMethod
    public void open() throws InterruptedException, FindFailed, IOException {
        Methods.openCXphone(100);
        //before groups to launch ie browser
    }*/

    //alailability schedule for transfer point - not really needed
    @AfterMethod
    public void teardown() throws IOException {
        try {
            transferInitiator.quit();
            transferReceiver.quit();
            boolean isIE = Methods.isIE(transferInitiator)||Methods.isIE(transferReceiver);

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