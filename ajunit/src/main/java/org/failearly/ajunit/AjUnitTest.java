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
package org.failearly.ajunit;

import org.failearly.ajunit.builder.JoinPointSelector;

/**
 * AjUnitTest the INTERFACE any ajUnit test must provide.
 */
public interface AjUnitTest {

    /**
     * Setup the ajUnit test - The arrange/given part of ajUnit tests. Defines among other things the ajUnit Universe.
     *
     * @param ajUnitSetup the setup instance.
     */
    void setup(AjUnitSetup ajUnitSetup);

    /**
     * Within this method the test fixture classes should be called, so that the aspect could be applied - the act/when part of ajUnit tests.
     * <br/></br>
     * Important notes:<br/></br>
     * <ul>
     *     <li>Execute methods/constructors only on classes which has been added to the ajUnit universe.
     *              (see {@link org.failearly.ajunit.AjUnitSetup#addTestFixtureClass(Class)}</li>
     *     <li>Execute also parts of the test fixture classes which should not be selected by your Aspect's pointcut.</li>
     * </ul>
     */
    void execute();

    /**
     * Assert the pointcut definition - the assert/then part of ajUnit tests. <b>This is the most important function of ajUnit.</b>
     * <br/></br>
     * The {@code joinPointSelector} is responsible to build the <i>join point selector</i>. A join point selector
     * is the counterpart of an Aspect's pointcut. It separates the ajUnit Universe in two halves:<br/></br>
     * <ul>
     *     <li>The join points which should be applied (or touched) by your specified Aspect.</li>
     *     <li>And the join points which shouldn't be touched at all.</li>
     * </ul>
     *
     * @param joinPointSelector the join point selector builder.
     */
    void assertPointcut(JoinPointSelector joinPointSelector);

}