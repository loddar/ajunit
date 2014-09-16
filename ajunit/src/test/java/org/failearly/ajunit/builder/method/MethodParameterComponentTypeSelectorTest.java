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

import org.failearly.ajunit.builder.AbstractBaseClass;
import org.failearly.ajunit.builder.AbstractJoinPointSelectorTest;
import org.failearly.ajunit.builder.AnyAnnotation;
import org.failearly.ajunit.builder.TestSubject9;
import org.failearly.ajunit.builder.types.ListOperator;
import org.failearly.ajunit.builder.types.StringMatcher;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * MethodArgumentComponentTypeSelectorTest contains tests for {@link MethodParameterTypeSelector#componentType()}.
 */
public abstract class MethodParameterComponentTypeSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    private MethodParameterComponentTypeSelector parameterComponentTypeSelector;

    protected MethodParameterComponentTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject9.class);
    }

    @Override
    protected void doAdditionalSetup(MethodJoinPointSelector selectorBuilder) {
        this.parameterComponentTypeSelector = selectorBuilder
                                                    .parameters()
                                                        .parameterTypes(ListOperator.AT_LEAST_ONE)
                                                            .componentType();
    }

    @Test
    public void byClass() throws Exception {
        // act / when
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
                    .byClassName("Any", StringMatcher.STARTS_WITH)
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
        parameterComponentTypeSelector
                    .byPackageName("ajunit.builder", StringMatcher.CONTAINS)
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
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
        parameterComponentTypeSelector
                    .byTypeAnnotation(AnyAnnotation.class)
                .endComponentType();

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject9.method4(org.failearly.ajunit.builder.TestSubject1[])",
                "public void org.failearly.ajunit.builder.TestSubject9.method6(org.failearly.ajunit.builder.AnyInterface[])"
        );
    }


}
