package com.spring.datasource.repository.query;

import com.spring.datasource.core.convert.DataStoreConverter;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

import java.util.Iterator;

public class DataStoreConvertingParameterAccessor implements DataStoreParameterAccessor {

    private final DataStoreConverter dataStoreConverter;
    private final DataStoreParameterAccessor dataStoreParameterAccessor;

    public DataStoreConvertingParameterAccessor(DataStoreConverter converter, DataStoreParameterAccessor dataStoreParameterAccessor) {
        Assert.notNull(converter, "DataStoreConverter must not be null");
        Assert.notNull(dataStoreParameterAccessor, "DataStoreParameterAccessor must not be null");
        this.dataStoreConverter = converter;
        this.dataStoreParameterAccessor = dataStoreParameterAccessor;
    }

    @Override
    public Pageable getPageable() {
        return dataStoreParameterAccessor.getPageable();
    }

    @Override
    public Sort getSort() {
        return dataStoreParameterAccessor.getSort();
    }

    @Override
    public Object getBindableValue(int index) {
        return getConvertedValue(dataStoreParameterAccessor.getBindableValue(index), null);
    }

    @Override
    public boolean hasBindableNullValue() {
        return dataStoreParameterAccessor.hasBindableNullValue();
    }

    @Override
    public Iterator<Object> iterator() {
        return new ConvertingIterator(dataStoreParameterAccessor.iterator());
    }

    private Object getConvertedValue(Object value, TypeInformation<?> typeInformation) {
        return dataStoreConverter.convertToDataStoreType(value, typeInformation == null ? null : typeInformation.getActualType());
    }

    @Override
    public Object[] getValues() {
        return dataStoreParameterAccessor.getValues();
    }

    private class ConvertingIterator implements PotentiallyConvertingIterator {

        private final Iterator<Object> delegate;

        public ConvertingIterator(Iterator<Object> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object nextConverted(DataStorePersistentProperty property) {
            Object next = next();

            if (next == null) {
                return null;
            }
            //This is not implemented in DataStore
            if (property.isAssociation()) {
                return null;
            }
            return getConvertedValue(next, property.getTypeInformation());
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        public void remove() {
            delegate.remove();
        }

        @Override
        public Object next() {
            return delegate.next();
        }
    }


    public interface PotentiallyConvertingIterator extends Iterator<Object> {

        /**
         * Returns the next element which has already been converted.
         *
         * @return Object next element.
         */
        Object nextConverted(DataStorePersistentProperty property);
    }
}
