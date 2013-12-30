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
package org.failearly.ajunit.predicate;

import org.failearly.ajunit.util.AjAssert;

/**
 * PredicateBase is the base implementation for all predicates. Checking that the given parameter {@code object} is {@code not null}.
 */
public abstract class PredicateBase implements Predicate {

    @Override
    public final boolean evaluate(Object object) {
        AjAssert.parameterNotNull(object,"object");
        return doEvaluate(object);
    }

    /**
     * Does the actually evaluation. The object could be considered {@code not null}.
     * @param object {@code not null}
     * @return {@code true} or {@code false}.
     */
    protected abstract boolean doEvaluate(final Object object);
}
