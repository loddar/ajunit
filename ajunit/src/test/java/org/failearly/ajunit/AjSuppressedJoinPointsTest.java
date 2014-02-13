/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (http://fail-early.com/contact)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * AjSuppressedJoinPointsTest is responsible for ...
 */
public class AjSuppressedJoinPointsTest {

    public static final String ANY_UNIVERSE = "anyUniverse";

    private AjUniverse ajUniverse;

    @Before
    public void setUp() throws Exception {
        this.ajUniverse=AjUniversesHolder.createUniverseByClassNames(ANY_UNIVERSE, Arrays.asList(java.lang.Object.class.getName(),AnyWeavedClass.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        AjUniversesHolder.dropUniverse(ANY_UNIVERSE);
    }

    @Test
    public void javaLangObject() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jlo(),
                // SyncMethods
                "method-call{#apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "method-call{#apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "method-call{#apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "method-call{#apply=0, method=public final native void java.lang.Object.notify()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notify()}",
                "method-call{#apply=0, method=public final native void java.lang.Object.notifyAll()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notifyAll()}",

                // Standard Methods
                "method-call{#apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "method-execution{#apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "method-call{#apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "method-execution{#apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "method-call{#apply=0, method=public native int java.lang.Object.hashCode()}",
                "method-execution{#apply=0, method=public native int java.lang.Object.hashCode()}",
                "method-call{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "method-execution{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",

                // Cloning
                "method-call{#apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "method-execution{#apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",

                "method-call{#apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "method-execution{#apply=0, method=private static native void java.lang.Object.registerNatives()}",

                "constructor-call{#apply=0, constructor=public java.lang.Object()}",
                "constructor-execution{#apply=0, constructor=public java.lang.Object()}",
                "method-call{#apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "method-execution{#apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}"
        );
    }

    @Test
    public void jlo() throws Exception {
        assertThat("Alias of javaLangObject()?", AjSuppressedJoinPoints.jlo().predicate(), sameInstance(AjSuppressedJoinPoints.javaLangObject().predicate()));
    }

    @Test
    public void jloStandardMethods() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloStandardMethods(),
                // Standard Methods
                "method-call{#apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "method-execution{#apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "method-call{#apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "method-execution{#apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "method-call{#apply=0, method=public native int java.lang.Object.hashCode()}",
                "method-execution{#apply=0, method=public native int java.lang.Object.hashCode()}",
                "method-call{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "method-execution{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}"
        );
    }

    @Test
    public void jloSyncMethods() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloSyncMethods(),
                // SyncMethods
                "method-call{#apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "method-call{#apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "method-call{#apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "method-call{#apply=0, method=public final native void java.lang.Object.notify()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notify()}",
                "method-call{#apply=0, method=public final native void java.lang.Object.notifyAll()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
    }

    @Test
    public void jloClone() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloClone(),
                // Cloning
                "method-call{#apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "method-execution{#apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}"
        );
    }


    @Test
    public void jloEquals() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloEquals(),
                "method-call{#apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "method-execution{#apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}"
        );
    }

    @Test
    public void jloHashCode() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloHashCode(),
                "method-call{#apply=0, method=public native int java.lang.Object.hashCode()}",
                "method-execution{#apply=0, method=public native int java.lang.Object.hashCode()}"
        );
    }

    @Test
    public void jloToString() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloToString(),
                "method-call{#apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "method-execution{#apply=0, method=public java.lang.String java.lang.Object.toString()}"
        );
    }

    @Test
    public void jloGetClass() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloGetClass(),
                "method-call{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "method-execution{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}"
        );
    }

    @Test
    public void jloFinalize() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloFinalize(),
                "method-call{#apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "method-execution{#apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}"
        );
    }

    @Test
    public void jloConstructor() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.jloConstructor(),
                "constructor-call{#apply=0, constructor=public java.lang.Object()}",
                "constructor-execution{#apply=0, constructor=public java.lang.Object()}"
        );
    }

    @Test
    public void ajcMethod() throws Exception {
        assertSelectedJoinPoints(
                AjSuppressedJoinPoints.ajcMethod("Clinit"),
                "method-call{#apply=0, method=private static void org.failearly.ajunit.AjSuppressedJoinPointsTest$AnyWeavedClass.ajc$Clinit()}",
                "method-execution{#apply=0, method=private static void org.failearly.ajunit.AjSuppressedJoinPointsTest$AnyWeavedClass.ajc$Clinit()}"
        );
    }

    private void assertSelectedJoinPoints(SuppressedJoinPoint suppressedJoinPoint, String... expectedJoinPoints) {
        // act / when
        final List<String> joinPoints = filterJoinPointsBy(suppressedJoinPoint);

        // assert / then
        assertThat("Selected Join Points?", joinPoints, containsInAnyOrder(expectedJoinPoints));
    }

    private List<String> filterJoinPointsBy(final SuppressedJoinPoint method) {
        final List<String> joinPoints=new LinkedList<>();
        final Predicate predicate=method.predicate();

        // act / when
        this.ajUniverse.visitJoinPoints(new AjJoinPointVisitor() {
            @Override
            public void visit(AjJoinPoint joinPoint) {
                if( predicate.evaluate(joinPoint) ) {
                    joinPoints.add(joinPoint.toString());
                }
            }
        });
        return joinPoints;
    }

    @SuppressWarnings("all")
    private static class AnyWeavedClass {
        private String ajc$field;
        private static Object ajc$other;
        private static void ajc$Clinit() { }
        private void ajc$other(String value) {
            ajc$field = value;
        }
    }

}
