import org.openqa.selenium.WebDriver;

/**
 * Created by SChubuk on 04.10.2017.
 */
public class ChromeData extends Data {

    public ChromeData(){
    this.webphoneUrl = "http://172.21.7.239/gbwebphone/";
    this.method = "usual";
    this.browser = "chrome";
    }

    public void printData(){
        System.out.println("ChromeData");
        super.printData();
    }

}
