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
 * Tests for {@link MethodJoinPointSelector}.
 *
 * @see MethodJoinPointSelector#byDeclaringClass(Class)
 * @see MethodJoinPointSelector#byDeclaringClassName(String, org.failearly.ajunit.builder.StringMatcherType)
 * @see MethodJoinPointSelector#byExtending(Class)
 * @see MethodJoinPointSelector#byImplementingAllOf(Class[])
 * @see MethodJoinPointSelector#byImplementingAnyOf(Class[])
 */
public abstract class MethodJoinPointSelectorByClassTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected MethodJoinPointSelectorByClassTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject1.class, TestSubject2.class);
    }

    @Test
    public void byDeclaringClass() throws Exception {
        // act / when
        selectorBuilder.byDeclaringClass(TestSubject1.class);

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()"
        );
    }

    @Test
    public void byDeclaringClassNameEquals() throws Exception {
        // act / when
        selectorBuilder.byDeclaringClassName("TestSubject1", StringMatcherType.EQUALS);

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()"
        );
    }

    @Test
    public void byDeclaringClassNameStartsWith() throws Exception {
        // act / when
        selectorBuilder.byDeclaringClassName("Test", StringMatcherType.STARTS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()",
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()"
        );
    }

    @Test
    public void byDeclaringClassNameEndsWith() throws Exception {
        // act / when
        selectorBuilder.byDeclaringClassName("Subject2", StringMatcherType.ENDS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()"
        );
    }

    @Test
    public void byDeclaringClassNameContains() throws Exception {
        // act / when
        selectorBuilder.byDeclaringClassName("stSub", StringMatcherType.CONTAINS);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()",
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()"
        );
    }

    @Test
    public void byDeclaringClassNameRegularExpression() throws Exception {
        // act / when
        selectorBuilder.byDeclaringClassName(".*stSub.*1", StringMatcherType.REGEX);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()"
        );
    }

    @Test
    public void byExtendingABaseClass() throws Exception {
        // act / when
        selectorBuilder.byExtending(AbstractBaseClass.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()"
        );
    }

    @Test
    public void byExtendingItself() throws Exception {
        // act / when
        selectorBuilder.byExtending(TestSubject2.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()"
        );
    }

    @Test
    public void byNotExtendingABaseClass() throws Exception {
        // act / when
        selectorBuilder.byNotExtending(AbstractBaseClass.class);

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
    public void byImplementingAnyOfSingleInterface() throws Exception {
        // act / when
        selectorBuilder.byImplementingAnyOf(Serializable.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()"
        );
    }

    @Test
    public void byImplementingAnyOfTwoInterface() throws Exception {
        // act / when
        selectorBuilder.byImplementingAnyOf(Serializable.class, AnyInterface.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()",
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()"
        );
    }

    @Test
    public void byImplementingAllOfSingleInterface() throws Exception {
        // act / when
        selectorBuilder.byImplementingAllOf(Serializable.class);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()"
        );
    }

    @Test
    public void byImplementingAllOfTwoInterface() throws Exception {
        // act / when
        selectorBuilder.byImplementingAllOf(Serializable.class, AnyInterface.class);

        // assert / then
        assertBuildJoinPointSelector(/* None of the classes implements both interfaces.*/);
    }

    @Test
    public void byImplementingNoneOfTwoInterface() throws Exception {
        // act / when
        selectorBuilder.byImplementingNoneOf(Serializable.class, AnyInterface.class);

        // assert / then
        assertBuildJoinPointSelector(
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
