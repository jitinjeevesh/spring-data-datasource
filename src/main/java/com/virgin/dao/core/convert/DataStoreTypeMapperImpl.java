package com.virgin.dao.core.convert;

import com.google.cloud.datastore.Entity;
import org.springframework.data.convert.DefaultTypeMapper;
import org.springframework.data.convert.SimpleTypeInformationMapper;
import org.springframework.data.convert.TypeAliasAccessor;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.MappingContext;

import java.util.Arrays;
import java.util.List;

public class DataStoreTypeMapperImpl extends DefaultTypeMapper<Entity> implements DataStoreTypeMapper {

    public static final String DEFAULT_TYPE_KEY = "_class";
    private final TypeAliasAccessor<Entity> accessor;
    private final String typeKey;

    public DataStoreTypeMapperImpl() {
        this(DEFAULT_TYPE_KEY);
    }

    public DataStoreTypeMapperImpl(String typeKey) {
        this(typeKey, Arrays.asList(new SimpleTypeInformationMapper()));
    }

    public DataStoreTypeMapperImpl(String typeKey, MappingContext<? extends PersistentEntity<?, ?>, ?> mappingContext) {
        this(typeKey, new EntityTypeAliasAccessor(typeKey), mappingContext, Arrays.asList(new SimpleTypeInformationMapper()));
    }

    public DataStoreTypeMapperImpl(String typeKey, List<? extends TypeInformationMapper> mappers) {
        this(typeKey, new EntityTypeAliasAccessor(typeKey), null, mappers);
    }

    private DataStoreTypeMapperImpl(String typeKey, TypeAliasAccessor<Entity> accessor, MappingContext<? extends PersistentEntity<?, ?>, ?> mappingContext,
                                    List<? extends TypeInformationMapper> mappers) {

        super(accessor, mappingContext, mappers);

        this.typeKey = typeKey;
        this.accessor = accessor;
    }


    public static final class EntityTypeAliasAccessor implements TypeAliasAccessor<Entity> {
        private final String typeKey;

        public EntityTypeAliasAccessor(String typeKey) {
            this.typeKey = typeKey;
        }

        public Object readAliasFrom(Entity source) {
            if (source instanceof Entity) {
                return null;
            }
            return source.getKey(typeKey);
        }

        public void writeTypeTo(Entity sink, Object alias) {
            //TODO: Implementation of write
        }
    }
}
