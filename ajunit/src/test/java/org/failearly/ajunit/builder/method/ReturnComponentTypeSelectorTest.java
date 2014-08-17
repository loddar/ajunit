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

import java.io.Serializable;
import java.util.Map;

/**
 * Tests for {@link MethodJoinPointSelector}.
 *
 * @see MethodJoinPointSelector#returnType(org.failearly.ajunit.builder.LogicalOperator)
 */
public abstract class ReturnComponentTypeSelectorTest extends AbstractJoinPointSelectorTest<ReturnComponentTypeSelector> {

    protected ReturnComponentTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject5.class);
    }

    @Test
    public void byPrimitive() throws Exception {
        // act / when
        selectorBuilder.byPrimitive().endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()"
        );
    }

    @Test
    public void byPrimitiveWrapperType() throws Exception {
        // act / when
        selectorBuilder.byPrimitiveWrapperType().endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()"
        );
    }

    @Test
    public void byEnum() throws Exception {
        // act / when
        selectorBuilder.byEnum().endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()"
        );
    }

    @Test
    public void byAnnotation() throws Exception {
        // act / when
        selectorBuilder.byAnnotation().endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()"
        );
    }

    @Test
    public void byInterface() throws Exception {
        // act / when
        selectorBuilder.byInterface().endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()"
        );
    }

    @Test
    public void byCollectionOrMap() throws Exception {
        // act / when
        selectorBuilder.or()
                            .byCollection()
                            .byMap()
                       .end();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
    }


    @Test
    public void byClass() throws Exception {
        // act / when
        selectorBuilder.byClass(String.class).endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()"
        );
    }

    @Test
    public void byExtending() throws Exception {
        // act / when
        selectorBuilder.byExtending(String.class).endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()"
        );
    }

    @Test
    public void byNotExtending() throws Exception {
        // act / when
        selectorBuilder.byNotExtending(Object.class).endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()"
        );
    }

    @Test
    public void byImplementingAnyOf() throws Exception {
        // act / when
        selectorBuilder.byImplementingAnyOf(AnyInterface.class, Serializable.class).endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.lang.String[] org.failearly.ajunit.builder.TestSubject5.getStringArray()",
                "public java.lang.String[][] org.failearly.ajunit.builder.TestSubject5.getStringMatrix()",
                "public org.failearly.ajunit.builder.AnyEnum[] org.failearly.ajunit.builder.TestSubject5.getEnumVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()",
                "public java.lang.Integer[][] org.failearly.ajunit.builder.TestSubject5.getIntegerMatrix()",
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
    }

    @Test
    public void byImplementingAllOf() throws Exception {
        // act / when
        selectorBuilder.byImplementingAllOf(Map.class, Serializable.class);

        // assert / then
        assertBuildJoinPointSelector(
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()"
        );
    }

    @Test
    public void byImplementingNoneOf() throws Exception {
        // act / when
        selectorBuilder.byImplementingNoneOf(Map.class, Serializable.class).endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public int[] org.failearly.ajunit.builder.TestSubject5.getIntArray()",
                "public int[][] org.failearly.ajunit.builder.TestSubject5.getIntMatrix()",
                "public org.failearly.ajunit.builder.AnyAnnotation[] org.failearly.ajunit.builder.TestSubject5.getAnnotationVector()",
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()"
        );
    }

    @Test
    public void byClassName() throws Exception {
        // act / when
        selectorBuilder.byClassName("Map", StringMatcherType.ENDS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()"
        );
    }

    @Test
    public void byPackageName() throws Exception {
        // act / when
        selectorBuilder.byPackageName("java.util", StringMatcherType.EQUALS).endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public java.util.HashMap[][][] org.failearly.ajunit.builder.TestSubject5.getHashMap()",
                "public java.util.LinkedList[][][] org.failearly.ajunit.builder.TestSubject5.getLinkedList()"
        );
    }

    @Test
    public void byTypeAnnotation() throws Exception {
        // act / when
        selectorBuilder.byTypeAnnotation(AnyAnnotation.class).endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.AnyInterface[] org.failearly.ajunit.builder.TestSubject5.getAnyInterface()"
        );
    }

}
