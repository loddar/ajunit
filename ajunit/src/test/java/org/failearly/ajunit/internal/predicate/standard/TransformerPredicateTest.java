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
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.predicate.standard.StandardPredicates#transformerPredicate(org.failearly.ajunit.internal.transformer.Transformer, org.failearly.ajunit.internal.predicate.Predicate)}.
 */
public class TransformerPredicateTest {
    private static final Object ANY_PARAMETER = "value not used";

    @Test
    public void transformerReturnsNotNull() throws Exception {
        // arrange / given
        final Predicate predicate = StandardPredicates.transformerPredicate(
                    StandardTransformers.identityTransformer(String.class),
                    StandardPredicates.alwaysTrue()
                );

        // assert / then
        assertThat("Predicate result?", predicate.evaluate(ANY_PARAMETER), is(true));
    }

    @Test
    public void transformerReturnsNull() throws Exception {
        // arrange / given
        final Predicate predicate = StandardPredicates.transformerPredicate(
                StandardTransformers.nullTransformer(),
                StandardPredicates.alwaysTrue()
        );

        // assert / then
        assertThat("Predicate result?", predicate.evaluate(ANY_PARAMETER), is(false));
    }

    @Test
    public void compositeTransformerReturnsNotNull() throws Exception {
        // arrange / given
        final CompositePredicate predicate = StandardPredicates.transformerPredicate(
                StandardTransformers.identityTransformer(String.class),
                LogicalPredicates.and()
        );

        predicate.addPredicate(StandardPredicates.alwaysTrue());

        // assert / then
        assertThat("Predicate result?", predicate.evaluate(ANY_PARAMETER), is(true));
    }

    @Test
    public void compositeTransformerReturnsNull() throws Exception {
        // arrange / given
        final CompositePredicate predicate = StandardPredicates.transformerPredicate(
                StandardTransformers.nullTransformer(),
                LogicalPredicates.and()
        );

        predicate.addPredicate(StandardPredicates.alwaysTrue());

        // assert / then
        assertThat("Predicate result?", predicate.evaluate(ANY_PARAMETER), is(false));
    }


}
