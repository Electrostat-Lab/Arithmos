package pthread.model;

/**
 * The Simple reflection model describes method data.
 * @author pavl_g.
 */
public abstract class SimpleModel {
    protected String classRelativePath;
    protected String methodName;
    protected String methodSignature;
    protected int methodId;

    public void setClassRelativePath(final String classRelativePath) {
        this.classRelativePath = classRelativePath;
    }
    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }
    public void setMethodSignature(final String methodSignature) {
        this.methodSignature = methodSignature;
    }
    public void setMethodId(final int methodId) {
        this.methodId = methodId;
    }
    public String getClassRelativePath() {
        return classRelativePath;
    }
    public String getMethodName() {
        return methodName;
    }
    public String getMethodSignature() {
        return methodSignature;
    }
    public int getMethodId() {
        return methodId;
    }
}
