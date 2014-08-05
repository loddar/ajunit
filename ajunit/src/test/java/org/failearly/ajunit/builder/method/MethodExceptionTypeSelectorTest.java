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
 * Tests for {@link MethodExceptionTypeSelector}.
 *
 * @see MethodJoinPointSelector#byExceptionTypes(org.failearly.ajunit.builder.ListLogicalOperator)
 */
public abstract class MethodExceptionTypeSelectorTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected MethodExceptionTypeSelectorTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject1.class, TestSubject6.class);
    }

    @Test
    public void endExceptionTypes() throws Exception {
        // act / when
        final MethodJoinPointSelector instance = selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).endExceptionTypes();

        // assert / then
        assertThat("endExceptionTypes() returns correct selector builder?", instance, sameInstance(selectorBuilder));
    }

    @Test
    public void byError() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byError().endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method0() throws java.lang.Error"
        );
    }

    @Test
    public void byRuntimeException() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byRuntimeException().endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method3() throws java.lang.RuntimeException",
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"        );
    }

    @Test
    public void byCheckedException() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byCheckedException().endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method2() throws java.lang.Exception",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"
        );
    }

    @Test
    public void byClass() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byClass(Exception.class).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method2() throws java.lang.Exception"
        );
    }

    @Test
    public void byImplementingAnyOf() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byImplementingAnyOf(AnyException.class, RuntimeException.class).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method3() throws java.lang.RuntimeException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"
        );
    }

    @Test
    public void byImplementingAllOf() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byImplementingAllOf(AnyException.class, RuntimeException.class).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException",
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException"
        );
    }

    @Test
    public void byImplementingNoneOf() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byImplementingNoneOf(Exception.class, RuntimeException.class).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "public void org.failearly.ajunit.builder.TestSubject6.method0() throws java.lang.Error",
                "public void org.failearly.ajunit.builder.TestSubject6.method1() throws java.lang.Throwable"
        );
    }

    @Test
    public void byExtending() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byExtending(Error.class).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method0() throws java.lang.Error"
        );
    }

    @Test
    public void byNotExtending() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byNotExtending(Exception.class).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "public void org.failearly.ajunit.builder.TestSubject6.method0() throws java.lang.Error",
                "public void org.failearly.ajunit.builder.TestSubject6.method1() throws java.lang.Throwable"
        );
    }

    @Test
    public void byClassName() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byClassName("Any", StringMatcherType.STARTS_WITH).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"
        );
    }

    @Test
    public void byPackageName() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byPackageName("builder", StringMatcherType.ENDS_WITH).endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"
        );
    }

    @Test
    public void byTypeAnnotation() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF)
                            .byTypeAnnotation(AnyAnnotation.class)
                        .endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"
        );
    }

    @Test
    public void logicalOperatorAnyOf() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ANY_OF).byError().byRuntimeException().endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method0() throws java.lang.Error",
                "public void org.failearly.ajunit.builder.TestSubject6.method3() throws java.lang.RuntimeException",
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException",
                "public void org.failearly.ajunit.builder.TestSubject6.method5() throws org.failearly.ajunit.builder.AnyException,java.sql.SQLException"
        );
    }

    @Test
    public void logicalOperatorAllOf() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.ALL_OF).byError().byRuntimeException().endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method0() throws java.lang.Error",
                "public void org.failearly.ajunit.builder.TestSubject6.method3() throws java.lang.RuntimeException",
                "public void org.failearly.ajunit.builder.TestSubject6.method4() throws org.failearly.ajunit.builder.AnyException"
        );
    }

    @Test
    public void logicalOperatorNoneOf() throws Exception {
        // act / when
        selectorBuilder.byExceptionTypes(ListLogicalOperator.NONE_OF).byError().byRuntimeException().endExceptionTypes();


        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject6.method1() throws java.lang.Throwable",
                "public void org.failearly.ajunit.builder.TestSubject6.method2() throws java.lang.Exception",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException"
        );
    }


}
