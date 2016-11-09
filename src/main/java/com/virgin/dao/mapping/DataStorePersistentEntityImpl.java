package com.virgin.dao.mapping;

import com.virgin.dao.core.mapping.Kind;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

public class DataStorePersistentEntityImpl<T> extends BasicPersistentEntity<T, DataStorePersistentProperty>
        implements DataStorePersistentEntity<T>, ApplicationContextAware {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private final String kind;
    private final Expression expression;
    private final StandardEvaluationContext context;

    public DataStorePersistentEntityImpl(TypeInformation<T> typeInformation) {
        super(typeInformation);
        Class<?> rawType = typeInformation.getType();
        String fallback = rawType.getSimpleName();
        Kind kind = this.findAnnotation(Kind.class);
        this.expression = detectExpression(kind);
        this.context = new StandardEvaluationContext();
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context.addPropertyAccessor(new BeanFactoryAccessor());
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        context.setRootObject(applicationContext);
    }

    public String getKind() {
        return expression == null ? kind : expression.getValue(context, String.class);
    }

    private static Expression detectExpression(Kind kind) {
        if (kind == null) {
            return null;
        }
        String name = kind.name();
        if (!StringUtils.hasText(name)) {
            return null;
        }
        Expression expression = PARSER.parseExpression(kind.name(), ParserContext.TEMPLATE_EXPRESSION);
        return expression instanceof LiteralExpression ? null : expression;
    }
}
