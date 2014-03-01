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

import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder}.
 *
 * @see org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder#endMethod()
 * @see org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder#anyMethod()
 */
public final class MethodJoinPointSelectorBuilderByPackageNameTest extends MethodJoinPointSelectorBuilderBaseTest {

    public MethodJoinPointSelectorBuilderByPackageNameTest() {
        super(AjJoinPointType.METHOD_EXECUTION);
    }

    @Test
    public void byPackageNameEquals() throws Exception {
        // act / when
        selectorBuilder.byPackageName("org.failearly.ajunit.builder", StringMatcherType.EQUALS);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject1.otherMethod()}",
                // TestSubject2
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject2.otherMethod()}",
                "method-execution{#apply=0, method=protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()}",
                "method-execution{#apply=0, method=protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()}",
                "method-execution{#apply=0, method=protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()}"
        );
    }

    @Test
    public void byPackageNameStartsWith() throws Exception {
        // act / when
        selectorBuilder.byPackageName("org.failearly", StringMatcherType.STARTS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject1.otherMethod()}",
                // TestSubject2
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject2.otherMethod()}",
                "method-execution{#apply=0, method=protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()}",
                "method-execution{#apply=0, method=protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()}",
                "method-execution{#apply=0, method=protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()}"
        );
    }

    @Test
    public void byPackageNameEndsWith() throws Exception {
        // act / when
        selectorBuilder.byPackageName("ajunit.builder", StringMatcherType.ENDS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject1.otherMethod()}",
                // TestSubject2
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject2.otherMethod()}",
                "method-execution{#apply=0, method=protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()}",
                "method-execution{#apply=0, method=protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()}",
                "method-execution{#apply=0, method=protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()}"
        );
    }

    @Test
    public void byPackageNameContains() throws Exception {
        // act / when
        selectorBuilder.byPackageName("ajunit", StringMatcherType.CONTAINS);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject1.otherMethod()}",
                // TestSubject2
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject2.otherMethod()}",
                "method-execution{#apply=0, method=protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()}",
                "method-execution{#apply=0, method=protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()}",
                "method-execution{#apply=0, method=protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()}"
        );
    }

    @Test
    public void byPackageNameRegex() throws Exception {
        // act / when
        selectorBuilder.byPackageName(".*ajunit.*", StringMatcherType.REGEX);

        // assert / then
        assertBuildJoinPointSelector(
                // TestSubject1
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject1.otherMethod()}",
                // TestSubject2
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject2.otherMethod()}",
                "method-execution{#apply=0, method=protected abstract void org.failearly.ajunit.builder.TestSubject2.abstractMethod0()}",
                "method-execution{#apply=0, method=protected synchronized void org.failearly.ajunit.builder.TestSubject2.syncMethod0()}",
                "method-execution{#apply=0, method=protected strictfp void org.failearly.ajunit.builder.TestSubject2.strictMethod0()}"
        );
    }



}
