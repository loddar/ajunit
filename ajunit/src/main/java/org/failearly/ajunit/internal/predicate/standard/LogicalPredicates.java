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
package org.failearly.ajunit.internal.predicate.standard;

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;

import java.util.Arrays;
import java.util.List;

/**
 * LogicalPredicates provides factory methods for logical operations.
 */
public final class LogicalPredicates {
    /**
     * Creates an (empty) logical AND predicate.
     */
    public static CompositePredicate and() {
        return new AndPredicate();
    }

    /**
     * Creates an logical AND predicate.
     */
    public static Predicate and(Predicate... predicates) {
        return composite(and(), predicates);
    }

    /**
     * Creates an logical AND predicate.
     */
    public static Predicate and(List<Predicate> predicates) {
        return composite(and(), predicates);
    }


    /**
     * Creates an (empty) logical OR predicate.
     */
    public static CompositePredicate or() {
        return new OrPredicate();
    }

    /**
     * Creates an logical OR predicate.
     */
    public static Predicate or(Predicate... predicates) {
        return composite(or(), predicates);
    }

    /**
     * Creates an logical OR predicate.
     */
    public static Predicate or(List<Predicate> predicates) {
        return composite(or(), predicates);
    }

    /**
     * Creates an (empty) logical XOR predicate.
     */
    public static CompositePredicate xor() {
        return new XorPredicate();
    }

    /**
     * Negates the predicate.
     */
    public static Predicate not(Predicate predicate) {
        return new NotPredicate(predicate);
    }

    /**
     * Negates the result of the composite predicate.
     */
    public static CompositePredicate not(CompositePredicate predicate) {
        return new NotCompositePredicate(predicate);
    }

    /**
     * Creates an (empty) logical NAND predicate.
     */
    public static CompositePredicate nand() {
        return not(and());
    }

    /**
     * Creates an (empty) logical NOR predicate.
     */
    public static CompositePredicate nor() {
        return not(or());
    }

    /**
     * Creates an logical NOR predicate.
     */
    public static Predicate nor(List<Predicate> predicates) {
        return not(composite(or(), predicates));
    }

    private static CompositePredicate composite(CompositePredicate compositePredicate, Predicate[] predicates) {
        return composite(compositePredicate, Arrays.asList(predicates));
    }

    private static CompositePredicate composite(CompositePredicate compositePredicate, Iterable<Predicate> predicates) {
        for (Predicate predicate : predicates) {
            compositePredicate.addPredicate(predicate);
        }

        return compositePredicate;
    }
}