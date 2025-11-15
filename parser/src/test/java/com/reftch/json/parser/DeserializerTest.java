package com.reftch.json.parser;

import java.util.Arrays;
import java.util.List;

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

    @Test
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

    public static class Object2 {
        int i;
        int[] arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with int array")
    void shouldJsonToObject2() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[1,2,3]}";

        var mapper = MapperFactory.createMapper(Object2.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object2();
        expected.i = 1;
        expected.arr = new int[] { 1, 2, 3 };
        Assertions.assertEquals(expected.i, actual.i);
        System.out.println(actual);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(expected.arr[0], actual.arr[0]);
        Assertions.assertEquals(expected.arr[1], actual.arr[1]);
        Assertions.assertEquals(expected.arr[2], actual.arr[2]);
    }

    public static class Object3 {
        int i;
        String[] arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with string array")
    void shouldJsonToObject3() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[\"1\",\"2\",\"3\"]}";

        var mapper = MapperFactory.createMapper(Object3.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object3();
        expected.i = 1;
        expected.arr = new String[] { "1", "2", "3" };
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(expected.arr[0], actual.arr[0]);
        Assertions.assertEquals(expected.arr[1], actual.arr[1]);
        Assertions.assertEquals(expected.arr[2], actual.arr[2]);
    }

    public static class Object4 {
        int i;
        double[] arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with double array")
    void shouldJsonToObject4() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[1.1,2.23]}";

        var mapper = MapperFactory.createMapper(Object4.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object4();

        expected.i = 1;
        expected.arr = new double[] { 1.1, 2.23 };
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(expected.arr[0], actual.arr[0]);
        Assertions.assertEquals(expected.arr[1], actual.arr[1]);
    }

    public static class Object5 {
        int i;
        boolean[] arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with boolean array")
    void shouldJsonToObject5() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[true,false]}";

        var mapper = MapperFactory.createMapper(Object5.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object5();

        expected.i = 1;
        expected.arr = new boolean[] { true, false };
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(expected.arr[0], actual.arr[0]);
        Assertions.assertEquals(expected.arr[1], actual.arr[1]);
    }

    public static class Object6 {
        int i;
        float[] arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with float array")
    void shouldJsonToObject6() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[1.1f,3.3f]}";

        var mapper = MapperFactory.createMapper(Object6.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object6();

        expected.i = 1;
        expected.arr = new float[] { 1.1f, 3.3f };
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(expected.arr[0], actual.arr[0]);
        Assertions.assertEquals(expected.arr[1], actual.arr[1]);
    }

    public static class Object7 {
        int i;
        byte[] arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with byte array")
    void shouldJsonToObject7() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[1,2,3]}";

        var mapper = MapperFactory.createMapper(Object7.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object7();

        expected.i = 1;
        expected.arr = new byte[] { 1, 2, 3 };
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(expected.arr[0], actual.arr[0]);
        Assertions.assertEquals(expected.arr[1], actual.arr[1]);
        Assertions.assertEquals(expected.arr[2], actual.arr[2]);
    }

    public static class Object8 {
        int i;
        char[] arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with char array")
    void shouldJsonToObject8() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[\"1\",\"2\"]}";

        var mapper = MapperFactory.createMapper(Object8.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object8();

        expected.i = 1;
        expected.arr = new char[] { '1', '2' };
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(expected.arr[0], actual.arr[0]);
        Assertions.assertEquals(expected.arr[1], actual.arr[1]);
    }

    @Test
    @DisplayName("Should correctly map json to class object with empty array")
    void shouldJsonToObject9() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[]}";

        var mapper = MapperFactory.createMapper(Object8.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object8();

        expected.i = 1;
        expected.arr = new char[] {};
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.length, actual.arr.length);
        Assertions.assertEquals(0, actual.arr.length);
    }

    public static class Object10 {
        int i;
        List<String> arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with list with strings")
    void shouldJsonToObject10() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[\"1\",\"2\",\"3\"]}";

        var mapper = MapperFactory.createMapper(Object10.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object10();
        expected.i = 1;
        expected.arr = Arrays.asList("1", "2", "3");
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.size(), actual.arr.size());
        Assertions.assertEquals(expected.arr.get(0), actual.arr.get(0));
        Assertions.assertEquals(expected.arr.get(1), actual.arr.get(1));
        Assertions.assertEquals(expected.arr.get(2), actual.arr.get(2));
    }

    public static class Object11 {
        int i;
        List<Integer> arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with list with integers")
    void shouldJsonToObject11() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[1,2,3]}";

        var mapper = MapperFactory.createMapper(Object11.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object11();
        expected.i = 1;
        expected.arr = Arrays.asList(1, 2, 3);
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.size(), actual.arr.size());
        Assertions.assertEquals(expected.arr.get(0), actual.arr.get(0));
        Assertions.assertEquals(expected.arr.get(1), actual.arr.get(1));
        Assertions.assertEquals(expected.arr.get(2), actual.arr.get(2));
    }

    public static class Object12 {
        int i;
        List<Boolean> arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with list with boolean")
    void shouldJsonToObject12() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[true,false,true]}";

        var mapper = MapperFactory.createMapper(Object12.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object12();
        expected.i = 1;
        expected.arr = Arrays.asList(true, false, true);
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.size(), actual.arr.size());
        Assertions.assertEquals(expected.arr.get(0), actual.arr.get(0));
        Assertions.assertEquals(expected.arr.get(1), actual.arr.get(1));
        Assertions.assertEquals(expected.arr.get(2), actual.arr.get(2));
    }

    public static class Object13 {
        int i;
        List<Double> arr;
    }

    @Test
    @DisplayName("Should correctly map json to class object with list with double")
    void shouldJsonToObject13() throws Exception {
        // Given JSON string
        var json = "{\"i\":1,\"arr\":[1.2,2.35,3.1415]}";

        var mapper = MapperFactory.createMapper(Object13.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object13();
        expected.i = 1;
        expected.arr = Arrays.asList(1.2, 2.35, 3.1415);
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertEquals(expected.arr.size(), actual.arr.size());
        Assertions.assertEquals(expected.arr.get(0), actual.arr.get(0));
        Assertions.assertEquals(expected.arr.get(1), actual.arr.get(1));
        Assertions.assertEquals(expected.arr.get(2), actual.arr.get(2));
    }

    @Test
    @DisplayName("Should correctly map json to class object with null object")
    void shouldJsonToObject14() throws Exception {
        // Given JSON string
        var json = "{\"i\":1}";

        var mapper = MapperFactory.createMapper(Object13.class);

        // When
        var actual = mapper.toObject(json);
        // Then
        var expected = new Object13();
        expected.i = 1;
        Assertions.assertEquals(expected.i, actual.i);
        Assertions.assertNull(actual.arr);
    }

}
