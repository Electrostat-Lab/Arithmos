package main;

import main.TestMaths;
import main.TestPhysics;
import main.TestPatternMatching;
import main.TestSortingAlgorithms;
import main.TestJavaSemaphore;
import main.nativethreads.TestNativeThreading;

public class TestCase {
    public static void main(String[] args) {
        // test maths
        TestMaths.execute();
        TestPhysics.execute();

        // test algos
        TestPatternMatching.execute();
        TestSortingAlgorithms.execute();

        // test threadings
        TestNativeThreading.execute();
        TestJavaSemaphore.execute();
    }
}
