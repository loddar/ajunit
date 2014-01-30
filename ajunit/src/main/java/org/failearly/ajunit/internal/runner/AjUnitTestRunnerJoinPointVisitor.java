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

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * AjUnitTestRunnerJoinPointVisitor is responsible for splitting the AjUniverse. The AjUnitTestRunner is responsible for the evaluation of
 * these results.
 * <br/><br/>
 * The splitting results:<br/>
 * <ul>
 *     <li>the matching join points</li>
 *     <li>the none matching join points</li>
 *     <li>the suppressed join points, not enabled by {@link org.failearly.ajunit.AjUnitSetup#enableSuppressedJoinPoints(org.failearly.ajunit.SuppressedJoinPoint)}</li>
 * </ul>
 *
 * @see org.failearly.ajunit.internal.universe.AjUniverse#visitJoinPoints(org.failearly.ajunit.internal.universe.AjJoinPointVisitor)
*/
final class AjUnitTestRunnerJoinPointVisitor implements AjJoinPointVisitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjUnitTestRunnerJoinPointVisitor.class);

    private static final Predicate SUPPRESSED_JOIN_POINTS=SuppressedJoinPointFactory.PREDICATE_ALL_SUPPRESSED_JOINPOINTS;
    private final Predicate enabledJoinPointsPredicate;
    private final Predicate joinPointSelector;

    private final List<AjJoinPoint> matchingJoinPoints=new LinkedList<>();
    private final List<AjJoinPoint> noneMatchingJoinPoints=new LinkedList<>();
    private final List<AjJoinPoint> suppressedJoinPoints =new LinkedList<>();

    AjUnitTestRunnerJoinPointVisitor(List<Predicate> enabledJoinPoints, Predicate joinPointSelector) {
        this.enabledJoinPointsPredicate = toEnabledJoinPointPredicate(enabledJoinPoints);
        this.joinPointSelector = joinPointSelector;
    }

    private Predicate toEnabledJoinPointPredicate(List<Predicate> enabledJoinPoints) {
        final Predicate enabledJoinPointsPredicate;
        if( enabledJoinPoints.isEmpty() ) {
            enabledJoinPointsPredicate = StandardPredicates.alwaysFalse();
        }
        else {
            enabledJoinPointsPredicate = LogicalPredicates.or()
                    .addPredicates(enabledJoinPoints);
        }
        return enabledJoinPointsPredicate;
    }

    @Override
    public void visit(AjJoinPoint joinPoint) {
        if( isNotSuppressed(joinPoint) ) {
            if (joinPointSelector.evaluate(joinPoint)) {
                matchingJoinPoints.add(joinPoint);
            }
            else {
                noneMatchingJoinPoints.add(joinPoint);
            }
        }
        else {
            LOGGER.debug("ajUnit Join Point has been suppressed: '{}'", joinPoint);
            suppressedJoinPoints.add(joinPoint);
        }
    }

    private boolean isNotSuppressed(AjJoinPoint joinPoint) {
        if( enabledJoinPointsPredicate.evaluate(joinPoint) ) {
            LOGGER.debug("ajUnit a Suppressed Join Point has been enabled: '{}'", joinPoint);
            return true;
        }

        return ! SUPPRESSED_JOIN_POINTS.evaluate(joinPoint);
    }

    List<AjJoinPoint> getMatchingJoinPoints() {
        return Collections.unmodifiableList(matchingJoinPoints);
    }

    List<AjJoinPoint> getNoneMatchingJoinPoints() {
        return Collections.unmodifiableList(noneMatchingJoinPoints);
    }

    List<AjJoinPoint> getSuppressedJoinPoints() {
        return Collections.unmodifiableList(suppressedJoinPoints);
    }
}
