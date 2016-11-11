package com.virgin.dao.core.convert;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.convert.EntityInstantiators;

public abstract class AbstractDataStoreConverter implements DataStoreConverter, InitializingBean {

    protected final GenericConversionService conversionService;
    protected EntityInstantiators instantiators = new EntityInstantiators();
    protected DataStoreMapperFactory dataStoreMapperFactory;

    public AbstractDataStoreConverter(GenericConversionService conversionService) {
        this.conversionService = conversionService == null ? new DefaultConversionService() : conversionService;
        this.dataStoreMapperFactory = DataStoreMapperFactory.getInstance();
    }

    public void afterPropertiesSet() {
        initializeConverters();
    }

    private void initializeConverters() {
//        conversionService.addConverter(DataStoreConverters.IntegerToLongConverter.INSTANCE);
//        conversionService.addConverter(DataStoreConverters.BooleanToBooleanValueConverter.INSTANCE);
//        conversionService.addConverter(DataStoreConverters.IntegerToLongValueConverter.INSTANCE);
//        conversionService.addConverter(DataStoreConverters.LongToLongValueConverter.INSTANCE);
//        conversionService.addConverter(DataStoreConverters.StringToStringValueConverter.INSTANCE);
        DataStoreConverters.registerConverters(conversionService);
    }
}
