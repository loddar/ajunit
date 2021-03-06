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
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

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
        final AjUniverse universe = AjUniversesHolder.buildUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // assert / then
        assertThat("Universe created?", universe, notNullValue());
        assertThat("Universe initialized?", universe.isInitialized(), is(true));
        assertThat("Universe name?", universe.getUniverseName(), is(universeName));
    }

    @Test
    public void createTwiceReturnsSameInstance() throws Exception {
        // arrange / given
        final String universeName = "universe-3";
        final AjUniverse initialUniverse = AjUniversesHolder.buildUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // act / when
        final AjUniverse universe = AjUniversesHolder.buildUniverseByClasses(universeName, toClassList(Object.class));

        // assert / then
        assertThat("Universe same instances?", universe, sameInstance(initialUniverse));
    }

    @Test
    public void findKnownUniverse() throws Exception {
        // arrange / given
        final String universeName = "universe-4";
        final AjUniverse initialUniverse = AjUniversesHolder.buildUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // act / when
        final AjUniverse universe = AjUniversesHolder.findOrCreateUniverse(universeName);

        // assert / then
        assertThat("Universe same instances?", universe, sameInstance(initialUniverse));
    }

    @Test
    public void dropUniverse() throws Exception {
        // arrange / given
        final String universeName = "universe-6";
        AjUniversesHolder.buildUniverseByClasses(universeName, toClassList(AjUniverseHolderTest.class));

        // act / when
        AjUniversesHolder.dropUniverse(universeName);

        // assert / then
        assertThat("None existing universe?", AjUniversesHolder.findUniverse(universeName), nullValue());
    }


    private List<Class<?>> toClassList(Class<?>... classes) {
        return Arrays.asList(classes);
    }
}
