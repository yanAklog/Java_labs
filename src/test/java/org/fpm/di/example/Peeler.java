package org.fpm.di.example;

public class Peeler {

    private final Peelable peelable;

    public Peeler(Peelable peelable) {
        this.peelable = peelable;

    }

    public Peelable getDependency() { return peelable;}
}
