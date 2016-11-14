package com.virgin.dao.core.convert;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.exception.DataStoreMappingException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import java.math.BigDecimal;
import java.util.*;

public abstract class DataStoreConverters {

    private DataStoreConverters() {
    }

    public static List<Converter> getConvertersToRegister() {
        List<Converter> converters = new ArrayList<Converter>();
        //To datasource converters
        converters.add(IntegerToLongConverter.INSTANCE);
        converters.add(BooleanToBooleanValueConverter.INSTANCE);
        converters.add(IntegerToLongValueConverter.INSTANCE);
        converters.add(LongToLongValueConverter.INSTANCE);
        converters.add(SortToLongValueConverter.INSTANCE);
        converters.add(StringToStringValueConverter.INSTANCE);
        converters.add(BigDecimalToDoubleValueMapper.INSTANCE);
        converters.add(ByteArrayToBlobValueMapper.INSTANCE);
        converters.add(CalenderToDateTimeValueMapper.INSTANCE);
        converters.add(CharArrayToStringValueMapper.INSTANCE);
        converters.add(CharToStringValueMapper.INSTANCE);
        converters.add(DateToDateTimeValueMapper.INSTANCE);
        converters.add(DoubleToDateTimeValueMapper.INSTANCE);
        converters.add(EnumToStringValueMapper.INSTANCE);
        converters.add(FloatToDoubleValueMapper.INSTANCE);

        //from datasource converters
        converters.add(LongValueToLongConverter.INSTANCE);
        converters.add(BooleanValueToBooleanConverter.INSTANCE);
        converters.add(DoubleValueToBigDecimalConverter.INSTANCE);
        converters.add(BlobValueToByteArrayConverter.INSTANCE);
        converters.add(StringValueToCharArrayConverter.INSTANCE);
        converters.add(DateTimeValueToDateConverter.INSTANCE);
        converters.add(StringValueToCharConverter.INSTANCE);
        converters.add(DateTimeValueToCalendarConverter.INSTANCE);
        converters.add(DoubleValueToFloatConverter.INSTANCE);
        converters.add(DoubleValueToDoubleConverter.INSTANCE);
        converters.add(StringValueToStringConverter.INSTANCE);
        converters.add(LongValueToShortConverter.INSTANCE);
        converters.add(LongValueToIntegerConverter.INSTANCE);
        converters.add(StringValueToListConverter.INSTANCE);
        converters.add(LongValueToObjectConverter.INSTANCE);
        converters.add(BooleanValueToObjectConverter.INSTANCE);
        converters.add(StringValueToObjectConverter.INSTANCE);
        converters.add(DoubleValueToObjectConverter.INSTANCE);
        return converters;
    }

    public static void registerConverters(GenericConversionService conversionService) {
        List<Converter> converters = DataStoreConverters.getConvertersToRegister();
        for (Converter converter : converters) {
            conversionService.addConverter(converter);
        }
    }

    public static enum IntegerToLongConverter implements Converter<Integer, Long> {
        INSTANCE;

        @Override
        public Long convert(Integer id) {
            return id == null ? null : (long) id;
        }
    }

    public static enum BooleanToBooleanValueConverter implements Converter<Boolean, BooleanValue> {
        INSTANCE;

        @Override
        public BooleanValue convert(Boolean source) {
            return BooleanValue.newBuilder(source).build();
        }
    }

    public static enum IntegerToLongValueConverter implements Converter<Integer, LongValue> {
        INSTANCE;

        @Override
        public LongValue convert(Integer source) {
            return LongValue.newBuilder(source).build();
        }
    }

    public static enum LongToLongValueConverter implements Converter<Long, LongValue> {
        INSTANCE;

        @Override
        public LongValue convert(Long source) {
            return LongValue.newBuilder(source).build();
        }
    }

    public static enum SortToLongValueConverter implements Converter<Short, LongValue> {
        INSTANCE;

        @Override
        public LongValue convert(Short source) {
            return LongValue.newBuilder(source).build();
        }
    }

    public static enum StringToStringValueConverter implements Converter<String, StringValue> {
        INSTANCE;

        @Override
        public StringValue convert(String source) {
            return StringValue.newBuilder(source).build();
        }
    }

    public static enum BigDecimalToDoubleValueMapper implements Converter<BigDecimal, DoubleValue> {
        INSTANCE;

        @Override
        public DoubleValue convert(BigDecimal source) {
            return DoubleValue.newBuilder((source).doubleValue()).build();
        }
    }

    public static enum ByteArrayToBlobValueMapper implements Converter<byte[], BlobValue> {
        INSTANCE;

        @Override
        public BlobValue convert(byte[] source) {
            return BlobValue.newBuilder(Blob.copyFrom(source)).build();
        }
    }

    public static enum CalenderToDateTimeValueMapper implements Converter<Calendar, DateTimeValue> {
        INSTANCE;

        @Override
        public DateTimeValue convert(Calendar source) {
            return DateTimeValue.newBuilder(DateTime.copyFrom(source)).build();
        }
    }

    public static enum CharArrayToStringValueMapper implements Converter<char[], StringValue> {
        INSTANCE;

        @Override
        public StringValue convert(char[] source) {
            return StringValue.newBuilder(new String(source)).build();
        }
    }

    public static enum CharToStringValueMapper implements Converter<Character, StringValue> {
        INSTANCE;

        @Override
        public StringValue convert(Character source) {
            return StringValue.newBuilder(String.valueOf(source)).build();
        }
    }

    public static enum DateToDateTimeValueMapper implements Converter<Date, DateTimeValue> {
        INSTANCE;

