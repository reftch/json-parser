package com.reftch.json.parser;

import com.reftch.json.parser.impl.MapperImpl;

public class MapperFactory {
    /**
     * Creates a complete JSON mapper with both serializer and deserializer capabilities
     ¸* 
     * @param <T> the type to map
     * @return a JsonMapper instance that can handle both serialization and deserialization
     */
    public static <T> Mapper<T> createMapper(Class<T> clazz) {
        return new MapperImpl<>(clazz);
    }
}
