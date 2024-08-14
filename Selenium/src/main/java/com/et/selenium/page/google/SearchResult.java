package com.et.selenium.page.google;


import com.et.selenium.annotation.PageFragment;
import com.et.selenium.page.Base;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageFragment// using custom annotation created; src/main/java/com/demo/seleniumspring/annotation/PageFragment.java
public class SearchResult extends Base {

    @FindBy(css = "div.g")
    private List<WebElement> results;

    public int getCount() {
        return this.results.size();
    }

    @Override
    public boolean isAt() {
        return this.wait.until((d) -> !this.results.isEmpty());
    }
}
