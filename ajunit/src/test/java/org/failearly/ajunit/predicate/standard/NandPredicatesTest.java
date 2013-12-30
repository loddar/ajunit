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

import org.junit.Test;

import static org.failearly.ajunit.predicate.standard.StandardPredicates.predicateNand;

/**
 * Tests for {@link StandardPredicates#predicateNand()}.
 */
public class NandPredicatesTest extends CompositePredicatesTest {

    public NandPredicatesTest() {
        super("NAND", predicateNand());
    }

    @Test
    public void trueTrue() throws Exception {
        // arrange / given
        registerPredicates(TRUE, TRUE);

        // assert / then
        assertCompositePredicateEvaluatesTo(false);
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
        assertCompositePredicateEvaluatesTo(true);
    }
    @Test
    public void evaluateFalse() throws Exception {
        // arrange / given
        registerPredicates(FALSE);

        // assert / then
        assertCompositePredicateEvaluatesTo(true);
    }

    @Test
    public void evaluateTrue() throws Exception {
        // arrange / given
        registerPredicates(TRUE);

        // assert / then
        assertCompositePredicateEvaluatesTo(false);
    }
}

