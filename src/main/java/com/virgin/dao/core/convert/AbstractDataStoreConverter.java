package com.virgin.dao.core.convert;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import com.virgin.dao.core.convert.support.IntegerToLongConverter;
import org.springframework.data.convert.EntityInstantiators;

public abstract class AbstractDataStoreConverter implements DataStoreConverter, InitializingBean {

    protected final GenericConversionService conversionService;
    protected EntityInstantiators instantiators = new EntityInstantiators();

    public AbstractDataStoreConverter(GenericConversionService conversionService) {
        this.conversionService = conversionService == null ? new DefaultConversionService() : conversionService;
    }

    public String test() {
        return "This is the test AbstractDataStoreConverter";
    }

    public void afterPropertiesSet() {
        initializeConverters();
    }

    private void initializeConverters() {
        System.out.println(".........................Inside initializeConverters...........");
        System.out.println(conversionService);
//        conversionService.addConverter(DataStoreConverters.IntegerToLongConverter.INSTANCE);
        conversionService.addConverter(new IntegerToLongConverter());
        System.out.println(conversionService);
//        conversions.registerConvertersIn(conversionService);
    }
}
