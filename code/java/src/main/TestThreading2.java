package main;

import pthread.Pthread;
import pthread.ThreadDispatcher;
import pthread.model.ParameterList;

public class TestThreading2 extends Pthread {
    public TestThreading2(final ParameterList parameterList) {
        super(parameterList);
    }
    @Override
    public void invoke(final ThreadDispatcher threadDispatcher) {
        /* Test finish, should finish the whole Mutex thread */
        // threadDispatcher.finish();

        System.out.println((String)parameterList.getParams()[0] + (String)parameterList.getParams()[1]);
    }
}

