package pthread;

import pthread.model.ThreadModel;

/**
 * A thread utility used for disptaching native threads against jvm.
 * 
 * @author pavl_g.
 */
public class ThreadDispatcher {
    
    private static ThreadDispatcher threadDispatcher;
    private final int OPERATION_MODE;

    public enum OperationMode {
        MUTEX(123);

        private final int value;

        OperationMode(final int value) {
            this.value = value;
        }
    }

    static {
        System.loadLibrary("ArithmosNatives");
    }

    /**
     * Private modifier to inhibit instantiation.
     */
    private ThreadDispatcher(final int OPERATION_MODE) {
        this.OPERATION_MODE = OPERATION_MODE;
    }

    public static ThreadDispatcher getInstance() {
        return getInstance(OperationMode.MUTEX);
    }
    
    public static ThreadDispatcher getInstance(final OperationMode operationMode) {
        if (threadDispatcher == null) {
            synchronized (ThreadDispatcher.class) {
                if (threadDispatcher == null) {
                    threadDispatcher = new ThreadDispatcher(operationMode.value);
                }
            }
        }
        return threadDispatcher;
    }

    /**
     * Dispatches a thread on a method with a model.
     * @param model the method model.
     */
    public native void dispatch(final ThreadModel model);

    /**
     * When called inside a native thread context, detaches it from the jvm thread.
     */
    public native int finish();

}