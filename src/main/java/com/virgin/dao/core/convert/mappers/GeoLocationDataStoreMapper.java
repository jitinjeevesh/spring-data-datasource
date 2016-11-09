package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.*;
import com.jmethods.catatumbo.GeoLocation;
import com.virgin.dao.core.convert.DataStoreMapper;

public class GeoLocationDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        GeoLocation geoLocation = (GeoLocation) input;
        return LatLngValue.newBuilder(LatLng.of(geoLocation.getLatitude(), geoLocation.getLongitude()));
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        LatLngValue value = (LatLngValue) input;
        LatLng coordinates = value.get();
        return new GeoLocation(coordinates.getLatitude(), coordinates.getLongitude());
    }

}
