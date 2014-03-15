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
public abstract class MethodJoinPointSelectorByReturnArrayTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected MethodJoinPointSelectorByReturnArrayTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject4.class);
    }

    @Test
    public void byArray() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArray()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject4.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject4.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject4.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject4.getIntMatrix()"

        );
    }

    @Test
    public void byArrayVector() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(NamedDimension.VECTOR, DimensionComparator.EQUALS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject4.getStringArray()",
                "public int[] org.failearly.ajunit.builder.TestSubject4.getIntArray()"

        );
    }

    @Test
    public void byArrayMatrix() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(NamedDimension.MATRIX, DimensionComparator.EQUALS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject4.getStringMatrix()",
                "public int[][] org.failearly.ajunit.builder.TestSubject4.getIntMatrix()"

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
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject4.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject4.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject4.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject4.getIntMatrix()"
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
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject4.getStringMatrix()",
                "public int[][] org.failearly.ajunit.builder.TestSubject4.getIntMatrix()"
        );
    }

    @Test
    public void byArrayGreaterThenVector() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(NamedDimension.VECTOR, DimensionComparator.GREATER_THEN)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject4.getStringMatrix()",
                "public int[][] org.failearly.ajunit.builder.TestSubject4.getIntMatrix()"
        );
    }

    @Test
    public void byArrayMaxMatrix() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(NamedDimension.MATRIX, DimensionComparator.MAX)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject4.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject4.getStringMatrix()",
                "public int[] org.failearly.ajunit.builder.TestSubject4.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject4.getIntMatrix()"
        );
    }

    @Test
    public void byArrayLessThenMatrix() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byArrayDimension(NamedDimension.MATRIX, DimensionComparator.LESS_THEN)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject4.getStringArray()",
                "public int[] org.failearly.ajunit.builder.TestSubject4.getIntArray()"
        );
    }


}
