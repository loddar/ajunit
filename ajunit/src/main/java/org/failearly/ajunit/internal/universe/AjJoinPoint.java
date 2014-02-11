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
 */
package org.failearly.ajunit.internal.universe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The ajUnit join point definition. Will be collected by {@link AjUniverse}.
 */
public interface AjJoinPoint {
    /**
     * @return the ajUnit joinpoint type.
     */
    AjJoinPointType getJoinPointType();

    /**
     * @return The declaring class of the methods, constructor or field.
     */
    Class<?> getDeclaringClass();

    /**
     * @return {@code null} or the method object.
     */
    Method getMethod();

    /**
     * @return {@code null} or the field object.
     */
    Field  getField();

    /**
     * @return {@code null} or the constructor object.
     */
    Constructor getConstructor();

    /**
     * @return the number of applications (#executions of {@link #apply()}).
     */
    int getNumApplications();

    /**
     * Called by the ajUnit aspect.
     */
    void apply();

    /**
     * @return a short string representation of the joinpoint.
     */
    String toShortString();
}
