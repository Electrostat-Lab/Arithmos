package main.nativethreads;

import pthread.Pthread;
import pthread.ThreadDispatcher;
import pthread.model.ParameterList;

public class Initializer extends Pthread {

    public Initializer(final ParameterList parameterList) {
        super(parameterList);
    }
    @Override
    public void invoke(ThreadDispatcher threadDispatcher) {
        System.out.println((String)parameterList.getParams()[0] + (String)parameterList.getParams()[1]);
    }
}

