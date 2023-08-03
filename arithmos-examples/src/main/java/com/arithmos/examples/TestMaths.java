package com.arithmos.examples;

import com.arithmos.number.NumberFormat;
import java.util.Arrays;

/**
 * Tests math classes provided by this api.
 * 
 * @author pavl_g.
 */
public class TestMaths {
    public static void execute() {
        System.out.println(Arrays.toString(NumberFormat.convertToBinary(13)));
        System.out.println(Arrays.toString(NumberFormat.convertToBinary("Guru")));
        System.out.println(NumberFormat.convertToDecimal("1101"));
        System.out.println(NumberFormat.convertToDecimal(1, 1, 0, 1));
    }
}