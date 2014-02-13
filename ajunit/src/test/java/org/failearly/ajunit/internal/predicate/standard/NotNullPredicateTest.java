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

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * NotNullPredicateTest contains tests for {@link StandardPredicates#predicateNotNull()}.
 */
public class NotNullPredicateTest {
    private final Predicate isNotNullPredicate = StandardPredicates.predicateNotNull();

    @Test
    public void type() throws Exception {
        assertThat("IsNotNull type?", isNotNullPredicate.getType(), is("NotNull"));
    }

    @Test
    public void notNull() throws Exception {
        assertThat("IsNotNull evaluates to?", isNotNullPredicate.evaluate("VALUE"), is(true));
    }

    @Test
    public void isNull() throws Exception {
        assertThat("IsNotNull evaluates to?", isNotNullPredicate.evaluate(null), is(false));
    }
}
