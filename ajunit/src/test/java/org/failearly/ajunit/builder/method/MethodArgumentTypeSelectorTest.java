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
 * Tests for {@link org.failearly.ajunit.builder.method.MethodArgumentTypeSelector}.
 */
public abstract class MethodArgumentTypeSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    private MethodArgumentsSelector methodArgumentsSelector;

    protected MethodArgumentTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject8.class);
    }

    @Override
    protected void doAdditionalSetup(MethodJoinPointSelector selectorBuilder) {
        methodArgumentsSelector = selectorBuilder.byArguments(LogicalOperator.OR);
    }

    @Test
    public void byClass() throws Exception {
        // act / when
        methodArgumentsSelector.byArgumentTypes(ListLogicalOperator.ANY_OF)
                    .byClass(String.class)
                .endArgumentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byClassName() throws Exception {
        // act / when
        methodArgumentsSelector.byArgumentTypes(ListLogicalOperator.ANY_OF)
                    .byClassName("Str", StringMatcherType.STARTS_WITH)
                .endArgumentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byExtending() throws Exception {
        // act / when
        methodArgumentsSelector.byArgumentTypes(ListLogicalOperator.ANY_OF)
                    .byExtending(Object.class)
                .endArgumentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }

    @Test
    public void byNotExtending() throws Exception {
        // act / when
        methodArgumentsSelector.byArgumentTypes(ListLogicalOperator.ALL_OF)
                    .byNotExtending(Object.class)
                .endArgumentType();

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
        methodArgumentsSelector.byArgumentTypes(ListLogicalOperator.ANY_OF)
                    .byImplementingAnyOf(Serializable.class)
                .endArgumentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byImplementingAllOf() throws Exception {
        // act / when
        methodArgumentsSelector.byArgumentTypes(ListLogicalOperator.ANY_OF)
                    .byImplementingAllOf(Serializable.class)
                .endArgumentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject8.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byImplementingNoneOf() throws Exception {
        // act / when
        methodArgumentsSelector.byArgumentTypes(ListLogicalOperator.ALL_OF)
                    .byImplementingNoneOf(Serializable.class)
                .endArgumentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject8.method1(int)",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }


}
