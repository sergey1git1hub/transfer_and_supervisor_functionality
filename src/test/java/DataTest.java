/**
 * Created by SChubuk on 20.11.2017.
 */
public class DataTest {
    public static void main(String[] args){
        Data data = new Data();

        data.printData();


        Data ieData = new IEData();
        ieData.printData();

        Data chromeData = new ChromeData();
        chromeData.printData();
    }
}
