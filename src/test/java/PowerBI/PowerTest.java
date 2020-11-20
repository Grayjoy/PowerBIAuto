package PowerBI;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.net.URL;

import io.appium.java_client.windows.WindowsDriver;


public class PowerTest {

    private static WindowsDriver PowerBISession = null;

    Actions actions = new Actions(PowerBISession);

    CopyToBuffer test = new CopyToBuffer();

    @BeforeClass
    public static void setup() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", ConfProperties.getProperty("pathToApp"));

            PowerBISession = new WindowsDriver(new URL(ConfProperties.getProperty("URLforServerAppium")), capabilities);
            PowerBISession.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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


        selectData();

        if (ConfProperties.getProperty("ParsingParameters").equals("string")) {
            parserString();
        }
        if (ConfProperties.getProperty("ParsingParameters").equals("column")) {
            parserColumn();
        }
        if (ConfProperties.getProperty("ParsingParameters").equals("cell")) {
            parserCell();
        } else {
            System.out.println("Invalid value.Please select string, column or cell");
        }
    }


    public void selectData() throws InterruptedException {

        Actions actions = new Actions(PowerBISession);
        actions.contextClick(PowerBISession.findElement(By.name("Level 2, " + ConfProperties.getProperty("TableName")))).perform();
        actions.moveToElement(PowerBISession.findElementByName("Transform Data, 1 of 2.")).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();

    }

    public void parserColumn() throws IOException, InterruptedException {

        switchWindow();

        searchColumn();

        PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();
        actions.contextClick().perform();
        PowerBISession.getKeyboard().sendKeys(Keys.ARROW_DOWN);
        PowerBISession.getKeyboard().sendKeys(Keys.ENTER);

        createAndWriteFile();

    }

    public void parserString() throws InterruptedException, IOException {
        switchWindow();

        searchColumn();

        searchingValueInColumn();

        PowerBISession.findElementByName("Table actions").click();
        PowerBISession.findElementByName("Copy Entire Table, 1 of 20.").click();

        createAndWriteFile();

    }

    public void parserCell() throws IOException, InterruptedException {
        switchWindow();

        searchingValueInColumn();

        PowerBISession.findElementByName(ConfProperties.getProperty("ColumnNameForCell")).click();
        actions.sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.LEFT_CONTROL, "c").perform();
        actions.sendKeys(Keys.LEFT_CONTROL).perform();


        createAndWriteFile();

    }

    public void switchWindow() throws InterruptedException {
        Thread.sleep(5000);

        ArrayList<String> tabs2 = new ArrayList<String>(PowerBISession.getWindowHandles());
        PowerBISession.switchTo().window(tabs2.get(0));
    }

    public void searchColumn() {
        try {
            PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();

        } catch (NoSuchElementException e) {
            PowerBISession.findElementByName("View").click();
            PowerBISession.findElementByName("Go to Column").click();
            PowerBISession.findElementByName(ConfProperties.getProperty("ColumnName")).click();
            actions.sendKeys(Keys.ENTER).perform();
        }
    }

    public void createAndWriteFile() throws IOException {
        File temp = File.createTempFile("temp", ".txt", new File("C:\\Project\\PowerBIauto\\src\\test\\resources"));
        test.ReadClipboard(temp.getAbsolutePath());
    }

    public void searchingValueInColumn() throws InterruptedException {

        searchColumn();


        actions.sendKeys(Keys.LEFT_ALT, Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.LEFT_ALT).perform();


        PowerBISession.findElementByName("Text Filters").click();
        PowerBISession.findElementByName("Begins With...").click();

        PowerBISession.findElementByName("Value for Clause 1, Enter or select a value").click();
        Thread.sleep(3000);
        PowerBISession.getKeyboard().sendKeys(ConfProperties.getProperty("ValueCell"));

        PowerBISession.findElementByName("OK").click();
    }


}
