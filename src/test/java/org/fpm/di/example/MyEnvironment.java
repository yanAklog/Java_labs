package org.fpm.di.example;

import org.fpm.di.Configuration;
import org.fpm.di.Container;
import org.fpm.di.Environment;

public class MyEnvironment implements Environment {

    @Override
    public Container configure(Configuration configuration) {

        MyBinder binder = new MyBinder();
        configuration.configure(binder);
        MyContainer container = new MyContainer(binder.getSerandImpl(), binder.getSerandInst());
        return container;
    }
}
