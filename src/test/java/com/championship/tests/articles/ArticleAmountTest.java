package com.championship.tests.articles;

import com.championship.pages.MainPage;
import com.championship.tests.LoggedInUserAbstractTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ArticleAmountTest extends LoggedInUserAbstractTest {

    private final String categoryName;
    private final int expectedArticleAmount;

    @Test
    public void verifyArticleAmountPerCategory() {
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(categoryName);
        assertThat(mainPage.getArticlesPerCategory(categoryName).size())
            .as("Amount of menu buttons for category {} should be {}",
                    categoryName, expectedArticleAmount)
            .isEqualTo(expectedArticleAmount);
    }

    public ArticleAmountTest(String categoryName, int articleAmount) {
        this.categoryName = categoryName;
        this.expectedArticleAmount = articleAmount;
    }

    @Parameterized.Parameters
    public static Collection categories() {
        return Arrays.asList(new Object[][]{
                {"Advertisers", 2},
                {"Publishers", 2},
                {"Top level clients", 10},
        });
    }
}