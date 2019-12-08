package com.championship.tests.profile;

import com.championship.pages.MainPage;
import com.championship.pages.UserProfilePage;
import com.championship.tests.LoggedInUserAbstractTest;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserProfileTest extends LoggedInUserAbstractTest {

    private static final String FIRST_NAME = "testFirstName";
    private static final String LAST_NAME = "testLastName";

    @Test
    public void verifyThatClickProfileIconOpensUserProfileSettingsPage() {
        UserProfilePage userProfilePage = new MainPage()
                .verifyAt()
                .clickProfileIcon();
        userProfilePage.verifyAt();
    }

    @Test
    public void verifyThatErrorMessageIsDisplayedForFirstNameField() {
        UserProfilePage userProfilePage = new UserProfilePage()
                .open()
                .verifyAt()
                .fillInLastNameInputField(LAST_NAME)
                .clickSaveUserInfoButton();
        assertEquals("Please set your first name.", userProfilePage.getFirstNameErrorMessage());
    }

    @Test
    public void verifyThatErrorMessageIsDisplayedForLastNameField() {
        UserProfilePage userProfilePage = new UserProfilePage()
                .open()
                .verifyAt()
                .fillInFirstNameInputField(FIRST_NAME)
                .clickSaveUserInfoButton();
        assertEquals("Please set your last name.", userProfilePage.getLastNameErrorMessage());
    }

    @Test
    public void verifyThatFirstNameAndLastNameValuesAreSaved() {
        new UserProfilePage()
                .open()
                .verifyAt()
                .fillInFirstNameInputField(FIRST_NAME)
                .fillInLastNameInputField(LAST_NAME)
                .clickSaveUserInfoButton();
        UserProfilePage userProfilePage = new MainPage()
                .open()
                .verifyAt()
                .clickProfileIcon()
                .verifyAt();
        assertEquals(FIRST_NAME, userProfilePage.getFirstNameValue());
        assertEquals(LAST_NAME, userProfilePage.getLastNameValue());
    }
}