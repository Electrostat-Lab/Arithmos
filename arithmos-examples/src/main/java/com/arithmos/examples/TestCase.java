package com.arithmos.examples;

/**
 * Module based test unit.
 * 
 * @author pavl_g.
 */
public class TestCase {
    public static void main(String[] args) {
        // test maths
        TestMaths.execute();
        TestPhysics.execute();

        // test algos
        TestPatternMatching.execute();
        TestSortingAlgorithms.execute();
    }
}
