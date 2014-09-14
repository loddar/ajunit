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

import org.failearly.ajunit.internal.Named;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;

/**
 * FakeCompositePredicate decorates a normal predicate to a (fake) composite predicate.
 */
final class FakeCompositePredicate extends Named implements CompositePredicate {
    private final Predicate predicate;

    FakeCompositePredicate(Predicate predicate) {
        super(""+predicate, false);
        this.predicate = predicate;
    }

    @Override
    public CompositePredicate addPredicate(Predicate predicate) {
        AjAssert.assertCondition(false, MessageBuilders.message(
                "Just faking a composite predicate. Don't use addPredicate()!")
        );
        return this;
    }

    @Override
    public CompositePredicate addPredicates(Iterable<Predicate> predicates) {
        AjAssert.assertCondition(false, MessageBuilders.message(
                "Just faking a composite predicate. Don't use addPredicates()!")
        );
        return this;
    }

    @Override
    public boolean noPredicates() {
        return false;
    }

    @Override
    public boolean test(Object object) {
        return predicate.test(object);
    }
}
