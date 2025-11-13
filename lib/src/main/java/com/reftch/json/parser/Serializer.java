package com.reftch.json.parser;

public interface Serializer<T> {
    /**
     * Converts an object to its JSON representation
     *
     * @param object the object to convert to JSON
     * @return JSON string representation of the object
     * @throws JsonMapperException if serialization fails
     */
    String toJson(T object) throws JsonMapperException;
}
