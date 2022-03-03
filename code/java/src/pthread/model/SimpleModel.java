package pthread.model;
import pthread.Pthread;

/**
 * The Simple reflection model describes method data.
 * @author pavl_g.
 */
public abstract class SimpleModel {
    protected String classPath = "";
    protected int delay = 0;
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
}
