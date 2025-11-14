package com.reftch.json.parser.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reftch.json.parser.MapperException;

abstract class AbstractDeserializer<T> {

    @SuppressWarnings("unchecked")
    protected Constructor<T> findRecordConstructor(Class<?> recordClass) {
        if (!recordClass.isRecord()) {
            throw new IllegalArgumentException("Class is not a record: " + recordClass.getName());
        }

        // Get all record components
        RecordComponent[] components = recordClass.getRecordComponents();
        Class<?>[] paramTypes = Arrays.stream(components)
                .map(RecordComponent::getType)
                .toArray(Class<?>[]::new);

        // Find the constructor with matching parameter types
        try {
            return (Constructor<T>) recordClass.getDeclaredConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No canonical constructor found for record: " +
                    recordClass.getName(), e);
        }
    }

    protected Map<String, String> parseFieldValues(String json) {
        Map<String, String> fieldValues = new HashMap<>();

        if (json.isEmpty() || !json.startsWith("{") || !json.endsWith("}")) {
            return fieldValues;
        }

        // Remove outer braces
        var content = json.substring(1, json.length() - 1).trim();
        if (content.isEmpty()) {
            return fieldValues;
        }

        // Split by commas, but be careful with nested objects
        var pairs = splitJsonFields(json);

        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty())
                continue;

            int colonIndex = pair.indexOf(':');
            if (colonIndex > 0) {
                var key = pair.substring(0, colonIndex).trim();
                // Remove quotes from key
                key = key.replaceAll("^\"|\"$", "");

                var valueStr = pair.substring(colonIndex + 1).trim();
                fieldValues.put(key, valueStr);
            }
        }

        return fieldValues;
    }

    private String[] splitJsonFields(String json) {
        // Trim outer braces if present
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
        }

        List<String> fields = new ArrayList<>();
        var start = 0;
        var braceCount = 0;
        var bracketCount = 0;
        var inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            var c = json.charAt(i);

            // Handle quote toggling
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                switch (c) {
                    case '{' -> braceCount++;
                    case '}' -> braceCount--;
                    case '[' -> bracketCount++;
                    case ']' -> bracketCount--;
                    case ',' -> {
                        if (braceCount == 0 && bracketCount == 0) {
                            fields.add(json.substring(start, i).trim());
                            start = i + 1;
                        }
                    }
                }
            }
        }

        if (start < json.length()) {
            fields.add(json.substring(start).trim());
        }

        return fields.toArray(String[]::new);
    }

    protected Object convertValue(String valueStr, Class<?> targetType) throws MapperException {
        if (valueStr.equals("null")) {
            return null;
        }

        // Remove surrounding quotes if present
        if (valueStr.startsWith("\"") && valueStr.endsWith("\"")) {
            valueStr = valueStr.substring(1, valueStr.length() - 1);
            valueStr = unescapeJsonString(valueStr);
        }

        return switch (targetType) {
            case Class<?> t when t == String.class -> valueStr;
            case Class<?> t when t == int.class || t == Integer.class -> Integer.parseInt(valueStr);
            case Class<?> t when t == long.class || t == Long.class -> Long.parseLong(valueStr);
            case Class<?> t when t == double.class || t == Double.class -> Double.parseDouble(valueStr);
            case Class<?> t when t == float.class || t == Float.class -> Float.parseFloat(valueStr);
            case Class<?> t when t == boolean.class || t == Boolean.class -> Boolean.parseBoolean(valueStr);
            case Class<?> t when t == short.class || t == Short.class -> Short.parseShort(valueStr);
            case Class<?> t when t == byte.class || t == Byte.class -> Byte.parseByte(valueStr);
            case Class<?> t when t == char.class || t == Character.class -> valueStr.charAt(0);
            default -> {
                // For complex objects, you might want to parse recursively
                yield valueStr;
            }
        };
    }

    private String unescapeJsonString(String str) {
        return str.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }

}