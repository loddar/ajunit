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

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link MethodParametersSelector}.
 */
public abstract class MethodArgumentsSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    private MethodParametersSelector methodArgumentsSelector;

    protected MethodArgumentsSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject7.class);
    }

    @Override
    protected void doAdditionalSetup(MethodJoinPointSelector selectorBuilder) {
        methodArgumentsSelector = selectorBuilder.arguments(LogicalOperator.OR);
    }

    @Test
    public void endArgumentsSelector() throws Exception {
        // act / when
        final MethodJoinPointSelector instance = methodArgumentsSelector.endParametersSelector();

        // assert / then
        assertThat("endArgumentsSelector() returns correct selector builder?", instance, sameInstance(selectorBuilder));
    }

    @Test
    public void byNoArguments() throws Exception {
        // act / when
        methodArgumentsSelector.byNoParameters().endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method0()",
                // java.lang.Object
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
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
    public void byNumberOfArgumentsEquals() throws Exception {
        // act / when
        methodArgumentsSelector.byNumberOfParameters(1, NumberComparator.EQUALS).endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                // java.lang.Object
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void byNumberOfArgumentsLessThen() throws Exception {
        // act / when
        methodArgumentsSelector.byNumberOfParameters(1, NumberComparator.LESS_THEN).endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method0()",
                // java.lang.Object
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
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
    public void byNumberOfArgumentsLessEqualsThen() throws Exception {
        // act / when
        methodArgumentsSelector.byNumberOfParameters(1, NumberComparator.LESS_EQUALS_THEN).endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method0()",
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                // java.lang.Object
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public java.lang.String java.lang.Object.toString()",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void byNumberOfArgumentsGreaterThen() throws Exception {
        // act / when
        methodArgumentsSelector.byNumberOfParameters(1, NumberComparator.GREATER_THEN).endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"
        );
    }

    @Test
    public void byNumberOfArgumentsGreaterEqualsThen() throws Exception {
        // act / when
        methodArgumentsSelector.byNumberOfParameters(1, NumberComparator.GREATER_EQUALS_THEN).endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"
        );
    }

    @Test
    public void endArgumentPosition() throws Exception {
        // act / when
        final MethodParametersSelector instance = methodArgumentsSelector.parameterTypes(Position.FIRST, 0, 1).endParameterType();

        // assert / then
        assertThat("endArgumentsSelector() returns correct selector builder?", instance, sameInstance(methodArgumentsSelector));
    }

    @Test
    public void byArgumentPositionByClass() throws Exception {
        // act / when
        methodArgumentsSelector.parameterTypes(Position.FIRST, 0)
                    .byClass(int.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)"
        );
    }

    @Test
    public void byArgumentPositionsByClass() throws Exception {
        // act / when
        methodArgumentsSelector.parameterTypes(Position.FIRST, 0, 1)
                    .byClass(String.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byArgumentPositionsByPrimitive() throws Exception {
        // act / when
        methodArgumentsSelector.parameterTypes(Position.FIRST, 0)
                    .byPrimitive()
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException"
        );
    }

    @Test
    public void byArgumentPositionsLast() throws Exception {
        // act / when
        methodArgumentsSelector.parameterTypes(Position.LAST, 0)
                    .byClass(int.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"
        );
    }

    @Test
    public void byAnyOfArgumentTypes() throws Exception {
        // act / when
        methodArgumentsSelector.parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byClass(int.class)
                    .byClass(String.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"
        );
    }

    @Test
    public void byAllOfArgumentTypes() throws Exception {
        // act / when
        methodArgumentsSelector.parameterTypes(ListOperator.EACH)
                    .byClass(int.class)
                    .byClass(String.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)"
        );
    }


    @Test
    public void byNoneOfArgumentTypes() throws Exception {
        // act / when
        methodArgumentsSelector.parameterTypes(ListOperator.NONE)
                    .byClass(int.class)
                    .byClass(String.class)
                .endParameterType();

        // assert / then
        assertBuildJoinPointSelector(
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }


    @Test
    public void and() throws Exception {
        // act / when
        methodArgumentsSelector.and()
                .parameterTypes(ListOperator.NONE)
                    .byClass(int.class)
                    .byClass(String.class)
                .endParameterType()
                .parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byClass(Object.class)
                .endParameterType()
            .end()
        .endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void or() throws Exception {
        // act / when
        methodArgumentsSelector.or()
                .parameterTypes(ListOperator.EACH)
                    .byClass(int.class)
                    .byClass(String.class)
                .endParameterType()
                .parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byClass(Object.class)
                .endParameterType()
            .end()
        .endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void nor() throws Exception {
        // act / when
        methodArgumentsSelector.nor()
                .parameterTypes(ListOperator.EACH)
                    .byClass(int.class)
                    .byClass(String.class)
                .endParameterType()
                .parameterTypes(ListOperator.AT_LEAST_ONE)
                    .byClass(Object.class)
                .endParameterType()
            .end()
        .endParametersSelector();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject7.method0()",
                // java.lang.Object
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public java.lang.String java.lang.Object.toString()",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException"
        );
    }
}
