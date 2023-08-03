package com.arithmos.sorting;

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
     * Time Complexity =
     *
     * @param list the list used for sorting.
     * @param sortAlgorithm the type of the sort.
     * @return a new sorted list in the format {@link List<String>}.
     */
    public static List<String> selectionSort(final String[] list, final SortAlgorithm sortAlgorithm) {
        // find time spent
        final double time = System.currentTimeMillis() / 1000d;
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
        final double _time = System.currentTimeMillis() / 1000d;
        final double __time = _time - time;
        logger.log(Level.INFO, "Spent : " + __time + " seconds");
        return Arrays.asList(list);
    }

    /**
    * Mirror Sort : Sorts a string array listerals.
    * Using recursion in a 2d format, the outer loop loops over the main chunks in an incremental manner,
    * while the inner loop loops over the main chunks in a decremental manner till reaching the current chunk (passed from the outer loop)
    * comparing the outer index with the inner index.
    *
    * Time Complexity =
    *
    * @param list
    * @param sortAlgorithm
    * @return a new sorted list in the format {@link List<String>}.
    */
    public static List<String> mirrorSort(final String[] list, final SortAlgorithm sortAlgorithm) {
        // find time spent
        final double time = System.currentTimeMillis() / 1000d;
        logger.log(Level.INFO, String.valueOf(time));


        SortUtils.recursiveLoop(list, 0, SortUtils.Directionality.FORWARD, new SortUtils.ActionInjection<String>(){
            @Override
            public void inject(final String[] array, final int index) {
                //inner-loop
                SortUtils.customComparative(list, list.length - 1, index, SortUtils.Directionality.BACKWARD, new SortUtils.ActionInjection<String>(){
                      @Override
                      public void inject(final String[] _array, final int _index) {
                          // comparison code
                          // return the most least string
                          final int __index = compare(list, index, _index, sortAlgorithm);
                          // swap the pointers if the least item is one of the mirror indices.
                          // otherwise keep everything as it is
                          if (__index == index) {
                              return;
                          }
                          String temp = list[index];
                          // sprint the least String on the top
                          list[index] = list[__index];
                          list[_index] = temp;
                          // nullyfying the pointers, helps GC to clear the object data
                          temp = null;
                      }
                  });
            }
        });
        final double _time = System.currentTimeMillis() / 1000d;
        final double __time = _time - time;
        logger.log(Level.INFO, "Spent : " + __time + " seconds");
        return Arrays.asList(list);
    }

    public static List<String> bubbleSort(final String[] list, final SortAlgorithm sortAlgorithm) {
        // find time spent
        final double time = System.currentTimeMillis() / 1000d;
        logger.log(Level.INFO, String.valueOf(time));

        SortUtils.recursiveLoop(list, 0, SortUtils.Directionality.FORWARD, new SortUtils.ActionInjection<String>(){
            @Override
            public void inject(final String[] array, final int index) {
                //inner-loop, loops over items beginning from the current item
                SortUtils.customComparative(list, index + 1, list.length - 1, SortUtils.Directionality.FORWARD, new SortUtils.ActionInjection<String>(){
                      @Override
                      public void inject(final String[] _array, final int _index) {
                          // comparison code
                          // return the most least string
                          final int __index = compare(list, index, _index, sortAlgorithm);
                          // swap the pointers if the least item is one of the mirror indices.
                          // otherwise keep everything as it is
                          if (__index == index) {
                              return;
                          }
                          String temp = list[index];
                          // sprint the least String on the top
                          list[index] = list[__index];
                          list[_index] = temp;
                          // nullyfying the pointers, helps GC to clear the object data
                          temp = null;
                      }
                  });
            }
        });
        final double _time = System.currentTimeMillis() / 1000d;
        final double __time = _time - time;
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
     * @return the index of the least ASCII code characters, default return value is the firstString index.
     */
    public static int compare(final String[] list, final int firstString, final int secondString, final SortAlgorithm sortAlgorithm) {
        for (int i = 0; i < Math.min(list[firstString].length(), list[secondString].length()); i++) {
            // skip if the letters are equal
            if (list[firstString].charAt(i) == list[secondString].charAt(i)) {
                continue;
            }
            // return the String containing the smallest ASCII code characters if the sort algo is A-Z
            // return the String containing the greatest ASCII code characters if the sort algo is Z-A
            if (list[firstString].charAt(i) < list[secondString].charAt(i)) {
                // return the String containing the greatest ASCII code characters
                if (sortAlgorithm == SortAlgorithm.Z_A) {
                    return secondString;
                }
                return firstString;
            } else if (list[secondString].charAt(i) < list[firstString].charAt(i)) {
                if (sortAlgorithm == SortAlgorithm.Z_A) {
                    return firstString;
                }
                return secondString;
            }
        }
        return firstString;
    }
}
