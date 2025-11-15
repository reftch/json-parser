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

    @Test
    @DisplayName("Should return json for Object with int array")
    void shouldMapIntArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                int[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new int[] { 1, 2 });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[1,2]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with long array")
    void shouldMapLongArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                long[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new long[] { 1L, 2L });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[1,2]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with double array")
    void shouldMapDoubleArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                double[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new double[] { 1.0, 2.0 });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[1.0,2.0]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with float array")
    void shouldMapFloatArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                float[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new float[] { 1.05f, 2.0005f });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[1.05,2.0005]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with byte array")
    void shouldMapByteArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                byte[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new byte[] { 1, 2, 3 });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[1,2,3]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with boolean array")
    void shouldMapBooleanArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                boolean[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new boolean[] { true, false, true });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[true,false,true]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with char array")
    void shouldMapCharArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                char[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new char[] { '1', '2', '3' });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[\"1\",\"2\",\"3\"]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with short array")
    void shouldMapShortArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                short[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new short[] { 1, 2, 3 });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[1,2,3]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with string array")
    void shouldMapStringArrayObject() throws MapperException {
        // Given
        record ArrayRecord(
                int i,
                String[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new String[] { "1", "2", "3" });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[\"1\",\"2\",\"3\"]}";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return json for Object with Object array")
    void shouldMapObjectArrayObject() throws MapperException {
        // Given
        record Record2(
                String name,
                int[] numbers) {
            @Override
            public String toString() {
                return "Record2 []";
            }
        }
        ;

        record ArrayRecord(
                int i,
                Record2[] arr) {
        }
        var mapper = MapperFactory.createMapper(ArrayRecord.class);
        var arrayRecord = new ArrayRecord(1, new Record2[] {
                new Record2("test1", new int[] { 1, 2, 3 }),
                new Record2("test2", new int[] { 4, 5, 6 })
        });
        // When
        var actual = mapper.toJson(arrayRecord);
        // Then
        var expected = "{\"i\":1,\"arr\":[\"Record2 []\",\"Record2 []\"]}";
        Assertions.assertEquals(expected, actual);
    }

}
