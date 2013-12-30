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

import org.failearly.ajunit.predicate.CompositePredicate;
import org.failearly.ajunit.predicate.Predicate;
import org.junit.Test;

import static org.failearly.ajunit.predicate.standard.StandardPredicates.predicateFalse;
import static org.failearly.ajunit.predicate.standard.StandardPredicates.predicateTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Base class for {@link org.failearly.ajunit.predicate.CompositePredicate} tests.
 */
public abstract class CompositePredicatesTest {
    protected static final Predicate TRUE=predicateTrue();
    protected static final Predicate FALSE=predicateFalse();
    private static final Object ANY_PARAMETER=Boolean.FALSE;

    private final CompositePredicate compositePredicate;
    private final String predicateName;

    public CompositePredicatesTest(String predicateName, CompositePredicate compositePredicate) {
        this.compositePredicate = compositePredicate;
        this.predicateName = predicateName;
    }

    protected final void registerPredicates(final Predicate... predicates) {
        for (final Predicate predicate : predicates) {
            compositePredicate.addPredicate(predicate);
        }
    }

    protected final void assertCompositePredicateEvaluatesTo(boolean expectedValue) {
        assertThat("Composite predicate '" + predicateName + "' evaluates to?", compositePredicate.evaluate(ANY_PARAMETER), is(expectedValue));
    }

    @Test
    public final void noPredicatesEvaluatesToTrue() throws Exception {
        // arrange / given
        registerPredicates();

        // assert / then
        assertCompositePredicateEvaluatesTo(true);
    }
}
