/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014  Marko Umek (ajunit.contact(at)gmail.com)
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

import org.failearly.ajunit.internal.predicate.Predicate;

import java.util.Collection;

/**
 * XorPredicate implements XOR(P1(o),..,Pn(o)). Returns {@code true} if <i>only one</i> predicate evaluates
 * to {@code true} otherwise {@code false}.
 */
final class XorPredicate extends CompoundPredicateBase {
    @Override
    protected boolean doApplyPredicates(Collection<Predicate> predicates, Object object) {
        int countTrues=0;
        for (final Predicate predicate : predicates) {
            if( predicate.evaluate(object) ) {
                countTrues += 1;
            }
            if( countTrues>1 ) {
                break;
            }
        }
        return 1==countTrues;
    }
}
