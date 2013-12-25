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
package org.failearly.ajunit.universe.impl;

import org.failearly.ajunit.universe.AjJoinPoint;
import org.failearly.ajunit.util.AjAssert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AjJoinPointImpl - The implementation.
 */
final class AjJoinPointImpl implements AjJoinPoint {

    private final AjJoinPointType joinPointType;
    private Class<?> declaringClass;
    private Method method;
    private Field field;
    private Constructor constructor;
    private int numApplications=0;

    AjJoinPointImpl(final AjJoinPointType joinPointType) {
        this.joinPointType = joinPointType;
    }


    @Override
    public AjJoinPointType getJoinPointType() {
        return this.joinPointType;
    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public Constructor getConstructor() {
        return this.constructor;
    }

    @Override
    public void apply() {
        this.numApplications++;
    }

    @Override
    public int getNumApplications() {
        return this.numApplications;
    }

    void setMethod(final Method method) {
        preconditions();
        AjAssert.parameterNotNull(method, "method");
        this.method = method;
        this.declaringClass=this.method.getDeclaringClass();
    }

    void setField(final Field field) {
        preconditions();
        AjAssert.parameterNotNull(field, "field");
        this.field = field;
        this.declaringClass=this.field.getDeclaringClass();
    }

    void setConstructor(final Constructor constructor) {
        preconditions();
        AjAssert.parameterNotNull(constructor, "constructor");
        this.constructor = constructor;
        this.declaringClass=this.constructor.getDeclaringClass();
    }

    private void preconditions() {
        AjAssert.attributeIsNull(this.declaringClass, "declaring class");
        AjAssert.attributeIsNull(this.method, "method");
        AjAssert.attributeIsNull(this.field, "field");
        AjAssert.attributeIsNull(this.constructor, "constructor");
    }


    @Override
    public String toString() {
        return AjJoinPointToStringBuilder.toLongString(this);
    }

    @Override
    public String toShortString() {
        return AjJoinPointToStringBuilder.toShortString(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AjJoinPointImpl)) return false;

        final AjJoinPointImpl that = (AjJoinPointImpl) o;

        if (joinPointType != that.joinPointType) return false;
        if (!declaringClass.equals(that.declaringClass)) return false;

        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (constructor != null ? !constructor.equals(that.constructor) : that.constructor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = joinPointType.hashCode();
        result = 31 * result + declaringClass.hashCode();
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (constructor != null ? constructor.hashCode() : 0);
        return result;
    }
}
