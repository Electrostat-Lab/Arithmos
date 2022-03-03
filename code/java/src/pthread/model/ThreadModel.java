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
     * @param clazz the threading class.
     */
    public ThreadModel(final Class<? extends Pthread> clazz, final ParameterList parameterList) {
        setClazz(clazz);
        setParameterList(parameterList);    
    }

    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }

    public ParameterList getParameterList() {
        return parameterList;
    }
    
}