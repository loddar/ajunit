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

import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.failearly.ajunit.internal.runner.AjUnitTestRunner;
import org.failearly.ajunit.internal.runner.Fail;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageUtils;

/**
 * AjUnitTestBase is responsible for ...
 */
public abstract class AjUnitTestBase implements AjUnitTest, Fail {

    private final AjUnitTestRunner testRunner=new AjUnitTestRunner(this, this);

    @Override
    public void setup(AjUnitSetup ajUnitSetup) {
        AjAssert.throwSetupError(MessageUtils.setupError("Missing setup.")
                        .line("Please override setup(AjUnitSetup)."));
    }

    @Override
    public void execute() {
        AjAssert.throwSetupError(MessageUtils.setupError("Missing execute().")
                .line("Please override execute()."));
    }

    @Override
    public void assertPointcut(JoinPointSelectorBuilder joinPointSelectorBuilder) {
        AjAssert.throwSetupError(MessageUtils.setupError("Missing assertPointcut.")
                .line("Please override assertPointcut(JoinPointSelectorBuilder)"));

    }

    protected final void doPointcutTest() {
        testRunner.doPointcutTest();
    }

    @Override
    public void doFail(String message) {
        throw new AssertionError(message);
    }
}
