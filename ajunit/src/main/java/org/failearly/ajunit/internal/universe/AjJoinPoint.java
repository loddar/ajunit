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
package org.failearly.ajunit.internal.universe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The ajUnit join point definition. Will be collected by {@link AjUniverse}.
 */
public interface AjJoinPoint {
    Logger LOGGER = LoggerFactory.getLogger(AjJoinPoint.class);

    /**
     * Used by {@link org.failearly.ajunit.AjUnitAspectBase}.
     */
    AjJoinPoint NULL_OBJECT = new AjJoinPoint() {
        @Override
        public AjJoinPointType getJoinPointType() {
            return null;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return null;
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
        public int getNumApplications() {
            return 0;
        }

        @Override
        public void apply() {
            LOGGER.info("Call apply on NULL_OBJECT");
        }

        @Override
        public String toShortString() {
            return null;
        }

        @Override
        public String toString(AjJoinPointStringBuilder stringBuilder) {
            return null;
        }
    };

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

    /**
     * Build a String using {@link AjJoinPointStringBuilder}.
     * @param stringBuilder the string builder
     * @return the string
     */
    String toString(AjJoinPointStringBuilder stringBuilder);
}
