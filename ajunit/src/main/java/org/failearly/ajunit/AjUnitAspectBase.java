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
package org.failearly.ajunit;

import org.aspectj.lang.JoinPoint;
import org.failearly.ajunit.internal.universe.*;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.AjUnitUtils;
import org.failearly.ajunit.internal.util.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * AjUnitAspectBase is THE base class for all ajUnit based aspects. The only provided method is {@link #doApply(org.aspectj.lang.JoinPoint)}.
 * This method should be called by the aspect's {@code advice}.
 */
public abstract class AjUnitAspectBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjUnitAspectBase.class);

    protected AjUnitAspectBase() {
    }

    /**
     * <i>Apply the join point</i>, means to find the associated ajUnit join point and then mark it as applied..
     * @param joinPoint the AspectJ join point.
     */
    protected final void doApply(final JoinPoint joinPoint) {
        final String universeName = AjUnitUtils.resolveUniverseName(this);
        LOGGER.info("ajUnit - {}: Apply AspectJ join point {}", universeName, joinPoint);
        final AjJoinPoint ajUnitJoinPoint=resolveAjJoinPoint(joinPoint, universeName);
        ajUnitJoinPoint.apply();
        LOGGER.debug("ajUnit - {}: Applied ajUnit join point {}", universeName, ajUnitJoinPoint);
    }

    private AjJoinPoint resolveAjJoinPoint(final JoinPoint joinPoint, String universeName) {
        final List<AjJoinPoint> joinPoints = lookupForJoinPointsInUniverse(joinPoint, universeName);
        validateResult(joinPoint, joinPoints);
        return joinPoints.get(0);
    }

    private List<AjJoinPoint> lookupForJoinPointsInUniverse(final JoinPoint joinPoint, String universeName) {
        final AjUniverse universe = AjUniversesHolder.findUniverse(universeName);
        return findAjJoinPointsInUniverse(joinPoint, universe);
    }

    private List<AjJoinPoint> findAjJoinPointsInUniverse(final JoinPoint joinPoint, AjUniverse universe) {
        if( universe !=null ) {
            final AjJoinPointMatcher matcher = resolveJoinPointMatcher(joinPoint);
            final List<AjJoinPoint> joinPoints=new ArrayList<>();
            universe.visitJoinPoints(new AjJoinPointVisitor() {
                @Override
                public void visit(AjJoinPoint ajJoinPoint) {
                    if (matcher.match(joinPoint, ajJoinPoint)) {
                        joinPoints.add(ajJoinPoint);
                    }
                }
            });

            return joinPoints;
        }

        return Arrays.asList(AjJoinPoint.NULL_OBJECT);
    }

    private void validateResult(JoinPoint joinPoint, List<AjJoinPoint> joinPoints) {
        final int numAssociatedJoinPoints = joinPoints.size();
        AjAssert.assertCondition(
                numAssociatedJoinPoints > 0,
                MessageUtils.message("Assertion failed: No associated ajUnit join point found for AspectJ join point").arg(joinPoint)
                        .line("Please add").arg(joinPoint.getSignature().getDeclaringTypeName()).part("to Test Fixtures.")
                        .line("Or the join point type").arg(joinPoint.getKind())
                        .part("is not yet supported: Propose a feature request to https://github.com/loddar/ajunit/issues.")
        );
        AjAssert.assertCondition(
                numAssociatedJoinPoints == 1,
                MessageUtils.message("Assertion failed: Exactly one join point expected, got").arg(numAssociatedJoinPoints)
        );

    }

    private AjJoinPointMatcher resolveJoinPointMatcher(JoinPoint joinPoint) {
        final AjJoinPointType ajJoinPointType=AjJoinPointType.resolveFromJoinPoint(joinPoint);
        return ajJoinPointType.getJoinPointMatcher();
    }
}
