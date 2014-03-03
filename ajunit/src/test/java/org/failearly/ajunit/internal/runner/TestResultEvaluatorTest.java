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
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link org.failearly.ajunit.internal.runner.TestResultEvaluator}.
 */
public class TestResultEvaluatorTest extends TestResultEvaluatorBaseTest<TestResultCollector> {

    public TestResultEvaluatorTest() {
        super(Mockito.mock(TestResultCollector.class));
    }

    @Test
    public void noFailure() throws Exception {
        // arrange / given
        final Predicate joinPointSelector = methodByName("selectedMethod");
        doPrepareTest(joinPointSelector, joinPointSelector, NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertNoMoreInteractions();
    }

    @Test
    public void failureNoJoinPointsSelected() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("unknownMethod"), NO_ASPECT_SIMULATION, NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertCalledNoJoinPointsSelected();
        assertNoMoreInteractions();
    }

    @Test
    public void failureSelectedButNotApplied() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("selectedMethod"), NO_ASPECT_SIMULATION, NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertCalledSelectedButNotApplied("method-execution(public void org.failearly.ajunit.internal.runner.TestResultEvaluatorBaseTest$AnyClass.selectedMethod())#a=0");
        assertNoMoreInteractions();
    }

    @Test
    public void failureJoinPointTypeMismatch() throws Exception {
        // arrange / given
        doPrepareTest(
                methodByNameAndType(AjJoinPointType.METHOD_EXECUTION, "selectedMethod"),
                methodByNameAndType(AjJoinPointType.METHOD_CALL, "selectedMethod"),
                false);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertCalledSelectedButNotApplied("method-execution(public void org.failearly.ajunit.internal.runner.TestResultEvaluatorBaseTest$AnyClass.selectedMethod())#a=0");
        assertCalledNoneSelectedButApplied("method-call(public void org.failearly.ajunit.internal.runner.TestResultEvaluatorBaseTest$AnyClass.selectedMethod())#a=1");
        assertNoMoreInteractions();
    }

    @Test
    public void failureMissingNoneSelected() throws Exception {
        // arrange / given
        final Predicate joinPointSelector = methodByName("selectedMethod", "otherMethod", "neverCalledMethod");
        doPrepareTest(joinPointSelector, joinPointSelector, NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertCalledMissingNoneSelected();
        assertNoMoreInteractions();
    }

    @Test
    public void failureNoneSelectedButApplied() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("selectedMethod"), methodByName("selectedMethod", "otherMethod"), NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertCalledNoneSelectedButApplied("method-execution(public void org.failearly.ajunit.internal.runner.TestResultEvaluatorBaseTest$AnyClass.otherMethod())#a=1");
        assertNoMoreInteractions();
    }

    @Test
    public void failureSuppressedButApplied() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("selectedMethod"), methodByName("selectedMethod", "toString"), NO_ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertCalledSuppressedButApplied("method-execution(public java.lang.String java.lang.Object.toString())#a=1");
        assertNoMoreInteractions();
    }

    @Test
    public void noFailureWithSuppressedJoinPoints() throws Exception {
        // arrange / given
        final Predicate joinPointSelector = methodByName("toString");
        doPrepareTest(joinPointSelector, joinPointSelector, ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertNoMoreInteractions();
    }

    @Test
    public void failureEnabledJoinPointButNotApplied() throws Exception {
        // arrange / given
        doPrepareTest(methodByName("toString"), NO_ASPECT_SIMULATION, ENABLE_JOIN_POINT);

        // act / when
        doEvaluateTestResult();

        // assert / then
        assertCalledSelectedButNotApplied("method-execution(public java.lang.String java.lang.Object.toString())#a=0");
        assertNoMoreInteractions();
    }

    private void assertNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(testResultCollector);
    }

    private void assertCalledMissingNoneSelected() {
        Mockito.verify(testResultCollector).missingNoneSelected();
    }

    private void assertCalledSelectedButNotApplied(String joinPoint) {
        Mockito.verify(testResultCollector).selectedButNotApplied(joinPoint);
    }

    private void assertCalledNoneSelectedButApplied(String joinPoint) {
        Mockito.verify(testResultCollector).noneSelectedButApplied(joinPoint);
    }

    private void assertCalledSuppressedButApplied(String joinPoint) {
        Mockito.verify(testResultCollector).suppressedButApplied(joinPoint);
    }


    private void assertCalledNoJoinPointsSelected() {
        Mockito.verify(testResultCollector).noJoinPointsSelected();
    }
}
