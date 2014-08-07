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
package org.failearly.ajunit.internal.predicate.standard;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * NullPredicateTest tests {@link StandardPredicates#isNotNull()} and {@link StandardPredicates#isNull()}.
 */
public class NullPredicatesTest {
    @Test
    public void isNotNull() throws Exception {
        final Predicate isNotNullPredicate = StandardPredicates.isNotNull();
        assertThat("IsNotNull(not null) evaluates to?", isNotNullPredicate.test("VALUE"), is(true));
        assertThat("IsNotNull(null) evaluates to?", isNotNullPredicate.test(null), is(false));
        assertThat("IsNotNull type?", isNotNullPredicate.getName(), is("IsNotNull"));
    }
    @Test
    public void isNull() throws Exception {
        final Predicate isNullPredicate = StandardPredicates.isNull();
        assertThat("IsNull(not null) evaluates to?", isNullPredicate.test("VALUE"), is(false));
        assertThat("IsNull(null) evaluates to?", isNullPredicate.test(null), is(true));
        assertThat("IsNull type?", isNullPredicate.getName(), is("IsNull"));
    }
}
