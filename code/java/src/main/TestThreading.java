package main;

import pthread.Pthread;
import pthread.model.ParameterList;

public class TestThreading extends Pthread {
    public TestThreading(final ParameterList parameterList) {
        super(parameterList);
    }
    @Override
    public void invoke() {
        System.out.println((String)parameterList.getParams()[0] + (String)parameterList.getParams()[1]);
    }
}

