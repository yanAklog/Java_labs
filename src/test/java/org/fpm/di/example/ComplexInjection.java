package org.fpm.di.example;

public class ComplexInjection {

    private final Juicer juicer;
    private final Peeler peeler;
    private final Peelable peelable;

    public ComplexInjection(Juicer juicer, Peeler peeler, Peelable peelable) {
        this.juicer = juicer;
        this.peeler = peeler;
        this.peelable = peelable;
    }

    public Juicer getDependency() { return juicer;}
    public Peeler getDependency_1() { return peeler;}
    public Peelable getDependency_2() { return peelable;}
}
