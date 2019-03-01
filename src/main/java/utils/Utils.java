package utils;

import consts.PropertiesNames;
import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.io.File.separator;
import static org.slf4j.LoggerFactory.getLogger;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Utils {
    private static String currentSessionFolderPath;
    private static final String TIME_ZONE = "GMT+1:00";
    private static final Logger LOG = getLogger(Utils.class);
    private static final String PNG = ".png";
    private static final String UNDERLINE = "_";

    static {
        String errMessage = " folder was not created";
        String pathToScreenshotsRoot = PropertyReader.getGlobalProperty(PropertiesNames.PATH_TO_SCREENSHOTS);
        if (!(new File(pathToScreenshotsRoot)).exists()) {
            LOG.warn("Folder does not exist - " + pathToScreenshotsRoot);
            assertTrue((new File(pathToScreenshotsRoot)).mkdir(), pathToScreenshotsRoot + errMessage);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy'T'HH-mm-ss-SSS");
        formatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

        String monthFolderPath = pathToScreenshotsRoot + separator + (new SimpleDateFormat("MM")).format(new Date());
        File monthFolder = new File(monthFolderPath);
        if (!monthFolder.exists()) {
            assertTrue(monthFolder.mkdir(), monthFolderPath + errMessage);
        }
        String currentTime = formatter.format(Calendar.getInstance().getTime());
        String dayFolderPath = monthFolderPath + separator + currentTime.split("T")[0];
        File dayFolder = new File(dayFolderPath);
        if (!dayFolder.exists()) {
            assertTrue(dayFolder.mkdir(), dayFolderPath + errMessage);
        }
        currentSessionFolderPath = dayFolderPath + separator + currentTime.split("T")[1];
        File currentSessionFolder = new File(currentSessionFolderPath);
        assertTrue(currentSessionFolder.mkdir(), currentSessionFolderPath + errMessage);
    }

    static String getPathToScreenshots() {
        return currentSessionFolderPath;
    }

    public static File doScreenshot(WebDriver driver) {
        File file = null;
        try {
            file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            LOG.error("Cannot take screenshot, error found");
            LOG.error(e.getMessage(), e);
        }
        return file;
    }

    public static void saveScreenshot(File screen, Scenario scenario) {
        if (screen == null) {
            LOG.error("Screenshots are empty!");
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
        formatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        String currentTime = formatter.format(Calendar.getInstance().getTime());
        String fileName = separator + currentTime + UNDERLINE + scenario.getName() + PNG;

        try {
            FileUtils.copyFile(screen, new File(currentSessionFolderPath + fileName));
            LOG.error("Created screenshots: \n" + currentSessionFolderPath + fileName);
        } catch (IOException io) {
            LOG.error("Cannot save screenshots: \n" + currentSessionFolderPath + fileName);
            LOG.warn(io.getMessage(), io);
        }
    }
}
