package org.fpm.di.example;

import org.fpm.di.Configuration;
import org.fpm.di.Binder;

public class MyConfiguration implements Configuration {
    @Override
    public void configure(Binder binder) {

        binder.bind(MySingleton.class);
        binder.bind(MyPrototype.class);
        binder.bind(UseA.class);
        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());


        binder.bind(Apple.class);
        binder.bind(Peelable.class, Apple.class);
        binder.bind(Peeler.class);
        binder.bind(Juicer.class);

        binder.bind(twoConstructors.class);
        binder.bind(twoConstructors_correct.class);
        binder.bind(UsingtwoConstructors.class);

        binder.bind(ComplexInjection.class);

        binder.bind(C.class, D.class);
    }
}
