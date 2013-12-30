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
import org.failearly.ajunit.predicate.PredicateBase;

/**
 * NotCompoundPredicate implements <code>NOT(cp(P1(o), ...,Pn(o)))</code>.
 * <ul>
 *     <li>cp := any implementation of {@link org.failearly.ajunit.predicate.CompositePredicate}. </li>
 *     <li>P1, ..., Pn := any {@link org.failearly.ajunit.predicate.Predicate} </li>
 * </ul>
 */
final class NotCompoundPredicate extends PredicateBase implements CompositePredicate {

    private final CompositePredicate compositePredicate;

    public NotCompoundPredicate(CompositePredicate compositePredicate) {
        this.compositePredicate = compositePredicate;
    }

    @Override
    public CompositePredicate addPredicate(Predicate predicate) {
        this.compositePredicate.addPredicate(predicate);
        return this;
    }

    @Override
    public boolean noPredicates() {
        return compositePredicate.noPredicates();
    }

    @Override
    protected boolean doEvaluate(Object object) {
        return noPredicates() || ! compositePredicate.evaluate(object);
    }

}
