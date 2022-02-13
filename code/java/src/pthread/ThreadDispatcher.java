package pthread;

import pthread.model.ThreadModel;

/**
 * A thread utility used for disptaching native threads against jvm.
 * 
 * @author pavl_g.
 */
public class ThreadDispatcher {
    
    static {
        System.loadLibrary("ArithmosNatives");
    }

    /**
     * Private modifier to inhibit instantiation.
     */
    private ThreadDispatcher() {

    } 

    /**
     * Dispatches a thread on a method with a model.
     * @param model the method model.
     */
    public static native void dispatch(final ThreadModel model);

    /**
     * When called inside a native thread context, detaches it from the jvm thread.
     */
    public static native int finish();

}