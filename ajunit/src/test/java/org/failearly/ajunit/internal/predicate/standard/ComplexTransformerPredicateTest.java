/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 marko (http://fail-early.com/contact)
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
 * Tests for
 * {@link StandardPredicates#transformerPredicate(org.failearly.ajunit.internal.transformer.Transformer, org.failearly.ajunit.internal.predicate.Predicate)}.
 */
public class ComplexTransformerPredicateTest {
    private static final Object C_VALUE = "$Final Result$";
    public static final String B_VALUE = "B.value";

    @Test
    public void chainedTransformer() throws Exception {
        // arrange / given
        final Predicate predicate = StandardPredicates.transformerPredicate(
                    StandardTransformers.transformerComposition(
                            StandardTransformers.reflection(A.class,"getB"),
                            StandardTransformers.reflection(B.class,"getC"),
                            StandardTransformers.reflection(C.class,"getValue")
                    ),
                    StandardPredicates.equalsPredicate(C_VALUE)
                );

        // assert / then
        assertThat("Predicate result?", predicate.evaluate(new A()), is(true));
    }

    @Test
    public void nestedTransformer() throws Exception {
        // arrange / given
        final CompositePredicate predicate = StandardPredicates.transformerPredicate(
                StandardTransformers.reflection(A.class,"getB"),
                LogicalPredicates.and()
        );

        final CompositePredicate toClassC = StandardPredicates.transformerPredicate(
                StandardTransformers.reflection(B.class,"getC"),
                LogicalPredicates.and()
        );

        toClassC.addPredicate(
                StandardPredicates.transformerPredicate(
                        StandardTransformers.reflection(C.class, "getValue"),
                        StandardPredicates.equalsPredicate(C_VALUE)
                )
        );

        predicate.addPredicate(toClassC);
        predicate.addPredicate(
                StandardPredicates.transformerPredicate(
                        StandardTransformers.reflection(B.class, "getValue"),
                        StandardPredicates.equalsPredicate(B_VALUE)
                )
        );

        // assert / then
        assertThat("Predicate result?", predicate.evaluate(new A()), is(true));
    }


    @SuppressWarnings("unused")
    private static class A {
        B getB() {
            return new B();
        }
    }

    @SuppressWarnings("unused")
    private static class B {

        C getC() {
            return new C();
        }

        String getValue() {
            return B_VALUE;
        }
    }

    @SuppressWarnings("unused")
    private static class C {
        Object getValue() {
            return C_VALUE;
        }
    }



}
