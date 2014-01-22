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
package org.failearly.ajunit;

/**
 * Setup the ajUnit Test. Adding test fixture classes and enabling suppressed join points.
 */
public interface AjUnitSetup {
    /**
     * Adds a class to the Universe. Setting up the test fixture.
     * @param clazz a class instance.
     * @return this.
     */
    AjUnitSetup addTestFixtureClass(Class<?> clazz);

    /**
     * Adds a class to the Universe. Setting up the test fixture.
     * @param className a full qualified class name.
     * @return this.
     */
    AjUnitSetup addTestFixtureClass(String className);

    /**
     * Opt in for suppressed join points. The usually suppressed join points (i.e. methods of {@link java.lang.Object}) could be enabled, so they will be
     * part of the universe (test fixture classes) again.
     *
     * @param suppressedJoinPoints any suppressed join point(s)
     * @return this
     *
     * @see org.failearly.ajunit.AjSuppressedJoinPoints
     */
    AjUnitSetup enableSuppressedJoinPoints(SuppressedJoinPoint suppressedJoinPoints);
}
