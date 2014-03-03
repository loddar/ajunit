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

import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for {@link TestResultEvaluator}.
 */
public class TestResultCollectorImplTest extends TestResultEvaluatorBaseTest<TestResultCollectorImpl> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestResultCollectorImplTest.class);

    public TestResultCollectorImplTest() {
        super(new TestResultCollectorImpl());
    }

    @Test
    public void noFailure() throws Exception {
        // arrange / given
        final Predicate joinPointSelector = methodByName("selectedMethod");
        doPrepareTest(joinPointSelector, joinPointSelector, false);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertNoFailure();
    }

    @Test
    public void failureMessageNoJoinPointAndMissingNoneSelectedJoinPoints() throws Exception {
        // act / when
        doEvaluateTestResult();

        // assert / then
        assertFailingMessage(
                "ajUnit - Pointcut test failed with 2 error(s)." +
                        "\n\nDetails:" +
                        "\n\n" +
                        "- No join points selected. Possible reasons:" +
                        "\n\t* Method assertPointcut(JoinPointSelector) uses notYetSpecified()." +
                        "\n\t* You defined a join point selector which has a impossible condition." +
                        "\n\n" +
                        "- Missing none selected join points. Possible reason:" +
                        "\n\t* Your ajUnit universe is too small. For example: Add a new method which should not selected by your pointcut definition."
        );
    }

    @Test
    public void failureMessageSelectedButNotApplied() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("selectedMethod", "otherMethod"), NO_ASPECT_SIMULATION, false);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertFailingMessage(
                "ajUnit - Pointcut test failed with 2 error(s)." +
                        "\n\nDetails:" +
                        "\n\n" +
                        "- 2 selected join point(s) has/have not(!) been applied. The join point(s):" +
                        "\n\t* method-execution(public void org.failearly.ajunit.internal.runner.TestResultEvaluatorBaseTest$AnyClass.otherMethod())#a=0" +
                        "\n\t* method-execution(public void org.failearly.ajunit.internal.runner.TestResultEvaluatorBaseTest$AnyClass.selectedMethod())#a=0" +
                        "\n- Possible reasons for this error:" +
                        "\n\t* You did not call/execute the listed join point(s). Adapt execute() accordingly." +
                        "\n\t* Your pointcut definition does not select the listed join point(s). Your pointcut definition is too strong." +
                        "\n\t* And of course it's possible that your join point selector isn't correct."
        );
    }

    @Test
    public void failureMessageNoneSelectedButApplied() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("selectedMethod"), methodByName("selectedMethod", "otherMethod"), NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertFailingMessage(
                "ajUnit - Pointcut test failed with 1 error(s)." +
                        "\n\nDetails:" +
                        "\n\n" +
                        "- 1 none(!) selected join point(s) has/have been applied. The join point(s):" +
                        "\n\t* method-execution(public void org.failearly.ajunit.internal.runner.TestResultEvaluatorBaseTest$AnyClass.otherMethod())#a=1" +
                        "\n- Possible reasons for this error:" +
                        "\n\t* Your pointcut definition should not select the listed join point(s). Your pointcut definition is too weak." +
                        "\n\t* And of course it's possible that your join point selector isn't correct."
        );
    }

    @Test
    public void failureMessageSuppressedButApplied() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("selectedMethod"), methodByName("selectedMethod", "toString"), NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertFailingMessage(
                "ajUnit - Pointcut test failed with 1 error(s)." +
                        "\n\nDetails:" +
                        "\n\n" +
                        "- 1 suppressed join point(s) has/have been applied. The join point(s):" +
                        "\n\t* method-execution(public java.lang.String java.lang.Object.toString())#a=1" +
                        "\n- Possible reasons for this error:" +
                        "\n\t* Your pointcut definition should not select the suppressed join point(s). Your pointcut definition is too weak." +
                        "\n\t* The suppressed join point(s) should be part of selectable join points. " +
                                "Enable suppressed join point(s) by AjUnitSetup.enableSuppressedJoinPoints()"
        );
    }

    private void assertFailingMessage(final String expectedMessage) {
        final FailureHandler failureHandler = Mockito.spy(new FailureHandler() {
            @Override
            public void doFail(String message) {
                LOGGER.info("\n"+message);
            }
        });
        testResultCollector.onFailure(failureHandler);
        Mockito.verify(failureHandler).doFail(expectedMessage);
        Mockito.verifyNoMoreInteractions(failureHandler);
    }

    private void assertNoFailure() {
        final FailureHandler failureHandler = Mockito.mock(FailureHandler.class);
        testResultCollector.onFailure(failureHandler);
        Mockito.verifyNoMoreInteractions(failureHandler);
    }

}
