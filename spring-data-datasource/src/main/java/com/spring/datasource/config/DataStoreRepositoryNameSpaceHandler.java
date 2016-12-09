package com.spring.datasource.config;

import com.spring.datasource.repository.config.DataStoreRepositoryConfigExtension;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.stereotype.Component;

@Component
public class DataStoreRepositoryNameSpaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("template", new DataStoreTemplateParser());
        RepositoryConfigurationExtension extension = new DataStoreRepositoryConfigExtension();
        RepositoryBeanDefinitionParser repositoryBeanDefinitionParser = new RepositoryBeanDefinitionParser(extension);
        registerBeanDefinitionParser("repositories", repositoryBeanDefinitionParser);
    }
}
