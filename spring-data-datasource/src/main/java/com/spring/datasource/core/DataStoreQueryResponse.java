package com.spring.datasource.core;

import java.util.List;

public interface DataStoreQueryResponse<T> {

    List<T> getResults();

    String getStartCursor();

    String getEndCursor();

}
