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


import org.failearly.ajunit.universe.AjJoinPointType;
import org.failearly.ajunit.universe.AjUniverse;
import org.failearly.ajunit.util.AbstractClassVisitor;
import org.failearly.ajunit.util.ClassUtils;
import org.failearly.ajunit.util.ClassVisitor;
import org.failearly.ajunit.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * AjUniverseInitializer initialize the {@link org.failearly.ajunit.universe.AjUniverse} instance by analyzing all
 * classes. It's a helper class.
 *
 * @see AjUniversesHolder
 */
final class AjUniverseInitializer {
    private final AjUniverseImpl universe;

    AjUniverseInitializer(final AjUniverseImpl universe) {
        this.universe = universe;
    }

    /**
     * Initialize the AjUniverse.
     *
     * @param testFixtureClasses the classes which spans the universe and therefore are the test fixtures.
     */
    AjUniverse initialize(final Collection<Class<?>> testFixtureClasses) {
        final ClassVisitor classVisitor = createClassVisitor();
        for (final Class<?> testFixtureClass : ClassUtils.getClassesAndInterfaces(testFixtureClasses)) {
            ReflectionUtils.visit(testFixtureClass, classVisitor);
        }
        universe.markInitialized();
        return universe;
    }

    private ClassVisitor createClassVisitor() {
        return new AbstractClassVisitor() {
            @Override
            public void visit(final Method method) {
                initMethodJoinPoints(method);
            }

            @Override
            public void visit(final Constructor<?> constructor) {
                initConstructorJoinPoints(constructor);
            }

            @Override
            public void visit(final Field field) {
                initFieldJoinPoints(field);
            }
        };
    }


    private void initMethodJoinPoints(final Method method) {
        for (AjJoinPointType joinPointType : AjJoinPointType.SUPPORTED_JOIN_POINT_TYPES.values()) {
            joinPointType.accept(method, this.universe);
        }
    }

    private void initFieldJoinPoints(final Field field) {
        for (AjJoinPointType joinPointType : AjJoinPointType.SUPPORTED_JOIN_POINT_TYPES.values()) {
            joinPointType.accept(field, this.universe);
        }
    }


    private void initConstructorJoinPoints(final Constructor<?> constructor) {
        for (AjJoinPointType joinPointType : AjJoinPointType.SUPPORTED_JOIN_POINT_TYPES.values()) {
            joinPointType.accept(constructor, this.universe);
        }
    }
}