        @Override
        public DateTimeValue convert(Date source) {
            return DateTimeValue.newBuilder(DateTime.copyFrom(source)).build();
        }
    }

    public static enum DoubleToDateTimeValueMapper implements Converter<Double, DoubleValue> {
        INSTANCE;

        @Override
        public DoubleValue convert(Double source) {
            return DoubleValue.newBuilder(source).build();
        }
    }

    public static enum EnumToStringValueMapper implements Converter<Enum, StringValue> {
        INSTANCE;

        @Override
        public StringValue convert(Enum source) {
            return StringValue.newBuilder(source.toString()).build();
        }
    }

    public static enum FloatToDoubleValueMapper implements Converter<Float, DoubleValue> {
        INSTANCE;

        @Override
        public DoubleValue convert(Float source) {
            return DoubleValue.newBuilder(source).build();
        }
    }

    //..........................From dataSource ..............................

    public static enum LongValueToLongConverter implements Converter<LongValue, Long> {
        INSTANCE;

        @Override
        public Long convert(LongValue source) {
            return source.get();
        }
    }

    public static enum BooleanValueToBooleanConverter implements Converter<BooleanValue, Boolean> {
        INSTANCE;

        @Override
        public Boolean convert(BooleanValue source) {
            return source.get();
        }
    }

    public static enum DoubleValueToBigDecimalConverter implements Converter<DoubleValue, BigDecimal> {
        INSTANCE;

        @Override
        public BigDecimal convert(DoubleValue source) {
            return BigDecimal.valueOf(source.get());
        }
    }

    public static enum BlobValueToByteArrayConverter implements Converter<BlobValue, byte[]> {
        INSTANCE;

        @Override
        public byte[] convert(BlobValue source) {
            return source.get().toByteArray();
        }
    }

    public static enum DateTimeValueToCalendarConverter implements Converter<DateTimeValue, Calendar> {
        INSTANCE;

        @Override
        public Calendar convert(DateTimeValue source) {
            return source.get().toCalendar();
        }
    }

    public static enum StringValueToCharArrayConverter implements Converter<StringValue, char[]> {
        INSTANCE;

        @Override
        public char[] convert(StringValue source) {
            return source.get().toCharArray();
        }
    }

    public static enum StringValueToCharConverter implements Converter<StringValue, Character> {
        INSTANCE;

        @Override
        public Character convert(StringValue source) {
            String str = source.get();
            if (str.length() != 1) {
                throw new DataStoreMappingException(String.format("Unable to convert %s to char", str));
            }
            return str.charAt(0);
        }
    }

    public static enum DateTimeValueToDateConverter implements Converter<DateTimeValue, Date> {
        INSTANCE;

        @Override
        public Date convert(DateTimeValue source) {
            return source.get().toDate();
        }
    }

    public static enum DoubleValueToDoubleConverter implements Converter<DoubleValue, Double> {
        INSTANCE;

        @Override
        public Double convert(DoubleValue source) {
            return source.get();
        }
    }
    //TODO: Implement later
/*
    public static enum StringValueToEnumConverter implements Converter<StringValue, Enum> {
        INSTANCE;

        @Override
        public Enum convert(StringValue source) {
            String value = source.get();
            return Enum.valueOf(enumClass, value);
        }
    }*/

    public static enum DoubleValueToFloatConverter implements Converter<DoubleValue, Float> {
        INSTANCE;

        @Override
        public Float convert(DoubleValue source) {
            Double d = source.get();
            if (d < Float.MIN_VALUE || d > Float.MAX_VALUE) {
                throw new DataStoreMappingException(String.format("Value %f is out of range for float type", d));
            }
            return d.floatValue();
        }
    }

    public static enum LongValueToIntegerConverter implements Converter<LongValue, Integer> {
        INSTANCE;

        @Override
        public Integer convert(LongValue source) {
            Long l = source.get();
            if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
                throw new DataStoreMappingException(String.format("Value %d is out of range for integer type", l));
            }
            return l.intValue();
        }
    }

    public static enum LongValueToShortConverter implements Converter<LongValue, Short> {
        INSTANCE;

        @Override
        public Short convert(LongValue source) {
            Long l = source.get();
            if (l < Short.MIN_VALUE || l > Short.MAX_VALUE) {
                throw new DataStoreMappingException(String.format("Value %d is out of range for short type", l));
            }
            return l.shortValue();
        }
    }

    public static enum StringValueToStringConverter implements Converter<StringValue, String> {
        INSTANCE;

        @Override
        public String convert(StringValue source) {
            return source.get();
        }
    }

    public static enum StringValueToListConverter implements Converter<StringValue, List> {
        INSTANCE;

        @Override
        public List convert(StringValue source) {
            return new ArrayList<>(Arrays.asList(source.get().replaceAll("^\\[|]$", "").replace("\"", "").split(",")));
        }
    }

    public static enum LongValueToObjectConverter implements Converter<LongValue, Object> {
        INSTANCE;

        @Override
        public Object convert(LongValue source) {
            return source.get();
        }
    }

    public static enum StringValueToObjectConverter implements Converter<StringValue, Object> {
        INSTANCE;

        @Override
        public Object convert(StringValue source) {
            return source.get();
        }
    }

    public static enum BooleanValueToObjectConverter implements Converter<BooleanValue, Object> {
        INSTANCE;

        @Override
        public Object convert(BooleanValue source) {
            return source.get();
        }
    }

    public static enum DoubleValueToObjectConverter implements Converter<DoubleValue, Object> {
        INSTANCE;

        @Override
        public Object convert(DoubleValue source) {
            return source.get();
        }
    }
}
