package com.championship.tests.articles;

import com.championship.pages.MainPage;
import com.championship.tests.LoggedInUserAbstractTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class ArticlesTest extends LoggedInUserAbstractTest {

    public final static String ADVERTISERS_CATEGORY = "Advertisers";
    public final static String ADVERTISERS_ARTICLE = "Test Advertiser";

    @Test
    public void articleIsSavedAfterPageRefreshTest() {
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(ADVERTISERS_CATEGORY)
                .clickArticle(ADVERTISERS_ARTICLE)
                .scrollDownTextArea()
                .clickMoveToSavedButton()
                .clickSavedCategory(ADVERTISERS_CATEGORY);
        mainPage.open();
        List articles = mainPage.getSavedArticlesForCategory(ADVERTISERS_CATEGORY);
        assertEquals(articles.size(), 1);
        assertEquals(articles.get(0), ADVERTISERS_ARTICLE);
    }

    @Test
    public void articleIsAddedToSavedSectionTest() {
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(ADVERTISERS_CATEGORY)
                .clickArticle(ADVERTISERS_ARTICLE)
                .scrollDownTextArea()
                .clickMoveToSavedButton()
                .clickSavedCategory(ADVERTISERS_CATEGORY);
        List articles = mainPage.getSavedArticlesForCategory(ADVERTISERS_CATEGORY);
        assertEquals(articles.get(0), ADVERTISERS_ARTICLE);
        assertEquals(articles.size(), 1);
    }

    @Test
    public void savedArticleIsRemovedFromSavedSectionTest() {
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(ADVERTISERS_CATEGORY)
                .clickArticle(ADVERTISERS_ARTICLE)
                .scrollDownTextArea()
                .clickMoveToSavedButton()
                .clickSavedCategory(ADVERTISERS_CATEGORY);
        List articles = mainPage.getSavedArticlesForCategory(ADVERTISERS_CATEGORY);
        assertEquals(articles.size(), 1);
        mainPage.clickRemoveFromSavedButton();
        SelenideElement removedArticle = mainPage.getSavedArticleElementsForCategory(ADVERTISERS_CATEGORY).get(0);
        assertTrue(removedArticle.is(Condition.disappear));
        assertFalse(removedArticle.is(Condition.visible));
    }

    @Test
    public void verifyMoveToSavedButtonIsDisabledIfTextAreaIsNotScrolled() {
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(ADVERTISERS_CATEGORY)
                .clickArticle(ADVERTISERS_ARTICLE);
        mainPage.getMoveToSavedButton().shouldBe(Condition.disabled);
    }

    @Test
    public void resizedImageTest() {
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(ADVERTISERS_CATEGORY)
                .clickArticle(ADVERTISERS_ARTICLE);
        int width = mainPage.getImageWidth();
        mainPage.moveSliderRight(10);
        int resizedWidth = mainPage.getImageWidth();
        assertTrue((width + 10) == resizedWidth);
    }
}