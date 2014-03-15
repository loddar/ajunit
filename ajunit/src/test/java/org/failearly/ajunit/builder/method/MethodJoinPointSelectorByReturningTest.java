/*
 * ajUnit - Unit Testing AspectJ.
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
package org.failearly.ajunit.builder.method;

import org.failearly.ajunit.builder.AbstractJoinPointSelectorTest;
import org.failearly.ajunit.builder.MethodJoinPointSelector;
import org.failearly.ajunit.builder.TestSubject4;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelector}.
 *
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturningVoid()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturningPrimitive()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturningCollection()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturningEnum()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturningArray()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturning(Class)
 */
public abstract class MethodJoinPointSelectorByReturningTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected MethodJoinPointSelectorByReturningTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject4.class);
    }

    @Test
    public void byReturningVoid() throws Exception {
        // act / when
        selectorBuilder.byReturningVoid();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public void org.failearly.ajunit.builder.TestSubject4.setInt(int)",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()"
        );
    }

    @Test
    public void byReturningPrimitive() throws Exception {
        // act / when
        selectorBuilder.byReturningPrimitive();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public boolean org.failearly.ajunit.builder.TestSubject4.getBoolean()",
                "public int org.failearly.ajunit.builder.TestSubject4.getInt()",
                "public byte org.failearly.ajunit.builder.TestSubject4.getByte()",
                "public short org.failearly.ajunit.builder.TestSubject4.getShort()",
                "public long org.failearly.ajunit.builder.TestSubject4.getLong()",
                "public float org.failearly.ajunit.builder.TestSubject4.getFloat()",
                "public double org.failearly.ajunit.builder.TestSubject4.getDouble()",
                // java.lang.Object
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()"
        );
    }
    

    @Test
    public void byReturningCollection() throws Exception {
        // act / when
        selectorBuilder.byReturningCollection();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public java.util.Collection org.failearly.ajunit.builder.TestSubject4.getCollection()",
                "public java.util.LinkedList org.failearly.ajunit.builder.TestSubject4.getList()",
                "public java.util.HashSet org.failearly.ajunit.builder.TestSubject4.getSet()",
                "public java.util.Vector org.failearly.ajunit.builder.TestSubject4.getVector()"
                // java.lang.Object

        );
    }

    @Test
    public void byReturningEnum() throws Exception {
        // act / when
        selectorBuilder.byReturningEnum();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public org.failearly.ajunit.builder.LogicalOperator org.failearly.ajunit.builder.TestSubject4.getEnum()"
                // java.lang.Object

        );
    }

    @Test
    public void byReturningArray() throws Exception {
        // act / when
        selectorBuilder.byReturningArray();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject4.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject4.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject4.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject4.getIntMatrix()"
                // java.lang.Object

        );
    }

    @Test
    public void byReturning() throws Exception {
        // act / when
        selectorBuilder.byReturning(int.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject4
                "public int org.failearly.ajunit.builder.TestSubject4.getInt()",
                // java.lang.Object
                "public native int java.lang.Object.hashCode()"
        );
    }
}
