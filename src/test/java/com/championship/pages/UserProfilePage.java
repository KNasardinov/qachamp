package com.championship.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class UserProfilePage extends BasePage {

    SelenideElement userProfileTab = $(byText("User Profile"));
    SelenideElement firstNameInputField = $("#firstNameInput");
    SelenideElement lastNameInputField = $("#lastNameInput");
    SelenideElement saveUserInfoButton = $(byText("Save user info"));
    SelenideElement paymentInfoTab = $(byText("Payment info"));
    SelenideElement lastNameErrorMessage = lastNameInputField.parent().$(".invalid-feedback");
    SelenideElement firstNameErrorMessage = firstNameInputField.parent().$(".invalid-feedback");

    public UserProfilePage open(){
        Selenide.open("/profile.html");
        return this;
    }

    @Override
    public UserProfilePage verifyAt() {
        userProfileTab.shouldBe(Condition.visible);
        userProfileTab.shouldBe(Condition.enabled);
        return this;
    }

    public UserProfilePage clickSaveUserInfoButton(){
        saveUserInfoButton.click();
        return this;
    }

    public UserProfilePage fillInFirstNameInputField(String firstName){
        firstNameInputField.val(firstName);
        return this;
    }

    public UserProfilePage fillInLastNameInputField(String lastName){
        lastNameInputField.val(lastName);
        return this;
    }

    public PaymentInfoPage clickPaymentInfoTab(){
        paymentInfoTab.click();
        return new PaymentInfoPage().verifyAt();
    }

    public String getFirstNameErrorMessage(){
        firstNameErrorMessage.shouldBe(Condition.visible);
        return firstNameErrorMessage.getText();
    }

    public String getLastNameErrorMessage(){
        lastNameErrorMessage.shouldBe(Condition.visible);
        return lastNameErrorMessage.getText();
    }

    public String getFirstNameValue(){
        return firstNameInputField.getValue();
    }

    public String getLastNameValue(){
        return lastNameInputField.getValue();
    }
}
