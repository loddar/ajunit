/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2013  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.universe.impl;

import org.failearly.ajunit.universe.AjJoinPoint;
import org.failearly.ajunit.universe.AjJoinPointVisitor;
import org.failearly.ajunit.universe.AjUniverse;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * AjUniverseInitializerTest tests for {@link org.failearly.ajunit.universe.impl.AjUniverseInitializer}
 */
public class AjUniverseInitializerTest {
    // Test Fixtures
    @SuppressWarnings("unused")
    private static class EmptyClass {
    }

    @SuppressWarnings("unused")
    private static class SecondClass {
    }

    @SuppressWarnings("unused")
    private interface MarkerInterface {
    }

    @SuppressWarnings("unused")
    private class EmptyInnerClass {
    }

    @SuppressWarnings("unused")
    private static class OtherClass implements MarkerInterface {
    }

    @SuppressWarnings("unused")
    private static class Inherited extends OtherClass implements MarkerInterface {
        private int intField;

        Inherited(int intField) {
            this.intField = intField;
        }


        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }
    }


    // The tests
    @Test
    public void createUniverse() throws Exception {
        // arrange / given
        final List<AjJoinPoint> joinPoints = new LinkedList<>();

        // act / when
        final AjUniverse universe = createUniverse(joinPoints);

        // assert / then
        assertThat("Universe should not be NOT initialized?", universe.isInitialized(), is(false));
        assertThat("Universe should not be NOT initialized?", universe.isInitialized(), is(false));
        assertThat("#Total Join Points?", joinPoints.isEmpty(), is(true));
    }

    @Test
    public void createUniverseAndInitialize() throws Exception {
        // arrange / given
        final List<AjJoinPoint> joinPoints = new LinkedList<>();
        final AjUniverseImpl universe = createUniverse(joinPoints);
        final AjUniverseInitializer initializer = new AjUniverseInitializer(universe);

        // act / when
        initializer.initialize(toClassCollection(EmptyClass.class));

        // assert / then
        assertThat("Universe is initialized?", universe.isInitialized(), is(true));
        assertThat("Any Join Points?", joinPoints.isEmpty(), is(false));
    }

    @Test
    public void universeInitWithEmptyClass() throws Exception {
        // act / when
        final List<AjJoinPoint> joinPoints = createAndInitializeUniverse(EmptyClass.class);

        // assert / then
        int totalExpectedJoinPoints = 28;
        assertThat("#Total Join Points?", joinPoints, Matchers.hasSize(totalExpectedJoinPoints));
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_GET);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_SET);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_EXECUTION,
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_CALL,
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_EXECUTION,
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyClass()}");
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_CALL,
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyClass()}");
        assertThat("All Join Points asserted?", totalExpectedJoinPoints, is(0));
    }

    @Test
    public void universeInitWithMarkerInterface() throws Exception {
        // act / when
        final List<AjJoinPoint> joinPoints = createAndInitializeUniverse(MarkerInterface.class);

        // assert / then
        int totalExpectedJoinPoints = 0;
        assertThat("#Total Join Points?", joinPoints, Matchers.hasSize(totalExpectedJoinPoints));
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_GET);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_SET);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_EXECUTION);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_CALL);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_EXECUTION);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_CALL);
        assertThat("All Join Points asserted?", totalExpectedJoinPoints, is(0));
    }


    @Test
    public void universeInitWithInnerEmptyClass() throws Exception {
        // act / when
        final List<AjJoinPoint> joinPoints = createAndInitializeUniverse(EmptyInnerClass.class);

        // assert / then
        int totalExpectedJoinPoints = 30;
        assertThat("#Total Join Points?", joinPoints, Matchers.hasSize(totalExpectedJoinPoints));
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_GET,
                "AjJoinPoint{type=FIELD_GET, #apply=0, field=final org.failearly.ajunit.universe.impl.AjUniverseInitializerTest org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyInnerClass.this$0}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_SET,
                "AjJoinPoint{type=FIELD_SET, #apply=0, field=final org.failearly.ajunit.universe.impl.AjUniverseInitializerTest org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyInnerClass.this$0}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_EXECUTION,
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_CALL,
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_EXECUTION,
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyInnerClass(org.failearly.ajunit.universe.impl.AjUniverseInitializerTest)}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_CALL,
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyInnerClass(org.failearly.ajunit.universe.impl.AjUniverseInitializerTest)}");
        assertThat("All Join Points asserted?", totalExpectedJoinPoints, is(0));
    }

    @Test
    public void universeInitWithComplexClass() throws Exception {
        // act / when
        final List<AjJoinPoint> joinPoints = createAndInitializeUniverse(Inherited.class);

        // assert / then
        int totalExpectedJoinPoints = 38;
        assertThat("#Total Join Points?", joinPoints, Matchers.hasSize(totalExpectedJoinPoints));
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_GET,
                "AjJoinPoint{type=FIELD_GET, #apply=0, field=private int org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited.intField}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_SET,
                "AjJoinPoint{type=FIELD_SET, #apply=0, field=private int org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited.intField}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_EXECUTION,
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notifyAll()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public int org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited.getIntField()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public void org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited.setIntField(int)}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_CALL,
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notifyAll()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public int org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited.getIntField()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public void org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited.setIntField(int)}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_EXECUTION,
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$OtherClass()}",
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$OtherClass(org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$OtherClass)}",
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited(int)}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_CALL,
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$OtherClass()}",
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$OtherClass(org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$OtherClass)}",
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$Inherited(int)}"
        );
        assertThat("All Join Points asserted?", totalExpectedJoinPoints, is(0));
    }

    @Test
    public void universeInitWithTwoClasses() throws Exception {
        // act / when
        final List<AjJoinPoint> joinPoints = createAndInitializeUniverse(EmptyClass.class, SecondClass.class);

        // assert / then
        int totalExpectedJoinPoints = 30;
        assertThat("#Total Join Points?", joinPoints, Matchers.hasSize(totalExpectedJoinPoints));
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_GET);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.FIELD_SET);
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_EXECUTION,
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_EXECUTION, #apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.METHOD_CALL,
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notify()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public native int java.lang.Object.hashCode()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "AjJoinPoint{type=METHOD_CALL, #apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_EXECUTION,
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyClass()}",
                "AjJoinPoint{type=CONSTRUCTOR_EXECUTION, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$SecondClass()}"
        );
        totalExpectedJoinPoints -= assertJoinPointsByType(joinPoints, AjJoinPointType.CONSTRUCTOR_CALL,
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=public java.lang.Object()}",
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$EmptyClass()}",
                "AjJoinPoint{type=CONSTRUCTOR_CALL, #apply=0, constructor=private org.failearly.ajunit.universe.impl.AjUniverseInitializerTest$SecondClass()}"
        );
        assertThat("All Join Points asserted?", totalExpectedJoinPoints, is(0));
    }


    private List<AjJoinPoint> createAndInitializeUniverse(Class<?>... classes) {
        final List<AjJoinPoint> joinPoints = new LinkedList<>();
        final AjUniverseImpl universe = createUniverse(joinPoints);
        final AjUniverseInitializer initializer = new AjUniverseInitializer(universe);

        // act / when
        initializer.initialize(toClassCollection(classes));

        return joinPoints;
    }

    private int assertJoinPointsByType(
            final List<AjJoinPoint> joinPoints,
            final AjJoinPointType type,
            final String... expectedJoinPoints) {
        final List<String> actualJoinPoints = selectJoinPoints(joinPoints, type);
        assertThat("Expected join points of type " + type + "?",
                actualJoinPoints,
                Matchers.containsInAnyOrder(expectedJoinPoints)
        );

        return actualJoinPoints.size();
    }

    private List<String> selectJoinPoints(final List<AjJoinPoint> joinPoints, final AjJoinPointType type) {
        final List<String> selected = new LinkedList<>();
        visitJoinPoints(joinPoints, new AjJoinPointVisitor() {
            @Override
            public void visit(final AjJoinPoint joinPoint) {
                if (type == joinPoint.getJoinPointType()) {
                    selected.add(joinPoint.toString());
                }
            }
        });
        return selected;
    }

    private static void visitJoinPoints(List<AjJoinPoint> joinPoints, AjJoinPointVisitor visitor) {
        for (AjJoinPoint joinPoint : joinPoints) {
            visitor.visit(joinPoint);
        }
    }


    private static Collection<Class<?>> toClassCollection(Class<?>... classes) {
        return Arrays.asList(classes);
    }

    private AjUniverseImpl createUniverse(final List<AjJoinPoint> joinPoints) {
        return new AjUniverseImpl("my-universe", joinPoints);
    }
}
