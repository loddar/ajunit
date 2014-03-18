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

import org.failearly.ajunit.internal.predicate.Predicate;

import java.util.Collection;

/**
 * Base class for all collection predicate.
 */
final class CollectionPredicate<T extends Collection> extends CollectionPredicateBase<T> {
    private final Predicate predicate;
    private final boolean breakingEvaluationResult;

    public CollectionPredicate(Class<T> clazz,  String type, Predicate predicate, boolean breakingEvaluationResult) {
        super(clazz, type);
        this.predicate = predicate;
        this.breakingEvaluationResult = breakingEvaluationResult;
    }

    @Override
    protected boolean doTypedEvaluate(T collection) {
        if( collection.isEmpty() )
            return false;

        return doApplyPredicateOnCollection(collection);
    }

    private boolean doApplyPredicateOnCollection(T collection) {
        for (Object object : collection) {
            if(breakingEvaluationResult == predicate.evaluate(object)) {
                return breakingEvaluationResult;
            }
        }

        return !breakingEvaluationResult;
    }
}
