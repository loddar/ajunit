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
package org.failearly.ajunit.internal.universe.impl;

import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilder;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.lang.reflect.Constructor;

/**
 * Any join point associated with {@link java.lang.reflect.Constructor}.
 */
final class ConstructorJoinPoint extends AjJoinPointBase {

    private final Constructor<?> constructor;

    ConstructorJoinPoint(final AjJoinPointType joinPointType, final Constructor<?> constructor) {
        super(joinPointType, constructor.getDeclaringClass());
        this.constructor = constructor;
    }


    @Override
    public Constructor getConstructor() {
        return this.constructor;
    }

    @Override
    public boolean equals(Object o) {
        if( ! super.equals(o) ) return false;

        if (!(o instanceof ConstructorJoinPoint)) return false;

        final ConstructorJoinPoint that = (ConstructorJoinPoint) o;

        return constructor.equals(that.constructor);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + constructor.hashCode();
        return result;
    }

    @Override
    public String toString(AjJoinPointStringBuilder stringBuilder) {
        return super.toString(stringBuilder.setConstructor(this.constructor));
    }
}
