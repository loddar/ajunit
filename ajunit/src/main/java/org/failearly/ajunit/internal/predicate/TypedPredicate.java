/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.predicate;

/**
 * TypedPredicate is a type safe version base class for {@link org.failearly.ajunit.internal.predicate.Predicate}.
 */
public abstract class TypedPredicate<T> extends PredicateBase {
    private final Class<T> parameterClass;

    protected TypedPredicate(final Class<T> parameterClass, String type) {
        super(type);
        this.parameterClass = parameterClass;
    }


    @Override
    protected final boolean doEvaluate(Object object) {
        return doTypedEvaluate(doCast(object));
    }

    /**
     * Does the actually (type safe) evaluate.
     * @param typedObject object already cast to T.
     * @see org.failearly.ajunit.internal.predicate.Predicate#evaluate(Object).
     */
    protected abstract boolean doTypedEvaluate(final T typedObject);

    private T doCast(Object object) {
        return parameterClass.cast(object);
    }
}
