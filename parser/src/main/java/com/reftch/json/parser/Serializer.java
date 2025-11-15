package com.reftch.json.parser;

public sealed interface Serializer<T> permits Mapper {
    /**
     * Converts an object to its JSON representation
     *
     * @param object the object to convert to JSON
     * @return JSON string representation of the object
     * @throws MapperException if serialization fails
     */
    String toJson(T object) throws MapperException;
}
