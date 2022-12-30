package org.fpm.di.example;

public class twoConstructors {

    private final Peelable peelable;
    private final int c;

    public twoConstructors(Peelable peelable) {
        this.peelable = peelable;
        this.c = 0;
    }

    public twoConstructors(int c){
        this.peelable = new Apple();
        this.c = c;
    }
}
