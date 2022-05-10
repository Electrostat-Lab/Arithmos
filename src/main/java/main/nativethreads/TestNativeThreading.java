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
    private static final int ONE_SECOND = 1000000;

    public static void execute() {
        final ThreadDispatcher threadDispatcher = ThreadDispatcher.getInstance(OperationMode.MUTEX);

        final ThreadModel finalizerModel = new ThreadModel();
        finalizerModel.setClazz(Finalizer.class);
        finalizerModel.setParameterList(new ParameterList(new Object[]{"This is the", " Finalizer " + Finalizer.class.getName()}));
        finalizerModel.setDelay(ONE_SECOND);
        
        final ThreadModel initializerModel = new ThreadModel();
        initializerModel.setClazz(Initializer.class);
        initializerModel.setParameterList(new ParameterList(new Object[]{"This is the", " Initializer " + Initializer.class.getName()}));
        initializerModel.setDelay(ONE_SECOND * 2);

        threadDispatcher.dispatch(initializerModel);
        threadDispatcher.dispatch(finalizerModel);


    }
}