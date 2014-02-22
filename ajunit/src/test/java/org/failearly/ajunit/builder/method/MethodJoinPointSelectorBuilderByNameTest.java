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
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

/**
 * Tests for {@link org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder#byName(String, org.failearly.ajunit.builder.StringMatcherType)}.
 */
public final class MethodJoinPointSelectorBuilderByNameTest extends AbstractJoinPointSelectorBuilderTest<MethodJoinPointSelectorBuilder> {

    public MethodJoinPointSelectorBuilderByNameTest() {
        super(AjJoinPointType.METHOD_EXECUTION);
    }

    @Override
    protected MethodJoinPointSelectorBuilder createSelectorBuilderUnderTest(JoinPointSelectorBuilder joinPointSelectorBuilder) {
        return joinPointSelectorBuilder.methodExecute();
    }

    @Test
    public void byNameEquals() throws Exception {
        // act / when
        selectorBuilder.byName("anyMethod", StringMatcherType.EQUALS);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}"
        );
    }

    @Test
    public void byNameStartsWith() throws Exception {
        // act / when
        selectorBuilder.byName("any", StringMatcherType.STARTS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}"
        );
    }

    @Test
    public void byNameEndsWith() throws Exception {
        // act / when
        selectorBuilder.byName("Method", StringMatcherType.ENDS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject1.otherMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()}",
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}",
                "method-execution{#apply=0, method=private void org.failearly.ajunit.builder.TestSubject2.otherMethod()}",
                "method-execution{#apply=0, method=void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()}"
        );
    }

    @Test
    public void byNameContains() throws Exception {
        // act / when
        selectorBuilder.byName("nyM", StringMatcherType.CONTAINS);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}"
        );
    }

    @Test
    public void byNameRegularExpression() throws Exception {
        // act / when
        selectorBuilder.byName(".*nyM.*", StringMatcherType.REGEX);

        // assert / then
        assertBuildJoinPointSelector(
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject1.anyMethod()}",
                "method-execution{#apply=0, method=public void org.failearly.ajunit.builder.TestSubject2.anyMethod()}"
        );
    }
}
