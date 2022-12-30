package org.fpm.di.example;

import org.fpm.di.Container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import javax.inject.Inject;
import javax.inject.Singleton;


public class MyContainer implements Container {

    private HashMap<Class<?>, Class<?>> services_and_impl;
    private HashMap<Class<?>, Object> services_and_inst;

    public MyContainer(HashMap<Class<?>, Class<?>> services_and_impl, HashMap<Class<?>, Object> services_and_inst) {
        this.services_and_impl = services_and_impl;
        this.services_and_inst = services_and_inst;

        services_and_impl.forEach((k, v) -> {
            if (k.isAnnotationPresent(Singleton.class) && !this.services_and_inst.containsKey(k)){
                this.services_and_inst.put(k, null);

                if (v.isAnnotationPresent(Singleton.class) && !this.services_and_inst.containsKey(v)) {
                    this.services_and_inst.put(v, null);
                }

            }

            else if (v.isAnnotationPresent(Singleton.class) && !this.services_and_inst.containsKey(v)) {
                this.services_and_inst.put(v, null);

                if (k.isAnnotationPresent(Singleton.class) && !this.services_and_inst.containsKey(k)) {
                    this.services_and_inst.put(k, null);
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getComponent(Class<T> clazz) {

        if (this.services_and_inst.containsKey(clazz) && services_and_inst.get(clazz) != null) {
            return (T) services_and_inst.get(clazz);
        }

        return this.getInstance(clazz, new HashMap<>());
    }
    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class <T> clazz, HashMap<Class<?>, Object> created_inst) {

        // check if we had already created instance of class clazz
        if (created_inst.containsKey(clazz)) { return (T) created_inst.get(clazz);}

        // check if clazz is singleton or has a default instance
        if (this.services_and_inst.containsKey(clazz) && services_and_inst.get(clazz) != null) { return (T) services_and_inst.get(clazz);}


        Class<?> implementation = this.services_and_impl.get(clazz);

        if (implementation == null) {throw new IllegalArgumentException("no implementation for " + clazz.getName());}


        if (implementation != clazz) {

            T desired_instance = (T) getInstance(implementation, created_inst);
            created_inst.put(clazz, desired_instance);
            if (this.services_and_inst.containsKey(clazz) && services_and_inst.get(clazz) == null) {
                this.services_and_inst.put(clazz, desired_instance);
            }



            return desired_instance;
        }

        // getting injectable constructor
        Constructor<?> injectable_constructor = getConstructor(implementation);

        // getting constructor's parameters types
        Class<?>[] paramTypes = injectable_constructor.getParameterTypes();


        if (paramTypes.length == 0) {

            try {
                T desired_instance = (T) injectable_constructor.newInstance();
                created_inst.put(clazz, desired_instance);
                if (this.services_and_inst.containsKey(clazz) && services_and_inst.get(clazz) == null) {
                    this.services_and_inst.put(clazz, desired_instance);
                }


                return desired_instance;

            } catch (Exception e) {
                throw new RuntimeException("Access to the constructor " + injectable_constructor.getName() + "denied");
            }
        }

        Object[] necessary_instances = new Object[paramTypes.length];

        for(int i=0; i< paramTypes.length; i++) {
            T param_instance = (T) getInstance(paramTypes[i], created_inst);
            necessary_instances[i] = param_instance;

            // adding it to the list of created instances
            created_inst.put(paramTypes[i], param_instance);

            // adding it to the list of singletons
            if (!paramTypes[i].isInterface() && this.services_and_inst.containsKey(paramTypes[i]) && services_and_inst.get(paramTypes[i]) == null) {
                this.services_and_inst.put(paramTypes[i], param_instance);
            }

        }

        try {
            T desired_instance = (T) injectable_constructor.newInstance(necessary_instances);

            created_inst.put(clazz, desired_instance);

            if (this.services_and_inst.containsKey(clazz) && services_and_inst.get(clazz) == null) {
                this.services_and_inst.put(clazz, desired_instance);
            }

            return desired_instance;

        } catch (Exception e) {
            throw new RuntimeException("Access to the constructor " + injectable_constructor.getName() + "denied");
        }


    }




    private Constructor<?> getConstructor(Class<?> implementation) {

        Constructor<?>[] constructors = implementation.getDeclaredConstructors();

        if (constructors.length == 1) {return constructors[0];}

        int index = 0;
        int counter = 0;

        for (int i = 0; i< constructors.length; i++) {

            if (constructors[i].isAnnotationPresent(Inject.class)) {
                index = i;
                counter += 1;
            }

        }

        if (counter == 1) {return constructors[index];}
        else {throw new IllegalArgumentException("Only one constructor in " + implementation.getName() + "can be injectable");}

    }
}
