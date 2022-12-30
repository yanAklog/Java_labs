package org.fpm.di.example;

import javax.inject.Inject;

public class twoConstructors_correct {

    private final Peelable peelable;
    private final int c;

    @Inject
    public twoConstructors_correct(Peelable peelable) {
        this.peelable = peelable;
        this.c = 0;
    }

    public twoConstructors_correct(int c){
        this.peelable = new Apple();
        this.c = c;
    }

    public Peelable getDependency() { return peelable;}
}

