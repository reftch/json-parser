package com.reftch.json.parser;

public sealed interface Deserializer<T> permits Mapper {
    /**
     * Converts a JSON string back to an object of the specified class
     *
     * @param json  the JSON string to convert
     * @param clazz the class of the object to create
     * @param <T>   the type of the object to create
     * @return an instance of clazz populated with data from json
     * @throws MapperException if deserialization fails
     */
    T toObject(String json) throws MapperException;
}
