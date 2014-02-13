/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.predicate;

/**
 * CompositePredicate decorates {@link Predicate} with collection behaviour. Usually used for logical predicate implementation.
 *
 * @see org.failearly.ajunit.internal.predicate.standard.StandardPredicates
 */
public interface CompositePredicate extends Predicate {
    /**
     * Adds a predicate to the composite predicate.
     *
     * @param predicate a predicate.
     * @return itself - {@code this}.
     */
    CompositePredicate addPredicate(Predicate predicate);

    /**
     * Adds a collection of predicates at once to the composite.
     * @param predicates a collection of predicates.
     * @return itself - {@code this}.
     */
    CompositePredicate addPredicates(Iterable<Predicate> predicates);

    /**
     * @return {@code true} if any predicate has been added.
     *
     * @see #addPredicate(Predicate)
     */
    boolean noPredicates();

    /**
     * Use the specified parameter ({@code object}) to perform a test on all added predicates. The actually implementation
     * defines the outcome of the overall result. If no predicates has been added, the composite predicate evaluates to {@code true}.
     *
     * @param object any object {@code not null}.
     *
     * @return {@code true} or {@code false}.
     *
     * @throws java.lang.ClassCastException if the input is the wrong class.
     * @throws java.lang.IllegalArgumentException if the input is invalid.
     */
    boolean evaluate(final Object object);
}
