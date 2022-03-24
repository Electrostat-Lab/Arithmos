package pthread;

import pthread.model.ThreadModel;

/**
 * A thread utility used for disptaching native threads against jvm.
 * 
 * @author pavl_g.
 */
public class ThreadDispatcher {
    
    private static ThreadDispatcher threadDispatcher;
    private final int OPERATION_TYPE_VALUE;

    public enum OperationType {
        ASYNC(456), MUTEX(123);

        private int value;

        OperationType(final int value) {
            this.value = value;
        }
    }

    static {
        System.loadLibrary("ArithmosNatives");
    }

    /**
     * Private modifier to inhibit instantiation.
     */
    private ThreadDispatcher(final int OPERATION_TYPE_VALUE) {
        this.OPERATION_TYPE_VALUE = OPERATION_TYPE_VALUE;
    }

    public static ThreadDispatcher getInstance() {
        return getInstance(OperationType.ASYNC);
    }
    
    public static ThreadDispatcher getInstance(final OperationType operationType) {
        if (threadDispatcher == null) {
            synchronized (ThreadDispatcher.class) {
                if (threadDispatcher == null) {
                    threadDispatcher = new ThreadDispatcher(operationType.value);
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