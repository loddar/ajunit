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
import org.failearly.ajunit.internal.runner.AjUnitTestRunner;
import org.failearly.ajunit.internal.runner.FailureHandler;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;

/**
 * AjUnitTestBase is the base implementation for {@link org.failearly.ajunit.AjUnitTest}.
 */
public abstract class AjUnitTestBase implements AjUnitTest, FailureHandler {

    private final AjUnitTestRunner testRunner = AjUnitTestRunner.createTestRunner(this, this);

    @Override
    public void setup(AjUnitSetup ajUnitSetup) {
        AjAssert.throwSetupError(MessageBuilders.setupError("Missing setup.")
                .line("Please override setup(AjUnitSetup)."));
    }

    @Override
    public void execute() {
        AjAssert.throwSetupError(MessageBuilders.setupError("Missing execute().")
                .line("Please override execute()."));
    }

    @Override
    public void assertPointcut(JoinPointSelector joinPointSelector) {
        AjAssert.throwSetupError(MessageBuilders.setupError("Missing assertPointcut.")
                .line("Please override assertPointcut(JoinPointSelector)"));

    }

    protected final void doPointcutTest() {
        testRunner.doPointcutTest();
    }

    @Override
    public void doFail(String message) {
        throw new AssertionError(message);
    }
}
