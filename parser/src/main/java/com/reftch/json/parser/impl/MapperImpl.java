package com.reftch.json.parser.impl;

import com.reftch.json.parser.MapperException;
import com.reftch.json.parser.Mapper;

public class MapperImpl<T> implements Mapper<T>  {
    private final SerializerImpl<T> serializer;
    private final DeserializerImpl<T> deserializer;
    private final Class<T> clazz;

    public MapperImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.serializer = new SerializerImpl<>();
        this.deserializer = new DeserializerImpl<>();
    }

    @Override
    public String toJson(T object) throws MapperException {
        return serializer.toJson(object);
    }

    @Override
    public T toObject(String json) throws MapperException {
        return deserializer.toObject(json, clazz);
    }
}
