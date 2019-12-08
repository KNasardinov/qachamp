package com.championship.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.Setter;
import static com.codeborne.selenide.Selenide.$;

@Getter
@Setter
public class LoginPage extends BasePage {

    private static long SIGN_UP_TIMEOUT = 15000;

    private SelenideElement loginInputForm =
            $("div.form-group[onclick='startInputLogin()']");
    private SelenideElement loginInputField = $("#loginInput");
    private SelenideElement passwordInputForm =
            $("div.form-group[onclick='startInputPassword()']");
    private SelenideElement passwordInputField = $("#passwordInput");
    private SelenideElement hoverMeButton = $("button.btn-primary:nth-child(2)");
    private SelenideElement signUpButton = $("div.card-footer > div > img");

    @Override
    public LoginPage verifyAt() {
        getLoginInputForm().shouldBe(Condition.visible);
        return this;
    }

    public LoginPage enterLogin(String login) {
        loginInputForm.click();
        loginInputField.setValue(login);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordInputForm.click();
        passwordInputField.setValue(password);
        return this;
    }

    public LoginPage clickSignUpButton() {
        hoverHoverMeButton();
        signUpButton.click();
        return this;
    }

    public LoginPage hoverHoverMeButton() {
        hoverMeButton.hover();
        signUpButton.waitUntil(Condition.visible, SIGN_UP_TIMEOUT);
        return this;
    }

    public LoginPage confirmLogin() {
        Selenide.confirm();
        return this;
    }

    public LoginPage dismissLogin() {
        Selenide.dismiss();
        return this;
    }
}