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

import org.failearly.ajunit.AjUnitAspectBase;
import org.failearly.ajunit.AjUnitTest;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderImpl;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.*;

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

    private final AjUnitTest ajUnitTest;
    private final FailureHandler failureHandler;

    private AjUnitTestRunner(AjUnitTest ajUnitTest, FailureHandler failureHandler) {
        this.ajUnitTest = ajUnitTest;
        this.failureHandler = failureHandler;
    }

    /**
     * The factory method.
     * @return an AjUnitTestRunner instance.
     */
    public static AjUnitTestRunner createTestRunner(AjUnitTest ajUnitTest, FailureHandler failureHandler) {
        return new AjUnitTestRunner(ajUnitTest, failureHandler);
    }

    /**
     * Executes the entire pointcut test.
     */
    public final void doPointcutTest() {
        String universeName = null;
        try {
            // arrange / given
            universeName = resolveUniverseName();
            final List<Predicate> enabledJoinPoints=new LinkedList<>();
            final AjUniverse universe = setupAjUnitTest(universeName, enabledJoinPoints);
            final Set<AjJoinPointType> joinPointTypes = new HashSet<>();
            final Predicate joinPointSelector = buildJoinPointSelector(joinPointTypes);

            // act / when
            doExecute();

            // assert / then
            assertPointcutDefinition(universe, joinPointTypes, joinPointSelector, enabledJoinPoints);
        } finally {
            // Cleanup
            if (universeName != null) {
                AjUniversesHolder.dropUniverse(universeName);
            }
        }
    }

    private void doExecute() {
        this.ajUnitTest.execute();
    }

    private void assertPointcutDefinition(
                        final AjUniverse universe,
                        final Set<AjJoinPointType> joinPointTypes,
                        final Predicate joinPointSelector,
                        final List<Predicate> enabledJoinPoints)
    {
        final ApplyJoinPointSelector joinPointVisitor = new ApplyJoinPointSelector(enabledJoinPoints, joinPointSelector);
        universe.visitJoinPoints(joinPointVisitor);
        final TestResultCollectorImpl testResultCollector=new TestResultCollectorImpl();
        final TestResultEvaluator testResultEvaluator=new TestResultEvaluator();
        testResultEvaluator.init(joinPointVisitor, joinPointTypes)
                           .evaluateTestResult(testResultCollector);
        testResultCollector.onFailure(this.failureHandler);
    }

    private void assertAssociatedAspect(String universeName, AjUnitSetupImpl ajUnitTestSetup) {
        final String aspectClassName = ajUnitTestSetup.getAspectName();
        doAssertAspectClassName(aspectClassName);
        final Class<?> aspectClass = ClassUtils.loadClass(aspectClassName, false);
        doAssertAspectExtendsBaseAspectClass(aspectClass);
        doAssertAspectsUniverse(aspectClass, universeName);
    }

    private void doAssertAspectClassName(String aspectClassName) {
        if( aspectClassName==null ) {
            throwSetupError(MessageUtils.setupError("Missing aspect or not yet assigned.")
                    .line("Create an aspect and ...")
                    .line("assign it by calling AjUnitSetup.assignAspect(\"full.path.MyAspect\") in setup(AjUnitSetup)."));
        }
    }

    private void doAssertAspectsUniverse(Class<?> aspectClass, String universeName) {
        final String aspectsUniverseName = AjUnitUtils.resolveUniverseName(aspectClass);
        if (!universeName.equals(aspectsUniverseName)) {
            throwSetupError(
                    MessageUtils.setupError("Aspect").arg(aspectClass.getCanonicalName()).part("has wrong universe name:").arg(aspectsUniverseName).fullStop()
                            .line("Please use universe name:").arg(universeName).fullStop()
            );
        }
    }

    private void doAssertAspectExtendsBaseAspectClass(Class<?> aspectClass) {
        if (!AjUnitAspectBase.class.isAssignableFrom(aspectClass)) {
            throwSetupError(MessageUtils.setupError("Test aspect")
                    .arg(aspectClass.getCanonicalName())
                    .part("does not extend AjUnitAspectBase!")
                    .line("Please extend your aspect from one of the provided base aspects:")
                    .subLine("AjUnitAspect or AjUnitClassicAspect")
                    .subLine("AjUnitBeforeAspect or AjUnitBeforeClassicAspect")
                    .subLine("AjUnitAfterAspect or AjUnitAfterClassicAspect")
                    .subLine("AjUnitAroundAspect or AjUnitAroundClassicAspect")
            );
        }
    }

    private AjUniverse setupAjUnitTest(String universeName, List<Predicate> enabledJoinPoints) {
        final AjUnitSetupImpl ajUnitTestSetup = new AjUnitSetupImpl();
        ajUnitTest.setup(ajUnitTestSetup);
        enabledJoinPoints.addAll(ajUnitTestSetup.getEnabledJoinPoints());

        assertAssociatedAspect(universeName, ajUnitTestSetup);
        assertUniverseSetup(ajUnitTestSetup);

        return AjUniversesHolder.createUniverseByClasses(universeName, ajUnitTestSetup.getTestFixtureClasses());
    }

    private String resolveUniverseName() {
        return AjUnitUtils.resolveUniverseName(this.ajUnitTest);
    }

    private void assertUniverseSetup(AjUnitSetupImpl ajUnitTestSetup) {
        final List<Class<?>> testFixtureClasses = ajUnitTestSetup.getTestFixtureClasses();
        if (testFixtureClasses.isEmpty()) {
            throwSetupError(MessageUtils.setupError("Missing test fixture class(es).")
                    .line("Apply addTestFixtureClass(<class> or <class name>) for every test fixture class.")
            );
        }
    }

    private void throwSetupError(MessageBuilder messageBuilder) {
        AjAssert.throwSetupError(messageBuilder);
    }

    private Predicate buildJoinPointSelector(final Set<AjJoinPointType> joinPointTypes) {
        final JoinPointSelectorBuilderImpl joinPointBuilder = new JoinPointSelectorBuilderImpl(joinPointTypes);
        ajUnitTest.assertPointcut(joinPointBuilder);
        if (!joinPointBuilder.anyPredicateDefined()) {
            throwSetupError(MessageUtils.setupError("Missing implementation of assertPointcut(JoinPointSelector)."));
        }
        return joinPointBuilder.build();
    }

}
