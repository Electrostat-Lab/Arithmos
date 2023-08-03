package com.arithmos.examples;

import com.arithmos.time.TimeFormat;

/**
 * Tests the physics libs provided by this api.
 * 
 * @author pavl_g.
 */
public class TestPhysics {
    public static void execute() {
         //test time format
        String time = TimeFormat.getFormattedTime(
            TimeFormat.TimeRepresentFormat.Format.COLON_NO_LABELS,
            Math.pow(10, 6) * 60 * 20 * 10,
            TimeFormat.Time.InputType.INPUT_TYPE_MICROS);

        System.out.println(time);
    }
}