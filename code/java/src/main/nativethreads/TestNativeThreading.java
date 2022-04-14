package main.nativethreads;

import pthread.ThreadDispatcher;
import pthread.ThreadDispatcher.OperationMode;
import pthread.model.ParameterList;
import pthread.model.ThreadModel;
import main.nativethreads.Finalizer;
import main.nativethreads.Initializer;
import pthread.Pthread;

/**
 * Test pthreads api on a java application using Model->thread->Object->Model architecture
 * 
 * @author pavl_g.
 */
public class TestNativeThreading {
    private static final int FIVE_SECONDS = 5000000;

    public static void execute() {
        final ThreadDispatcher threadDispatcher = ThreadDispatcher.getInstance();

        final ThreadModel finalizerModel = new ThreadModel();
        finalizerModel.setClazz(Finalizer.class);
        finalizerModel.setParameterList(new ParameterList(new Object[]{"This is the", " Finalizer " + Finalizer.class.getName()}));
        finalizerModel.setDelay(FIVE_SECONDS);
        
        final ThreadModel initializerModel = new ThreadModel();
        initializerModel.setClazz(Initializer.class);
        initializerModel.setParameterList(new ParameterList(new Object[]{"This is the", " Initializer " + Initializer.class.getName()}));
        initializerModel.setDelay(FIVE_SECONDS * 2);

        threadDispatcher.dispatch(finalizerModel);
        threadDispatcher.dispatch(initializerModel);

    }
}