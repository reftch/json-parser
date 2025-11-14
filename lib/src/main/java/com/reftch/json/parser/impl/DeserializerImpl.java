package com.reftch.json.parser.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;

import com.reftch.json.parser.MapperException;

public class DeserializerImpl<T> extends AbstractDeserializer<T>  {

    T toObject(String json, Class<T> clazz) throws MapperException {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }

        try {
            return switch (clazz) {
                case Class<T> c when c.isRecord() -> toObjectForRecord(json, clazz);
                default -> toObjectForRegularClass(json, clazz);
            };
        } catch (Exception e) {
            throw new MapperException("Error during converting data", e);
        }
    }

    private T toObjectForRecord(String json, Class<T> recordClass) throws MapperException {
        try {
            // Find the canonical constructor
            var constructor = findRecordConstructor(recordClass);

            // Parse JSON to extract field values
            var fieldValues = parseFieldValues(json);

            // Get parameter types and values
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] paramValues = new Object[paramTypes.length];

            RecordComponent[] rc = recordClass.getRecordComponents();
            for (int i = 0; i < paramTypes.length; i++) {
                var fieldName = rc[i].getName();
                var valueStr = fieldValues.get(fieldName);
                paramValues[i] = valueStr != null ? convertValue(valueStr, paramTypes[i]) : null;
            }

            return constructor.newInstance(paramValues);
        } catch (IllegalAccessException | InstantiationException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new MapperException(e.getLocalizedMessage());
        }
    }

    private T toObjectForRegularClass(String json, Class<T> clazz) throws MapperException {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();

            // Parse JSON to extract field values
            var fieldValues = parseFieldValues(json);
            for (var entry : fieldValues.entrySet()) {
                var field = clazz.getDeclaredField(entry.getKey());
                if (field != null) {
                    field.setAccessible(true);
                    var valueStr = fieldValues.get(field.getName());
                    field.set(object, convertValue(valueStr, field.getType()));
                    field.setAccessible(false);
                }
            }
            return object;
        } catch (IllegalAccessException | InstantiationException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            throw new MapperException(e.getLocalizedMessage());
        }
    }

}
