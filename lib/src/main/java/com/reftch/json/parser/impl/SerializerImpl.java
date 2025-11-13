package com.reftch.json.parser.impl;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.reftch.json.parser.MapperException;

public class SerializerImpl<T> {

    String toJson(T object) throws MapperException {
        if (object == null) {
            return "{}";
        }

        var clazz = object.getClass();
        var fields = clazz.getDeclaredFields();

        return Arrays.stream(fields)
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        var name = field.getName();
                        var value = field.get(object);
                        field.setAccessible(false);

                        return "\"" + name + "\":" + formatValue(value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining(",", "{", "}"));

    }

    private String formatValue(Object value) {
        return switch (value) {
            case null -> "null";
            case String s -> "\"" + escapeJsonString(s) + "\"";
            case Number n -> n.toString();
            case Boolean b -> b.toString();
            default -> "\"" + value.toString() + "\"";
        };
    }

    private String escapeJsonString(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

}
