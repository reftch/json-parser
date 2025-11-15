package com.reftch.json.parser.impl;

import java.lang.reflect.Array;
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
            case Object[] array -> formatArray(array);
            case int[] array -> formatPrimitiveArray(array);
            case long[] array -> formatPrimitiveArray(array);
            case double[] array -> formatPrimitiveArray(array);
            case float[] array -> formatPrimitiveArray(array);
            case boolean[] array -> formatPrimitiveArray(array);
            case byte[] array -> formatPrimitiveArray(array);
            case short[] array -> formatPrimitiveArray(array);
            case char[] array -> formatPrimitiveArray(array);
            default -> "\"" + value.toString() + "\"";
        };
    }

    private String formatArray(Object[] array) {
        return Arrays.stream(array)
                .map(this::formatValue)
                .collect(Collectors.joining(",", "[", "]"));
    }

    private String formatPrimitiveArray(Object array) {
        int length = Array.getLength(array);
        StringBuilder sb = new StringBuilder("[");
        
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            sb.append(formatValue(element));
            if (i < length - 1) {
                sb.append(",");
            }
        }
        
        sb.append("]");
        return sb.toString();
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
