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

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.member.MemberTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.junit.After;
import org.junit.Before;

import java.util.Arrays;

/**
 * TestResultEvaluatorBaseTest is responsible for ...
 */
public class TestResultEvaluatorBaseTest<T extends TestResultCollector> {
    public static final Predicate NO_ASPECT_SIMULATION = null;
    protected static final boolean NO_ENABLE_JOIN_POINT = false;
    protected static final boolean ENABLE_JOIN_POINT = true;
    private static final String UNIVERSE_NAME = "TestResultEvaluatorBaseTest$Universe";
    protected final TestResultEvaluator testResultEvaluator=new TestResultEvaluator();
    protected final T testResultCollector;
    private AjUniverse ajUniverse;

    public TestResultEvaluatorBaseTest(final T testResultCollector) {
        this.testResultCollector = testResultCollector;
    }

    protected static Predicate methodByName(String... names) {
        return methodByNameAndType(AjJoinPointType.METHOD_EXECUTION, names);
    }

    protected static Predicate methodByNameAndType(AjJoinPointType joinPointType,
                                                           String... names) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        AjpTransformers.ajpJoinPointFilterTransformer(joinPointType),
                        AjpTransformers.methodTransformer(),
                        MemberTransformers.nameTransformer()
                ),
                byAnyName(names)

        );
    }

    private static Predicate byAnyName(String... names) {
        final CompositePredicate anyOf = LogicalPredicates.or();
        for (String name : names) {
            anyOf.addPredicate(StandardPredicates.predicateEquals(name));
        }
        return anyOf;
    }

    @Before
    public final void setUpUniverse() throws Exception {
        ajUniverse = AjUniversesHolder.createUniverseByClasses(UNIVERSE_NAME, Arrays.<Class<?>>asList(AnyClass.class));
    }

    @After
    public final void tearDown() throws Exception {
        AjUniversesHolder.dropUniverse(UNIVERSE_NAME);
    }

    private ApplyJoinPointSelector evaluateUniverseJoinPoints(Predicate joinPointSelector, Predicate... enabledJoinPoints) {
        final ApplyJoinPointSelector joinPointVisitor = new ApplyJoinPointSelector(Arrays.asList(enabledJoinPoints), joinPointSelector);
        ajUniverse.visitJoinPoints(joinPointVisitor);
        return joinPointVisitor;
    }

    protected final void simulateAspectApplication(final Predicate predicate) {
        ajUniverse.visitJoinPoints(new AjJoinPointVisitor() {
            @Override
            public void visit(AjJoinPoint joinPoint) {
                if (predicate.evaluate(joinPoint)) {
                    joinPoint.apply();
                }
            }
        });
    }

    protected final void doEvaluateTestResult() {
        testResultEvaluator.evaluateTestResult(testResultCollector);
    }

    /**
     * Initialize the test evaluator.
     * @param joinPointSelector the join point selector
     * @param aspectSimulationPredicate {@code null} or the aspect simulation predicate which will
     *                                                call {@link org.failearly.ajunit.internal.universe.AjJoinPoint#apply()} on each selected join point.
     * @param enableJoinPoint {@code true} if the join points selected by {@code joinPointSelector} is part of the selected joint points and
     *                                    not part of the suppressed join points like {@link Object#toString()}.
     */
    protected final void doPrepareTest(Predicate joinPointSelector, Predicate aspectSimulationPredicate, boolean enableJoinPoint) {
        if( aspectSimulationPredicate!=null ) {
            simulateAspectApplication(aspectSimulationPredicate);
        }
        testResultEvaluator.init(
                createApplyJoinPointSelector(joinPointSelector, enableJoinPoint), Arrays.asList(AjJoinPointType.METHOD_EXECUTION));
    }

    private ApplyJoinPointSelector createApplyJoinPointSelector(Predicate joinPointSelector, boolean enableJoinPoint) {
        return enableJoinPoint ? evaluateUniverseJoinPoints(joinPointSelector, joinPointSelector) : evaluateUniverseJoinPoints(joinPointSelector);
    }

    @SuppressWarnings("all")
    private static class AnyClass {
        public void selectedMethod() {}
        public void otherMethod() {}
        public void neverCalledMethod() {}
    }
}
