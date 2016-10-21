/*
package com.virgin.dao.config;

import com.virgin.dao.DefaultDataStoreEntityManager;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.config.BeanComponentDefinitionBuilder;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class DefaultDataStoreEntityManagerParser extends AbstractBeanDefinitionParser {

    @Override
    protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext)
            throws BeanDefinitionStoreException {

        String id = super.resolveId(element, definition, parserContext);
        return StringUtils.hasText(id) ? id : "defaultDataStoreEntityManager";
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanComponentDefinitionBuilder helper = new BeanComponentDefinitionBuilder(element, parserContext);

        String converterRef = element.getAttribute("converter-ref");
        String dbFactoryRef = element.getAttribute("db-factory-ref");

        BeanDefinitionBuilder mongoTemplateBuilder = BeanDefinitionBuilder.genericBeanDefinition(DefaultDataStoreEntityManager.class);
//        setPropertyValue(mongoTemplateBuilder, element, "write-concern", "writeConcern");

      */
/*  if (StringUtils.hasText(dbFactoryRef)) {
            mongoTemplateBuilder.addConstructorArgReference(dbFactoryRef);
        } else {
            mongoTemplateBuilder.addConstructorArgReference(BeanNames.DB_FACTORY_BEAN_NAME);
        }*//*


     */
/*   if (StringUtils.hasText(converterRef)) {
            mongoTemplateBuilder.addConstructorArgReference(converterRef);
        }*//*


        BeanDefinitionBuilder writeConcernPropertyEditorBuilder = getWriteConcernPropertyEditorBuilder();

        BeanComponentDefinition component = helper.getComponent(writeConcernPropertyEditorBuilder);
        parserContext.registerBeanComponent(component);

        return (AbstractBeanDefinition) helper.getComponentIdButFallback(mongoTemplateBuilder,
                BeanNames.MONGO_TEMPLATE_BEAN_NAME).getBeanDefinition();
    }
}
*/
