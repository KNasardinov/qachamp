package com.championship.tests.articles;

import com.championship.pages.MainPage;
import com.championship.tests.LoggedInUserAbstractTest;
import com.codeborne.selenide.Condition;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ArticleImageTest extends LoggedInUserAbstractTest {

    private static final Logger log = getLogger(ArticleImageTest.class.getName());

    private final String category;
    private final int publisher;
    private static int CONTENT_PAGE_LOAD_TIMEOUT = 10000;

    public ArticleImageTest(String category, int publisher) {
        this.category = category;
        this.publisher = publisher;
    }

    @Test
    public void fileContentWithDescriptionComparisonTest() {
        log.info("Started test for category = " + category + " and id = " + publisher);
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(category)
                .clickArticleWithinCategory(category, publisher);
        mainPage.getArticleImage().waitUntil(Condition.visible, CONTENT_PAGE_LOAD_TIMEOUT);
        assertThat(mainPage.getArticleImage().isImage())
                .as("Image should be properly downloaded")
                .isTrue();
    }

    @Parameterized.Parameters
    public static Collection categories() {
        List publishers = getListPublishers("Publishers", 2);
        List advertisers = getListPublishers("Advertisers", 2);
        List topLevelClients = getListPublishers("Top level clients", 10);

        return CollectionUtils.union(
                CollectionUtils.union(publishers, advertisers),
                topLevelClients);
    }

    private static List getListPublishers(String category, int size) {
        List publishers = new ArrayList();
        for (int i = 0; i < size; i++) {
            Object[] publisher = {category, i};
            publishers.add(publisher);
        }
        return publishers;
    }
}
