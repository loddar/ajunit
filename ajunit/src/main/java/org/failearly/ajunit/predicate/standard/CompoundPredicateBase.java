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
import org.failearly.ajunit.util.AjAssert;

import java.util.Collection;
import java.util.LinkedList;


/**
 * CompoundPredicateBase is the base class for {@link org.failearly.ajunit.predicate.CompositePredicate} implementations.
 */
abstract class CompoundPredicateBase implements CompositePredicate {
    private final Collection<Predicate> predicates=new LinkedList<>();

    protected CompoundPredicateBase() {
    }

    @Override
    public boolean evaluate(final Object object) {
        AjAssert.parameterNotNull(object, "object");
        if( noPredicates() ) {
            return true;
        }
        return doEvaluate(predicates, object);
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

    protected abstract boolean doEvaluate(final Collection<Predicate> predicates, final Object object);

}
