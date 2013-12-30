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

import org.failearly.ajunit.predicate.Predicate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.predicate.standard.IsSubclassOfPredicate}.
 */
public class IsSubclassOfPredicateTest {
    private static interface IFace {}
    private static class Base {}
    private static class Derived extends Base implements IFace {}

    @Test
    public void baseToItself() throws Exception {
        doTest(Base.class, Base.class, true);
    }

    @Test
    public void derivedFromBase() throws Exception {
        doTest(Base.class, Derived.class, true);
    }

    @Test
    public void derivedFromIFace() throws Exception {
        doTest(IFace.class, Derived.class, true);
    }

    @Test
    public void unrelated() throws Exception {
        doTest(IFace.class, Base.class, false);
        doTest(Base.class, IFace.class, false);
    }

    @Test
    public void derivedAndBaseExchanged() throws Exception {
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
