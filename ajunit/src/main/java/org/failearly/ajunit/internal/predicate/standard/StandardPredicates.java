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

import org.failearly.ajunit.internal.predicate.AbstractPredicate;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.TypedPredicate;
import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * StandardPredicates provides factory methods for some standard {@link Predicate}s.
 */
public final class StandardPredicates {

    private static final Predicate P_TRUE = new ConstantPredicate(true);
    private static final Predicate P_FALSE = new ConstantPredicate(false);
    private static final Predicate P_IS_NULL = new AbstractPredicate("IsNull") {
        @Override
        public boolean test(Object object) {
            return object == null;
        }
    };
    private static final Predicate P_IS_NOT_NULL = new AbstractPredicate("IsNotNull") {
        @Override
        public boolean test(Object object) {
            return object != null;
        }
    };
    private static final TypedPredicate<Boolean> BOOLEAN_IDENTITY_PREDICATE = new TypedPredicate<Boolean>(Boolean.class, "BooleanIdentity") {
        @Override
        protected boolean doTypedTest(Boolean value) {
            return value;
        }
    };

    private StandardPredicates() {}


    /**
     * Predicate evaluates always to {@code true}.
     */
    public static Predicate alwaysTrue() {
        return P_TRUE;
    }

    /**
     * Predicate evaluates always to {@code false}.
     */
    public static Predicate alwaysFalse() {
        return P_FALSE;
    }

    /**
     * Predicate applies {@link java.lang.Object#equals(Object)}.
     */
    public static <T> Predicate equalsTo(final T object) {
        return new EqualsPredicate<>(object);
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
        return new SimpleTransformerPredicate(transformer, predicate);
    }

    /**
     * The created Predicate, applies first the {@code transformer} and if the transformed object is {@code not null}, the
     * {@code compositePredicate} will be applied. Briefly:  <code>CP(T(input))</code>.
     *
     * @param transformer the transformer
     * @param compositePredicate the compositePredicate
     * @return {@code false} if the transformer returns {@code null} or applies the {@code compositePredicate} on the transformed object.
     */
    public static CompositePredicate transformerPredicate(final Transformer transformer, final CompositePredicate compositePredicate) {
        return new DelegatingTransformerPredicate(transformer, compositePredicate);
    }

    /**
     * Predicate checks if the object to test is {@code not null}.
     */
    public static Predicate isNotNull() {
        return P_IS_NOT_NULL;
    }

    /**
     * Predicate checks if the object to test is {@code null}.
     */
    public static Predicate isNull() {
        return P_IS_NULL;
    }

    /**
     * Predicates checks whether given object is a {@code Boolean} object and returns the value without any further checks.
     * Internal use only.
     */
    public static Predicate booleanIdentity() {
        return BOOLEAN_IDENTITY_PREDICATE;
    }


    /**
     * Gives an unnamed predicate a name.
     * @param name the name
     * @param predicate the predicate to be applied
     * @return the named predicate
     */
    public static Predicate named(String name, Predicate predicate) {
        return new NamedPredicate(name, predicate);
    }

}
