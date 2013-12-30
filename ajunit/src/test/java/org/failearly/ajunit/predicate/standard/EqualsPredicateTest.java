/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2013  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.predicate.standard;

import org.failearly.ajunit.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.predicate.standard.EqualsPredicate}.
 */
public class EqualsPredicateTest {
    private final Predicate equalsPredicate=StandardPredicates.predicateEquals("VALUE");

    @Test
    public void equals() throws Exception {
        // assert / then
        assertThat("Equals evaluate to?", equalsPredicate.evaluate("VALUE"), is(true));
    }

    @Test
    public void notEquals() throws Exception {
        // assert / then
        assertThat("Equals evaluate to?", equalsPredicate.evaluate("ANY-OTHER-VALUE"), is(false));
    }
}
