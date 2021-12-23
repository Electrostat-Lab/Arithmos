package main;

import patternMatching.PatternMatching;
import testKotlin.Test;
import testScala.TestScala;
import sorting.ItemsSorting;

public class TestCase {
    public static void main(String[] args) {
        // test kotlin
        final Test test1 = new Test();
        test1.testMe();
        // test scala
        final TestScala test2 = new TestScala();
        test2.testMe();
        
        System.out.println(PatternMatching.findBrute("Pavly Gerges", "Gerges"));

        System.out.println(PatternMatching.findBoyerMoore("Pavly Gerges", "Gerges"));
        System.out.println(ItemsSorting.selectionSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.A_Z));
        System.out.println(ItemsSorting.selectionSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.Z_A));
    }
}
