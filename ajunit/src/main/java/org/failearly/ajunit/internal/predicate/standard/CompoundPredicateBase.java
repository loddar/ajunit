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
import org.failearly.ajunit.internal.predicate.PredicateBase;

import java.util.Collection;
import java.util.LinkedList;


/**
 * CompoundPredicateBase is the base class for {@link org.failearly.ajunit.internal.predicate.CompositePredicate} implementations.
 */
abstract class CompoundPredicateBase extends PredicateBase implements CompositePredicate {
    private final Collection<Predicate> predicates=new LinkedList<>();

    protected CompoundPredicateBase() {
    }

    @Override
    protected final boolean doEvaluate(final Object object) {
        return noPredicates() || doApplyPredicates(predicates, object);
    }

    @Override
    public final CompositePredicate addPredicate(final Predicate predicate) {
        predicates.add(predicate);
        return this;
    }

    @Override
    public final boolean noPredicates() {
        return predicates.isEmpty();
    }

    /**
     * Apply the added predicates on the given parameter {@code object}.
     * @param predicates the added predicates.
     * @param object the parameter of {@link #evaluate(Object)}.
     * @return {@code true} or {@code false}.
     */
    protected abstract boolean doApplyPredicates(final Collection<Predicate> predicates, final Object object);

}
