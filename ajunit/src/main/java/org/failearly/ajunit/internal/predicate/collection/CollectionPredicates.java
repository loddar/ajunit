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
package org.failearly.ajunit.internal.predicate.collection;

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.TypedPredicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;

import java.util.Collection;

/**
 * CollectionPredicates provides factory methods for applying a single predicate on collection of objects.
 */
public abstract class CollectionPredicates {

    private static final Predicate P_IS_EMPTY = new TypedPredicate <Collection>(Collection.class, "isEmpty") {
        @Override
        protected boolean doTypedEvaluate(Collection collection) {
            return collection.isEmpty();
        }
    };

    private static final Predicate P_IS_NOT_EMPTY = LogicalPredicates.not(P_IS_EMPTY);

    private CollectionPredicates() {
    }

    /**
     * The resulting compositePredicate evaluates to {@code true},
     * if any of the elements evaluates to {@code true} by using given {@code compositePredicate}.
     * If the collection is empty the compositePredicate returns {@code false}.
     *
     * @param compositePredicate the compositePredicate to by applied on the collection element.
     * @return new composite predicate.
     */
    public static CompositePredicate anyOf(CompositePredicate compositePredicate) {
        return new CollectionPredicate<>(Collection.class, "AnyOf", compositePredicate, true);
    }

    /**
     * The resulting compositePredicate evaluates to {@code true},
     * if all of the elements evaluates to {@code true} by using given {@code compositePredicate}.
     * If the collection is empty the compositePredicate returns {@code false}.
     *
     * @param compositePredicate the compositePredicate to by applied on the collection element.
     * @return new compositePredicate.
     */
    public static CompositePredicate allOf(CompositePredicate compositePredicate) {
        return new CollectionPredicate<>(Collection.class, "AllOf", compositePredicate, false);
    }

    /**
     * The resulting predicate evaluates to {@code true},
     * if none of the elements evaluates to {@code true} by using given {@code predicate}.
     * If the collection is empty the predicate returns {@code false}.
     *
     * @param compositePredicate the predicate to by applied on the collection element.
     * @return new predicate.
     */
    public static CompositePredicate noneOf(CompositePredicate compositePredicate) {
        return new NoneOfCollectionPredicate<>(Collection.class, compositePredicate);
    }

    /**
     * The resulting predicate evaluates to {@code true}, if the collection is empty.
     * @return new predicate.
     */
    public static Predicate isEmpty() {
        return P_IS_EMPTY;
    }

    /**
     * The resulting predicate evaluates to {@code true}, if the collection is not empty.
     * @return new predicate.
     */
    public static Predicate isNotEmpty() {
        return P_IS_NOT_EMPTY;
    }
}
