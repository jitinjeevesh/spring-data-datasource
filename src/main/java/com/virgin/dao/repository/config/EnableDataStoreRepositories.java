package com.virgin.dao.repository.config;

import com.virgin.dao.repository.support.DataStoreRepositoryFactoryBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DataStoreRepositoriesRegistrar.class)
public @interface EnableDataStoreRepositories {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Filter[] includeFilters() default {};

    Filter[] excludeFilters() default {};

    String repositoryImplementationPostfix() default "Impl";

    String namedQueriesLocation() default "";

    Key queryLookupStrategy() default Key.CREATE_IF_NOT_FOUND;

    Class<?> repositoryFactoryBeanClass() default DataStoreRepositoryFactoryBean.class;

    Class<?> repositoryBaseClass() default DefaultRepositoryBaseClass.class;

    String dataStoreTemplateRef() default "dataStoreTemplate";
}
