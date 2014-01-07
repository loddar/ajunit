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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit;

import org.failearly.ajunit.builder.JoinPointSelectorBuilder;

/**
 * AjUnitTest the base class for all ajUnit test classes.
 */
public abstract class AjUnitTest extends AjUnitBase {

    protected AjUnitTest(Class<?>... testFixtureClasses) {
        super(testFixtureClasses);
    }

    protected AjUnitTest(String... classNames) throws ClassNotFoundException {
        super(classNames);
    }

    public abstract void setup();


    protected final void executePointcutTest() {
        // arrange / given
        final JoinPointSelectorBuilder joinPointBuilder=null /* TODO: Implementation */;
        setupJoinPointSelectorBuilder(joinPointBuilder);

        // act / when
        executeTestFixture();

        // assert / then

    }

    /**
     * Setup the pointcut predicate.
     * @param joinPointBuilder
     */
    protected abstract void setupJoinPointSelectorBuilder(JoinPointSelectorBuilder joinPointBuilder);


    /**
     * Execute the test fixture classes, to apply the
     */
    protected abstract void executeTestFixture();
}
