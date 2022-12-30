package org.fpm.di.example;

import org.fpm.di.Binder;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Objects;

public class MyBinder implements Binder {

    private HashMap<Class<?>, Class<?>> services_and_impl;
    private HashMap<Class<?>, Object> services_and_inst;

    public MyBinder() {
        services_and_impl = new HashMap<>();
        services_and_inst = new HashMap<>();
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        check_impl(clazz);
        services_and_impl.put(clazz, clazz);

    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        check_impl(implementation);
        services_and_impl.put(clazz, implementation);

    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        check_inst(instance);
        services_and_inst.put(clazz, instance);

    }

    private <T> void check_impl(Class<T> implementation) {
        if (Modifier.isAbstract(implementation.getModifiers()) || implementation.isInterface()) {
            throw new IllegalArgumentException("Need an implementation");
        }

    }

    private void check_inst(Object instance) {
        Objects.requireNonNull(instance);
    }

    public HashMap<Class<?>, Class<?>> getSerandImpl() { return services_and_impl;}
    public HashMap<Class<?>, Object> getSerandInst() { return services_and_inst;}
}
