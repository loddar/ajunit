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

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelector}.
 *
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byReturnType(org.failearly.ajunit.builder.LogicalOperator)
 */
public abstract class MethodJoinPointSelectorByReturnTypeTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected MethodJoinPointSelectorByReturnTypeTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject3.class);
    }

    @Test
    public void endReturnType() throws Exception {
        // act / when
        final MethodJoinPointSelector instance = selectorBuilder.byReturnType(LogicalOperator.OR).endReturnType();

        // assert / then
        assertThat("endReturnType() returns correct selector builder?", instance, sameInstance(selectorBuilder));
    }

    @Test
    public void byReturningVoid() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byClass(void.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject3
                "public void org.failearly.ajunit.builder.TestSubject3.setAnyValue(int)",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()"
        );
    }

    @Test
    public void byReturningClassName() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byClassName("TestSub", StringMatcherType.STARTS_WITH)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject3
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()"
        );
    }

    @Test
    public void byImplementingAnyOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byImplementingAnyOf(AnyInterface.class, Serializable.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()",
                "public java.lang.String java.lang.Object.toString()",
                "public final native java.lang.Class java.lang.Object.getClass()"
        );
    }

    @Test
    public void byExtending() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byExtending(AbstractBaseClass.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()"
        );
    }

    @Test
    public void byImplementingAllOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byImplementingAllOf(AnyInterface.class, Serializable.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()"
        );
    }

    @Test
    public void byPackageName() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                    .byPackageName("org.failearly", StringMatcherType.STARTS_WITH)
                    .byPackageName("java.lang", StringMatcherType.CONTAINS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()",
                "public java.lang.String java.lang.Object.toString()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
    }



    @Test
    public void byReturnTypeOr() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.OR)
                                    .byClass(void.class)
                                    .byClass(int.class)
                           .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject3
                "public void org.failearly.ajunit.builder.TestSubject3.setAnyValue(int)",
                "public int org.failearly.ajunit.builder.TestSubject3.getAnyValue()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()",
                "public native int java.lang.Object.hashCode()"
        );
    }

    @Test
    public void byReturnTypeAnyOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.ANY_OF)
                    .byClass(void.class)
                    .byClass(int.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject3
                "public void org.failearly.ajunit.builder.TestSubject3.setAnyValue(int)",
                "public int org.failearly.ajunit.builder.TestSubject3.getAnyValue()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()",
                "public native int java.lang.Object.hashCode()"
        );
    }

    @Test
    public void byReturnTypeUnion() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.ANY_OF)
                    .byClass(void.class)
                    .byClass(int.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject3
                "public void org.failearly.ajunit.builder.TestSubject3.setAnyValue(int)",
                "public int org.failearly.ajunit.builder.TestSubject3.getAnyValue()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "private static native void java.lang.Object.registerNatives()",
                "public native int java.lang.Object.hashCode()"
        );
    }

    @Test
    public void byReturnTypeAnd() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.AND)
                    .byImplementingAnyOf(Serializable.class)
                    .byPackageName("org.failearly.ajunit.builder", StringMatcherType.EQUALS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()"
        );
    }

    @Test
    public void byReturnTypeIntersect() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.INTERSECT)
                    .byImplementingAnyOf(Serializable.class)
                    .byPackageName("org.failearly.ajunit.builder", StringMatcherType.EQUALS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()"
        );
    }

    @Test
    public void byReturnTypeAllOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.ALL_OF)
                .byImplementingAnyOf(Serializable.class)
                .byPackageName("org.failearly.ajunit.builder", StringMatcherType.EQUALS)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()"
        );
    }

    @Test
    public void byReturnTypeNor() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.NOR)
                    .byImplementingAnyOf(Serializable.class)
                    .byClass(void.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()",
                "public int org.failearly.ajunit.builder.TestSubject3.getAnyValue()",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
    }

    @Test
    public void byReturnTypeNoneOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.NONE_OF)
                .byImplementingAnyOf(Serializable.class)
                .byClass(void.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()",
                "public int org.failearly.ajunit.builder.TestSubject3.getAnyValue()",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
    }

    @Test
    public void byReturnTypeComplement() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.COMPLEMENT)
                    .byImplementingAnyOf(Serializable.class)
                    .byClass(void.class)
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()",
                "public int org.failearly.ajunit.builder.TestSubject3.getAnyValue()",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
    }

    @Test
    public void byReturnTypeComplexExpressionNoneOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.ANY_OF)
                    .byPackageName("org.failearly.ajunit.builder", StringMatcherType.EQUALS)
                    .noneOf()
                        .byImplementingAnyOf(Serializable.class)
                        .byClass(void.class)
                    .end()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public int org.failearly.ajunit.builder.TestSubject3.getAnyValue()",
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
    }

    @Test
    public void byReturnTypeComplexExpressionAllOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.ANY_OF)
                    .byPackageName("org.failearly.ajunit.builder", StringMatcherType.EQUALS)
                    .allOf()
                        .byImplementingAnyOf(Serializable.class)
                    .end()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()",
                "public org.failearly.ajunit.builder.TestSubject1 org.failearly.ajunit.builder.TestSubject3.getTestSubject1()",
                "public java.lang.String java.lang.Object.toString()",
                "public final native java.lang.Class java.lang.Object.getClass()"
        );
    }

    @Test
    public void byReturnTypeComplexExpressionAnyOf() throws Exception {
        // act / when
        selectorBuilder.byReturnType(LogicalOperator.ALL_OF)
                    .byPackageName("org.failearly.ajunit.builder", StringMatcherType.EQUALS)
                    .anyOf()
                        .byImplementingAnyOf(Serializable.class)
                    .end()
                .endReturnType();

        // assert / then
        assertBuildJoinPointSelector(
                "public org.failearly.ajunit.builder.TestSubject3 org.failearly.ajunit.builder.TestSubject3.getTestSubject3()",
                "public org.failearly.ajunit.builder.TestSubject2 org.failearly.ajunit.builder.TestSubject3.getTestSubject2()"
        );
    }
}
