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
import org.failearly.ajunit.modifier.AccessModifier;
import org.failearly.ajunit.modifier.MethodModifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelector}.
 *
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#end()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#or()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#anyOf()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#union()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#and()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#allOf()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#intersect()
 */
public abstract class MethodJoinPointSelectorLogicalOpsTest<T extends MethodJoinPointSelector<T>> extends AbstractJoinPointSelectorTest<T> {

    protected MethodJoinPointSelectorLogicalOpsTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject1.class, TestSubject2.class);
    }

    @Test
    public void orReturnsNewInstance() throws Exception {
        // act / when
        final T orInstance = selectorBuilder.or();

        // assert / then
        assertThat("or() returns new instance?", orInstance, not(sameInstance(selectorBuilder)));
    }

    @Test
    public void orEnd() throws Exception {
        // act / when
        final T instance = selectorBuilder.or().end();

        // assert / then
        assertThat("end() returns parent selector builder?", instance, sameInstance(selectorBuilder));
    }

    @Test
    public void or() throws Exception {
        // act / when
        selectorBuilder.or()
                .byClass(TestSubject1.class)
                .byName("toString", StringMatcherType.EQUALS)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()",
                // java.lang.Object
                "public java.lang.String java.lang.Object.toString()"
        );
    }

    @Test
    public void anyOf() throws Exception {
        // act / when
        selectorBuilder.anyOf()
                .byClass(TestSubject1.class)
                .byName("Method", StringMatcherType.ENDS_WITH)
                .byName("toString", StringMatcherType.EQUALS)
                .end();

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
                // java.lang.Object
                "public java.lang.String java.lang.Object.toString()"
        );
    }

    @Test
    public void union() throws Exception {
        // act / when
        selectorBuilder.union()
                .byClass(TestSubject1.class)
                .byName("Method0", StringMatcherType.ENDS_WITH)
                .byName("toString", StringMatcherType.EQUALS)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()",
                // TestSubject2
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()",
                // java.lang.Object
                "public java.lang.String java.lang.Object.toString()"
        );
    }

    @Test
    public void andReturnsNewInstance() throws Exception {
        // act / when
        final T andInstance = selectorBuilder.and();

        // assert / then
        assertThat("and() returns new instance?", andInstance, not(sameInstance(selectorBuilder)));
    }

    @Test
    public void andEnd() throws Exception {
        // act / when
        final T instance = selectorBuilder.and().end();

        // assert / then
        assertThat("end() returns parent selector builder?", instance, sameInstance(selectorBuilder));
    }


    @Test
    public void and() throws Exception {
        // act / when
        selectorBuilder.and()
                .byName("Method", StringMatcherType.CONTAINS)
                .byAnyOfAccessModifiers(AccessModifier.PUBLIC, AccessModifier.PROTECTED)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()"
        );
    }

    @Test
    public void intersect() throws Exception {
        // act / when
        selectorBuilder.intersect()
                .byName("Method", StringMatcherType.CONTAINS)
                .byAnyOfAccessModifiers(AccessModifier.PUBLIC, AccessModifier.PROTECTED)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()"
        );
    }

    @Test
    public void allOf() throws Exception {
        // act / when
        selectorBuilder.allOf()
                .byName("Method", StringMatcherType.CONTAINS)
                .byAnyOfAccessModifiers(AccessModifier.PUBLIC, AccessModifier.PROTECTED)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()",
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()"
        );
    }

    @Test
    public void norReturnsNewInstance() throws Exception {
        // act / when
        final T andInstance = selectorBuilder.nor();

        // assert / then
        assertThat("nor() returns new instance?", andInstance, not(sameInstance(selectorBuilder)));
    }

    @Test
    public void norEnd() throws Exception {
        // act / when
        final T instance = selectorBuilder.and().end();

        // assert / then
        assertThat("nor() returns parent selector builder?", instance, sameInstance(selectorBuilder));
    }

    @Test
    public void nor() throws Exception {
        // act / when
        selectorBuilder.nor()
                    .byClass(TestSubject1.class)
                    .byName("toString", StringMatcherType.EQUALS)
                    .byName("Method0", StringMatcherType.ENDS_WITH)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable"
        );
    }

    @Test
    public void noneOf() throws Exception {
        // act / when
        selectorBuilder.noneOf()
                    .byClass(TestSubject1.class)
                    .byName("toString", StringMatcherType.EQUALS)
                    .byName("Method0", StringMatcherType.ENDS_WITH)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable"
        );
    }

    @Test
    public void neitherNor() throws Exception {
        // act / when
        selectorBuilder.neitherNor()
                    .byClass(TestSubject1.class)
                    .byName("toString", StringMatcherType.EQUALS)
                    .byName("Method0", StringMatcherType.ENDS_WITH)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable"
        );
    }

    @Test
    public void complement() throws Exception {
        // act / when
        selectorBuilder.complement()
                    .byClass(TestSubject1.class)
                    .byName("toString", StringMatcherType.EQUALS)
                    .byName("Method0", StringMatcherType.ENDS_WITH)
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                // java.lang.Object
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()",
                "protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException",
                "private static native void java.lang.Object.registerNatives()",
                "protected void java.lang.Object.finalize() throws java.lang.Throwable"
        );
    }


    @Test
    public void complexLogicalExpression() throws Exception {
        // act / when
        selectorBuilder
                .anyOf()
                    .byName("Method0", StringMatcherType.ENDS_WITH)
                    .allOf()
                        .byClassName("Object", StringMatcherType.EQUALS)
                        .byAnyOfAccessModifiers(AccessModifier.PUBLIC)
                        .byAnyOfMethodModifiers(MethodModifier.NATIVE)
                    .end()
                .end();

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject2
                "protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()",
                "protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()",
                "protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()",
                // java.lang.Object
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()"
        );
    }

    @Test
    public void endMethodOnLogicalExpression() throws Exception {
        assertEndSpecificJoinPointBuilder("endMethod()", selectorBuilder.and().or().and().endMethod());
    }
}
