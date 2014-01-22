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
package org.failearly.ajunit;

import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderImpl;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.*;

import java.util.*;


/**
 * AjUnitTest the base class for all ajUnit test testFixtureClasses.
 */
public abstract class AjUnitTest {

    /**
     * Internal use only. Only for actually test class implementation.
     */
    protected final void doPointcutTest() {
        String universeName = null;
        try {
            // arrange / given
            universeName = resolveUniverseName();
            assertAssociatedAspect(universeName);
            final AjUniverse universe = setupUniverse(universeName);
            final Set<AjJoinPointType> joinPointTypes = new HashSet<>();
            final Predicate joinPointSelector = createJoinPointSelector(joinPointTypes);

            // act / when
            executeTestFixtures();

            // assert / then
            assertPointcutDefinition(universe, joinPointTypes, joinPointSelector);
        } finally {
            // Cleanup
            if (universeName != null) {
                AjUniversesHolder.dropUniverse(universeName);
            }
        }

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
                    .line("The setupJoinPointSelector() uses notYetSpecified().")
                    .line("Method executeTestFixtures() does not provide a proper implementation.")
            );
        } else {
            final List<AjJoinPoint> appliedJoinPoints = resolveAppliedJoinPoints(matchingJoinPoints, joinPointTypes);
            if (appliedJoinPoints.size() < matchingJoinPoints.size()) {
                doFail(incompleteMessage(matchingJoinPoints, appliedJoinPoints));
            }
        }
    }

    private MessageBuilder incompleteMessage(List<AjJoinPoint> matchingJoinPoints, List<AjJoinPoint> appliedJoinPoints) {
        final MessageBuilder messageBuilder = MessageUtils.message("The pointcut definition is incomplete.");
        return messageBuilder;
    }

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

    private void assertAssociatedAspect(String universeName) {
        final String aspectClassName = getAssociatedAspect();
        final Class<?> aspectClass = ClassUtils.loadClass(aspectClassName, false);
        doAssertAspectExtendsBaseAspectClass(aspectClass);
        doAssertAspectsUniverse(aspectClass, universeName);
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

    /**
     * Provide the associated aspect as full qualified class name.
     * <br/></br>
     * The associated aspect should comply with following conditions:
     * <ul>
     * <li>The aspect must be inherit from {@code org.failearly.ajunit.AjUnitAspect} or one of
     * the provided sub aspects.</li>
     * <li>The aspect must be annotated with {@link org.failearly.ajunit.AjUniverseName}.</li>
     * </ul>
     *
     * @return the aspect's full qualified class name.
     */
    protected String getAssociatedAspect() {
        throwSetupError(MessageUtils.setupError("No associated aspect defined.")
                .line("Create an aspect and ...")
                .line("override getAssociatedAspect() providing the full qualified class name of the aspect."));
        return null;
    }

    private AjUniverse setupUniverse(String universeName) {
        final AjUnitSetupImpl ajUnitTestSetup = new AjUnitSetupImpl();
        initializeTest(ajUnitTestSetup);

        assertUniverseSetup(ajUnitTestSetup);

        return AjUniversesHolder.createUniverseByClasses(universeName, ajUnitTestSetup.testFixtureClasses);
    }

    private String resolveUniverseName() {
        return AjUnitUtils.resolveUniverseName(this);
    }

    private void assertUniverseSetup(AjUnitSetupImpl ajUnitTestSetup) {
        final List<Class<?>> testFixtureClasses = ajUnitTestSetup.testFixtureClasses;
        if (testFixtureClasses.isEmpty()) {
            throwSetupError(MessageUtils.setupError("Missing test fixture class(es).")
                    .line("Apply addTestFixtureClass(<class> or <class name>) for every test fixture class.")
            );
        }
    }

    /**
     * Adds the test fixture classes to the ajUnit universe.
     *
     * @param ajUnitSetup the universe setup instance.
     */
    protected void initializeTest(AjUnitSetup ajUnitSetup) {
        throwSetupError(MessageUtils.setupError("Missing implementation of initializeTest(AjUnitSetup)!"));
    }

    private void throwSetupError(MessageBuilder messageBuilder) {
        AjAssert.throwSetupError(messageBuilder);
    }

    private void doFail(MessageBuilder message) {
        doFail(message.build());
    }

    /**
     * Internal use only. Only for actually test class implementation.
     */
    protected abstract void doFail(String message);

    private Predicate createJoinPointSelector(final Set<AjJoinPointType> joinPointTypes) {
        final JoinPointSelectorBuilderImpl joinPointBuilder = new JoinPointSelectorBuilderImpl(joinPointTypes);
        setupJoinPointSelector(joinPointBuilder);
        if (!joinPointBuilder.anyPredicateDefined()) {
            throwSetupError(MessageUtils.setupError("Missing valid implementation of setupJoinPointSelector(JoinPointSelectorBuilder)."));
        }
        return joinPointBuilder.build();
    }

    /**
     * Setup the pointcut predicate.
     *
     * @param joinPointSelectorBuilder
     */
    protected void setupJoinPointSelector(JoinPointSelectorBuilder joinPointSelectorBuilder) {
        throwSetupError(MessageUtils.setupError("Missing setupJoinPointSelector.")
                .line("Please override setupJoinPointSelector(JoinPointSelectorBuilder)"));
    }


    /**
     * Within this method the test fixture classes should be called, so that the aspect could be applied.
     */
    protected abstract void executeTestFixtures();

    private static class AjUnitSetupImpl implements AjUnitSetup {
        private final List<Class<?>> testFixtureClasses = new ArrayList<>();

        private final CompositePredicate enabledJoinPoints = LogicalPredicates.or();

        public AjUnitSetupImpl() {
        }

        Predicate getEnabledJoinPoints() {
            return enabledJoinPoints;
        }

        List<Class<?>> getTestFixtureClasses() {
            return testFixtureClasses;
        }

        @Override
        public AjUnitSetup addTestFixtureClass(Class<?> clazz) {
            testFixtureClasses.add(clazz);
            return this;
        }

        @Override
        public AjUnitSetup addTestFixtureClass(String className) {
            testFixtureClasses.add(ClassUtils.loadClass(className, false));
            return this;
        }

        @Override
        public AjUnitSetup enableSuppressedJoinPoints(SuppressedJoinPoint suppressedJoinPoint) {
            enabledJoinPoints.addPredicate(suppressedJoinPoint.predicate());
            return this;
        }

    }
}
