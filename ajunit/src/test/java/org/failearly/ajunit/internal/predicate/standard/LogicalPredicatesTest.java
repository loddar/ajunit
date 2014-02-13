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
package org.failearly.ajunit.internal.predicate.standard;

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import java.util.Arrays;

import static org.failearly.ajunit.internal.predicate.standard.StandardPredicates.alwaysFalse;
import static org.failearly.ajunit.internal.predicate.standard.StandardPredicates.alwaysTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Base class for Logical {@link org.failearly.ajunit.internal.predicate.Predicate} tests.
 */
public abstract class LogicalPredicatesTest {
    protected static final Predicate TRUE= alwaysTrue();
    protected static final Predicate FALSE= alwaysFalse();
    private static final Object ANY_PARAMETER=Boolean.FALSE;

    private final CompositePredicate compositePredicate;
    private final String predicateName;

    public LogicalPredicatesTest(String predicateName, CompositePredicate compositePredicate) {
        this.compositePredicate = compositePredicate;
        this.predicateName = predicateName;
    }

    protected final void registerPredicates(final Predicate... predicates) {
        compositePredicate.addPredicates(Arrays.asList(predicates));
    }

    protected final void assertCompositePredicateEvaluatesTo(boolean expectedValue) {
        assertThat("Logical predicate '" + predicateName + "' evaluates to?", compositePredicate.evaluate(ANY_PARAMETER), is(expectedValue));
    }

    @Test
    public final void noPredicatesEvaluatesToTrue() throws Exception {
        // arrange / given
        registerPredicates();

        // assert / then
        assertCompositePredicateEvaluatesTo(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput() throws Exception {
        // act / when
        compositePredicate.evaluate(null);
    }
}
