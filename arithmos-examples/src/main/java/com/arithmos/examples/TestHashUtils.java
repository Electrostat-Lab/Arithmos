package com.arithmos.examples;

import com.arithmos.util.HashingUtils;

public class TestHashUtils {
    public static void main(String[] args) {
        final Object key = new Object();
        final Object value = new Object();
        for (int i = 0; i <= 50; i++) {
            System.out.println(HashingUtils.modCompress(i, 50));
        }
//        System.out.println(HashingUtils.modCompress(key.hashCode() ^ value.hashCode(), 40));
//
//        int number = 0b11111111000000000000000000000000;
//        System.out.println("Before spreading: " + number);
//        System.out.println("After spreading: " + HashingUtils.spreadMSBtoLSB(number));
    }
}
