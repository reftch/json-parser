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

    protected Map<String, String> parseRecordFieldValues(String json) {
        Map<String, String> fieldValues = new HashMap<>();

        if (json.isEmpty() || !json.startsWith("{") || !json.endsWith("}")) {
            return fieldValues;
        }

        // Remove outer braces
        String content = json.substring(1, json.length() - 1).trim();
        if (content.isEmpty()) {
            return fieldValues;
        }

        // Split by commas, but be careful with nested objects
        String[] pairs = splitJsonFields(json);

        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty())
                continue;

            int colonIndex = pair.indexOf(':');
            if (colonIndex > 0) {
                String key = pair.substring(0, colonIndex).trim();
                // Remove quotes from key
                key = key.replaceAll("^\"|\"$", "");

                String valueStr = pair.substring(colonIndex + 1).trim();
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
        int start = 0;
        int braceCount = 0;
        int bracketCount = 0;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }
            if (!inQuotes) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                } else if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                } else if (c == ',' && braceCount == 0 && bracketCount == 0) {
                    fields.add(json.substring(start, i).trim());
                    start = i + 1;
                }
            }
        }
        if (start < json.length()) {
            fields.add(json.substring(start).trim());
        }
        return fields.toArray(new String[0]);
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

        if (targetType == String.class) {
            return valueStr;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(valueStr);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(valueStr);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(valueStr);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(valueStr);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(valueStr);
        } else if (targetType == short.class || targetType == Short.class) {
            return Short.parseShort(valueStr);
        } else if (targetType == byte.class || targetType == Byte.class) {
            return Byte.parseByte(valueStr);
        } else if (targetType == char.class || targetType == Character.class) {
            return valueStr.charAt(0);
        }

        // For complex objects, you might want to parse recursively
        return valueStr;
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