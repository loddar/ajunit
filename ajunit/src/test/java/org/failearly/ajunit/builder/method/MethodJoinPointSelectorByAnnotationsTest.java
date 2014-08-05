/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 marko (http://fail-early.com/contact)
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
 * Tests for {@link MethodJoinPointSelector#byPackageName(String, org.failearly.ajunit.builder.StringMatcherType)}.
 */
public abstract class MethodJoinPointSelectorByAnnotationsTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected MethodJoinPointSelectorByAnnotationsTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject1.class, TestSubject2.class, TestSubject7.class);
    }

    @Test
    public void byTypeAnnotation() throws Exception {
        // act / when
        selectorBuilder.byTypeAnnotation(AnyAnnotation.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()"
        );
    }

    @Test
    public void byTypeAnnotationWithoutInheritance() throws Exception {
        // act / when
        selectorBuilder.byTypeAnnotation(AnyOtherAnnotation.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject7
                "public void org.failearly.ajunit.builder.TestSubject7.method0()",
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byAnyOfTwoAnnotations() throws Exception {
        // act / when
        //noinspection unchecked
        selectorBuilder
                .byTypeAnnotations(LogicalOperator.ANY_OF, AnyAnnotation.class, AnyOtherAnnotation.class)
            .endMethod();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()",

                // TestSubject7
                "public void org.failearly.ajunit.builder.TestSubject7.method0()",
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)"
        );
    }

    @Test
    public void byAllOfTypeAnnotations() throws Exception {
        // act / when
        //noinspection unchecked
        selectorBuilder
                    .byTypeAnnotations(LogicalOperator.ALL_OF, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endMethod();

        // assert / then
        assertBuildJoinPointSelector(
                // No one class has been annotated with both annotations.
        );
    }

    @Test
    public void byNoneOfTypeAnnotations() throws Exception {
        // act / when
        //noinspection unchecked
        selectorBuilder
                    .byTypeAnnotations(LogicalOperator.NONE_OF, AnyAnnotation.class, AnyOtherAnnotation.class)
                .endMethod();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
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
    public void byMethodAnnotation() throws Exception {
        // act / when
        selectorBuilder.byMethodAnnotation(AnyAnnotation.class).endMethod();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()"
        );
    }


    @Test
    public void byAnyOfMethodAnnotations() throws Exception {
        // act / when
        //noinspection unchecked
        selectorBuilder
                .byMethodAnnotations(LogicalOperator.ANY_OF, AnyAnnotation.class, AnyOtherAnnotation.class)
            .endMethod();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()"
        );
    }

    @Test
    public void byAllOfMethodAnnotations() throws Exception {
        // act / when
        //noinspection unchecked
        selectorBuilder
                .byMethodAnnotations(LogicalOperator.ALL_OF, AnyAnnotation.class, AnyOtherAnnotation.class)
            .endMethod();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()"
        );
    }

    @Test
    public void byNoneOfMethodAnnotations() throws Exception {
        // act / when
        //noinspection unchecked
        selectorBuilder
                .byMethodAnnotations(LogicalOperator.NONE_OF, AnyAnnotation.class, AnyOtherAnnotation.class)
            .endMethod();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject7
                "public void org.failearly.ajunit.builder.TestSubject7.method0()",
                "public void org.failearly.ajunit.builder.TestSubject7.method1(int)",
                "public void org.failearly.ajunit.builder.TestSubject7.method2(int,java.lang.String)",
                "public void org.failearly.ajunit.builder.TestSubject7.method3(java.lang.String,java.lang.String,int)",
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
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

}
