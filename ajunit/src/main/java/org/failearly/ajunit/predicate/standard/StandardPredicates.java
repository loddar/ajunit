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

import org.failearly.ajunit.predicate.CompositePredicate;
import org.failearly.ajunit.predicate.Predicate;

/**
 * StandardPredicates is the factory utility class for standard Predicate implementations.
 */
public final class StandardPredicates {

    public static final ConstantPredicate P_TRUE = new ConstantPredicate(true);
    public static final ConstantPredicate P_FALSE = new ConstantPredicate(false);

    private StandardPredicates() {}

    public static CompositePredicate predicateAnd() {
        return new AndPredicate();
    }

    public static CompositePredicate predicateOr() {
        return new OrPredicate();
    }

    public static CompositePredicate predicateXor() {
        return new XorPredicate();
    }

    public static CompositePredicate predicateNand() {
        return predicateNot(predicateAnd());
    }

    public static CompositePredicate predicateNor() {
        return predicateNot(predicateOr());
    }

    public static Predicate predicateNot(Predicate predicate) {
        return new NotPredicate(predicate);
    }

    public static CompositePredicate predicateNot(CompositePredicate predicate) {
        return new NotCompoundPredicate(predicate);
    }

    public static Predicate predicateTrue() {
        return P_TRUE;
    }

    public static Predicate predicateFalse() {
        return P_FALSE;
    }

    public static <T> Predicate predicateEquals(final T object) {
        return new EqualsPredicate<>(object);
    }

    public static <T> Predicate predicateNotEquals(final T object) {
        return predicateNot(predicateEquals(object));
    }

    public static Predicate predicateIsSubclass(final Class<?> clazz) {
        return new IsSubclassOfPredicate(clazz);
    }


}
