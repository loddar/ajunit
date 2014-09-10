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

/**
 * Tests for {@link MethodParameterTypeSelector}.
 */
public abstract class MethodParameterTypeSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    private MethodParametersSelector methodParametersSelector;

    protected MethodParameterTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject8.class);
    }

    @Override
    protected void doAdditionalSetup(MethodJoinPointSelector selectorBuilder) {
        methodParametersSelector = selectorBuilder.arguments(LogicalOperator.OR);
    }

    @Test
    public void byClass() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byClass(String.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byClassName() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byClassName("Str", StringMatcher.STARTS_WITH)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byExtending() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byExtending(AbstractBaseClass.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method5(org.failearly.ajunit.builder.TestSubject1)"
        );
    }

    @Test
    public void byNotExtending() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.EACH)
                    .byNotExtending(Object.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method1(int)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException"
        );
    }

    @Test
    public void byImplementingAnyOf() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byImplementingAnyOf(Serializable.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)",
                "public void org.failearly.ajunit.builder.TestSubject8.method4(org.failearly.ajunit.builder.AnyEnum)",
                "public void org.failearly.ajunit.builder.TestSubject8.method8(java.lang.Long)",
                "public void org.failearly.ajunit.builder.TestSubject8.method9(java.util.HashMap)",
                "public void org.failearly.ajunit.builder.TestSubject8.method11(int[][])"
        );
    }

    @Test
    public void byImplementingAllOf() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byImplementingAllOf(Serializable.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)",
                "public void org.failearly.ajunit.builder.TestSubject8.method4(org.failearly.ajunit.builder.AnyEnum)",
                "public void org.failearly.ajunit.builder.TestSubject8.method8(java.lang.Long)",
                "public void org.failearly.ajunit.builder.TestSubject8.method9(java.util.HashMap)",
                "public void org.failearly.ajunit.builder.TestSubject8.method11(int[][])"
        );
    }

    @Test
    public void byImplementingNoneOf() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.EACH)
                    .byImplementingNoneOf(Serializable.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject8.method5(org.failearly.ajunit.builder.TestSubject1)",
                "public void org.failearly.ajunit.builder.TestSubject8.method6(org.failearly.ajunit.builder.AnyAnnotation)",
                "public void org.failearly.ajunit.builder.TestSubject8.method7(org.failearly.ajunit.builder.AnyInterface)",
                "public void org.failearly.ajunit.builder.TestSubject8.method10(java.util.Set)",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void byPackageName() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byPackageName("lang", StringMatcher.CONTAINS)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)",
                "public void org.failearly.ajunit.builder.TestSubject8.method8(java.lang.Long)",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void byEnum() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byEnum()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method4(org.failearly.ajunit.builder.AnyEnum)"
        );
    }

    @Test
    public void byTypeAnnotation() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byTypeAnnotation(AnyAnnotation.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method5(org.failearly.ajunit.builder.TestSubject1)",
                "public void org.failearly.ajunit.builder.TestSubject8.method7(org.failearly.ajunit.builder.AnyInterface)"
        );
    }

    @Test
    public void byAnnotation() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byAnnotation()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method6(org.failearly.ajunit.builder.AnyAnnotation)"
        );
    }

    @Test
    public void byInterface() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byInterface()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method6(org.failearly.ajunit.builder.AnyAnnotation)",
                "public void org.failearly.ajunit.builder.TestSubject8.method7(org.failearly.ajunit.builder.AnyInterface)",
                "public void org.failearly.ajunit.builder.TestSubject8.method10(java.util.Set)"
        );
    }

    @Test
    public void byPrimitiveWrapperType() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byPrimitiveWrapperType()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method8(java.lang.Long)"
        );
    }

    @Test
    public void byCollection() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byCollection()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method10(java.util.Set)"
        );
    }

    @Test
    public void byMap() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byMap()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method9(java.util.HashMap)"
        );
    }

    @Test
    public void byArray() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byArray()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method11(int[][])"
        );
    }

    @Test
    public void byArrayDimension() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byArrayDimension(1, DimensionComparator.GREATER_THEN)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method11(int[][])"
        );
    }

    @Test
    public void and() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                        .and()
                            .byImplementingAnyOf(Serializable.class)
                            .byPackageName("java", StringMatcher.STARTS_WITH)
                        .end()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)",
                "public void org.failearly.ajunit.builder.TestSubject8.method8(java.lang.Long)",
                "public void org.failearly.ajunit.builder.TestSubject8.method9(java.util.HashMap)"
        );
    }

    @Test
    public void or() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                        .or()
                            .byImplementingAnyOf(Serializable.class)
                            .byPackageName("java", StringMatcher.STARTS_WITH)
                        .end()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)",
                "public void org.failearly.ajunit.builder.TestSubject8.method4(org.failearly.ajunit.builder.AnyEnum)",
                "public void org.failearly.ajunit.builder.TestSubject8.method8(java.lang.Long)",
                "public void org.failearly.ajunit.builder.TestSubject8.method9(java.util.HashMap)",
                "public void org.failearly.ajunit.builder.TestSubject8.method10(java.util.Set)",
                "public void org.failearly.ajunit.builder.TestSubject8.method11(int[][])",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void nor() throws Exception {
        // act / when
        methodParametersSelector.parameterTypes(ListOperator.EACH)
                        .nor()
                            .byImplementingAnyOf(Serializable.class)
                            .byPackageName("java", StringMatcher.STARTS_WITH)
                        .end()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject8.method5(org.failearly.ajunit.builder.TestSubject1)",
                "public void org.failearly.ajunit.builder.TestSubject8.method6(org.failearly.ajunit.builder.AnyAnnotation)",
                "public void org.failearly.ajunit.builder.TestSubject8.method7(org.failearly.ajunit.builder.AnyInterface)",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"
        );
    }


}
