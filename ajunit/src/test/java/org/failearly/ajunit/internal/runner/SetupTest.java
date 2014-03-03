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
package org.failearly.ajunit.internal.runner;

import any.company.aspect.MyTestFixture;
import any.company.aspect.PointcutTestWithoutUniverseName;
import any.company.aspect.PointcutUnitTest;
import org.failearly.ajunit.AjUnitSetup;
import org.failearly.ajunit.builder.JoinPointSelector;
import org.junit.Test;

/**
 * AjUnitSetupTest tests the setup errors.
 */
public class SetupTest extends AbstractTestRunnerTest {

    @Test
    public void missingAjUniverseNameOnTestClass() throws Exception {
        assertAjUnitSetupError(new PointcutTestWithoutUniverseName(),
                "ajUnit - Setup Error: Missing annotation @AjUniverseName for class/aspect 'PointcutTestWithoutUniverseName'");
    }

    @Test
    public void missingSetup() throws Exception {
        assertAjUnitSetupError(new PointcutUnitTest(),
                "ajUnit - Setup Error: Missing setup.\n" +
                        "- Please override setup(AjUnitSetup)."
        );
    }

    @Test
    public void noAssociatedAspect() throws Exception {
        assertAjUnitSetupError(new PointcutUnitTest() {
            @Override
            public void setup(AjUnitSetup ajUnitSetup) {
            }
        },
                "ajUnit - Setup Error: Missing aspect or not yet assigned.\n" +
                        "- Create an aspect and ...\n" +
                        "- assign it by calling AjUnitSetup.assignAspect(\"full.path.MyAspect\") in setup(AjUnitSetup)."
        );
    }

    @Test
    public void notFullQualifiedAspectName() throws Exception {
        assertAjUnitSetupError(new PointcutUnitTest() {
            @Override
            public void setup(AjUnitSetup ajUnitSetup) {
                ajUnitSetup.assignAspect("MissingBaseAspectAspect");
            }
        },
                "ajUnit - Setup Error: Class 'MissingBaseAspectAspect' could not be found.\n" +
                        "- The class name must be full qualified."
        );
    }

    @Test
    public void aspectDoesNotExtendBaseAspect() throws Exception {
        assertAjUnitSetupError(new PointcutUnitTest() {
            @Override
            public void setup(AjUnitSetup ajUnitSetup) {
                ajUnitSetup.assignAspect("any.company.aspect.MissingBaseAspectAspect");
            }
        },
                "ajUnit - Setup Error: Test aspect 'any.company.aspect.MissingBaseAspectAspect' does not extend AjUnitAspectBase!\n" +
                        "- Please extend your aspect from one of the provided base aspects:\n" +
                        "\t* AjUnitAspect or AjUnitClassicAspect\n" +
                        "\t* AjUnitBeforeAspect or AjUnitBeforeClassicAspect\n" +
                        "\t* AjUnitAfterAspect or AjUnitAfterClassicAspect\n" +
                        "\t* AjUnitAroundAspect or AjUnitAroundClassicAspect"

        );
    }

    @Test
    public void aspectHasWrongUniverseName() throws Exception {
        assertAjUnitSetupError(new PointcutUnitTest() {
            @Override
            public void setup(AjUnitSetup ajUnitSetup) {
                ajUnitSetup.assignAspect("any.company.aspect.WrongUniverseAspect");
            }
        },
                "ajUnit - Setup Error: Aspect 'any.company.aspect.WrongUniverseAspect' has wrong universe name: 'otherUniverseName'.\n" +
                        "- Please use universe name: 'testUniverse'."
        );
    }

    @Test
    public void missingTestFixtureClasses() throws Exception {
        assertAjUnitSetupError(new PointcutUnitTest() {
            @Override
            public void setup(AjUnitSetup ajUnitSetup) {
                ajUnitSetup.assignAspect("any.company.aspect.CorrectAjUnitClassicAspect");
            }
        },
                "ajUnit - Setup Error: Missing test fixture class(es).\n" +
                        "- Apply addTestFixtureClass(<class> or <class name>) for every test fixture class."
        );
    }

    @Test
    public void missingSetupJoinPointSelector() throws Exception {
       assertAjUnitSetupError(
               new PointcutUnitTest() {

                   @Override
                   public void setup(AjUnitSetup ajUnitSetup) {
                       ajUnitSetup
                          .addTestFixtureClass(MyTestFixture.class)
                          .assignAspect("any.company.aspect.CorrectAjUnitClassicAspect");
                   }

                   @Override
                   public void execute() {
                       new MyTestFixture().anyMethod();
                   }
               },
               "ajUnit - Setup Error: Missing assertPointcut.\n" +
                       "- Please override assertPointcut(JoinPointSelector)"
       );
    }

    @Test
    public void missingValidJoinPointSelector() throws Exception {
        assertAjUnitSetupError(
                new PointcutUnitTest() {
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
                    public void assertPointcut(JoinPointSelector joinPointSelector) {
                        // Missing implementation.
                    }
                },
                "ajUnit - Setup Error: Missing implementation of assertPointcut(JoinPointSelector)."
        );
    }

    private static void assertAjUnitSetupError(PointcutUnitTestBase testClass, String expectedMessage) {
        assertException(testClass, AjUnitSetupError.class, expectedMessage);
    }

}
