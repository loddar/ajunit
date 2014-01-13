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
 */
package org.failearly.ajunit.internal.predicate.number;

import org.failearly.ajunit.internal.predicate.Predicate;

import static org.failearly.ajunit.internal.predicate.standard.LogicalPredicates.not;

/**
 * IntegerPredicates provides factory methods for {@link java.lang.Integer} related functions.
 */
public abstract class IntegerPredicates {
    private IntegerPredicates() {
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} evaluates to {@code true} if <code>value > baseValue</code>.
     *
     * @param baseValue the base value
     * @return the predicate
     */
    public static Predicate greaterThen(final int baseValue) {
        return new GreaterThenPredicate(baseValue);
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} evaluates to {@code true} if <code>value <= baseValue</code>.
     *
     * @param baseValue the base value
     * @return the predicate
     */
    public static Predicate lessEqualThen(final int baseValue) {
        return not(greaterThen(baseValue));
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} evaluates to {@code true} if <code>value < baseValue</code>.
     *
     * @param baseValue the base value
     * @return the predicate
     */
    public static Predicate lessThen(final int baseValue) {
        return new LessThenPredicate(baseValue);
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} evaluates to {@code true} if <code>value >= baseValue</code>.
     *
     * @param baseValue the base value
     * @return the predicate
     */
    public static Predicate greaterEqualThen(final int baseValue) {
        return not(lessThen(baseValue));
    }
}
