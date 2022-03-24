package pthread;

import pthread.model.ParameterList;

public abstract class Pthread {
    protected final ParameterList parameterList;
    public Pthread(final ParameterList parameterList) {
        this.parameterList = parameterList;
    }
    public abstract void invoke(final ThreadDispatcher threadDispatcher);
}
