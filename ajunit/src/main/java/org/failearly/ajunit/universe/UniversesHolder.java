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
package org.failearly.ajunit.universe;

import org.failearly.ajunit.AjUnitObject;
import org.failearly.ajunit.AjUniverseName;
import org.failearly.ajunit.util.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * UniversesHolder is responsible for holding the universes.
 */
public final class UniversesHolder {

    private static final UniversesHolder INSTANCE = new UniversesHolder();
    private final Map<String, UniverseImpl> universes = new HashMap<>();

    private UniversesHolder() {
    }

    /**
     * Fetches the universe associated with current ajUnitObject.
     * @param ajUnitObject the aj unit object's (AJUnit test class and aspect).
     *
     * @param testSubjectClasses the classes of test subjects the aspect will be applied on.
     *
     * @return the universe associated.
     */
    public static Universe fetchUniverse(final AjUnitObject ajUnitObject, final Class<?>... testSubjectClasses) {
        final UniverseImpl universe = INSTANCE.getOrCreateUniverse(ajUnitObject);
        INSTANCE.initializeUniverse(universe, testSubjectClasses);
        return universe;
    }

    private void initializeUniverse(final UniverseImpl universe, final Class<?>... classes) {
        if (classes.length > 0) {
            final ClassVisitor classVisitor = createClassVisitor(createUniverseBuilder(universe));
            for (final Class<?> aClass : ClassUtils.getClassesAndInterfaces(classes)) {
                ReflectionUtils.visit(aClass, classVisitor);
            }
        }
    }

    private ClassVisitor createClassVisitor(final UniverseBuilder universeBuilder) {
        return new AbstractClassVisitor() {
            @Override
            public void visit(final Method method) {
                JoinPointType.buildUniverse(method, universeBuilder);
            }

            @Override
            public void visit(final Constructor<?> constructor) {
                JoinPointType.buildUniverse(constructor, universeBuilder);
            }

            @Override
            public void visit(final Field field) {
                JoinPointType.buildUniverse(field, universeBuilder);
            }
        };
    }

    private UniverseBuilder createUniverseBuilder(final UniverseImpl universe) {
        return new UniverseBuilder(universe);
    }

    private synchronized UniverseImpl getOrCreateUniverse(final AjUnitObject ajUnitObject) {
        final String universeName = resolveUniverseName(ajUnitObject);
        UniverseImpl universe = universes.get(universeName);
        if (universe == null) {
            universe = new UniverseImpl(universeName);
            universes.put(universeName, universe);
        }
        return universe;
    }

    private String resolveUniverseName(final AjUnitObject ajUnitObject) {
        final AjUniverseName universeName = AnnotationUtils.findClassAnnotation(ajUnitObject.getClass(), AjUniverseName.class);
        if (universeName == null) {
            throw new IllegalArgumentException("Aspect/TestClass: Missing annotation @AjUniverseName");
        }
        return universeName.value();
    }


}
