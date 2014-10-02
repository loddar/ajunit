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

import org.failearly.ajunit.AjUnitTest;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.ClassUtils;
import org.failearly.ajunit.internal.util.MessageBuilder;
import org.failearly.ajunit.internal.util.MessageBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * AjUnitTestRunner the internal test runner.
 *
 * @see org.failearly.ajunit.AjUnitTest
 * @see org.failearly.ajunit.AjUnitTestBase
 */
public final class AjUnitTestRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AjUnitTestRunner.class);

    private final AjUnitTest ajUnitTest;
    private final FailureHandler failureHandler;
    private final List<Predicate> enabledJoinPoints = new LinkedList<>();
    private AjUniverse universe;
    private int setupCounter = 0;

    private AjUnitTestRunner(AjUnitTest ajUnitTest, FailureHandler failureHandler) {
        this.ajUnitTest = ajUnitTest;
        this.failureHandler = failureHandler;
    }

    /**
     * The factory method.
     *
     * @return an AjUnitTestRunner instance.
     */
    public static AjUnitTestRunner createTestRunner(AjUnitTest ajUnitTest, FailureHandler failureHandler) {
        return new AjUnitTestRunner(ajUnitTest, failureHandler);
    }

    public void setup() {
        if (setupCounter == 0) {
            universe = doSetup(enabledJoinPoints);
            applyTestAspect();
        }
        setupCounter++;
    }

    public void tearDown() {
        setupCounter--;
        if (setupCounter == 0) {
            dropUniverse();
        }
    }

    private void dropUniverse() {
        if (this.universe != null) {
            AjUniversesHolder.dropUniverse(universe.getUniverseName());
            this.universe = null;
        }
    }

    /**
     * Executes the entire pointcut test.
     */
    public final void pointcutTest() {
        // arrange / given
        final Set<AjJoinPointType> joinPointTypes = new HashSet<>();
        final Predicate joinPointSelector = buildJoinPointSelector(joinPointTypes);
        LOGGER.info("Created join point selector: \n{}", joinPointSelector);

        // assert / then
        assertPointcutDefinition(joinPointTypes, joinPointSelector);
    }

    public final void aspectAssociationTest() {
        assertAspectAssociationType();
    }

    private void assertAspectAssociationType() {
        final int numberOfExpectedAspects = ajUnitTest.expectedNumberOfAspectInstances();
        final int currentNumberOfAspects = universe.getNumberOfAspectInstances();
        if (currentNumberOfAspects != numberOfExpectedAspects) {
            failureHandler.doFail(
                    createMessageForFailedAspectAssociation(universe, numberOfExpectedAspects, currentNumberOfAspects)
            );
        }
    }

    private String createMessageForFailedAspectAssociation(AjUniverse universe, int expectedNumberOfAspects, int currentNumberOfAspects) {
        return MessageBuilders.message("Aspect association type test failed:")
                .newline("#Expected instance(s) is/are").arg(expectedNumberOfAspects).part("but was").arg(currentNumberOfAspects)
                .newline().newline("Possible reasons:")
                .line("Your aspect").arg(universe.getAspectName())
                .part("uses other aspect association then singleton. For example per object.")
                .part("Please override expectedNumberOfAspectInstances() accordingly.")
                .line("The returned value (=" + expectedNumberOfAspects + ") of expectedNumberOfAspectInstances() is wrong.")
                .line("You change execute() without adapting expectedNumberOfAspectInstances() accordingly.")
                .build();
    }

    private void applyTestAspect() {
        LOGGER.info("Executing the test classes");
        try {
            this.ajUnitTest.execute();
        } catch (Exception ex) {
            LOGGER.warn("Caught exception from " + this.ajUnitTest.getClass() + ".execute()", ex);
        }
    }

    private void assertPointcutDefinition(
            final Set<AjJoinPointType> joinPointTypes,
            final Predicate joinPointSelector) {
        final ApplyJoinPointSelector joinPointVisitor = new ApplyJoinPointSelector(enabledJoinPoints, joinPointSelector);
        universe.visitJoinPoints(joinPointVisitor);
        final TestResultCollectorImpl testResultCollector = new TestResultCollectorImpl();
        final TestResultEvaluator testResultEvaluator = new TestResultEvaluator();
        testResultEvaluator.init(joinPointVisitor, joinPointTypes).evaluateTestResult(testResultCollector);
        testResultCollector.onFailure(this.failureHandler);
    }

    private void checkForAssociatedAspect(AjUnitSetupImpl ajUnitTestSetup) {
        final String aspectClassName = ajUnitTestSetup.getAspectName();
        doAssertAspectClassName(aspectClassName);
        ClassUtils.loadClass(aspectClassName, false);
    }

    private void doAssertAspectClassName(String aspectClassName) {
        if (aspectClassName == null) {
            throwSetupError(MessageBuilders.setupError("Missing aspect or not yet assigned.")
                    .line("Create an aspect and ...")
                    .line("assign it by calling AjUnitSetup.assignAspect(\"full.path.MyAspect\") in setup(AjUnitSetup)."));
        }
    }

    private AjUniverse doSetup(List<Predicate> enabledJoinPoints) {
        final AjUnitSetupImpl ajUnitTestSetup = new AjUnitSetupImpl();
        ajUnitTest.setup(ajUnitTestSetup);
        enabledJoinPoints.addAll(ajUnitTestSetup.getEnabledJoinPoints());

        checkForAssociatedAspect(ajUnitTestSetup);
        checkUniverseSetup(ajUnitTestSetup);

        return AjUniversesHolder.buildUniverseByClasses(ajUnitTestSetup.getAspectName(), ajUnitTestSetup.getTestFixtureClasses());
    }

    private void checkUniverseSetup(AjUnitSetupImpl ajUnitTestSetup) {
        final List<Class<?>> testFixtureClasses = ajUnitTestSetup.getTestFixtureClasses();
        if (testFixtureClasses.isEmpty()) {
            throwSetupError(MessageBuilders.setupError("Missing test fixture class(es).")
                            .line("Apply addTestFixtureClass(<class> or <class name>) for every test fixture class.")
            );
        }
    }

    private void throwSetupError(MessageBuilder messageBuilder) {
        AjAssert.throwSetupError(messageBuilder);
    }

    private Predicate buildJoinPointSelector(final Set<AjJoinPointType> joinPointTypes) {
        final JoinPointSelectorImpl joinPointBuilder = new JoinPointSelectorImpl(joinPointTypes);
        ajUnitTest.assertPointcut(joinPointBuilder);
        if (!joinPointBuilder.anyPredicateDefined()) {
            throwSetupError(MessageBuilders.setupError("Missing implementation of assertPointcut(JoinPointSelector)."));
        }
        return joinPointBuilder.build();
    }

}
