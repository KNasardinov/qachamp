package com.championship.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$;

public class PaymentInfoPage extends BasePage {

    SelenideElement setYourCardInputField = $("#cardNumberInput");
    SelenideElement chooseYourPaymentSystemDropDown = $("#paymentSystemSelect");
    SelenideElement selectDayOfPaymentSlider = $("#paymentRange");
    SelenideElement savePaymentInfoButton = $("button[onclick='savePaymentInfo()']");
    SelenideElement currentValueLabel = $("div.bd-example h6");
    SelenideElement cardNumberErrorMessage = setYourCardInputField.parent().$(".invalid-feedback");
    SelenideElement paymentSystemErrorMessage = chooseYourPaymentSystemDropDown.parent().$(".invalid-feedback");

    @Override
    public PaymentInfoPage verifyAt() {
        savePaymentInfoButton.shouldBe(Condition.visible);
        savePaymentInfoButton.shouldBe(Condition.enabled);
        return this;
    }

    public PaymentInfoPage clickSavePaymentInfoButton(){
        savePaymentInfoButton.click();
        return this;
    }

    public PaymentInfoPage fillInSetYourCardNumber(String cardNumber){
        setYourCardInputField.val(cardNumber);
        return this;
    }

    public PaymentInfoPage chooseYourPaymentSystemDropDown(String paymentSystem){
        chooseYourPaymentSystemDropDown.selectOptionContainingText(paymentSystem);
        return this;
    }

    public PaymentInfoPage selectDayOfPayment(int dayOfPayment) {
        int currentSelectedValue = getDayOfPaymentLabelValue();
        if (currentSelectedValue == dayOfPayment) {
            return this;
        }
        if (currentSelectedValue > dayOfPayment) {
            moveSliderTimes(currentSelectedValue - dayOfPayment, Keys.ARROW_LEFT);
        } else {
            moveSliderTimes(dayOfPayment - currentSelectedValue, Keys.ARROW_RIGHT);
        }
        return this;
    }

    private void moveSliderTimes(int times, Keys keys){
        for(int i = 0; i < times; i++){
            selectDayOfPaymentSlider.sendKeys(keys);
        }
    }

    public int getDayOfPaymentLabelValue(){
        String currentValue = currentValueLabel.getText();
        int currentSelectedValue;
        if("".equals(currentValue)){
            currentSelectedValue = 1;
        } else {
            currentSelectedValue = Integer.valueOf(currentValue.split(":")[1].trim());
        }
        return currentSelectedValue;
    }

    public String getPaymentSystem() {
        return chooseYourPaymentSystemDropDown.getSelectedText();
    }

    public String getYourCardNumber() {
        return setYourCardInputField.getValue();
    }

    public String getCardNumberErrorMessage(){
        return cardNumberErrorMessage.getText();
    }

    public String getPaymentSystemErrorMessage(){
        return paymentSystemErrorMessage.getText();
    }
}
