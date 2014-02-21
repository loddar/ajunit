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
package org.failearly.ajunit.builder;

import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * MethodJoinPointSelectorBuilderTest tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder}.
 */
public final class MethodJoinPointSelectorBuilderTest extends AbstractJoinPointSelectorBuilderTest<MethodJoinPointSelectorBuilder> {

    public MethodJoinPointSelectorBuilderTest() {
        super(AjJoinPointType.METHOD_EXECUTION);
    }

    @Test
    public void testEndMethod() throws Exception {
        assertEndSpecificJoinPointBuilder("endMethod()", selectorBuilder.endMethod());
        assertJoinPointType();
    }

    @Test
    public void testAnyMethod() throws Exception {
        // act / when
        selectorBuilder.anyMethod();

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject.anyMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject.otherMethod()}",
                "method-execution{#apply=0, method=protected void java.lang.Object.finalize() throws java.lang.Throwable}",
                "method-execution{#apply=0, method=public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=public final void java.lang.Object.wait() throws java.lang.InterruptedException}",
                "method-execution{#apply=0, method=private static native void java.lang.Object.registerNatives()}",
                "method-execution{#apply=0, method=public boolean java.lang.Object.equals(java.lang.Object)}",
                "method-execution{#apply=0, method=public java.lang.String java.lang.Object.toString()}",
                "method-execution{#apply=0, method=public native int java.lang.Object.hashCode()}",
                "method-execution{#apply=0, method=public final native java.lang.Class java.lang.Object.getClass()}",
                "method-execution{#apply=0, method=protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notify()}",
                "method-execution{#apply=0, method=public final native void java.lang.Object.notifyAll()}"
        );
    }

    @Test @Ignore
    public void testByName() throws Exception {
        // arrange / given


        // act / when

        // assert / then
        assertThat("not yet implemented", false, is(true));
    }

    @Override
    protected MethodJoinPointSelectorBuilder createSelectorBuilderUnderTest(JoinPointSelectorBuilder joinPointSelectorBuilder) {
        return joinPointSelectorBuilder.methodExecute();
    }
}
