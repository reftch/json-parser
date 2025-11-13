package com.reftch.json.parser;

/**
 * A generic interface for JSON mapping operations that combines both serialization and deserialization capabilities.
 * 
 * The JsonMapper interface provides a unified contract for converting Java objects to JSON and vice versa,
 * making it easy to implement custom JSON mapping logic for specific types.
 * 
 * @param <T> the type of object that this mapper can serialize and deserialize
 */
public interface Mapper<T> extends Serializer<T>, Deserializer<T>  {

}