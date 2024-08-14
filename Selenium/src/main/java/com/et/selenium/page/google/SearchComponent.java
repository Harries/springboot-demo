package com.et.selenium.page.google;


import com.et.selenium.annotation.PageFragment;
import com.et.selenium.page.Base;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageFragment// using custom annotation created; src/main/java/com/demo/seleniumspring/annotation/PageFragment.java
public class SearchComponent extends Base {

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(name="btnK")
    private List<WebElement> searchBtns;

    public void search(final String keyword) {
        this.searchBox.sendKeys(keyword);
        this.searchBox.sendKeys(Keys.TAB);
        // CLICK first search button
        this.searchBtns
                .stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
    }

    @Override
    public boolean isAt() {
        return this.wait.until(driver1 -> this.searchBox.isDisplayed());
    }
}
