package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import com.virgin.dao.core.exception.MappingException;

import java.math.BigDecimal;

public class DecimalDataStoreMapper implements DataStoreMapper {

    private static final int MAX_PRECISION = 18;

    private final int precision;

    private final int scale;

    public DecimalDataStoreMapper(int precision, int scale) {
        if (precision <= 0 || precision > MAX_PRECISION) {
            throw new IllegalArgumentException(String.format("precision must be between %d and %d", 1, MAX_PRECISION));
        }
        if (scale < 0 || scale > precision) {
            throw new IllegalArgumentException(
                    String.format("scale must be between %d and precision (%d)", 0, precision));
        }
        this.precision = precision;
        this.scale = scale;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        BigDecimal original = null;
        try {
            original = (BigDecimal) input;
            // Ensure we fix the scale, yes we don't round anything up or down.
            // It is the responsibility of the API user. We only set the scale
            // to zero fill any unused fractional digits, or remove any zeroes
            // from the end. This helps us validate the precision and ensure
            // that the number is within the expected bounds. Any better ways?
            BigDecimal n = original.setScale(scale);
            if (n.precision() > precision) {
                throw new MappingException(String.format("Value %s is not a valid Decimal(%d, %d)",
                        original.toPlainString(), precision, scale));
            }
            n = n.movePointRight(scale);
            return LongValue.newBuilder(n.longValueExact());
        } catch (MappingException exp) {
            throw exp;
        } catch (Exception exp) {
            Object arg1 = original == null ? input : original.toPlainString();
            throw new MappingException(String.format("Value %s is not a valid Decimal(%d, %d)", arg1, precision, scale),
                    exp);
        }
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        try {
            BigDecimal n = new BigDecimal(((LongValue) input).get());
            if (n.precision() > precision) {
                throw new MappingException(
                        String.format("Cannot map %s to Decimal(%d, %d). Value is larger than the defined precision. ",
                                n.toPlainString(), precision, scale));
            }
            return n.movePointLeft(scale);
        } catch (MappingException exp) {
            throw exp;
        } catch (Exception exp) {
            throw new MappingException(String.format("Cannot map %s to Decimal(%d, %d", input, precision, scale), exp);
        }
    }

}
