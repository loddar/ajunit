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

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;

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
     * Creates an (empty) logical OR predicate.
     */
    public static CompositePredicate or() {
        return new OrPredicate();
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


}