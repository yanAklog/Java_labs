package org.fpm.di.example;

import org.fpm.di.Container;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class Example {

    private Container container;

    @Before
    public void setUp() {
        container = new MyEnvironment().configure(new MyConfiguration());
    }

    @Test
    public void shouldInjectSingleton() {
        assertSame(container.getComponent(MySingleton.class), container.getComponent(MySingleton.class));
    }

    @Test
    public void shouldInjectPrototype() {
        assertNotSame(container.getComponent(MyPrototype.class), container.getComponent(MyPrototype.class));
    }

    @Test
    public void shouldBuildInjectionGraph() {
        /*
        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());
        */
        final B bAsSingleton = container.getComponent(B.class);
        assertSame(container.getComponent(A.class), bAsSingleton);
        assertSame(container.getComponent(B.class), bAsSingleton);
    }

    @Test
    public void shouldBuildInjectDependencies() {
        final UseA hasADependency = container.getComponent(UseA.class);
        assertSame(hasADependency.getDependency(), container.getComponent(B.class));
    }

    @Test
    public void shouldBuildInjectDependencies_1() {
        final Juicer juicer = container.getComponent(Juicer.class);
        assertSame(juicer.getDependency(), container.getComponent(Apple.class));
    }

    @Test
    public void shouldBuildInjectDependencies_2() {
        final Peeler peeler1 = container.getComponent(Peeler.class);
        final Peeler peeler2 = container.getComponent(Peeler.class);
        assertNotSame(peeler1, peeler2);
        assertSame(peeler1.getDependency(), peeler2.getDependency());
    }

    @Test
    public void shouldBuildInjectDependencies_3() {
        final Juicer juicer = container.getComponent(Juicer.class);
        assertNotSame(juicer.getDependency_1(), container.getComponent(Peeler.class));
    }

    @Test
    public void shouldBuildInjectDependencies_4() {
        final Juicer juicer = container.getComponent(Juicer.class);
        assertSame(juicer.getDependency_1().getDependency(), juicer.getDependency());
    }

    @Test
    public void shouldBuildInjectDependencies_5() {
        final ComplexInjection ci = container.getComponent(ComplexInjection.class);
        assertSame(ci.getDependency().getDependency(), ci.getDependency_2());
        assertSame(ci.getDependency().getDependency_1(), ci.getDependency_1());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        twoConstructors tc = container.getComponent(twoConstructors.class);
    }

    @Test
    public void shouldUseInject() {
        twoConstructors_correct tc = container.getComponent(twoConstructors_correct.class);
        assertSame(tc.getDependency(), container.getComponent(Apple.class));
        assertSame(tc.getDependency(), container.getComponent(Peelable.class));
    }

    @Test
    public void shouldUseInject_1() {
        UsingtwoConstructors utc = container.getComponent(UsingtwoConstructors.class);
        assertNotSame(utc.getDependency(), container.getComponent(Peeler.class));
        assertNotSame(utc.getDependency_1(), container.getComponent(twoConstructors_correct.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowImplementationException() {
        C c = container.getComponent(C.class);
    }
}