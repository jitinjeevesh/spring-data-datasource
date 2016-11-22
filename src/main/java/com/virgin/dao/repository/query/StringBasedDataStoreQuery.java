package com.virgin.dao.repository.query;

import com.virgin.dao.core.DataStoreOperation;
import com.virgin.dao.core.query.StringQuery;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

public class StringBasedDataStoreQuery extends AbstractDataStoreQuery {

    private static final String COUND_AND_DELETE = "Manually defined query for %s cannot be both a count and delete query at the same time!";
    private final String query;
    private final boolean isCountQuery;
    private final boolean isDeleteQuery;
    private final boolean isAllowLiteral;

    public StringBasedDataStoreQuery(DataStoreQueryMethod queryMethod, DataStoreOperation operations, SpelExpressionParser expressionParser, EvaluationContextProvider evaluationContextProvider) {
        this(queryMethod.getAnnotatedQuery(), queryMethod, operations, expressionParser, evaluationContextProvider);
    }


    public StringBasedDataStoreQuery(String query, DataStoreQueryMethod method, DataStoreOperation dataStoreOperation,
                                     SpelExpressionParser expressionParser, EvaluationContextProvider evaluationContextProvider) {
        super(method, dataStoreOperation);
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(expressionParser, "SpelExpressionParser must not be null!");

        this.isCountQuery = method.hasAnnotatedQuery() && method.getQueryAnnotation().count();
        this.isDeleteQuery = method.hasAnnotatedQuery() && method.getQueryAnnotation().delete();
        this.isAllowLiteral = method.hasAnnotatedQuery() && method.getQueryAnnotation().allowLiteral();
        this.query = query;

        if (isCountQuery && isDeleteQuery) {
            throw new IllegalArgumentException(String.format(COUND_AND_DELETE, method));
        }
    }


    @Override
    protected StringQuery createQuery(DataStoreConvertingParameterAccessor accessor) {
        StringQueryCreator stringQueryCreator = new StringQueryCreator(query, accessor, isAllowLiteral);
        StringQuery stringQuery = stringQueryCreator.createQuery();
        System.out.println(stringQuery.getQuery());
        return stringQuery;
    }

    @Override
    protected boolean isCountQuery() {
        return this.isCountQuery;
    }

    @Override
    protected boolean isDeleteQuery() {
        return this.isDeleteQuery;
    }
}
