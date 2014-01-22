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
package any.company.aspect;

import org.failearly.ajunit.AjUnitSetup;
import org.failearly.ajunit.AjUnitSetupError;
import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.junit.Test;

/**
 * AjUnitSetupTest is responsible for ...
 */
public class AjUnitSetupTest extends AbstractAjUnitTestTest {

    @Test
    public void missingAjUniverseNameOnTestClass() throws Exception {
        assertAjUnitSetupError(new AjUnitTestWithoutUniverseName(),
                "ajUnit - Setup Error: Missing annotation @AjUniverseName for class/aspect 'AjUnitTestWithoutUniverseName'");
    }

    @Test
    public void missingAssociatedAspect() throws Exception {
        assertAjUnitSetupError(new AnyAjUnitTest(),
                "ajUnit - Setup Error: No associated aspect defined.\n" +
                        "- Create an aspect and ...\n" +
                        "- override getAssociatedAspect() providing the full qualified class name of the aspect."
        );
    }

    @Test
    public void wrongClassNameOfAspect() throws Exception {
        assertAjUnitSetupError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "MissingBaseAspectAspect";
            }
        },
                "ajUnit - Setup Error: Class 'MissingBaseAspectAspect' could not be found.\n" +
                        "- The class name must be full qualified."
        );
    }

    @Test
    public void aspectDoesNotExtendBaseAspect() throws Exception {
        assertAjUnitSetupError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.MissingBaseAspectAspect";
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
        assertAjUnitSetupError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.WrongUniverseAspect";
            }
        },
                "ajUnit - Setup Error: Aspect 'any.company.aspect.WrongUniverseAspect' has wrong universe name: 'otherUniverseName'.\n" +
                        "- Please use universe name: 'testUniverse'."
        );
    }

    @Test
    public void missingInitializeTestImplementation() throws Exception {
        assertAjUnitSetupError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.CorrectAjUnitClassicAspect";
            }
        },
                "ajUnit - Setup Error: Missing implementation of initializeTest(AjUnitSetup)!"
        );
    }

    @Test
    public void missingTestFixtureClasses() throws Exception {
        assertAjUnitSetupError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.CorrectAjUnitClassicAspect";
            }

            @Override
            protected void initializeTest(AjUnitSetup ajUnitSetup) {
            }
        },
                "ajUnit - Setup Error: Missing test fixture class(es).\n" +
                        "- Apply addTestFixtureClass(<class> or <class name>) for every test fixture class."
        );
    }

    @Test
    public void missingSetupJoinPointSelector() throws Exception {
       assertAjUnitSetupError(
               new AnyAjUnitTest() {
                   @Override
                   protected String getAssociatedAspect() {
                       return "any.company.aspect.CorrectAjUnitClassicAspect";
                   }

                   @Override
                   protected void initializeTest(AjUnitSetup ajUnitSetup) {
                       ajUnitSetup.addTestFixtureClass(MyTestFixture.class);
                   }

                   @Override
                   protected void executeTestFixtures() {
                       new MyTestFixture().anyMethod();
                   }
               },
               "ajUnit - Setup Error: Missing setupJoinPointSelector.\n" +
                       "- Please override setupJoinPointSelector(JoinPointSelectorBuilder)"
       );
    }

    @Test
    public void missingValidJoinPointSelector() throws Exception {
        assertAjUnitSetupError(
                new AnyAjUnitTest() {
                    @Override
                    protected String getAssociatedAspect() {
                        return "any.company.aspect.CorrectAjUnitClassicAspect";
                    }

                    @Override
                    protected void initializeTest(AjUnitSetup ajUnitSetup) {
                        ajUnitSetup.addTestFixtureClass(MyTestFixture.class);
                    }

                    @Override
                    protected void executeTestFixtures() {
                        new MyTestFixture().anyMethod();
                    }

                    @Override
                    protected void setupJoinPointSelector(JoinPointSelectorBuilder joinPointSelectorBuilder) {
                        // Missing implementation.
                    }
                },
                "ajUnit - Setup Error: Missing valid implementation of setupJoinPointSelector(JoinPointSelectorBuilder)."
        );
    }

    @Test
    public void methodExecution() throws Exception {
        assertAjUnitSetupError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.CorrectAjUnitClassicAspect";
            }

            @Override
            protected void initializeTest(AjUnitSetup ajUnitSetup) {
                ajUnitSetup.addTestFixtureClass(MyTestFixture.class);
            }

            @Override
            protected void executeTestFixtures() {
                new MyTestFixture().anyMethod();
            }

            @Override
            protected void setupJoinPointSelector(JoinPointSelectorBuilder joinPointSelectorBuilder) {
                joinPointSelectorBuilder.methodExecute();
            }
        },
                "ajUnit - Setup Error: Missing valid implementation of setupJoinPointSelector(JoinPointSelectorBuilder)."
        );
    }


    private static void assertAjUnitSetupError(AbstractAjUnitTest testClass, String expectedMessage) {
        assertException(testClass, AjUnitSetupError.class, expectedMessage);
    }

}
