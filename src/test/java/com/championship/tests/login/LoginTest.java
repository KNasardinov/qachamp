package com.championship.tests.login;

import com.championship.pages.BasePage;
import com.championship.pages.LoginPage;
import com.championship.pages.MainPage;
import com.championship.tests.AbstractTest;
import com.codeborne.selenide.Condition;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginTest extends AbstractTest {

    @Test
    public void testLoginWithValidCredentials() {
        new LoginPage()
                .verifyAt()
                .enterLogin("test")
                .enterPassword("test")
                .clickSignUpButton()
                .confirmLogin()
                .confirmLogin();
        new MainPage()
                .verifyAt();
    }

    @Test
    public void testLoginWithInValidPassword() {
        LoginPage loginPage = new LoginPage()
                .verifyAt()
                .enterLogin("test")
                .enterPassword("test test")
                .clickSignUpButton()
                .confirmLogin()
                .confirmLogin();
        loginPage.verifyAt();
    }

    @Test
    public void testLoginWithValidCredentialsAndDismissFirstPopUp() {
        LoginPage loginPage = new LoginPage()
                .verifyAt()
                .enterLogin("test")
                .enterPassword("test")
                .clickSignUpButton()
                .dismissLogin();
        loginPage.verifyAt();
    }

    @Test
    public void testLoginWithValidCredentialsAndDismissSecondPopUp() {
        LoginPage loginPage = new LoginPage()
                .verifyAt()
                .enterLogin("test")
                .enterPassword("test")
                .clickSignUpButton()
                .confirmLogin()
                .dismissLogin();
        loginPage.verifyAt();
    }

    @Test
    public void testLoginWithInValidLogin() {
        BasePage loginPage = new LoginPage()
                .verifyAt()
                .enterLogin("test invalid login")
                .enterPassword("test")
                .clickSignUpButton()
                .confirmLogin()
                .confirmLogin();
        loginPage.verifyAt();
    }

    @Test
    public void testHoverMeButtonDisabledIfLoginIsNotFilled() {
        LoginPage loginPage = new LoginPage()
                .verifyAt()
                .enterLogin("")
                .enterPassword("test");
        assertFalse(loginPage.getHoverMeButton().is(Condition.enabled));
    }

    @Test
    public void testHoverMeButtonDisabledIfPasswordIsNotFilled() {
        LoginPage loginPage = new LoginPage()
                .verifyAt()
                .enterLogin("test")
                .enterPassword("");
        assertFalse(loginPage.getHoverMeButton().is(Condition.enabled));
    }

    @Test
    public void testHoverMeButtonIsDisplayedIfUserRemovesEnteredValues() {
        LoginPage loginPage = new LoginPage()
                .verifyAt()
                .enterLogin("test")
                .enterPassword("test")
                .hoverHoverMeButton()
                .enterLogin("");
        assertTrue(loginPage.getHoverMeButton().is(Condition.visible));
    }
}