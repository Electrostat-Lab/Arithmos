package pthread.model;

import pthread.Pthread;
import pthread.model.SimpleModel;

public class ThreadModel extends SimpleModel {
    /**
     * For late initialization.
     */
    public ThreadModel() {
        super();
    }
    /**
     * For quick initialization.
     * 
     * @param clazz the threading class.
     * @param parameterList
     * @param delay delay in microseconds.
     */
    public ThreadModel(final Class<? extends Pthread> clazz, final ParameterList parameterList, final int delay) {
        super();
        setClazz(clazz);
        setParameterList(parameterList);    
        setDelay(delay);
    }
    
}