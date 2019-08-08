package com.Prisec;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class PriceCheckTest {
    private PriceCheck tester;
    private TreeMap<String, TreeMap<Double, String>> prefixMap = new TreeMap<>();
    private HashSet<String> companies = new HashSet<>(Arrays.asList("A", "B", "C"));

    @Before
    public void setUp() {
//        INITIALIZING THE MAP WITH SOME TEST VALUES
        TreeMap<Double, String> innerMap;
        innerMap = new TreeMap<>();
        innerMap.put(0.7, "B");
        innerMap.put(0.5, "C");
        prefixMap.put("125", innerMap);

        innerMap = new TreeMap<>();
        innerMap.put(1.8, "A");
        innerMap.put(1.7, "B");
        prefixMap.put("1", innerMap);

        innerMap = new TreeMap<>();
        innerMap.put(0.3, "A");
        innerMap.put(1.1, "C");
        prefixMap.put("12", innerMap);

        innerMap = new TreeMap<>();
        innerMap.put(2.7, "B");
        innerMap.put(0.6, "C");
        prefixMap.put("49", innerMap);

        innerMap = new TreeMap<>();
        innerMap.put(0.9, "A");
        innerMap.put(1.4, "B");
        innerMap.put(1.5, "C");
        prefixMap.put("99", innerMap);

        innerMap = new TreeMap<>();
        innerMap.put(2.2, "B");
        innerMap.put(0.45, "C");
        prefixMap.put("999", innerMap);

        tester = new PriceCheck(prefixMap);
    }

    @Test
    public void insert() {
        String[] command;
        command = "INSERT A 122 0.99".split(" ");
        tester.insert(command);
        assertEquals(prefixMap.size()+1, tester.getPrefixMap().size());
        command = "INSERT D 122 0.1".split(" ");
        tester.insert(command);
        assertEquals(prefixMap.size()+1, tester.getPrefixMap().size());
        assertEquals(companies.size()+1, tester.getCompanies().size());
    }

    @Test
    public void query() {
        String phone;
        phone = "14996879800";
        assertArrayEquals(new String[]{"B", "1", "1.7"},tester.query(phone));
        phone = "125798093";
        assertArrayEquals(new String[]{"A", "12", "0.3"},tester.query(phone));
        phone = "6662576837";
        assertArrayEquals(new String[]{},tester.query(phone));
        phone = "9992198345";
        assertArrayEquals(new String[]{"C", "999", "0.45"},tester.query(phone));
    }
}