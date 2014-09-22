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
package org.failearly.ajunit.aspect.classic;

import org.failearly.ajunit.AjUnitAspectBase;

/**
 * AjUnitClassicAspect is THE base aspect for ajUnit aspects.
 */
public abstract aspect AjUnitClassicAspect extends AjUnitAspectBase {


    protected AjUnitClassicAspect() {
    }

    /**
     * The pointcut definition under test.
     */
    protected abstract pointcut pointcutUnderTest();

    /**
     * Selects all join points which will be executed below {@link org.failearly.ajunit.AjUnitTest#execute()}.
     *
     * @see org.failearly.ajunit.AjUnitTest#execute()
     */
    private pointcut cflowbelowExecutingAjUnitTestExecute() : cflowbelow(execution(void org.failearly.ajunit.AjUnitTest.execute()));

    /**
     * Exclude all join points within ajUnit.
     */
    private pointcut excludeAjUnit() : ! within(org.failearly.ajunit..*);

    /**
     * The actually pointcut definition to test.
     */
    protected pointcut pointcutDefinition() : pointcutUnderTest() && cflowbelowExecutingAjUnitTestExecute() && excludeAjUnit();

    /**
     * This pointcut definition does not select anything, has just a purpose as place holder.
     */
    protected pointcut notYetSpecified();

    /**
     * All method execution join points. May be a good starting point for develop your own point cut.
     */
    protected pointcut allMethodExecution() : execution(* *(..));


}
