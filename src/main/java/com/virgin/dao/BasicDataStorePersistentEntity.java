package com.virgin.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

public class BasicDataStorePersistentEntity<T> extends BasicPersistentEntity<T, DataStorePersistentProperty> implements DataStorePersistentEntity<T>, ApplicationContextAware {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    //    private final StandardEvaluationContext context;
    //    private final Expression expression;
    private final String kind;

    public BasicDataStorePersistentEntity(TypeInformation<T> typeInformation) {
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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       /* context.addPropertyAccessor(new BeanFactoryAccessor());
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        context.setRootObject(applicationContext);*/
    }

    @Override
    public String getKind() {
        return kind;
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
