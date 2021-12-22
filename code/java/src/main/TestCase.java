package main;
import patternMatching.PatternMatching;
import sorting.ItemsSorting;

public class TestCase {
    public static void main(String[] args) {
        System.out.println(PatternMatching.findBrute("Pavly Gerges", "Gerges"));

        System.out.println(PatternMatching.findBoyerMoore("Pavly Gerges", "Gerges"));
        System.out.println(ItemsSorting.selectionSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.A_Z));
        System.out.println(ItemsSorting.selectionSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.Z_A));
    }
}
