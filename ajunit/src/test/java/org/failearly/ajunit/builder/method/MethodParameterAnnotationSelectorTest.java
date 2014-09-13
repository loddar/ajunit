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
        methodParametersSelector = selectorBuilder.arguments(LogicalOperator.OR);
    }

    @Test
    public void anyPositionbyArgumentAnnotation() throws Exception {
        // act / when
        methodParametersSelector.parameterAnnotations(ListOperator.AT_LEAST_ONE)
                    .byParameterAnnotations(ListOperator.AT_LEAST_ONE, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method1(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)"
        );
    }

    @Test
    public void eachPositionbyArgumentAnnotation() throws Exception {
        // act / when
        methodParametersSelector.parameterAnnotations(ListOperator.EACH)
                    .byParameterAnnotations(ListOperator.AT_LEAST_ONE, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method2(int,java.lang.String)"
        );
    }


    @Test
    public void noneOfPositionbyArgumentAnnotation() throws Exception {
        // act / when
        methodParametersSelector.parameterAnnotations(ListOperator.NONE)
                    .byParameterAnnotations(ListOperator.AT_LEAST_ONE, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endParameterAnnotation();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject10.method3(int,java.lang.String)",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)"
        );
    }
}
