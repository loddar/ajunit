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
import org.failearly.ajunit.builder.AnyAnnotation;
import org.failearly.ajunit.builder.AnyOtherAnnotation;
import org.failearly.ajunit.builder.TestSubject10;
import org.failearly.ajunit.builder.types.LogicalOperator;
import org.failearly.ajunit.builder.types.Position;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

/**
 * MethodsArgumentAnnotationSelectorTest contains the tests for MethodsArgumentAnnotationSelector.
 */
@SuppressWarnings("all")
public abstract class MethodParameterAnnotationSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {
    private MethodParametersSelector methodParametersSelector;

    protected MethodParameterAnnotationSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject10.class);
    }

    @Override
    protected void doAdditionalSetup(MethodJoinPointSelector selectorBuilder) {
        methodParametersSelector = selectorBuilder.parameters();
    }

    /**
     * (..,@(AnyAnnotation || AnyOtherAnnotation) (*),..)
     */
    @Test
    public void anyParameterAnnotationAtLeastOneAnnotation() throws Exception {
        // act / when
        methodParametersSelector.anyParameterAnnotation()
                    .byParameterAnnotations(LogicalOperator.OR, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method0(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method1(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)"
        );
    }

    /**
     * (..,@AnyAnnotation @AnyOtherAnnotation (*),..)
     */
    @Test
    public void anyParameterAnnotationEachAnnotationMustBeProvided() throws Exception {
        // act / when
        methodParametersSelector.anyParameterAnnotation()
                    .byParameterAnnotations(LogicalOperator.AND, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)"
        );
    }

    /**
     * (..,! @(AnyAnnotation || AnyOtherAnnotation) (*),..)
     */
    @Test
    public void anyParameterAnnotationNoneOfAnnotations() throws Exception {
        // act / when
        methodParametersSelector.anyParameterAnnotation()
                    .byParameterAnnotations(LogicalOperator.NOR, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method0(int,java.lang.String)",
                // "public void org.failearly.ajunit.builder.TestSubject10.method1(int,java.lang.String)",
                // "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method3(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method4(int,java.lang.String)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void byExistingParameterAnnotation() throws Exception {
        // act / when
        methodParametersSelector.anyParameterAnnotation()
                    .byExistingParameterAnnotation()
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method0(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method1(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method3(int,java.lang.String)"
        );
    }

    @Test
    public void byMissingParameterAnnotation() throws Exception {
        // act / when
        methodParametersSelector.anyParameterAnnotation()
                    .byMissingParameterAnnotation()
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method0(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method3(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method4(int,java.lang.String)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void anyParameterAnnotationNoneOfAnnotationsButAnyExisting() throws Exception {
        // act / when
        methodParametersSelector.anyParameterAnnotation()
                    .byParameterAnnotations(LogicalOperator.NOR, AnyAnnotation.class, AnyOtherAnnotation.class)
                    .byExistingParameterAnnotation()
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method3(int,java.lang.String)"
        );
    }


    /**
     * (@(AnyAnnotation || AnyOtherAnnotation) (*), @(AnyAnnotation || AnyOtherAnnotation) (*), ..)
     */
    @Test
    public void allParameterAnnotationAtLeastOneAnnotation() throws Exception {
        // act / when
        methodParametersSelector.parameterAnnotations(Position.FIRST, 0, 1)
                    .byParameterAnnotations(LogicalOperator.OR, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method1(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)"
        );
    }

    /**
     * (@AnyAnnotation @AnyOtherAnnotation) (*), @AnyAnnotation @AnyOtherAnnotation) (*), ..)
     */
    @Test
    public void allParameterAnnotationEachAnnotation() throws Exception {
        // act / when
        methodParametersSelector.parameterAnnotations(Position.FIRST, 0, 1)
                    .byParameterAnnotations(LogicalOperator.AND, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                // "public void org.failearly.ajunit.builder.TestSubject10.method1(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)"
        );
    }



}
