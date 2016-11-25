package com.spring.datasource.core;

import java.util.List;

public class DataStoreQueryResponseImpl<T> implements DataStoreQueryResponse<T> {

    protected List<T> results = null;

    protected String startCursor = null;

    protected String endCursor = null;

    @Override
    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String getStartCursor() {
        return startCursor;
    }

    public void setStartCursor(String startCursor) {
        this.startCursor = startCursor;
    }

    @Override
    public String getEndCursor() {
        return endCursor;
    }

    public void setEndCursor(String endCursor) {
        this.endCursor = endCursor;
    }
}
