import org.openqa.selenium.WebDriver;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class Data {
    WebDriver driver;
    String browser;
    String webphoneUrl;
    String method;
    String username;
    String group;
    String number1;
    String number2;
    String dbTable; // not used for now
    String dbPhoneNumber;  //not used for now

    public Data(){
        this.username  = "81016";
        this.number1 = "94949";
        this.number2 = "94948";
    }

    public void printData(){
        System.out.println("Driver is: " + this.driver);
        System.out.println("Browser is: " + this.browser);
        System.out.println("WebphoneUrl is: " + this.webphoneUrl);
        System.out.println("Method is: " + this.method);
        System.out.println("Username is: " + this.username);
        System.out.println("Group is: " + this.group);
        System.out.println("Number1 is: " + this.number1);
        System.out.println("Number2 is: " + this.number2);
        System.out.println("DbTable is: " + this.dbTable); //not used for now
        System.out.println("DbPhoneNumber is: " + this.dbPhoneNumber); //not used for now
        System.out.println();
    }
}
