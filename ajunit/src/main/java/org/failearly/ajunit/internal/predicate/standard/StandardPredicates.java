/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.predicate.standard;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * StandardPredicates provides factory methods for some standard {@link Predicate}s.
 */
public abstract class StandardPredicates {

    private static final Predicate P_TRUE = new ConstantPredicate(true);
    private static final Predicate P_FALSE = new ConstantPredicate(false);

    private StandardPredicates() {}


    /**
     * Predicate evaluates always to {@code true}.
     * FOR TESTING PURPOSES ONLY.
     */
    public static Predicate alwaysTrue() {
        return P_TRUE;
    }

    /**
     * Predicate evaluates always to {@code false}.
     * FOR TESTING PURPOSES ONLY.
     */
    public static Predicate alwaysFalse() {
        return P_FALSE;
    }

    /**
     * Predicate applies {@link java.lang.Object#equals(Object)}.
     */
    public static <T> Predicate predicateEquals(final T object) {
        return new EqualsPredicate<>(object);
    }

    /**
     * Predicate applies {@link java.lang.Class#isAssignableFrom(Class)}.
     */
    public static Predicate predicateIsSubclass(final Class<?> clazz) {
        return new IsSubclassOfPredicate(clazz);
    }

    /**
     * The created predicate, applies first the {@code transformer} and if the transformed object is {@code not null}, the
     * {@code predicate} will be applied. Briefly:  <code>P(T(input))</code>.
     *
     * @param transformer the transformer
     * @param predicate the predicate
     * @return {@code false} if the transformer returns {@code null} or applies the {@code predicate} on the transformed object.
     */
    public static Predicate transformerPredicate(final Transformer transformer, final Predicate predicate) {
        return new TransformerPredicate(transformer, predicate);
    }

}