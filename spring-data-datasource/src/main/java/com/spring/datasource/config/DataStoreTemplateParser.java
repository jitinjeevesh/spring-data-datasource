package com.spring.datasource.config;

import com.spring.datasource.core.DataStoreTemplate;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.config.BeanComponentDefinitionBuilder;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class DataStoreTemplateParser extends AbstractBeanDefinitionParser {

    @Override
    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext)
            throws BeanDefinitionStoreException {

        System.out.println("...............resolveId ...DataStoreTemplateParser...................................");
        String id = super.resolveId(element, definition, parserContext);
        return StringUtils.hasText(id) ? id : BeanName.DATA_STORE_TEMPLATE_BEAN_NAME;
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        System.out.println("................Inside parsing DataStoreTemplateParser..............................");
        BeanComponentDefinitionBuilder helper = new BeanComponentDefinitionBuilder(element, parserContext);

        BeanDefinitionBuilder datasourceDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DataStoreTemplate.class);

//        mongoTemplateBuilder.addConstructorArgReference(dbFactoryRef);
        return (AbstractBeanDefinition) helper.getComponentIdButFallback(datasourceDefinitionBuilder,
                BeanName.DATA_STORE_TEMPLATE_BEAN_NAME).getBeanDefinition();
    }
}
