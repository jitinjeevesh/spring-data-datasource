package com.virgin.dao.config;

import com.virgin.dao.repository.config.DataStoreRepositoryConfigExtension;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

public class DataStoreRepositoryNameSpaceHandler extends NamespaceHandlerSupport {

    public void init() {
        System.out.println("..............Inside init.........................................");
        registerBeanDefinitionParser("template", new DataStoreTemplateParser());
        RepositoryConfigurationExtension extension = new DataStoreRepositoryConfigExtension();
        RepositoryBeanDefinitionParser repositoryBeanDefinitionParser = new RepositoryBeanDefinitionParser(extension);
        registerBeanDefinitionParser("repositories", repositoryBeanDefinitionParser);
    }
}
