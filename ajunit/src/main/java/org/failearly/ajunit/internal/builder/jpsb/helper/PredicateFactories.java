/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.builder.jpsb.helper;

import org.failearly.ajunit.internal.predicate.Predicate;

import java.util.HashMap;
import java.util.Map;

/**
 * PredicateMap is responsible for ...
 */
final class PredicateFactories<E,T> {
    private final Map<E,PredicateFactory<T>> predicateFactories=new HashMap<>();

    PredicateFactories<E,T> addFactory(E type, PredicateFactory<T> factory) {
        predicateFactories.put(type, factory);
        return this;
    }

    Predicate createPredicate(E type, T input) {
        return predicateFactories.get(type).createPredicate(input);
    }
}
