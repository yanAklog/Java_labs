package org.fpm.di.example;

public class Juicer {
    private final Peelable peelable;
    private final Peeler peeler;

    public Juicer(Peelable peelable, Peeler peeler) {
        this.peelable = peelable;
        this.peeler = peeler;
    }

    public Peelable getDependency() {
        return peelable;
    }
    public Peeler getDependency_1() { return peeler;}
}
