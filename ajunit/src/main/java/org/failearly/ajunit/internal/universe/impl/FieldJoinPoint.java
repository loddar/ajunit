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

import java.lang.reflect.Field;

/**
 * Any join point associated with {@link java.lang.reflect.Field}.
 */
final class FieldJoinPoint extends AjJoinPointBase {

    private final Field field;

    FieldJoinPoint(final AjJoinPointType joinPointType, final Field field) {
        super(joinPointType, field.getDeclaringClass());
        this.field = field;
    }


    @Override
    public Field getField() {
        return this.field;
    }


    @Override
    public boolean equals(Object o) {
        if( ! super.equals(o) ) return false;

        if (!(o instanceof FieldJoinPoint)) return false;

        final FieldJoinPoint that = (FieldJoinPoint) o;

        return field.equals(that.field);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + field.hashCode();
        return result;
    }

    @Override
    protected void toString(StringBuilder stringBuilder, boolean longToString) {
        if( longToString ) {
            stringBuilder.append(", field=").append(this.field);
        }
        else {
            stringBuilder.append(this.field);
        }
    }
}
