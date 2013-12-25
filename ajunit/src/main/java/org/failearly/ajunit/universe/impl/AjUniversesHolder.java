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

import org.failearly.ajunit.universe.AjUniverse;
import org.failearly.ajunit.util.AjAssert;
import org.failearly.ajunit.util.ClassUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AjUniversesHolder is responsible for holding all (named) universes.
 *
 * @see org.failearly.ajunit.AjUniverseName
 * @see org.failearly.ajunit.universe.AjUniverse
 */
public final class AjUniversesHolder {

    private static final AjUniversesHolder INSTANCE = new AjUniversesHolder();
    private final ConcurrentMap<String, AjUniverseImpl> universes = new ConcurrentHashMap<>();

    private AjUniversesHolder() {
    }

    /**
     * Creates the universe associated with current ajUnitObject which must be marked also with {@link
     * org.failearly.ajunit.AjUniverseName}.
     *
     * @param universeName       the universe name.
     * @param testFixtureClasses the test fixture classes the aspect will be apply on.
     * @return the universe associated.
     */
    public static AjUniverse createUniverseByClasses(final String universeName, final Collection<Class<?>> testFixtureClasses) {
        return INSTANCE.doCreateUniverse(universeName, testFixtureClasses);
    }

    /**
     * Creates the universe associated with current ajUnitObject which must be marked also with {@link
     * org.failearly.ajunit.AjUniverseName}.
     *
     * @param universeName       the universe name.
     * @param testFixtureClassNames the test fixture class names the aspect will be apply on.
     * @return the universe associated.
     *
     * @throws java.lang.ClassNotFoundException
     */
    public static AjUniverse createUniverseByClassNames(final String universeName, final Collection<String> testFixtureClassNames)
            throws ClassNotFoundException {
        AjAssert.parameterNotEmpty(testFixtureClassNames, "testFixturesClassNames");
        final Collection<Class<?>> testFixtureClasses=loadClassesWithoutInit(testFixtureClassNames);
        return INSTANCE.doCreateUniverse(universeName, testFixtureClasses);
    }

    private static Collection<Class<?>> loadClassesWithoutInit(Collection<String> testFixtureClassNames) throws ClassNotFoundException {
        final Collection<Class<?>> classes=new HashSet<>();
        for (String className : testFixtureClassNames) {
            classes.add(ClassUtils.loadClass(className, false));
        }
        return classes;
    }

    private AjUniverse doCreateUniverse(final String universeName, final Collection<Class<?>> testFixtureClasses) {
        AjAssert.parameterNotEmpty(testFixtureClasses, "testFixturesClasses");
        final AjUniverseImpl universe=universes.putIfAbsent(universeName, new AjUniverseImpl(universeName));
        return initializeUniverse(universe, testFixtureClasses);
    }

    private AjUniverse initializeUniverse(final AjUniverseImpl universe, final Collection<Class<?>> testFixtureClasses) {
        if( ! universe.isInitialized() ) {
            final AjUniverseInitializer universeInitializer = new AjUniverseInitializer(universe);
            universeInitializer.initialize(testFixtureClasses);
        }

        AjAssert.state(universe.isInitialized(), "Universe " + universe.getUniverseName() +" must be in state initialized.");

        return universe;
    }

    public static AjUniverse findUniverse(String universeName) {
        return INSTANCE.doFindUniverse(universeName);
    }

    private AjUniverse doFindUniverse(String universeName) {
        final AjUniverse ajUniverse = universes.get(universeName);
        AjAssert.state(ajUniverse != null, "Could not find universe by name '" + universeName + "'");
        return ajUniverse;
    }
}
