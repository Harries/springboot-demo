package com.et.selenium.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Lazy
@Component
public class ScreenShotUtil {

    @Autowired
    private TakesScreenshot driver;

    // location of screenshot file
    @Value("${screenshot.path}")
    private String path;

    public void takeScreenShot(final String imgName) throws IOException {
        // takes screenshot as saves to path in app properties file using given imgName ex. test.png
        if (System.getenv("CLOUD_RUN_FLAG") == null) {
            try {
                File sourceFile = this.driver.getScreenshotAs(OutputType.FILE);
                File  targetfile = new File(path+"/"+imgName);
                FileCopyUtils.copy(sourceFile, targetfile);
                System.out.println("Saving screenshot to " + path);
            } catch (Exception e) {
                System.out.println("Something went wrong with screenshot capture" + e);
            }
        }


    }
}
