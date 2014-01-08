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
package org.failearly.ajunit.internal.predicate;

/**
 * Predicate is highly inspired by Predicate implementation in Jakarta commons collection.
 */
public interface Predicate {
    /**
     * Use the specified parameter ({@code object}) to perform a test that returns true or false.
     *
     * @param object any object {@code not null}.
     *
     * @return {@code true} or {@code false}.
     *
     * @throws java.lang.ClassCastException if the input is the wrong class.
     * @throws java.lang.IllegalArgumentException if the input is invalid.
     */
    boolean evaluate(final Object object);

    /**
     * @return String representation (TYPE)
     */
    String toString();

    /**
     * @return  Type or name of the predicate.
     */
    String getType();
}
