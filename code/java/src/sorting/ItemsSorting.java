package sorting;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ItemsSorting {

    /**
     * Use this enum to select the type of sort.
     * @see ItemsSorting#selectionSort(String[], SortAlgorithm)
     * @see ItemsSorting#compare(String[], int, int, SortAlgorithm)
     */
    public enum SortAlgorithm {
        A_Z, Z_A
    }

    private static final Logger logger = Logger.getLogger(ItemsSorting.class.getName());
    
    /**
     * Private constructor to inhibit its intialization.
     *
     */
    private ItemsSorting() {

    }
    
    /**
     * Sorts a list of strings by :
     * a) searching for the string owing the smallest ASCII.
     * b) mark it down.
     * c) swap it to the top in exchange with the current item.
     *
     * @param list the list used for sorting.
     * @param sortAlgorithm the type of the sort.
     * @return a new sorted list in the format {@link List<String>}.
     */
    public static List<String> selectionSort(final String[] list, final SortAlgorithm sortAlgorithm) {
        // find time spent
        double time = System.currentTimeMillis() / 1000d;
        logger.log(Level.INFO, String.valueOf(time));

        // sanity check the inputs.
        assert list != null;
        final Object[] pointer = new Object[1];
        // a) find the least item
        // b) mark it down
        // c) then swap it to the top of the array
        for(int i = 0; i < list.length; i++) {
            // mark down the current index as the smallest index until proved otherwise
            int smallestIndex = i;

            for(int j = i + 1; j < list.length; j++) {
                // a) finding the smallest item according to ASCII and mark its index
                // b) mark it down
                smallestIndex = compare(list, smallestIndex, j, sortAlgorithm);
            }
            // c) swap it to the top of the array
            pointer[0] = list[i];
            // swap the smallest item to the top in exchange with the current item
            list[i] = list[smallestIndex];
            // in exchange with the current item
            list[smallestIndex] = (String) pointer[0];
        }
        double _time = System.currentTimeMillis() / 1000d;
        double __time = _time - time;
        logger.log(Level.INFO, "Spent : " + __time + " seconds");
        return Arrays.asList(list);
    }

    /**
     * Searches for the String with the least ASCII code characters in an array literal.
     * ==> Steps :
     * It compares 2 strings' characters :
     * - if they are equal then skip to the next character.
     * - if one char < another char then return the index of the String owing this char.
     *
     * @param list the list used for searching.
     * @param firstString the first string index used for comparison.
     * @param secondString the second string index used for comparison.
     * @param sortAlgorithm sorting algorithm.
     * @return the index of the least ASCII code characters.
     */
    public static int compare(final String[] list, final int firstString, final int secondString, final SortAlgorithm sortAlgorithm) {
        for (int x = 0; x < Math.min(list[firstString].length(), list[secondString].length()); x++) {
            // skip if the letters are equal
            if (list[firstString].charAt(x) == list[secondString].charAt(x)) {
                continue;
            }
            // return the String containing the smallest ASCII code characters if the sort algo is A-Z
            // return the String containing the greatest ASCII code characters if the sort algo is Z-A
            if (list[firstString].charAt(x) < list[secondString].charAt(x)) {
                // return the String containing the greatest ASCII code characters
                if (sortAlgorithm == SortAlgorithm.Z_A) {
                    return secondString;
                }
                return firstString;
            } else if (list[secondString].charAt(x) < list[firstString].charAt(x)) {
                if (sortAlgorithm == SortAlgorithm.Z_A) {
                    return firstString;
                }
                return secondString;
            }
        }
        return firstString;
    }
}
