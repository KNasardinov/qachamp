package com.championship.tests.profile;

import com.championship.pages.PaymentInfoPage;
import com.championship.pages.UserProfilePage;
import com.championship.tests.LoggedInUserAbstractTest;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PaymentInfoTest extends LoggedInUserAbstractTest {

    @Test
    public void verifyThatPaymentInfoIsSavedTest() {
        UserProfilePage userProfilePage = new UserProfilePage().open().verifyAt();
        PaymentInfoPage paymentInfoPage = userProfilePage.clickPaymentInfoTab();
        paymentInfoPage.fillInSetYourCardNumber("1234567890123");
        paymentInfoPage.chooseYourPaymentSystemDropDown("Visa");
        paymentInfoPage.selectDayOfPayment(10);
        paymentInfoPage.clickSavePaymentInfoButton();
        userProfilePage = new UserProfilePage().open().verifyAt();
        paymentInfoPage = userProfilePage.clickPaymentInfoTab();
        assertEquals(10, paymentInfoPage.getDayOfPaymentLabelValue());
        assertEquals("1234567890123", paymentInfoPage.getYourCardNumber());
        assertEquals("Visa", paymentInfoPage.getPaymentSystem());
    }

    @Test
    public void verifySetYourCardNumberErrorMessage() {
        UserProfilePage userProfilePage = new UserProfilePage().open().verifyAt();
        PaymentInfoPage paymentInfoPage = userProfilePage.clickPaymentInfoTab();
        paymentInfoPage.chooseYourPaymentSystemDropDown("Visa");
        paymentInfoPage.clickSavePaymentInfoButton();
        assertEquals("Please set your card number.",
                paymentInfoPage.getCardNumberErrorMessage());
    }

    @Test
    public void verifyChoosePaymentSystemErrorMessage() {
        UserProfilePage userProfilePage = new UserProfilePage().open().verifyAt();
        PaymentInfoPage paymentInfoPage = userProfilePage.clickPaymentInfoTab();
        paymentInfoPage.fillInSetYourCardNumber("1234567890123");
        paymentInfoPage.clickSavePaymentInfoButton();
        assertEquals("Please select your payment system.",
                paymentInfoPage.getPaymentSystemErrorMessage());
    }

    @Test
    public void verifyThatPaymentInfoIsSavedWithoutDayOfPaymentTest() {
        UserProfilePage userProfilePage = new UserProfilePage().open().verifyAt();
        PaymentInfoPage paymentInfoPage = userProfilePage.clickPaymentInfoTab();
        paymentInfoPage.fillInSetYourCardNumber("1234567890123");
        paymentInfoPage.chooseYourPaymentSystemDropDown("Visa");
        paymentInfoPage.clickSavePaymentInfoButton();
        userProfilePage = new UserProfilePage().open().verifyAt();
        paymentInfoPage = userProfilePage.clickPaymentInfoTab();
        assertEquals("1234567890123", paymentInfoPage.getYourCardNumber());
        assertEquals("Visa", paymentInfoPage.getPaymentSystem());
    }
}