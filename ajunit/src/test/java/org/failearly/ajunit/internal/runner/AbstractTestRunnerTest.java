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
package org.failearly.ajunit.internal.runner;

import org.failearly.ajunit.internal.util.MessageUtils;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * AjUnitTestRunnerTest is the base class for AjUnitTestRunner's tests.
 */
public abstract class AbstractTestRunnerTest {
    protected static void assertException(PointcutUnitTestBase testClass, Class<? extends Throwable> expectedException, String expectedMessage) {
        try {
            testClass.testPointcut();
        } catch (Throwable ex) {
            ex.printStackTrace();
            assertThat("Expected exception type", ex, instanceOf(expectedException));
            assertThat("Excepted message?", ex.getMessage(), is(expectedMessage));
            return;
        }

        doFail(expectedException);
    }

    private static void doFail(Class<? extends Throwable> expectedException) {
        fail(MessageUtils.setupError("Exception expected:").arg(expectedException.getCanonicalName()).build());
    }
}
