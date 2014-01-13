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
package org.failearly.ajunit.internal.predicate.standard;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import static org.failearly.ajunit.internal.predicate.standard.StandardPredicates.alwaysFalse;
import static org.failearly.ajunit.internal.predicate.standard.StandardPredicates.alwaysTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.predicate.standard.NotPredicate}.
 */
public class NotPredicateTest {
    private static final Predicate TRUE = alwaysTrue();
    private static final Predicate FALSE = alwaysFalse();
    private static final Object ANY_PARAMETER = Boolean.FALSE;

    @Test
    public void evaluateTrue() throws Exception {
        // arrange / given
        final Predicate notPredicate = LogicalPredicates.not(TRUE);

        // act / when

        // assert / then
        assertThat("Not predicate evaluates to?", notPredicate.evaluate(ANY_PARAMETER), is(false));
    }

    @Test
    public void evaluateFalse() throws Exception {
        // arrange / given
        final Predicate notPredicate = LogicalPredicates.not(FALSE);

        // act / when

        // assert / then
        assertThat("Not predicate evaluates to?", notPredicate.evaluate(ANY_PARAMETER), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput() throws Exception {
        // arrange / given
        final Predicate notPredicate = LogicalPredicates.not(TRUE);

        // act / when
        notPredicate.evaluate(null);
    }

}
