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
import java.security.Key;
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

    Actions actions = new Actions(PowerBISession);

    Test test = new Test();

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

    @AfterClass
    public static void TearDown() {
        if (PowerBISession != null) {
            PowerBISession.quit();
        }
        PowerBISession = null;
    }

    @org.junit.Test
    public void click() throws InterruptedException, IOException {
        Thread.sleep(1000);
        PowerBISession.findElementByName("Close").click();
        PowerBISession.findElementByName("Get data").click();
        PowerBISession.findElementByName("OData feed").click();
        Thread.sleep(1000);
        PowerBISession.findElementByClassName("Internet Explorer_Server").sendKeys(ConfProperties.getProperty("URLforDataSource"));
        PowerBISession.findElementByName("OK").click();

        //if (PowerBISession.findElementByName("SQL Server database").isDisplayed())
        //inputReg();

        selectData();
//        parserColumn();
        parserCell();
    }


    public void inputReg() {
        PowerBISession.findElementByName("Credentials").click();
        PowerBISession.findElementByName("Basic").click();
        PowerBISession.findElementByName("User name").sendKeys("kliuchkovskii_weSuVq");
        PowerBISession.findElementByName("Password").sendKeys("19961225");
        PowerBISession.findElementByName("Connect").click();
    }

    public void selectData() throws InterruptedException {

        Actions actions = new Actions(PowerBISession);
        actions.contextClick(PowerBISession.findElement(By.name("Level 2, " + ConfProperties.getProperty("TableName")))).perform();
        actions.moveToElement(PowerBISession.findElementByName("Transform Data, 1 of 2.")).perform();
        Thread.sleep(1000);
        PowerBISession.getKeyboard().sendKeys(Keys.ENTER);

    }

    public void parserColumn() throws IOException, InterruptedException {
        Thread.sleep(5000);

        ArrayList<String> tabs2 = new ArrayList<String>(PowerBISession.getWindowHandles());
        PowerBISession.switchTo().window(tabs2.get(0));

        try {
            PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();

        }
        catch (NoSuchElementException e){
            PowerBISession.findElementByName("View").click();
            PowerBISession.findElementByName("Go to Column").click();
            PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();
            PowerBISession.getKeyboard().sendKeys(Keys.ENTER);
        }

        PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();
        actions.contextClick().perform();
        PowerBISession.getKeyboard().sendKeys(Keys.ARROW_DOWN);
        PowerBISession.getKeyboard().sendKeys(Keys.ENTER);

        File temp = File.createTempFile("temp",".txt",new File("C:\\Project\\PowerBIauto\\src\\test\\resources"));
        test.ReadClipboard(temp.getAbsolutePath());

    }

    public void parserString() throws InterruptedException, IOException {
        Thread.sleep(5000);

        ArrayList<String> tabs2 = new ArrayList<String>(PowerBISession.getWindowHandles());
        PowerBISession.switchTo().window(tabs2.get(0));

        try {
            PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();

        } catch (NoSuchElementException e){
            PowerBISession.findElementByName("View").click();
            PowerBISession.findElementByName("Go to Column").click();
            PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();
            PowerBISession.getKeyboard().sendKeys(Keys.ENTER);
        }

        PowerBISession.getKeyboard().sendKeys(Keys.LEFT_ALT,Keys.ARROW_DOWN);
        PowerBISession.getKeyboard().pressKey(Keys.LEFT_ALT);


        PowerBISession.findElementByName("Text Filters").click();
        PowerBISession.findElementByName("Begins With...").click();

        PowerBISession.findElementByName("Value for Clause 1, Enter or select a value").click();
        Thread.sleep(3000);
        PowerBISession.getKeyboard().sendKeys(ConfProperties.getProperty("ValueCell"));
        PowerBISession.getKeyboard().sendKeys(Keys.ENTER);
        PowerBISession.findElementByName("OK").click();

        PowerBISession.findElementByName("Table actions").click();
        PowerBISession.findElementByName("Copy Entire Table, 1 of 20.").click();

        File temp = File.createTempFile("temp",".txt",new File("C:\\Project\\PowerBIauto\\src\\test\\resources"));
        test.ReadClipboard(temp.getAbsolutePath());

    }

    public void parserCell() throws IOException, InterruptedException {
            parserString();
            PowerBISession.findElementByName(ConfProperties.getProperty("ColumnNameForCell")).click();
            PowerBISession.getKeyboard().sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN);
            PowerBISession.getKeyboard().sendKeys(Keys.LEFT_CONTROL,"c");


        File temp = File.createTempFile("temp",".txt",new File("C:\\Project\\PowerBIauto\\src\\test\\resources"));
        test.ReadClipboard(temp.getAbsolutePath());



    }

}
