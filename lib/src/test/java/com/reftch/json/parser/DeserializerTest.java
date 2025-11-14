package com.reftch.json.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeserializerTest {

    public record Person(String name, String surname) {
    }

    @Test
    @DisplayName("Should correctly map json to record")
    void shouldJsonToRecord() throws Exception {
        // Given
        var json = "{\"name\":\"John\",\"surname\":\"Smith\"}";
        var mapper = MapperFactory.createMapper(Person.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Person("John", "Smith");
        Assertions.assertEquals(expected, actual);
    }

    public static class PersonObject {
        String name;
        String surname;
    }

    @Test
    @DisplayName("Should correctly map json to class object")
    void shouldJsonToPersonObject() throws Exception {
        // Given JSON string
        var json = "{\"name\":\"John\",\"surname\":\"Smith\"}";

        var mapper = MapperFactory.createMapper(PersonObject.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new PersonObject();
        expected.name = "John";
        expected.surname = "Smith";
        Assertions.assertEquals(expected.name, actual.name);
        Assertions.assertEquals(expected.surname, actual.surname);
    }

    @Test
    @DisplayName("Should correctly map json with space to class object")
    void shouldJsonWithSpaceToPersonObject() throws Exception {
        // Given JSON string
        var json = "{\"name  \":\"John\",\"  surname \":\"Smith\"}";

        var mapper = MapperFactory.createMapper(PersonObject.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new PersonObject();
        expected.name = "John";
        expected.surname = "Smith";
        Assertions.assertEquals(expected.name, actual.name);
        Assertions.assertEquals(expected.surname, actual.surname);
    }

    public static class TestObject {
        byte b;
        short s;
        int i;
        long l;
        double d;
        float f;
        char c;
        boolean bool;
    }

    // @Test
    @DisplayName("Should correctly map json to class object")
    void shouldJsonToTestObject() throws Exception {
        // Given JSON string
        var json = "{\"b\":1,\"s\":2,\"i\":3,\"l\":4,\"d\":5.0,\"f\":6.0,\"c\":\"7\",\"bool\":true}";

        var mapper = MapperFactory.createMapper(TestObject.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new TestObject();
        expected.b = (byte) 1;
        expected.s = (short) 2;
        expected.i = 3;
        expected.l = 4L;
        expected.d = 5.0;
        expected.f = 6.0f;
        expected.c = '7';
        expected.bool = true;

        Assertions.assertEquals(expected.b, actual.b);
        Assertions.assertEquals(expected.s, actual.s);
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.l, actual.l);
        Assertions.assertEquals(expected.d, actual.d);
        Assertions.assertEquals(expected.f, actual.f);
        Assertions.assertEquals(expected.c, actual.c);
        Assertions.assertEquals(expected.bool, actual.bool);
    }

}
