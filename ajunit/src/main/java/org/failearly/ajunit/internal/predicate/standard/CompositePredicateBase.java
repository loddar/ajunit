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

import java.util.Collection;
import java.util.LinkedList;


/**
 * CompositePredicateBase is the base class for {@link org.failearly.ajunit.internal.predicate.CompositePredicate} implementations.
 */
abstract class CompositePredicateBase extends PredicateBase implements CompositePredicate {
    private final Collection<Predicate> predicates=new LinkedList<>();

    protected CompositePredicateBase(String name) {
        super(name);
    }

    @Override
    protected final boolean doTest(final Object object) {
        return noPredicates() || doApplyPredicates(predicates, object);
    }

    @Override
    public final CompositePredicate addPredicate(final Predicate predicate) {
        predicates.add(predicate);
        return this;
    }

    @Override
    public final CompositePredicate addPredicates(Iterable<Predicate> predicates) {
        for (Predicate predicate : predicates) {
            this.addPredicate(predicate);
        }
        return this;
    }

    @Override
    public final boolean noPredicates() {
        return predicates.isEmpty();
    }

    /**
     * Apply the added predicates on the given parameter {@code object}.
     * @param predicates the added predicates.
     * @param object the parameter of {@link #test(Object)}.
     * @return {@code true} or {@code false}.
     */
    protected abstract boolean doApplyPredicates(final Collection<Predicate> predicates, final Object object);


    @Override
    protected String getName0() {
        final StringBuilder stringBuilder=new StringBuilder(getName());
        boolean first=true;
        stringBuilder.append("(");
        for (Predicate predicate : predicates) {
            if(!first) {
                stringBuilder.append(",");
            }
            stringBuilder.append(predicate.toString());
            first=false;
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
