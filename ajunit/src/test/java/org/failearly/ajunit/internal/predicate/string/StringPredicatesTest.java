/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.predicate.string;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for  {@link org.failearly.ajunit.internal.predicate.string.StringPredicates}.
 */
public class StringPredicatesTest {

    private final Predicate startsWithPredicate = StringPredicates.startsWith("any.prefix");
    private final Predicate endsWithPredicate = StringPredicates.endsWith("any.suffix");
    private final Predicate containsPredicate = StringPredicates.contains("any.infix");
    private final Predicate regexPredicate = StringPredicates.regex(".*.any.infix.*");

    @Test
    public void startsWith() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", startsWithPredicate.evaluate("any.prefix.and.additional.stuff"), is(true));
    }

    @Test
    public void startsWithSameString() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", startsWithPredicate.evaluate("any.prefix"), is(true));
    }

    @Test
    public void startsWithNotPrefix() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", startsWithPredicate.evaluate("x.any.prefix.and.additional.stuff"), is(false));
    }

    @Test
    public void endsWith() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", endsWithPredicate.evaluate("some.additional.stuff.and.any.suffix"), is(true));
    }

    @Test
    public void endsWithSameString() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", endsWithPredicate.evaluate("any.suffix"), is(true));
    }

    @Test
    public void endsWithNotSuffix() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", endsWithPredicate.evaluate("some.additional.stuff.and.any.suffix.x"), is(false));
    }

    @Test
    public void contains() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", containsPredicate.evaluate("some.stuff.any.infix.and.more"), is(true));
    }

    @Test
    public void containsMissingInfix() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", containsPredicate.evaluate("some.stuff"), is(false));
    }

    @Test
    public void regex() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", regexPredicate.evaluate("some.stuff.any.infix.and.more"), is(true));
    }

    @Test
    public void regexNotMatching() throws Exception {
        // act/ assert / then
        assertThat("Evaluates to?", regexPredicate.evaluate("some.stuff.and.more"), is(false));
    }

}
