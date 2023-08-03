package com.arithmos.examples;

import com.arithmos.pattern.PatternMatching;
import java.lang.IllegalAccessException;

public class TestPatternMatching {
    public static void execute() {
        try {
            System.out.println(PatternMatching.findBrute("Pattern matching", "matching"));
            System.out.println(PatternMatching.findBoyerMoore("Pattern matching", "matching"));
        } catch(IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}


   