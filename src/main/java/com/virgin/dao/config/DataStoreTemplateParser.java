package com.virgin.dao.config;

import com.virgin.dao.DataStoreTemplate;
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

        String id = super.resolveId(element, definition, parserContext);
        return StringUtils.hasText(id) ? id : BeanName.DATA_STORE_TEMPLATE_BEAN_NAME;
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanComponentDefinitionBuilder helper = new BeanComponentDefinitionBuilder(element, parserContext);

        BeanDefinitionBuilder mongoTemplateBuilder = BeanDefinitionBuilder.genericBeanDefinition(DataStoreTemplate.class);

//        mongoTemplateBuilder.addConstructorArgReference(dbFactoryRef);
        return (AbstractBeanDefinition) helper.getComponentIdButFallback(mongoTemplateBuilder,
                BeanName.DATA_STORE_TEMPLATE_BEAN_NAME).getBeanDefinition();
    }
}
