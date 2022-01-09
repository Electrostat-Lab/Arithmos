package utils;

import java.lang.IllegalAccessException;
import java.lang.System;
import physics.Units;

/**
 * A utility used for calculating the elapsed time of an algorithm 
 * or various algos at the same time, it also plugs the time into a 
 * function to find the algo Big-O notation.
 *
 * @author pavl_g.
 */
public final class Chronograph {
    
    private double[] records = new double[0];
    private int nOfRec = records.length - 1;
    private static Chronograph chronograph;
    private static Object chronometer = new Object();
    
    private Chronograph() {
    }
    
    public static Chronograph getChronometer() {
        if (chronograph == null) {
             synchronized(chronometer) {
                  chronograph = new Chronograph();
             }
        }
        return chronograph;
    }
    
    public void recordPoint() {
        double[] temp = new double[records.length];
        temp = records;
        records = new double[records.length + 1];
        for (int i = 0; i < temp.length; i++) {
            records[i] = temp[i];
        }    
        records[++nOfRec] = System.currentTimeMillis();
    }
    
    public void removePoint(final int index) throws IllegalAccessException {
        if (index < 0 || index > nOfRec) {
            throw new IllegalAccessException("Cannot find this record !");
        }
        final double[] temp = new double[records.length];
        // fill a new array without the specified index
        for (int i = 0; i < records.length; i++) {
            if (i == index) {
                continue;
            }
            temp[i] = records[i];
        }
        // remove the empty times (clamp times)
        records = new double[temp.length - 1];
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == 0.0d) {
                continue;
            }
            records[i] = temp[i];
        }
    }
    
    public double getPoint(final int index) throws IllegalAccessException  {
        if (index < 0 || index > nOfRec) {
            throw new IllegalAccessException("Cannot find this record : " + index);
        }
        return records[index];
    }
    
    public void reset() {
        records = new double[0];
        nOfRec = records.length - 1;
    }
    
    public double getElapsedTime(int index0, int index1) throws IllegalAccessException  {
        return getPoint(index1) - getPoint(index0);
    }
    
    public double toSeconds(final double time) {
        return Units.convertInto(time, Units.IndexNotation.MILLI);
    }
}
