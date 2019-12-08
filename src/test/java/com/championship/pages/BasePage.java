package com.championship.pages;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.NoAlertPresentException;

public abstract class BasePage {

    public abstract BasePage verifyAt();

    public boolean isAlertDisplayed() {
        try {
            Selenide.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}