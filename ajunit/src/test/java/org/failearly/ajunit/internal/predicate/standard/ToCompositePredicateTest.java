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

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * ToCompositePredicateTest contains tests for {@link org.failearly.ajunit.internal.predicate.standard.StandardPredicates#toCompositePredicate(org.failearly.ajunit.internal.predicate.Predicate)}.
 */
public class ToCompositePredicateTest {
    private final Predicate equalsPredicate=StandardPredicates.equalsTo("VALUE");

    @Test
    public void equals() throws Exception {
        // arrange / given
        final CompositePredicate fakeCompositePredicate=StandardPredicates.toCompositePredicate(equalsPredicate);

        // assert / then
        assertThat("Equals evaluate to?", fakeCompositePredicate.test("VALUE"), is(true));
    }

    @Test
    public void notEquals() throws Exception {
        // arrange / given
        final CompositePredicate fakeCompositePredicate=StandardPredicates.toCompositePredicate(equalsPredicate);

        // assert / then
        assertThat("Equals evaluate to?", fakeCompositePredicate.test("ANY-OTHER-VALUE"), is(false));
    }

    @Test
    public void doNotDecorateCompositePredicates() throws Exception {
        // arrange / given
        final Predicate  originCompositePredicate=LogicalPredicates.and(equalsPredicate);
        final CompositePredicate fakeCompositePredicate=StandardPredicates.toCompositePredicate(originCompositePredicate);

        // assert / then
        assertThat("Do not decorate CompositePredicates?", fakeCompositePredicate, sameInstance(originCompositePredicate));
    }

    @Test
    public void toStringUsesDecoratedPredicateResult() throws Exception {
        // arrange / given
        final CompositePredicate fakeCompositePredicate=StandardPredicates.toCompositePredicate(equalsPredicate);

        // assert / then
        assertThat("toString() result?", fakeCompositePredicate.toString(), is("EqualsTo(VALUE)@EqualsPredicate"));
    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void doNotUseAddPredicate() throws Exception {
        // arrange / given
        final CompositePredicate fakeCompositePredicate=StandardPredicates.toCompositePredicate(StandardPredicates.alwaysFalse());

        // assert / then
        fakeCompositePredicate.addPredicate(equalsPredicate);
    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void doNotUseAddPredicates() throws Exception {
        // arrange / given
        final CompositePredicate fakeCompositePredicate=StandardPredicates.toCompositePredicate(StandardPredicates.alwaysFalse());

        // assert / then
        fakeCompositePredicate.addPredicates(Arrays.asList(equalsPredicate));
    }

}
