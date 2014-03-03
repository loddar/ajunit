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
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.predicate.standard.IsSubclassOfPredicate}.
 */
public class IsSubclassOfPredicateTest {
    private static interface IFace {}
    private static class Base {}
    private static class Derived extends Base implements IFace {}

    @Test
    public void baseToItself() {
        doTest(Base.class, Base.class, true);
    }

    @Test
    public void derivedFromBase() {
        doTest(Base.class, Derived.class, true);
    }

    @Test
    public void derivedFromIFace() {
        doTest(IFace.class, Derived.class, true);
    }

    @Test
    public void unrelated() {
        doTest(IFace.class, Base.class, false);
        doTest(Base.class, IFace.class, false);
    }

    @Test
    public void derivedAndBaseExchanged() {
        doTest(Derived.class, Base.class, false);
    }


    private void doTest(Class<?> baseClass, Class<?> parameterClass, boolean evaluatesTo) {
        // arrange / given
        final Predicate predicate=StandardPredicates.predicateIsSubclass(baseClass);

        // assert / then
        assertThat(parameterClass.getSimpleName() + " is sub class of " + baseClass.getSimpleName() +"?",
                predicate.evaluate(parameterClass),
                is(evaluatesTo));
    }
}
