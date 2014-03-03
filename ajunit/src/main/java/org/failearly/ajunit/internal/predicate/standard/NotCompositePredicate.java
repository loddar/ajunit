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
package org.failearly.ajunit.internal.predicate.standard;

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.PredicateBase;

/**
 * NotCompositePredicate implements <code>NOT(cp(P1(o), ...,Pn(o)))</code>.
 * <ul>
 *     <li>cp := any implementation of {@link org.failearly.ajunit.internal.predicate.CompositePredicate}. </li>
 *     <li>P1, ..., Pn := any {@link org.failearly.ajunit.internal.predicate.Predicate} </li>
 * </ul>
 */
final class NotCompositePredicate extends PredicateBase implements CompositePredicate {

    private final CompositePredicate compositePredicate;

    public NotCompositePredicate(CompositePredicate compositePredicate) {
        super("Not("+compositePredicate.toString()+")");
        this.compositePredicate = compositePredicate;
    }

    @Override
    public CompositePredicate addPredicate(Predicate predicate) {
        this.compositePredicate.addPredicate(predicate);
        return this;
    }

    @Override
    public CompositePredicate addPredicates(Iterable<Predicate> predicates) {
        return this.compositePredicate.addPredicates(predicates);
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
