package utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;
import static utils.Utils.getPathToScreenshots;

public class Reporter {
    private static final Logger LOG = getLogger(Reporter.class);
    private static final String REPORT_FOLDER = "report";
    private static final String PROJECT_NAME = "Weather";

    public static void generateReport() {
        File reportDirectory = new File("target/" + REPORT_FOLDER);
        String sep = File.separator;
        List<String> jsonFiles = new ArrayList<>();
        File[] directoryFiles = reportDirectory.listFiles();
        if (Objects.nonNull(directoryFiles)) {
            for (File file : directoryFiles) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    jsonFiles.add("./target/" + REPORT_FOLDER + sep + file.getName());
                }
            }
        }
        Configuration configuration = new Configuration(reportDirectory, PROJECT_NAME);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
        String pathToReportOverview = System.getProperty("user.dir") + sep + "target" + sep + REPORT_FOLDER ;
        File targetFolder = new File(pathToReportOverview);
        File[] files = targetFolder.listFiles();
        assertTrue(files != null && files.length > 0, "No files generated");
        try {
            for (File entry : files) {
                String startPath = getPathToScreenshots();
                if (entry.isFile()) {
                    LOG.info(entry + " copy file to " + startPath + sep + entry.getName());
                    FileUtils.copyFile(entry, new File(startPath + sep + entry.getName()));
                } else if (entry.isDirectory()) {
                    LOG.info(entry + " copy dir to " + startPath + sep + REPORT_FOLDER);
                    FileUtils.copyDirectory(entry, new File(startPath + sep + REPORT_FOLDER));
                }
            }
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }
        LOG.info("Report generated");
    }
}
