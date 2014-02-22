/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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

import org.failearly.ajunit.builder.AbstractJoinPointSelectorBuilderTest;
import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.modifier.MethodModifier;
import org.junit.Test;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder#byAnyOfMethodModifiers(org.failearly.ajunit.modifier.MethodModifier...)}.
 */
public final class MethodJoinPointSelectorBuilderByAnyOfMethodModifierTest extends AbstractJoinPointSelectorBuilderTest<MethodJoinPointSelectorBuilder> {

    public MethodJoinPointSelectorBuilderByAnyOfMethodModifierTest() {
        super(AjJoinPointType.METHOD_EXECUTION);
    }

    @Override
    protected MethodJoinPointSelectorBuilder createSelectorBuilderUnderTest(JoinPointSelectorBuilder joinPointSelectorBuilder) {
        return joinPointSelectorBuilder.methodExecute();
    }

    @Test
    public void byAnyOfMethodModifierFinal() throws Exception {
        // act / when
        selectorBuilder.byAnyOfMethodModifiers(MethodModifier.FINAL);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notify()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
    }

    @Test
    public void byAnyOfMethodModifierNative() throws Exception {
        // act / when
        selectorBuilder.byAnyOfMethodModifiers(MethodModifier.NATIVE);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public native int java.lang.Object.hashCode()}",
                "method-execution{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notify()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notifyAll()}",
                "method-execution{#apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "method-execution{#apply=0, method=private static native void java.lang.Object.registerNatives()}"
        );
    }

    @Test
    public void byAnyOfMethodModifierStatic() throws Exception {
        // act / when
        selectorBuilder.byAnyOfMethodModifiers(MethodModifier.STATIC);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=private static native void java.lang.Object.registerNatives()}"
        );
    }

    @Test
    public void byAnyOfMethodModifierAbstract() throws Exception {
        // act / when
        selectorBuilder.byAnyOfMethodModifiers(MethodModifier.ABSTRACT);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()}"
        );
    }

    @Test
    public void byAnyOfMethodModifierSynchronized() throws Exception {
        // act / when
        selectorBuilder.byAnyOfMethodModifiers(MethodModifier.SYNCHRONIZED);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()}"
        );
    }

    @Test
    public void byAnyOfMethodModifierStrict() throws Exception {
        // act / when
        selectorBuilder.byAnyOfMethodModifiers(MethodModifier.STRICT);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()}"
        );
    }

}
