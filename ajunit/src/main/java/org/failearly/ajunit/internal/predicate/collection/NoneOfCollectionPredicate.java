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
package org.failearly.ajunit.internal.predicate.collection;

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;

import java.util.Collection;

/**
 * NoneOfCollectionPredicate is responsible for ...
 */
final class NoneOfCollectionPredicate<T extends Collection> extends CollectionPredicateBase<T> {
    NoneOfCollectionPredicate(Class<T> clazz,  CompositePredicate compositePredicate) {
        super(clazz, "NoneOf", compositePredicate);
    }

    @Override
    protected boolean doApplyPredicateOnCollection(T collection, Predicate predicate) {
        for (Object object : collection) {
            if(predicate.test(object)) {
                return false;
            }
        }

        return true;
    }
}
