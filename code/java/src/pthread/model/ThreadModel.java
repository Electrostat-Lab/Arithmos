package pthread.model;

import pthread.Pthread;
import pthread.model.SimpleModel;

public class ThreadModel extends SimpleModel {
    protected ParameterList parameterList;
    /**
     * For late initialization.
     */
    public ThreadModel() {
        
    }
    /**
     * For quick initialization.
     * 
     * @param clazz the threading class.
     * @param parameterList
     * @param delay delay in microseconds.
     */
    public ThreadModel(final Class<? extends Pthread> clazz, final ParameterList parameterList, final int delay) {
        setClazz(clazz);
        setParameterList(parameterList);    
        setDelay(delay);
    }

    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }

    public ParameterList getParameterList() {
        return parameterList;
    }
    
}