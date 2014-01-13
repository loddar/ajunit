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

import org.failearly.ajunit.AjUniverseSetup;
import org.failearly.ajunit.internal.util.MessageBuilderUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * AjUnitSetupTest is responsible for ...
 */
public class AjUnitSetupTest {

    @Test
    public void missingAjUniverseNameOnTestClass() throws Exception {
        assertAssertError(new AjUnitTestWithoutUniverseName(),
                "ajUnit - Missing annotation @AjUniverseName for class/aspect 'AjUnitTestWithoutUniverseName'");
    }

    @Test
    public void missingAssociatedAspect() throws Exception {
        assertAssertError(new AnyAjUnitTest(),
                "ajUnit - No associated aspect defined.\n" +
                        "- Create an aspect and ...\n" +
                        "- override getAssociatedAspect() providing the full qualified class name of the aspect."
        );
    }

    @Test
    public void wrongClassNameOfAspect() throws Exception {
        assertException(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "MissingBaseAspectAspect";
            }
        },
                IllegalArgumentException.class,
                "ajUnit - Class 'MissingBaseAspectAspect' could not be found.\n" +
                        "- The class name must be full qualified."
        );
    }

    @Test
    public void aspectDoesNotExtendBaseAspect() throws Exception {
        assertAssertError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.MissingBaseAspectAspect";
            }
        },
                "ajUnit - Test aspect 'any.company.aspect.MissingBaseAspectAspect' does not extend AjUnitAspectBase!\n" +
                        "- Please extend your aspect from one of the provided base aspects:\n" +
                        "\t* AjUnitAspect or AjUnitClassicAspect\n" +
                        "\t* AjUnitBeforeAspect or AjUnitBeforeClassicAspect\n" +
                        "\t* AjUnitAfterAspect or AjUnitAfterClassicAspect\n" +
                        "\t* AjUnitAroundAspect or AjUnitAroundClassicAspect"

        );
    }

    @Test
    public void aspectHasWrongUniverseName() throws Exception {
        assertAssertError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.WrongUniverseAspect";
            }
        },
                "ajUnit - Aspect 'any.company.aspect.WrongUniverseAspect' has wrong universe name: 'otherUniverseName'.\n" +
                "- Please use universe name: 'testUniverse'."
        );
    }

    @Test
    public void missingInitializeTestImplementation() throws Exception {
        assertAssertError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.CorrectAjUnitClassicAspect";
            }
        },
                "ajUnit - Missing implementation of initializeTest(AjUniverseSetup)!"
        );
    }

    @Test
    public void missingTestFixtureClasses() throws Exception {
        assertAssertError(new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.CorrectAjUnitClassicAspect";
            }

            @Override
            protected void initializeTest(AjUniverseSetup ajUniverseSetup) {
            }
        },
                "ajUnit - Missing test fixture class(es).\n" +
                        "- Apply addTestFixtureClass(<class> or <class name>) for every test fixture class."
        );
    }

    @Test
    public void overrideInitializeTestCorrectImplementation() throws Exception {
        // assignAspect("any.company.aspect.CorrectAjUnitClassicAspect")
        new AnyAjUnitTest() {
            @Override
            protected String getAssociatedAspect() {
                return "any.company.aspect.CorrectAjUnitClassicAspect";
            }

            @Override
            protected void initializeTest(AjUniverseSetup ajUniverseSetup) {
                ajUniverseSetup.addTestFixtureClass(MyTestFixture.class);
            }

            @Override
            protected void executeTestFixtures() {
                new MyTestFixture().anyMethod();
            }
        }.testPointcut();
    }


    private static void assertException(AbstractAjUnitTest testClass, Class<? extends Throwable> expectedException, String expectedMessage) {
        try {
            testClass.testPointcut();
        } catch (Throwable ex) {
            assertThat("Expected exception type", ex, instanceOf(expectedException));
            assertThat("Excepted message?", ex.getMessage(), is(expectedMessage));
            return;
        }

        doFail(expectedException);
    }

    private static void assertAssertError(AbstractAjUnitTest testClass, String expectedMessage) {
        assertException(testClass, AssertionError.class, expectedMessage);
    }

    private static void doFail(Class<? extends Throwable> expectedException) {
        fail(MessageBuilderUtils.assertMessage("Exception expected:").arg(expectedException.getCanonicalName()).build());
    }

}
