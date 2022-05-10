package pthread.model;
import pthread.Pthread;

/**
 * The Simple reflection model describes method data.
 * @author pavl_g.
 */
public abstract class SimpleModel {
    protected String classPath = "";
    protected int delay = 0;
    protected ParameterList parameterList;
    protected String pthreadModelClass = ThreadModel.class.getCanonicalName().replace(".", "/");
    
    /**
     * Infers the model class path to the implementation,
     */
    public SimpleModel() {
        this.pthreadModelClass = this.getClass().getCanonicalName().replace(".", "/");
    }

    public void setClazz(Class<? extends Pthread> clazz) {
        if (clazz == null) {
            throw new IllegalStateException("Cannot operate on a void state");
        }
        this.classPath = clazz.getCanonicalName().replace(".", "/");
    }
    public String getClassPath() {
        return classPath;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
    public int getDelay() {
        return delay;
    }
    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }
    public ParameterList getParameterList() {
        return parameterList;
    }
    public String getPthreadModelClass() {
        return pthreadModelClass;
    }
}
