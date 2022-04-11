package main;

import java.io.File;
import java.nio.file.FileSystems;
import patternMatching.PatternMatching;
import testKotlin.Test;
import sorting.ItemsSorting;
import number.*;
import time.*;
import java.util.Arrays;
import pthread.ThreadDispatcher;
import pthread.ThreadDispatcher.OperationMode;
import pthread.model.ParameterList;
import pthread.model.ThreadModel;
import pthread.Pthread;

public class TestCase {
    private static final int FIVE_SECONDS = 5000000;
    static {
         System.loadLibrary("ArithmosNatives");
    }

    public static void main(String[] args) {
        final ThreadDispatcher threadDispatcher = ThreadDispatcher.getInstance();

        // test pthreads running from java using Model->thread->Object->Model architecture
        final ThreadModel model = new ThreadModel();
        model.setClazz(TestThreading2.class);
        model.setParameterList(new ParameterList(new Object[]{"My name ", " is Pavly"}));
        model.setDelay(FIVE_SECONDS);
        threadDispatcher.dispatch(model);
        
        final ThreadModel model2 = new ThreadModel();
        model2.setClazz(TestThreading.class);
        model2.setParameterList(new ParameterList(new Object[]{"I am a ", " medical Student"}));
        model2.setDelay(FIVE_SECONDS * 2);
        threadDispatcher.dispatch(model2);

        
        final ThreadModel model3 = new ThreadModel();
        model3.setClazz(TestThreading.class);
        model3.setParameterList(new ParameterList(new Object[]{"I am a ", " medical Student 2"}));
        model3.setDelay(FIVE_SECONDS);
        threadDispatcher.dispatch(model3);

        // test pthreads running from java using Model->thread->Object->Model architecture
        final ThreadModel model4 = new ThreadModel();
        model4.setClazz(TestThreading2.class);
        model4.setParameterList(new ParameterList(new Object[]{"My name ", " is Pavly 2"}));
        model4.setDelay(FIVE_SECONDS);
        threadDispatcher.dispatch(model4);
        testMath();
    }

    public static void testMath() {
        System.out.println(Arrays.toString(NumberFormat.convertToBinary(13)));
        System.out.println(Arrays.toString(NumberFormat.convertToBinary("Guru")));
        System.out.println(NumberFormat.convertToDecimal("1101"));
        System.out.println(NumberFormat.convertToDecimal(1, 1, 0, 1));
        //test time format
        String time = TimeFormat.getFormattedTime(
                TimeFormat.TimeRepresentFormat.Format.COLON_NO_LABELS,
                Math.pow(10, 6) * 60 * 20 * 10,
                TimeFormat.Time.InputType.INPUT_TYPE_MICROS);

        System.out.println(time);
    }

    public static void testSort() {
      try {
        System.out.println(PatternMatching.findBrute("Pavly Gerges", "Gerges"));
        System.out.println(PatternMatching.findBoyerMoore("Pavly Gerges", "Gerges"));
      } catch(java.lang.IllegalAccessException ex) {
        System.err.println(ex); 
      }
        System.out.println(ItemsSorting.selectionSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.A_Z));
        System.out.println(ItemsSorting.mirrorSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.A_Z));
        System.out.println(ItemsSorting.mirrorSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.Z_A));
        System.out.println(ItemsSorting.bubbleSort(new String[]{"Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"}, ItemsSorting.SortAlgorithm.A_Z));
    }
    public static native void testNatives();
}
