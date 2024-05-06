package com.meco.evil.pm;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class SearchResult<T> {
    ObservableList<T> data;
    Map<SearchMatchType, Integer> resultMatches = new HashMap();
    public SearchResult(ObservableList<T> data) {
        this.data = data;
    }

    public SearchResult() {
    }

    public ObservableList<T> getData() {
        return data;
    }

    public void setData(ObservableList<T> list) {
        this.data = list;
    }

    public int getResultCount() {
        return data.size();
    }

    public void addMatch(SearchMatchType type) {
        var value = resultMatches.get(type);
        if (value == null) {
            resultMatches.put(type, 1);
        } else {
            resultMatches.put(type, value + 1);
        }
    }

    enum SearchMatchType {
        Name,
        Description
    }
}
