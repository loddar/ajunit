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
 */
package org.failearly.ajunit.internal.predicate.number;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.predicate.number.IntegerPredicates}.
 */
public class IntegerPredicatesTest {
    @Test
    public void greaterThen() throws Exception {
        // arrange / given
        final Predicate predicate=IntegerPredicates.greaterThen(5);

        // act / when

        // assert / then
        assertThat("7 > 5?", predicate.evaluate(7), is(true));
        assertThat("4 > 5?", predicate.evaluate(4), is(false));
        assertThat("5 > 5?", predicate.evaluate(5), is(false));
    }

    @Test
    public void greaterEqualThen() throws Exception {
        // arrange / given
        final Predicate predicate=IntegerPredicates.greaterEqualThen(5);

        // act / when

        // assert / then
        assertThat("7 >= 5?", predicate.evaluate(7), is(true));
        assertThat("4 >= 5?", predicate.evaluate(4), is(false));
        assertThat("5 >= 5?", predicate.evaluate(5), is(true));
    }

    @Test
    public void lessThen() throws Exception {
        // arrange / given
        final Predicate predicate=IntegerPredicates.lessThen(5);

        // act / when

        // assert / then
        assertThat("4 < 5?", predicate.evaluate(4), is(true));
        assertThat("7 < 5?", predicate.evaluate(7), is(false));
        assertThat("5 < 5?", predicate.evaluate(5), is(false));
    }
    @Test
    public void lessEqualThen() throws Exception {
        // arrange / given
        final Predicate predicate=IntegerPredicates.lessEqualThen(5);

        // act / when

        // assert / then
        assertThat("4 <= 5?", predicate.evaluate(4), is(true));
        assertThat("7 <= 5?", predicate.evaluate(7), is(false));
        assertThat("5 <= 5?", predicate.evaluate(5), is(true));
    }
}
