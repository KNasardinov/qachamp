package com.championship.tests.articles;

import com.championship.pages.MainPage;
import com.championship.tests.LoggedInUserAbstractTest;
import com.championship.utils.FileDownloadedEnhancedFilter;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Config;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.StaticConfig;
import com.codeborne.selenide.proxy.FileDownloadFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;
import static java.util.logging.Logger.getLogger;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ArticleFileContentTest extends LoggedInUserAbstractTest {

    private static final Logger log = getLogger(ArticleFileContentTest.class.getName());

    private List<File> downloadedFiles;
    private FileDownloadFilter filter;
    private final String category;
    private final int publisher;

    public ArticleFileContentTest(String category, int publisher) {
        this.category = category;
        this.publisher = publisher;
    }

    @Test
    public void fileContentWithDescriptionComparisonTest() throws IOException {
        log.info("Started test for category = " + category + " and id = " + publisher);
        MainPage mainPage = new MainPage()
                .verifyAt()
                .clickCategory(category);
        mainPage.clickArticleWithinCategory(category, publisher);
        File downloadedFile = downloadFile(mainPage.getDownloadInfoButton());
        String descriptionText = mainPage.articleTextArea.innerText();
        String fileContent = FileUtils.readFileToString(downloadedFile, "UTF-8");
        assertThat(descriptionText)
                .as("File content should match to description on page for {} id = {}",
                        category, publisher)
                .isEqualTo(fileContent);
    }

    private File downloadFile(SelenideElement downloadButton) {
        filter.activate();
        downloadButton
                .waitUntil(Condition.visible, 15000)
                .click();
        filter.deactivate();
        downloadedFiles = filter.getDownloadedFiles();
        return downloadedFiles.get(0);
    }

    @Override
    @After
    public void tearDown() throws IOException {
        screenshot();
        close();
        cleanupFiles();
    }

    private void cleanupFiles() {
        if (!CollectionUtils.isEmpty(downloadedFiles)) {
            downloadedFiles.stream().forEach(File::delete);
        }
    }

    @Override
    @Before
    public void setUp() {
        startWebDriverWithValidCookies();
        Config config = new StaticConfig();
        filter = new FileDownloadedEnhancedFilter(config);
        getSelenideProxy().addResponseFilter("proxy-usages.response", filter);
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
