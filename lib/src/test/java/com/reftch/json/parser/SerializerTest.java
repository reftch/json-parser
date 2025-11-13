package com.reftch.json.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SerializerTest<T> {

    @Test
    @DisplayName("Should return empty {} for Object")
    void shouldReturnEmptyObject() throws MapperException {
        var mapper = MapperFactory.createMapper(Object.class);
        // Given
        Object object = new Object();
        // When
        var actual = mapper.toJson(object);
        // Then
        var expected = "{}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should correctly map String fields")
    void shouldMapStringFields() throws MapperException {
        // Given
        record Person(String name, String surname) {
        }
        var mapper = MapperFactory.createMapper(Person.class);
        var person = new Person("John", "Smith");
        // When
        var actual = mapper.toJson(person);
        // Then
        var expected = "{\"name\":\"John\",\"surname\":\"Smith\"}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should correctly map Integer fields")
    void shouldMapIntegerFields() throws MapperException {
        // Given
        record Person(String name, String surname, int age) {
        }
        var mapper = MapperFactory.createMapper(Person.class);
        var person = new Person("John", "Smith", 33);
        // When
        var actual = mapper.toJson(person);
        // Then
        var expected = "{\"name\":\"John\",\"surname\":\"Smith\",\"age\":33}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should correctly map Primitive fields")
    void shouldMapPrmitiveFields() throws MapperException {
        // Given
        record Primitive(
                byte b,
                short s,
                int i,
                long l,
                double d,
                float f,
                char c,
                boolean bool) {
        }
        var mapper = MapperFactory.createMapper(Primitive.class);
        var primitive = new Primitive((byte) 1, (short) 2, 3, 4L, 5.0, 6.0f, '7', true);
        // When
        var actual = mapper.toJson(primitive);
        // Then
        var expected = "{\"b\":1,\"s\":2,\"i\":3,\"l\":4,\"d\":5.0,\"f\":6.0,\"c\":\"7\",\"bool\":true}";
        Assertions.assertEquals(expected, actual);
    }

}
