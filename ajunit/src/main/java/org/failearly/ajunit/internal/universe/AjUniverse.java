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
package org.failearly.ajunit.internal.universe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * An AjUniverse builds the <i>Test Fixture</i> for the pointcut tests.
 */
public interface AjUniverse {
    AjUniverse NULL_UNIVERSE=new AjUniverse() {
        @Override
        public String getUniverseName() {
            return null;
        }

        @Override
        public boolean isInitialized() {
            return false;
        }

        @Override
        public void addJoinpoint(AjJoinPointType joinPointType, Method method) {

        }

        @Override
        public void addJoinpoint(AjJoinPointType joinPointType, Field field) {

        }

        @Override
        public void addJoinpoint(AjJoinPointType joinPointType, Constructor<?> constructor) {

        }

        @Override
        public void visitJoinPoints(AjJoinPointVisitor joinPointVisitor) {

        }
    };

    /**
     * The universe name.
     */
    String getUniverseName();

    /**
     * @return {@code true} if the universe has been initialized.
     */
    boolean isInitialized();

    /**
     * Create and add {@link org.failearly.ajunit.internal.universe.AjJoinPoint} for {@link java.lang.reflect.Method} instance.
     */
    void addJoinpoint(AjJoinPointType joinPointType, Method method);

    /**
     * Create and add {@link org.failearly.ajunit.internal.universe.AjJoinPoint} for {@link java.lang.reflect.Field} instance.
     */
    void addJoinpoint(AjJoinPointType joinPointType, Field field);

    /**
     * Create and add {@link org.failearly.ajunit.internal.universe.AjJoinPoint} for {@link java.lang.reflect.Constructor} instance.
     */
    void addJoinpoint(AjJoinPointType joinPointType, Constructor<?> constructor);

    /**
     * Visit all {@link AjJoinPoint} stored in the universe.
     *
     * @param joinPointVisitor the join point visitor.
     *
     */
    void visitJoinPoints(AjJoinPointVisitor joinPointVisitor);
}
