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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointMatcher;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.failearly.ajunit.internal.util.AjAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * AjUnitAspect is THE base aspect for ajUnit aspects.
 */
@Aspect
public abstract class AjUnitAspect extends AjUnitBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AjUnitAspect.class);

    protected AjUnitAspect() {
        increaseAspectInstances();
    }

    /**
     * The point cut definition under test.
     */
    @Pointcut("")
    protected abstract void pointcutUnderTest();


    protected final void doApply(final JoinPoint joinPoint) {
        LOGGER.debug("ajUnit - Applied aspectj join point: {}", joinPoint);
        final AjJoinPoint ajUnitJoinPoint=resolveAjJoinPoint(joinPoint);
        ajUnitJoinPoint.apply();
        LOGGER.debug("ajUnit - Found ajUnit join point: {}", ajUnitJoinPoint);
    }

    private AjJoinPoint resolveAjJoinPoint(final JoinPoint joinPoint) {
        final List<AjJoinPoint> joinPoints=new ArrayList<>();
        final AjJoinPointMatcher matcher = resolveJoinPointMatcher(joinPoint);
        visitJoinPoints(new AjJoinPointVisitor() {
            @Override
            public void visit(AjJoinPoint ajJoinPoint) {
                if (matcher.match(joinPoint, ajJoinPoint)) {
                    joinPoints.add(ajJoinPoint);
                }
            }
        });
        validateResult(joinPoint, joinPoints);
        return joinPoints.get(0);
    }

    private void validateResult(JoinPoint joinPoint, List<AjJoinPoint> joinPoints) {
        AjAssert.state(
                ! joinPoints.isEmpty(),
                "No ajUnit join point found. Add " + joinPoint.getSignature().getDeclaringTypeName() + " to Test Fixture."
        );
        AjAssert.state(
                joinPoints.size() == 1,
                "Exactly 1 join point expected, got " + joinPoints.size()
        );
    }

    private AjJoinPointMatcher resolveJoinPointMatcher(JoinPoint joinPoint) {
        final AjJoinPointType ajJoinPointType=AjJoinPointType.resolveFromJoinPoint(joinPoint);
        return ajJoinPointType.getJoinPointMatcher();
    }

}
