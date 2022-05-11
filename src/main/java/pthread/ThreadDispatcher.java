package pthread;

import pthread.model.ThreadModel;
import utils.natives.NativeImageLoader;

/**
 * A thread utility used for disptaching native threads against jvm.
 * 
 * @author pavl_g.
 */
public class ThreadDispatcher {
    
    private static ThreadDispatcher threadDispatcher;
    private final int OPERATION_MODE;

    public enum OperationMode {
        MUTEX(456),
        ASYNC(123);

        private final int value;

        OperationMode(final int value) {
            this.value = value;
        }
    }

    static {
        NativeImageLoader.loadLibrary();
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
     * Releases the thread model Mutex object memory.
     */
    public native boolean finish();

}
