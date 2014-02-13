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

import org.junit.Test;

/**
 * Tests for {@link org.failearly.ajunit.internal.predicate.standard.OrPredicate}.
 */
public class OrPredicatesTest extends LogicalPredicatesTest {

    public OrPredicatesTest() {
        super("OR", LogicalPredicates.or());
    }

    @Test
    public void trueTrue() throws Exception {
        // arrange / given
        registerPredicates(TRUE, TRUE);

        // assert / then
        assertCompositePredicateEvaluatesTo(true);
    }

    @Test
    public void trueFalse() throws Exception {
        // arrange / given
        registerPredicates(TRUE, FALSE);

        // assert / then
        assertCompositePredicateEvaluatesTo(true);
    }

    @Test
    public void falseTrue() throws Exception {
        // arrange / given
        registerPredicates(FALSE, TRUE);

        // assert / then
        assertCompositePredicateEvaluatesTo(true);
    }

    @Test
    public void falseFalse() throws Exception {
        // arrange / given
        registerPredicates(FALSE, FALSE);

        // assert / then
        assertCompositePredicateEvaluatesTo(false);
    }

    @Test
    public void evaluateFalse() throws Exception {
        // arrange / given
        registerPredicates(FALSE);

        // assert / then
        assertCompositePredicateEvaluatesTo(false);
    }

    @Test
    public void evaluateTrue() throws Exception {
        // arrange / given
        registerPredicates(TRUE);

        // assert / then
        assertCompositePredicateEvaluatesTo(true);
    }
}

