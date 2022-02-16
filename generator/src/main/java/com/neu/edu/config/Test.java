package com.neu.edu.config;

import java.util.Properties;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Properties property = System.getProperties();
        Set<Object> key = property.keySet();
        for (Object k :key){
            System.out.println(k.toString()+"   =============="+property.get(k.toString()));
        }
    }
}
