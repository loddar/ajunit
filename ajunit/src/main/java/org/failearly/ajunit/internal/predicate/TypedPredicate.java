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
package org.failearly.ajunit.internal.predicate;

/**
 * TypedPredicate is the type safe version base class for {@link org.failearly.ajunit.internal.predicate.Predicate}.
 */
public abstract class TypedPredicate<T> extends PredicateBase {
    private final Class<T> parameterClass;

    protected TypedPredicate(final Class<T> parameterClass, String name) {
        super(name);
        this.parameterClass = parameterClass;
    }


    @Override
    protected final boolean doTest(Object object) {
        return doTypedTest(doCast(object));
    }

    /**
     * Does the actually (type safe) test.
     * @param typedObject object already cast to T.
     * @see org.failearly.ajunit.internal.predicate.Predicate#test(Object).
     */
    protected abstract boolean doTypedTest(final T typedObject);

    private T doCast(Object object) {
        return parameterClass.cast(object);
    }
}
