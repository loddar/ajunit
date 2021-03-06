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
import org.failearly.ajunit.builder.TestSubject1;
import org.failearly.ajunit.builder.TestSubject2;
import org.failearly.ajunit.builder.types.StringMatcher;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

/**
 * Tests for {@link MethodJoinPointSelector#byName(String, org.failearly.ajunit.builder.types.StringMatcher)}.
 */
public abstract class MethodJoinPointSelectorByNameTest extends AbstractJoinPointSelectorTest<MethodJoinPointSelector> {

    protected MethodJoinPointSelectorByNameTest(AjJoinPointType expectedJoinPointType) {
        super(expectedJoinPointType, TestSubject1.class, TestSubject2.class);
    }

    @Test
    public void byNameEquals() throws Exception {
        // act / when
        selectorBuilder.byName("anyMethod", StringMatcher.EQUALS);

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()"
        );
    }

    @Test
    public void byNameStartsWith() throws Exception {
        // act / when
        selectorBuilder.byName("any", StringMatcher.STARTS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()"
        );
    }

    @Test
    public void byNameEndsWith() throws Exception {
        // act / when
        selectorBuilder.byName("Method", StringMatcher.ENDS_WITH);

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "private void org.failearly.ajunit.builder.TestSubject1.otherMethod()",
                "void org.failearly.ajunit.builder.TestSubject1.packagePrivateMethod()",
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()",
                "private void org.failearly.ajunit.builder.TestSubject2.otherMethod()",
                "void org.failearly.ajunit.builder.TestSubject2.packagePrivateMethod()"
        );
    }

    @Test
    public void byNameContains() throws Exception {
        // act / when
        selectorBuilder.byName("nyM", StringMatcher.CONTAINS);

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()"
        );
    }

    @Test
    public void byNameRegularExpression() throws Exception {
        // act / when
        selectorBuilder.byName(".*nyM.*", StringMatcher.REGEX);

        // assert / then
        assertBuildJoinPointSelector(
                "public void org.failearly.ajunit.builder.TestSubject1.anyMethod()",
                "public void org.failearly.ajunit.builder.TestSubject2.anyMethod()"
        );
    }
}
