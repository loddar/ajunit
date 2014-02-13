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

import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.lang.reflect.Method;

/**
 * Any join point associated with {@link java.lang.reflect.Method}.
 */
final class MethodJoinPoint extends AjJoinPointBase {

    private final Method method;

    MethodJoinPoint(final AjJoinPointType joinPointType, final Method method) {
        super(joinPointType, method.getDeclaringClass());
        this.method = method;
    }


    @Override
    public Method getMethod() {
        return this.method;
    }


    @Override
    public boolean equals(Object o) {
        if( ! super.equals(o) ) return false;

        if (!(o instanceof MethodJoinPoint)) return false;

        final MethodJoinPoint that = (MethodJoinPoint) o;

        return method.equals(that.method);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + method.hashCode();
        return result;
    }

    @Override
    protected void toString(StringBuilder stringBuilder, boolean longToString) {
        if( longToString ) {
            stringBuilder.append(", method=").append(this.method);
        }
        else {
            stringBuilder.append(this.method);
        }
    }
}
