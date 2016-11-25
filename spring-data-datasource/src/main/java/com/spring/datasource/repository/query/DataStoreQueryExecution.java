package com.spring.datasource.repository.query;

import com.spring.datasource.core.DataStoreOperation;
import com.spring.datasource.core.query.DataStoreQuery;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.EntityInstantiators;
import org.springframework.data.domain.Pageable;

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
        public Object execute(DataStoreQuery dataStoreQuery, Class<?> type, String kindName) {
            return countProjection ? operations.count(dataStoreQuery, type, kindName) : operations.findOne(dataStoreQuery, type, kindName);
        }
    }

    static final class UpdateEntityExecution implements DataStoreQueryExecution {

        private final DataStoreOperation operations;

        public UpdateEntityExecution(DataStoreOperation operations) {
            this.operations = operations;
        }

        @Override
        public Object execute(DataStoreQuery dataStoreQuery, Class<?> type, String kindName) {
            return operations.update(dataStoreQuery, type, kindName);
        }
    }

    static final class CollectionExecution implements DataStoreQueryExecution {

        private final DataStoreOperation operations;
        private final Pageable pageable;

        public CollectionExecution(DataStoreOperation operations, Pageable pageable) {
            this.operations = operations;
            this.pageable = pageable;
        }

        @Override
        public Object execute(DataStoreQuery query, Class<?> type, String kindName) {
            return operations.findAll(query, pageable, type, kindName);
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

        private final DataStoreOperation operations;
        private final EntityInstantiators instantiators;

        public ResultProcessingConverter(DataStoreOperation operations, EntityInstantiators instantiators) {
            this.operations = operations;
            this.instantiators = instantiators;
        }

        @Override
        public Object convert(Object source) {

          /*  Converter<Object, Object> converter = new DtoInstantiatingConverter(returnedType.getReturnedType(),
                    operations.getConverter().getMappingContext(), instantiators);

            return processor.processResult(source, converter);*/
            return source;
        }
    }
}
