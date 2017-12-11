import org.openqa.selenium.WebDriver;

/**
 * Created by SChubuk on 04.10.2017.
 */

/*Data data = new Data();
data.username = "";
data.browser = "chrome";*/

public class IEData extends Data {
    public IEData(){
    this.webphoneUrl = "http://172.21.24.109/gbwebphone/";
    this.method = "usual";
    this.browser = "ie";
    }
    public void printData(){
        System.out.println("IEData");
        super.printData();
    }
}
