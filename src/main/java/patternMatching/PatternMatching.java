package patternMatching;

import java.lang.IllegalAccessException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Chronograph;

/**
 * A Utility Class for patternMatching.PatternMatching Algos, includes :
 * -Brute Force
 *
 * @author pavl_g.
 */
public final class PatternMatching {

    private static final Logger logger = Logger.getLogger(PatternMatching.class.getName());
    private static final Chronograph chronograph = Chronograph.getChronometer();
    
    /**
     * Private constructor to inhibit its intialization.
     */
    private PatternMatching() {

    }
    /**
     * Finds the first index of the pattern string inside the main text using brute force
     * <br>
     * <b>Brute Force</b> : forces the outer loop to adapt to find the placement of the
     * pattern text inside the main text based on their lengths w/o looping on all chars.
     * <br>
     * <u>
     * When applying this technique in a general situation, we typically
     * enumerate all possible configurations of the inputs involved and pick the best of all
     * these enumerated configurations.
     * </u>
     * <br>
     * we simply test all the possible placements of the pattern relative to the
     * text.
     * <br>
     * <b>Formulas :</b>
     * <ol>
     * <li>Length of outer loop = textLength - patternLength + 1 -- will give you the number of chunks(tiles) to pass through their chars.</li>
     * <li>Index of the main text char to compare = index of current main text char + index of current pattern char. -- until it reaches the last index of the pattern char that would satisfy the chunk length.</li>
     * </ol>
     * ---chunks---
     * ---pattern---
     *
     * @param text    the main string text.
     * @param pattern the pattern to search for inside it.
     * @return the first index of the pattern -if-located- w/in the main text, otherwise exit w/ error <b>-1</b>.
     */
    public static int findBrute(String text, String pattern) throws IllegalAccessException {
        //find time
        chronograph.recordPoint();
        logger.log(Level.INFO, String.valueOf(chronograph.getPoint(0)));

        final int textLen = text.length();
        final int patternLen = pattern.length();
        //get the number of chunks, in which this algorithm would repeat on it.
        final int chunks = textLen - patternLen + 1;
        //limit the outer loop to some number chunks.
        for (int i = 0; i < chunks; i++) {
            //continue comparing text chars using the inner loop --force find the pattern inside the text
            for (int k = 0; k < patternLen && text.charAt(i + k) == pattern.charAt(k); k++) {
                // if the inner loop index reaches the last index of the pattern,
                //              terminate returning the first matched index
                if (k == patternLen - 1) {
                    //find time
                    chronograph.recordPoint();
                    final double __time = chronograph.toSeconds(chronograph.getElapsedTime(0, 1));
                    logger.log(Level.INFO, "Spent : " + __time + " seconds");
                    chronograph.reset();
                    return i;
                }
            }
        }
        //exit w/ error code
        return -1;
    }

    public static int findBoyerMoore(String text, String pattern) throws IllegalAccessException {
        //find time
        chronograph.recordPoint();
        logger.log(Level.INFO, String.valueOf(chronograph.getPoint(0)));

        final int textLen = text.length();
        final int patternLen = pattern.length();
        final Map<Character, Integer> last = new HashMap<>();
        assert patternLen != 0;
        //fill textLen with non-sense data
        for (int i = 0; i < textLen; i++) {
            last.put(text.charAt(i), -1);
        }
        //replace the matched occurrences with sense data
        //now only matched occurrences have defined indices
        for (int i = 0; i < patternLen; i++) {
            last.put(pattern.charAt(i), i);
        }
        for (int i = patternLen - 1, k = patternLen - 1; i < textLen; ) {
            if (text.charAt(i) == pattern.charAt(k)) {
                if (k == 0) {
                    //find time spent in this algo
                    chronograph.recordPoint();
                    final double __time = chronograph.toSeconds(chronograph.getElapsedTime(0, 1));
                    logger.log(Level.INFO, "Spent : " + __time + " seconds");
                    chronograph.reset();
                    return i;
                }
                i--;
                k--;
            } else {
                i = i + (patternLen - Math.min(k, 1 + last.get(text.charAt(i))));
                k = patternLen - 1;
            }
        }
        return -1;
    }
}
