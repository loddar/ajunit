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

import org.failearly.ajunit.internal.predicate.TypedPredicate;

/**
 * IsSubclassOfPredicate evaluates to {@code true} if the object is a subclass of current class/interface.
 */
final class IsSubclassOfPredicate extends TypedPredicate<Class<?>> {
    private final Class<?> clazz;

    @SuppressWarnings("unchecked")
    IsSubclassOfPredicate(Class<?> clazz) {
        super((Class<Class<?>>) clazz.getClass(),"IsSubclassOf("+clazz.getName()+")");
        this.clazz = clazz;
    }

    @Override
    protected boolean doTypedEvaluate(final Class<?> clazz) {
        return this.clazz.isAssignableFrom(clazz);
    }
}
