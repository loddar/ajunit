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

import org.failearly.ajunit.builder.*;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelector}.
 *
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturnType(org.failearly.ajunit.builder.LogicalOperator)
 */
public abstract class ReturnTypeSelectorArrayTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected ReturnTypeSelectorArrayTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject5.class);
    }

    @Test
    public void byArray() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArray()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()",
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"

        );
    }

    @Test
    public void byNotArray() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.NONE_OF)
                        .byArray()
                    .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject5.setInt(int)",
                "public boolean org.failearly.ajunit.builder.TestSubject5.getBoolean()",
                "public java.lang.String org.failearly.ajunit.builder.TestSubject5.getString()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public java.lang.String java.lang.Object.toString()",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()"
        );
    }

    @Test
    public void byArrayVector() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(1, DimensionComparator.EQUALS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()"

        );
    }

    @Test
    public void byArrayMatrix() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(2, DimensionComparator.EQUALS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()"

        );
    }

    @Test
    public void byArrayMinVector() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(1, DimensionComparator.MIN)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()",
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
    }

    @Test
    public void byArrayMinMatrix() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(2, DimensionComparator.MIN)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()",
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
    }

    @Test
    public void byArrayGreaterThenVector() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(1, DimensionComparator.GREATER_THEN)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()",
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
    }

    @Test
    public void byArrayMaxMatrix() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(2, DimensionComparator.MAX)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()",
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()"
        );
    }

    @Test
    public void byArrayLessThenMatrix() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(2, DimensionComparator.LESS_THEN)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()"

        );
    }
}
