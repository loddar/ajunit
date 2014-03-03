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

import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilder;
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilders;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Base class for {@link org.failearly.ajunit.internal.universe.AjJoinPoint} implementations.
 */
abstract class AjJoinPointBase implements AjJoinPoint {

    private final AjJoinPointType joinPointType;
    private final Class<?> declaringClass;
    private int numApplications = 0;

    AjJoinPointBase(final AjJoinPointType joinPointType, Class<?> declaringClass) {
        this.joinPointType = joinPointType;
        this.declaringClass = declaringClass;
    }


    @Override
    public final AjJoinPointType getJoinPointType() {
        return this.joinPointType;
    }

    @Override
    public final Class<?> getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Field getField() {
        return null;
    }

    @Override
    public Constructor getConstructor() {
        return null;
    }

    @Override
    public final void apply() {
        this.numApplications++;
    }

    @Override
    public final int getNumApplications() {
        return this.numApplications;
    }

    @Override
    public String toString(AjJoinPointStringBuilder stringBuilder) {
        return stringBuilder
                .setJoinPointType(this.joinPointType)
                .setNumberOfApplications(this.numApplications)
                .build();
    }

    @Override
    public final String toString() {
        return toString(AjJoinPointStringBuilders.toLongStringBuilder());
    }

    @Override
    public final String toShortString() {
        return toString(AjJoinPointStringBuilders.toShortStringBuilder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AjJoinPointBase)) return false;

        final AjJoinPointBase that = (AjJoinPointBase) o;

        return joinPointType == that.joinPointType && declaringClass.equals(that.declaringClass);

    }

    @Override
    public int hashCode() {
        int result = joinPointType.hashCode();
        result = 31 * result + declaringClass.hashCode();
        return result;
    }
}
