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

import org.failearly.ajunit.internal.util.AjAssert;

/**
 * PredicateBase is the base implementation for almost all predicate implementation.
 * <br/><br/>
 * Testing on {@code null} is supported by
 * <ul>
 *     <li>{@link org.failearly.ajunit.internal.predicate.standard.StandardPredicates#isNull()} or </li>
 *     <li>{@link org.failearly.ajunit.internal.predicate.standard.StandardPredicates#isNotNull()}</li>
 * </ul>
 */
public abstract class PredicateBase implements Predicate {

    private final String type;

    protected PredicateBase(String type) {
        this.type = type;
    }

    @Override
    public final boolean test(Object object) {
        AjAssert.parameterNotNull(object,"object");
        return doTest(object);
    }

    /**
     * Does the actually evaluation. The object could be considered {@code not null}.
     * @param object {@code not null}
     * @return {@code true} or {@code false}.
     */
    protected abstract boolean doTest(final Object object);

    @Override
    public String toString() {
        return type;
    }

    @Override
    public final String getType() {
        return type;
    }
}
