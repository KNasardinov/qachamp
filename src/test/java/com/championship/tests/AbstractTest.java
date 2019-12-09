package com.championship.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.Cookie;
import ru.yandex.qatools.allure.annotations.Attachment;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;

public abstract class AbstractTest {

    @BeforeClass
    public static void setupAll(){
        Configuration.fileDownload = PROXY;
        Configuration.proxyEnabled = true;

        String remoteUrl = System.getenv("REMOTE_WEB_DRIVER");
        if(remoteUrl != null){
            Configuration.remote = "http://" + remoteUrl + "/wd/hub";
        }
        String browser = System.getenv("BROWSER");
        if(browser != null) {
            Configuration.browser = browser;
        }
        String appAddress = System.getenv("APPLICATION");
        if(appAddress != null){
            Configuration.baseUrl = appAddress;
        }
    }

    @Before
    public void setUp() {
        open("/");
    }

    @After
    public void tearDown() throws IOException {
        screenshot();
        close();
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

    public void startWebDriverWithValidCookies(){
        open("/");
        Cookie loginCookie = new Cookie("secret", "IAmSuperSeleniumMaster");
        WebDriverRunner.getWebDriver().manage().addCookie(loginCookie);
        open("/");
    }
}
