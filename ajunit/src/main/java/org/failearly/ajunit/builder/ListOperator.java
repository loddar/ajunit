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
package org.failearly.ajunit.builder;

/**
 * Available list (logical) operators for lists like method/constructor exceptions or method/constructor parameters.
 *
 * @see org.failearly.ajunit.builder.method.MethodJoinPointSelector#exceptionTypes(ListOperator)
 * @see org.failearly.ajunit.builder.method.MethodParametersSelector#parameterTypes(ListOperator)
 * @see org.failearly.ajunit.builder.method.MethodParametersSelector#parameterAnnotations(ListOperator)
 */
public enum ListOperator {
    /**
     * The {@link org.failearly.ajunit.internal.predicate.Predicate} evaluates to {@code true} to at least one element of the list.
     *
     * @see org.failearly.ajunit.internal.predicate.collection.CollectionPredicates#atLeastOne(org.failearly.ajunit.internal.predicate.CompositePredicate)
     */
    AT_LEAST_ONE,

    /**
     * The {@link org.failearly.ajunit.internal.predicate.Predicate} evaluates to {@code true} for each elements of the list.
     *
     * @see org.failearly.ajunit.internal.predicate.collection.CollectionPredicates#each(org.failearly.ajunit.internal.predicate.CompositePredicate)
     */
    EACH,

    /**
     * The {@link org.failearly.ajunit.internal.predicate.Predicate} evaluates to {@code false} for none of elements of the list.
     *
     * @see org.failearly.ajunit.internal.predicate.collection.CollectionPredicates#none(org.failearly.ajunit.internal.predicate.CompositePredicate)
     */
    NONE
}
