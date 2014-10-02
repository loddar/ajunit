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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;


/**
 * AjUnit4Test is the base class for JUnit4 based test of ajUnit tests.
 * <br>
 */
public abstract class AjUnit4Test extends AjUnitTestBase {

    protected AjUnit4Test() {
    }

    @Before
    public void setup() {
        super.doSetup();
    }

    @Test
    public final void pointcutTest() {
        super.doPointcutTest();
    }

    @Test
    public final void aspectAssociationTest() {
        super.doAspectAssociationTest();
    }

    @After
    public void dropUniverse() {
        super.doTearDown();
    }

    @Override
    public final void doFail(String message) {
        fail(message);
    }
}
