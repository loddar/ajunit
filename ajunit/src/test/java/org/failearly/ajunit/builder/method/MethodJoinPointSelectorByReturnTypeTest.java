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

import org.failearly.ajunit.builder.AbstractJoinPointSelectorTest;
import org.failearly.ajunit.builder.LogicalOperator;
import org.failearly.ajunit.builder.MethodJoinPointSelector;
import org.failearly.ajunit.builder.TestSubject3;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelector}.
 *
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byDeclaringClass(Class)
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byDeclaringClassName(String, org.failearly.ajunit.builder.StringMatcherType)
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byExtending(Class)
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byImplementingAllOf(Class[])
 * @see org.failearly.ajunit.builder.MethodJoinPointSelector#byImplementingAnyOf(Class[])
 */
public abstract class MethodJoinPointSelectorByReturnTypeTest<T extends MethodJoinPointSelector<T>> extends AbstractJoinPointSelectorTest<T> {

    protected MethodJoinPointSelectorByReturnTypeTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject3.class);
    }

    @Test
    public void endReturnType() throws Exception {
        // act / when
        final T instance = selectorBuilder.byReturnType(LogicalOperator.OR).endReturnType();

        // assert / then
        assertThat("endReturnType() returns correct selector builder?", instance, sameInstance(selectorBuilder));
    }

    @Test
    public void byClassWithoutReturnType() throws Exception {
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

}
