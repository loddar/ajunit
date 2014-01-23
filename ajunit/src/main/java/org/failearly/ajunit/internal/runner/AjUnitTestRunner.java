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

import org.failearly.ajunit.AjUnitAspectBase;
import org.failearly.ajunit.AjUnitTest;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderImpl;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * AjUnitTestRunner the internal used test runner.
 *
 * @see org.failearly.ajunit.AjUnitTest
 * @see org.failearly.ajunit.AjUnitTestBase
 */
public class AjUnitTestRunner {

    private final AjUnitTest ajUnitTest;
    private final Fail fail;

    public AjUnitTestRunner(AjUnitTest ajUnitTest, Fail fail) {
        this.ajUnitTest = ajUnitTest;
        this.fail = fail;
    }

    /**
     * Executes the entire pointcut test.
     */
    public final void doPointcutTest() {
        String universeName = null;
        try {
            // arrange / given
            universeName = resolveUniverseName();
            final AjUniverse universe = setupUniverse(universeName);
            final Set<AjJoinPointType> joinPointTypes = new HashSet<>();
            final Predicate joinPointSelector = buildJoinPointSelector(joinPointTypes);

            // act / when
            doExecute();

            // assert / then
            assertPointcutDefinition(universe, joinPointTypes, joinPointSelector);
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

    private void assertPointcutDefinition(final AjUniverse universe, final Set<AjJoinPointType> joinPointTypes, final Predicate joinPointSelector) {
        final List<AjJoinPoint> matchingJoinPoints = new LinkedList<>();
        universe.visitJoinPoints(new AjJoinPointVisitor() {
            @Override
            public void visit(AjJoinPoint joinPoint) {
                if (joinPointSelector.evaluate(joinPoint)) {
                    matchingJoinPoints.add(joinPoint);
                }
            }
        });

        assertMatchingJoinPoints(matchingJoinPoints, joinPointTypes);
    }

    private void assertMatchingJoinPoints(List<AjJoinPoint> matchingJoinPoints, Set<AjJoinPointType> joinPointTypes) {
        if (matchingJoinPoints.isEmpty()) {
            doFail(MessageUtils.message("No join points matching. Possible reasons:")
                    .line("The pointcut definition does mot match.")
                    .line("Method assertPointcut(JoinPointSelectorBuilder) uses notYetSpecified().")
                    .line("Method execute() is empty or the implementation is not proper.")
            );
        } else {
            final List<AjJoinPoint> appliedJoinPoints = resolveAppliedJoinPoints(matchingJoinPoints, joinPointTypes);
            if (appliedJoinPoints.size() < matchingJoinPoints.size()) {
                doFail(incompleteMessage(matchingJoinPoints, appliedJoinPoints));
            }
        }
    }

    @SuppressWarnings("unused")
    private MessageBuilder incompleteMessage(List<AjJoinPoint> matchingJoinPoints, List<AjJoinPoint> appliedJoinPoints) {
        return MessageUtils.message("The pointcut definition is incomplete.");
    }

    @SuppressWarnings("unused")
    private List<AjJoinPoint> resolveAppliedJoinPoints(List<AjJoinPoint> joinPointList, Set<AjJoinPointType> joinPointTypes) {
        final List<AjJoinPoint> appliedJoinPoints = new LinkedList<>();
        for (AjJoinPoint joinPoint : joinPointList) {
            if (joinPoint.getNumApplications() > 0) {
                appliedJoinPoints.add(joinPoint);
            }
        }
        AjAssert.assertCondition(appliedJoinPoints.size() <= joinPointList.size(), MessageUtils.message("#applied > #matching join points."));
        return appliedJoinPoints;
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

    private AjUniverse setupUniverse(String universeName) {
        final AjUnitSetupImpl ajUnitTestSetup = new AjUnitSetupImpl();
        ajUnitTest.setup(ajUnitTestSetup);

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

    private void doFail(MessageBuilder message) {
        doFail(message.build());
    }

    private void doFail(String message) {
        fail.doFail(message);
    }

    private Predicate buildJoinPointSelector(final Set<AjJoinPointType> joinPointTypes) {
        final JoinPointSelectorBuilderImpl joinPointBuilder = new JoinPointSelectorBuilderImpl(joinPointTypes);
        ajUnitTest.assertPointcut(joinPointBuilder);
        if (!joinPointBuilder.anyPredicateDefined()) {
            throwSetupError(MessageUtils.setupError("Missing implementation of assertPointcut(JoinPointSelectorBuilder)."));
        }
        return joinPointBuilder.build();
    }
}