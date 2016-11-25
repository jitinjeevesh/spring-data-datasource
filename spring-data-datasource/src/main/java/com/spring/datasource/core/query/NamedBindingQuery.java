package com.spring.datasource.core.query;

import com.spring.datasource.repository.query.DataStoreConvertingParameterAccessor;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamedBindingQuery implements StringQuery {

    private final Map<String, Object> criteria = new LinkedHashMap<String, Object>();
    private final String query;
    private final DataStoreConvertingParameterAccessor accessor;

    public NamedBindingQuery(String query, DataStoreConvertingParameterAccessor accessor) {
        this.accessor = accessor;
        this.query = generateQuery(query);
    }

    //TODO: Datatype conversion is pending. e.g. enum and embedded object.
    private String generateQuery(String query) {
        Matcher matcher = Pattern.compile("@\\s*(\\w+)").matcher(query);
        int i = 0;
        try {
            while (matcher.find()) {
                if (String.class.isAssignableFrom(accessor.getValues()[i].getClass())) {
                    criteria.put(matcher.group(), "'" + accessor.getValues()[i] + "'");
                } else {
                    criteria.put(matcher.group(), accessor.getValues()[i]);
                }
                i++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidDataAccessApiUsageException(String.format("No parameter value found for the argument %s", matcher.group(1)));
        }
        return createQuery(query);
    }

    public String createQuery(String query) {
        Matcher matcher = Pattern.compile("@\\s*(\\w+)").matcher(query);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, String.valueOf(criteria.get(matcher.group())));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public String getQuery() {
        return this.query;
    }
}
