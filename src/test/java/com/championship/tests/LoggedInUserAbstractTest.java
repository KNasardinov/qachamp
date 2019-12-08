package com.championship.tests;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.Before;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Selenide.open;

public abstract class LoggedInUserAbstractTest extends AbstractTest {

    @Before
    public void setUp() {
        startWebDriverWithValidCookies();
    }

    public void startWebDriverWithValidCookies(){
        open("/");
        Cookie loginCookie = new Cookie("secret", "IAmSuperSeleniumMaster");
        WebDriverRunner.getWebDriver().manage().addCookie(loginCookie);
        open("/");
    }
}