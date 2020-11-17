package PowerBI;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;



public class PowerTest {

    private static WindowsDriver PowerBISession = null;

    WebDriverWait wait = new WebDriverWait(PowerBISession, 100);

    @BeforeClass
    public static void setup() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", ConfProperties.getProperty("pathToApp"));

            PowerBISession = new WindowsDriver(new URL(ConfProperties.getProperty("URLforServerAppium")), capabilities);
            PowerBISession.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @AfterClass
//    public static void TearDown() {
//        if (PowerBISession != null) {
//            PowerBISession.quit();
//        }
//        PowerBISession = null;
//    }

    @org.junit.Test
    public void click() throws InterruptedException, IOException {


        PowerBISession.findElementByName("Close").click();
        PowerBISession.findElementByName("Get data").click();
        PowerBISession.findElementByName("OData feed").click();
        PowerBISession.findElementByClassName("Internet Explorer_Server").sendKeys(ConfProperties.getProperty("URLforDataSource"));
        PowerBISession.findElementByName("OK").click();

        //if (PowerBISession.findElementByName("SQL Server database").isDisplayed())
        //inputReg();

        selectData();
        parserColumn();


//        PowerBISession.findElementByName("Load").click();
//        wait.until(ExpectedConditions.invisibilityOf(PowerBISession.findElementByName("Load")));
//        Thread.sleep(3000);
//        PowerBISession.findElementByName("Data").click();
    }


    public void inputReg() {
        PowerBISession.findElementByName("Credentials").click();
        PowerBISession.findElementByName("Basic").click();
        PowerBISession.findElementByName("User name").sendKeys("kliuchkovskii_weSuVq");
        PowerBISession.findElementByName("Password").sendKeys("19961225");
        PowerBISession.findElementByName("Connect").click();
    }

    public void selectData() {

        Actions actions = new Actions(PowerBISession);
        //PowerBISession.findElement(By.name("Level 2, " + ConfProperties.getProperty("TableName"))).click();
        actions.contextClick(PowerBISession.findElement(By.name("Level 2, " + ConfProperties.getProperty("TableName")))).perform();
        actions.moveToElement(PowerBISession.findElementByName("Transform Data, 1 of 2.")).perform();
        PowerBISession.getKeyboard().sendKeys(Keys.ENTER);




//            PowerBISession.findElementByName("Level 1, https://powerbi-cloud-prod.alphaservesp.com/api/export/power-bi/8b75174d1d13d27f13da3cd47f7dae8d")
//                    .click();
//            for (int i = 0; i < 30; i++) {
//            PowerBISession.getKeyboard().sendKeys(Keys.SHIFT,Keys.ARROW_DOWN);
//        }


            //PowerBISession.findElementByName("Level 2," + " " + retval).sendKeys(Keys.LEFT_CONTROL, Keys.ENTER);


//        String str = ConfProperties.getProperty("JiraCoreSections");
//        String[] arr = str.split(",");
//        for (String retval : arr){
//            if ()
//        }
    }

    public void parserColumn() throws IOException, InterruptedException {
        Thread.sleep(5000);

        ArrayList<String> tabs2 = new ArrayList<String>(PowerBISession.getWindowHandles());
        PowerBISession.switchTo().window(tabs2.get(0));





        Actions actions = new Actions(PowerBISession);
        actions.click();
        PowerBISession.findElementByName("Enter Data").click();

        Test test = new Test();
        actions.moveToElement(PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName"))).perform();
        actions.click();
        File temp = File.createTempFile("temp",".txt",new File("C:\\Project\\PowerBIauto\\src\\test\\resources"));
         test.ReadClipboard(temp.getAbsolutePath());

         String fileName = temp.getName();
        Optional<String> line = Files.lines(Paths.get(fileName)).findFirst();
        System.out.println(line.get());

    }

}
