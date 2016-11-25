package com.spring.datasource.repository.query;

import com.spring.datasource.mapping.DataStorePersistentEntity;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.EntityInstantiator;
import org.springframework.data.convert.EntityInstantiators;
import org.springframework.data.mapping.*;
import org.springframework.data.mapping.PreferredConstructor.Parameter;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.ParameterValueProvider;
import org.springframework.util.Assert;

class DtoInstantiatingConverter implements Converter<Object, Object> {

    private final Class<?> targetType;
    private final MappingContext<? extends PersistentEntity<?, ?>, ? extends PersistentProperty<?>> context;
    private final EntityInstantiator instantiator;

    public DtoInstantiatingConverter(Class<?> dtoType, MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> context,
                                     EntityInstantiators instantiator) {

        Assert.notNull(dtoType, "DTO type must not be null!");
        Assert.notNull(context, "MappingContext must not be null!");
        Assert.notNull(instantiator, "EntityInstantiators must not be null!");

        this.targetType = dtoType;
        this.context = context;
        this.instantiator = instantiator.getInstantiatorFor(context.getPersistentEntity(dtoType));
    }

    @Override
    public Object convert(Object source) {

        if (targetType.isInterface()) {
            return source;
        }

        final PersistentEntity<?, ?> sourceEntity = context.getPersistentEntity(source.getClass());
        final PersistentPropertyAccessor sourceAccessor = sourceEntity.getPropertyAccessor(source);
        final PersistentEntity<?, ?> targetEntity = context.getPersistentEntity(targetType);
        final PreferredConstructor<?, ? extends PersistentProperty<?>> constructor = targetEntity
                .getPersistenceConstructor();

        @SuppressWarnings({"rawtypes", "unchecked"})
        Object dto = instantiator.createInstance(targetEntity, new ParameterValueProvider() {

            @Override
            public Object getParameterValue(Parameter parameter) {
                return sourceAccessor.getProperty(sourceEntity.getPersistentProperty(parameter.getName()));
            }
        });

        final PersistentPropertyAccessor dtoAccessor = targetEntity.getPropertyAccessor(dto);

        targetEntity.doWithProperties(new SimplePropertyHandler() {

            @Override
            public void doWithPersistentProperty(PersistentProperty<?> property) {

                if (constructor.isConstructorParameter(property)) {
                    return;
                }

                dtoAccessor.setProperty(property,
                        sourceAccessor.getProperty(sourceEntity.getPersistentProperty(property.getName())));
            }
        });

        return dto;
    }
}
