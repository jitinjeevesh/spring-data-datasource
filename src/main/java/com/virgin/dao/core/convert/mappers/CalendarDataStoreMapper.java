package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.convert.DataStoreMapper;

import java.util.Calendar;

public class CalendarDataStoreMapper implements DataStoreMapper {

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		return DateTimeValue.newBuilder(DateTime.copyFrom((Calendar) input));
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		return ((DateTimeValue) input).get().toCalendar();
	}

}
