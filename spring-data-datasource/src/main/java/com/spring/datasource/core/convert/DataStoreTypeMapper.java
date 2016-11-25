package com.spring.datasource.core.convert;

import com.google.cloud.datastore.Entity;
import org.springframework.data.convert.TypeMapper;

public interface DataStoreTypeMapper extends TypeMapper<Entity> {
}
