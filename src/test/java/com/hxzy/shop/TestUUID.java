package com.hxzy.shop;

import org.junit.Test;

import java.util.UUID;


public class TestUUID {

    @Test
    public void test01(){
        // 6ccdbd23-eb6b-4f5f-9f5e-38677147b02b
        String token= UUID.randomUUID().toString();
        System.out.println(token);
    }
}
