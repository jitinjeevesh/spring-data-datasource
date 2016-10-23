package com.virgin.dao.mapping;

import com.virgin.dao.Kind;
import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

public class DataStorePersistentEntityImpl<T> extends BasicPersistentEntity<T, DataStorePersistentProperty>
        implements DataStorePersistentEntity<T> {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    //    private final StandardEvaluationContext context;
    //    private final Expression expression;
    private final String kind;

    public DataStorePersistentEntityImpl(TypeInformation<T> typeInformation) {
        super(typeInformation);
        Class<?> rawType = typeInformation.getType();
        String fallback = rawType.getSimpleName();
        Kind kind = this.findAnnotation(Kind.class);
//        this.expression = detectExpression(kind);
//        this.context = new StandardEvaluationContext();
        if (kind != null) {
            this.kind = StringUtils.hasText(kind.name()) ? kind.name() : fallback;
        } else {
            this.kind = fallback;
        }
    }

    @Override
    protected DataStorePersistentProperty returnPropertyIfBetterIdPropertyCandidateOrNull(DataStorePersistentProperty property) {
        return property.isIdProperty() ? property : null;
    }


//This included when we write the expression
/*    private static Expression detectExpression(Kind kind) {
        if (kind == null) {
            return null;
        }
        String name = kind.name();
        if (!StringUtils.hasText(name)) {
            return null;
        }
        Expression expression = PARSER.parseExpression(kind.name(), ParserContext.TEMPLATE_EXPRESSION);
        return expression instanceof LiteralExpression ? null : expression;
    }*/
}
