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
package org.failearly.ajunit.internal.universe.impl;

import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.util.AjAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AjUniversesHolder is responsible for holding all universes.
 *
 * @see org.failearly.ajunit.internal.universe.AjUniverse
 */
public final class AjUniversesHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AjUniversesHolder.class);

    private static final AjUniversesHolder INSTANCE = new AjUniversesHolder();
    private final ConcurrentMap<String, AjUniverseImpl> universes = new ConcurrentHashMap<>();

    private AjUniversesHolder() {
    }

    /**
     * Creates the universe associated with current ajUnitObject.
     *
     * @param universeName       the universe name.
     * @param testFixtureClasses the test fixture classes the aspect will be apply on.
     * @return the universe associated.
     */
    public static AjUniverse buildUniverseByClasses(final String universeName, final Collection<Class<?>> testFixtureClasses) {
        return INSTANCE.doFindOrCreateUniverse(universeName, testFixtureClasses);
    }

    /**
     * Find or create the (current) universe by universe name.
     */
    public static AjUniverse findOrCreateUniverse(String universeName) {
        return INSTANCE.doFindOrCreateUniverse(universeName);
    }

    /**
     * Drop the universe.
     */
    public static void dropUniverse(String universeName) {
        INSTANCE.doDropUniverse(universeName);
    }

    private void doDropUniverse(String universeName) {
        universes.remove(universeName);
    }

    private AjUniverseImpl doFindOrCreateUniverse(final String universeName) {
        final AjUniverseImpl newUniverse = new AjUniverseImpl(universeName);
        if( null==universes.putIfAbsent(universeName, newUniverse) ) {
            LOGGER.info("Universe {} has been created.", universeName);
        }
        return universes.get(universeName);
    }

    private AjUniverse doFindOrCreateUniverse(final String universeName, final Collection<Class<?>> testFixtureClasses) {
        AjAssert.parameterNotEmpty(testFixtureClasses, "testFixturesClasses");
        final AjUniverseImpl universe=doFindOrCreateUniverse(universeName);
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

    /**
     * Drop all universes. Currently used only for test class.
     */
    static void dropUniverses() {
        INSTANCE.universes.clear();
    }

    /**
     * Returns an existing universe or {@code null}.
     * @param universeName the universe name.
     */
    public static AjUniverse findUniverse(String universeName) {
        return INSTANCE.universes.get(universeName);
    }
}
