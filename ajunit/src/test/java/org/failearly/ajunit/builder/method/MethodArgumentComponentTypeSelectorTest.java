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
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * MethodArgumentComponentTypeSelectorTest contains tests for {@link org.failearly.ajunit.builder.method.MethodArgumentTypeSelector#componentType()}.
 */
public abstract class MethodArgumentComponentTypeSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    private MethodArgumentComponentTypeSelector argumentComponentTypeSelector;

    protected MethodArgumentComponentTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject9.class);
    }

    @Override
    protected void doAdditionalSetup(MethodJoinPointSelector selectorBuilder) {
        this.argumentComponentTypeSelector = selectorBuilder
                                                    .arguments(LogicalOperator.OR)
                                                        .argumentTypes(ListLogicalOperator.ANY_OF)
                                                            .componentType();
    }

    @Test
    public void byClass() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byClass(String.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method2(java.lang.String[])"
        );
    }

    @Test
    public void byClassName() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byClassName("Any", StringMatcherType.STARTS_WITH)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method3(org.failearly.ajunit.builder.AnyEnum[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method5(org.failearly.ajunit.builder.AnyAnnotation[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method6(org.failearly.ajunit.builder.AnyInterface[])"
        );
    }

    @Test
    public void byPackageName() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byPackageName("ajunit.builder", StringMatcherType.CONTAINS)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method3(org.failearly.ajunit.builder.AnyEnum[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method4(org.failearly.ajunit.builder.TestSubject1[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method5(org.failearly.ajunit.builder.AnyAnnotation[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method6(org.failearly.ajunit.builder.AnyInterface[])"
        );
    }

    @Test
    public void byExtending() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byExtending(AbstractBaseClass.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
            "public void org.failearly.ajunit.builder.TestSubject9.method4(org.failearly.ajunit.builder.TestSubject1[])"
        );
    }

    @Test
    public void byImplementingAnyOf() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byImplementingAnyOf(Serializable.class, Annotation.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method2(java.lang.String[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method3(org.failearly.ajunit.builder.AnyEnum[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method5(org.failearly.ajunit.builder.AnyAnnotation[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method7(java.lang.Long[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method8(java.util.HashMap[])"
        );
    }

    @Test
    public void byImplementingAllOf() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byImplementingAllOf(Serializable.class, Map.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method8(java.util.HashMap[])"
        );
    }

    @Test
    public void byImplementingNoneOf() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byImplementingNoneOf(Serializable.class, Map.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method1(int[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method4(org.failearly.ajunit.builder.TestSubject1[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method5(org.failearly.ajunit.builder.AnyAnnotation[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method6(org.failearly.ajunit.builder.AnyInterface[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method9(java.util.Set[])"
        );
    }

    @Test
    public void byNotExtending() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byNotExtending(Object.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method1(int[])"
        );
    }



    @Test
    public void byAnnotation() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byAnnotation()
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method5(org.failearly.ajunit.builder.AnyAnnotation[])"
        );
    }

    @Test
    public void byEnum() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byEnum()
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method3(org.failearly.ajunit.builder.AnyEnum[])"
        );
    }

    @Test
    public void byInterface() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byInterface()
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method5(org.failearly.ajunit.builder.AnyAnnotation[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method6(org.failearly.ajunit.builder.AnyInterface[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method9(java.util.Set[])"
        );
    }

    @Test
    public void byPrimitive() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byPrimitive()
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method1(int[])"
        );
    }

    @Test
    public void byPrimitiveWrapperType() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byPrimitiveWrapperType()
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method7(java.lang.Long[])"
        );
    }


    @Test
    public void byCollection() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byCollection()
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method9(java.util.Set[])"
        );
    }

    @Test
    public void byMap() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byMap()
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method8(java.util.HashMap[])"
        );
    }

    @Test
    public void byTypeAnnotation() throws Exception {
        // act / when
        argumentComponentTypeSelector
                    .byTypeAnnotation(AnyAnnotation.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method4(org.failearly.ajunit.builder.TestSubject1[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method6(org.failearly.ajunit.builder.AnyInterface[])"
        );
    }


}
