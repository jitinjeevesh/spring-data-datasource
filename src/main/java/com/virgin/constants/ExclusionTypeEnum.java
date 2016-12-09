package com.virgin.constants;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ExclusionTypeEnum {

    PURCHASED("PURCHASED"),
    VALIDATED("VALIDATED"),
    ATTRIBUTED("ATTRIBUTED");

    private final String value;

    public String getValue() {
        return value;
    }

    private ExclusionTypeEnum(String value) {
        this.value = value;
    }

    public static List<String> list() {
        return Stream.of(ExclusionTypeEnum.values()).map(ExclusionTypeEnum::getValue).collect(Collectors.toList());
    }

    public static String purchased() {
        return PURCHASED.value;
    }

    public static String validated() {
        return VALIDATED.value;
    }

    public static String attributed() {
        return ATTRIBUTED.value;
    }
}
