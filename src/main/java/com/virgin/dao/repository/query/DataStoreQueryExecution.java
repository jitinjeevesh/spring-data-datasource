package com.virgin.dao.repository.query;

import com.virgin.dao.core.DataStoreOperation;
import com.virgin.dao.core.query.DataStoreQuery;
import com.virgin.dao.core.query.DynamicQuery;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.EntityInstantiators;
import org.springframework.data.repository.query.ResultProcessor;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.util.ClassUtils;

public interface DataStoreQueryExecution {

    Object execute(DataStoreQuery dynamicQuery, Class<?> type, String collection);

    static final class SingleEntityExecution implements DataStoreQueryExecution {

        private final DataStoreOperation operations;
        private final boolean countProjection;

        public SingleEntityExecution(DataStoreOperation operations, boolean countProjection) {
            this.operations = operations;
            this.countProjection = countProjection;
        }

        @Override
        public Object execute(DataStoreQuery dynamicQuery, Class<?> type, String kindName) {
            return countProjection ? operations.count(dynamicQuery, type, kindName) : operations.findOne(dynamicQuery, type, kindName);
        }
    }

    static final class ResultProcessingExecution implements DataStoreQueryExecution {

        private final DataStoreQueryExecution delegate;
        private final Converter<Object, Object> converter;

        public ResultProcessingExecution(DataStoreQueryExecution delegate, Converter<Object, Object> converter) {
            this.delegate = delegate;
            this.converter = converter;
        }

        @Override
        public Object execute(DataStoreQuery dynamicQuery, Class<?> type, String kindName) {
            return converter.convert(delegate.execute(dynamicQuery, type, kindName));
        }
    }

    static final class ResultProcessingConverter implements Converter<Object, Object> {

        private final ResultProcessor processor;
        private final DataStoreOperation operations;
        private final EntityInstantiators instantiators;

        public ResultProcessingConverter(ResultProcessor processor, DataStoreOperation operations, EntityInstantiators instantiators) {
            this.processor = processor;
            this.operations = operations;
            this.instantiators = instantiators;
        }

        @Override
        public Object convert(Object source) {

            ReturnedType returnedType = processor.getReturnedType();

            if (ClassUtils.isPrimitiveOrWrapper(returnedType.getReturnedType())) {
                System.out.println(".....................................................Inside class utils.................................");
                return source;
            }

          /*  Converter<Object, Object> converter = new DtoInstantiatingConverter(returnedType.getReturnedType(),
                    operations.getConverter().getMappingContext(), instantiators);

            return processor.processResult(source, converter);*/
            return source;
        }
    }
}
