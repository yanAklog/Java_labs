package org.fpm.di.example;

import javax.inject.Singleton;

@Singleton
public class UsingtwoConstructors {

    private final Peeler peeler;
    private final twoConstructors_correct tcc;

    public UsingtwoConstructors(Peeler peeler, twoConstructors_correct tcc) {
        this.peeler = peeler;
        this.tcc = tcc;
    }

    public Peeler getDependency() { return peeler;}
    public twoConstructors_correct getDependency_1() { return tcc;}
}
