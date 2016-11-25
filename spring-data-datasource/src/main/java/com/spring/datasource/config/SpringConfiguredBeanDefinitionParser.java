package com.spring.datasource.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.ClassUtils;
import org.w3c.dom.Element;

public class SpringConfiguredBeanDefinitionParser implements BeanDefinitionParser {

    private static final String BEAN_CONFIGURER_ASPECT_BEAN_NAME = "org.springframework.context.config.internalBeanConfigurerAspect";

    private static final String BEAN_CONFIGURER_ASPECT_CLASS_NAME = "org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect";

    public BeanDefinition parse(Element element, ParserContext parserContext) {

        if (!parserContext.getRegistry().containsBeanDefinition(BEAN_CONFIGURER_ASPECT_BEAN_NAME)) {

            if (!ClassUtils.isPresent(BEAN_CONFIGURER_ASPECT_CLASS_NAME, getClass().getClassLoader())) {
                parserContext.getReaderContext().error(
                        "Could not configure Spring Data JPA auditing-feature because"
                                + " spring-aspects.jar is not on the classpath!\n"
                                + "If you want to use auditing please add spring-aspects.jar to the classpath.", element);
            }

            RootBeanDefinition def = new RootBeanDefinition();
            def.setBeanClassName(BEAN_CONFIGURER_ASPECT_CLASS_NAME);
            def.setFactoryMethodName("aspectOf");

            def.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            def.setSource(parserContext.extractSource(element));
            parserContext.registerBeanComponent(new BeanComponentDefinition(def, BEAN_CONFIGURER_ASPECT_BEAN_NAME));
        }
        return null;
    }
}