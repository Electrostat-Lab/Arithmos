package pthread.model;

public class ParameterList {
    private Object[] params = new Object[0];

    public ParameterList(final Object[] params) {
        this.params = params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }
}