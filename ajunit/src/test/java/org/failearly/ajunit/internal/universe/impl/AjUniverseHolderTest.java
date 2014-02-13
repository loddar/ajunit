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

import org.failearly.ajunit.internal.universe.AjUniverse;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * AjUniverseHolderTest contains tests for {@link org.failearly.ajunit.internal.universe.impl.AjUniversesHolder}.
 */
public class AjUniverseHolderTest {
    @Before
    public void dropAllUniverses() {
        AjUniversesHolder.dropUniverses();
    }

    @Test
    public void createAjUniverseByClasses() throws Exception {
        // arrange / given
        final String universeName = "universe-1";

        // act / when
        final AjUniverse universe = AjUniversesHolder.createUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // assert / then
        assertThat("Universe created?", universe, notNullValue());
        assertThat("Universe initialized?", universe.isInitialized(), is(true));
        assertThat("Universe name?", universe.getUniverseName(), is(universeName));
    }

    @Test
    public void createAjUniverseByClassNames() throws Exception {
        // arrange / given
        final String universeName = "universe-2";

        // act / when
        final AjUniverse universe = AjUniversesHolder.createUniverseByClassNames(
                universeName,
                Arrays.asList("org.failearly.ajunit.internal.universe.impl.AjUniverseHolderTest"));

        // assert / then
        assertThat("Universe created?", universe, notNullValue());
        assertThat("Universe initialized?", universe.isInitialized(), is(true));
        assertThat("Universe name?", universe.getUniverseName(), is(universeName));
    }

    @Test
    public void createTwiceReturnsSameInstance() throws Exception {
        // arrange / given
        final String universeName = "universe-3";
        final AjUniverse initialUniverse = AjUniversesHolder.createUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // act / when
        final AjUniverse universe = AjUniversesHolder.createUniverseByClasses(universeName, toClassList(Object.class));

        // assert / then
        assertThat("Universe same instances?", universe, sameInstance(initialUniverse));
    }

    @Test
    public void findKnownUniverse() throws Exception {
        // arrange / given
        final String universeName = "universe-4";
        final AjUniverse initialUniverse = AjUniversesHolder.createUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // act / when
        final AjUniverse universe = AjUniversesHolder.findUniverse(universeName);

        // assert / then
        assertThat("Universe same instances?", universe, sameInstance(initialUniverse));
    }

    @Test
    public void findUnknownUniverse() throws Exception {
        // arrange / given
        final String universeName = "universe-5";
        AjUniversesHolder.createUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // act / when
        try {
            AjUniversesHolder.findUniverse("any unknown instance name");

            // assert / then
            fail("java.lang.IllegalStateException expected");
        } catch (IllegalStateException e) {
            assertThat("Message", e.getMessage(), is("ajUnit - Illegal state: Could not find universe by name 'any unknown instance name'"));
        }
    }

    @Test
    public void dropUniverse() throws Exception {
        // arrange / given
        final String universeName = "universe-6";
        AjUniversesHolder.createUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // act / when
        AjUniversesHolder.dropUniverse(universeName);

        // assert / then
        try {
            AjUniversesHolder.findUniverse(universeName);
            fail("java.lang.IllegalStateException expected");
        } catch (IllegalStateException e) {
            assertThat("Message", e.getMessage(), is("ajUnit - Illegal state: Could not find universe by name 'universe-6'"));
        }
    }


    private List<Class<?>> toClassList(Class<?>... classes) {
        return Arrays.asList(classes);
    }
}
