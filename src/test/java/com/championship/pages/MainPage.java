package com.championship.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Keys;

import java.util.List;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Getter
@Setter
public class MainPage extends BasePage {

    public SelenideElement sectionTitle = $(byText("Articles to read"));
    public SelenideElement profileIcon = $("#avatarContainer > img");
    public SelenideElement articleTitle = $("div#dataCard h5.card-title");
    public SelenideElement articleDescriptionText = $("div#dataCard div.card-body > p.card-text");
    public SelenideElement articleTextArea = $("div#dataCard textarea");
    public SelenideElement imageDescriptionText = $("div#dataCard div.card-body > div > p.card-text");
    public SelenideElement articleImage = $("img#heroImage");
    public SelenideElement downloadInfoButton = $(byText("Download info"));
    public SelenideElement moveToSavedButton = $(byText("Move to saved"));
    public SelenideElement removedFromSavedButton = $(byText("Removed from saved"));
    public SelenideElement savedArticlesLabel = $x("//div[contains(text(), 'Saved articles')]");
    public SelenideElement articlesToReadLabel = $x("//div[contains(text(), 'Articles to read')]");
    public SelenideElement imageSlider = $("div.ui-slider-horizontal > span");

    @Override
    public MainPage verifyAt() {
        Selenide.page(this)
                .sectionTitle
                .shouldBe(Condition.visible);
        return this;
    }

    public MainPage open() {
        Selenide.open("/");
        return this;
    }

    public MainPage clickCategory(String categoryTitle) {
        articlesToReadLabel.parent().$(byText(categoryTitle)).click();
        return this;
    }

    public List<SelenideElement> getArticlesPerCategory(String categoryTitle) {
        return articlesToReadLabel
                .parent()
                .$(byText(categoryTitle))
                .parent()
                .$$(".sub-tree-element > button");
    }

    public MainPage clickArticle(String articleTitle) {
        articlesToReadLabel.parent().$(byText(articleTitle)).click();
        return this;
    }

    public MainPage clickArticleWithinCategory(String category, int articleTitle) {
        $$x("//div[contains(text(), 'Articles to read')]" +
                "/parent::div" +
                "//button[contains(text(), '" + category + "')]" +
                "/parent::div" +
                "//div/button")
                .get(articleTitle)
                .click();
        return this;
    }

    public MainPage clickMoveToSavedButton() {
        moveToSavedButton.scrollIntoView(true).click();
        return this;
    }

    public MainPage clickRemoveFromSavedButton() {
        removedFromSavedButton.scrollIntoView(true).click();
        return this;
    }

    public UserProfilePage clickProfileIcon() {
        profileIcon.click();
        return new UserProfilePage();
    }

    public MainPage scrollDownTextArea() {
        Selenide.executeJavaScript(
                "arguments[0].scrollTop=(arguments[0].scrollHeight - arguments[0].clientHeight)",
                articleTextArea);
        return this;
    }

    public MainPage clickSavedCategory(String category) {
        savedArticlesLabel
                .parent()
                .$(byText(category))
                .scrollIntoView(true)
                .click();
        return this;
    }

    public List<SelenideElement> getSavedArticleElementsForCategory(String category) {
        return $$x("//div[contains(text(), 'Saved articles')]" +
                "/parent::div" +
                "//button[contains(text(), '" + category + "')]" +
                "/parent::div" +
                "//div/button");
    }

    public List<String> getSavedArticlesForCategory(String category) {
        return getSavedArticleElementsForCategory(category)
                .stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());
    }

    public MainPage moveSliderRight(int times){
        imageSlider.scrollIntoView(true);
        for(int i = 0; i < times; i++){
            imageSlider.sendKeys(Keys.ARROW_RIGHT);
        }
        return this;
    }

    public int getImageWidth(){
        String width = getArticleImage().getAttribute("width");
        return Integer.parseInt(width);
    }
}