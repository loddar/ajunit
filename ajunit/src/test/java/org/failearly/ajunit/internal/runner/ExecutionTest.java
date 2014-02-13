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
package org.failearly.ajunit.internal.runner;

import any.company.aspect.MyTestFixture;
import any.company.aspect.PointcutUnitTest;
import org.failearly.ajunit.AjUnitSetup;
import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.junit.Test;

/**
 * ExecutionTest tests the {@link org.failearly.ajunit.internal.runner.AjUnitTestRunner} for the thrown Assertion Error Messages.
 */
public class ExecutionTest extends AbstractTestRunnerTest {
    @Test
    public void notYetSpecified() throws Exception {
        assertAssertionError(new PointcutUnitTest() {
            @Override
            public void setup(AjUnitSetup ajUnitSetup) {
                ajUnitSetup
                        .addTestFixtureClass(MyTestFixture.class.getName())
                        .assignAspect("any.company.aspect.CorrectAjUnitClassicAspect");
            }

            @Override
            public void execute() {
                new MyTestFixture().anyMethod();
            }

            @Override
            public void assertPointcut(JoinPointSelectorBuilder joinPointSelectorBuilder) {
                joinPointSelectorBuilder.notYetSpecified();
            }
        },
                "ajUnit - No join points matching. Possible reasons:" +
                        "\n- The pointcut definition does mot match." +
                        "\n- Method assertPointcut(JoinPointSelectorBuilder) uses notYetSpecified()." +
                        "\n- Method execute() is empty or the implementation is not proper."
        );
    }

    @Test
    public void anyMethodExecution() throws Exception {
        assertAssertionError(new PointcutUnitTest() {
            @Override
            public void setup(AjUnitSetup ajUnitSetup) {
                ajUnitSetup
                        .addTestFixtureClass(MyTestFixture.class.getName())
                        .assignAspect("any.company.aspect.CorrectAjUnitClassicAspect");
            }

            @Override
            public void execute() {
                new MyTestFixture().anyMethod();
            }

            @Override
            public void assertPointcut(JoinPointSelectorBuilder joinPointSelectorBuilder) {
                joinPointSelectorBuilder.methodExecute().anyMethod();
            }
        },
                "ajUnit - The pointcut definition is incomplete."
        );
    }

    private static void assertAssertionError(PointcutUnitTestBase testClass, String expectedMessage) {
        assertException(testClass, AssertionError.class, expectedMessage);
    }

}
