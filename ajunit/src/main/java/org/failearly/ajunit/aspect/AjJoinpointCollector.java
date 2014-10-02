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
package org.failearly.ajunit.aspect;

import org.aspectj.lang.JoinPoint;
import org.failearly.ajunit.internal.universe.*;
import org.failearly.ajunit.internal.universe.impl.AjUniversesHolder;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * AjJoinpointCollector is THE base class for all ajUnit based aspects.
 * This method should be called by the aspect's {@code advice}.
 */
public final class AjJoinpointCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjJoinpointCollector.class);
    private static final AjJoinPoint NULL_AJ_JOIN_POINT = new AjJoinPoint() {
        @Override
        public AjJoinPointType getJoinPointType() {
            return null;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return null;
        }

        @Override
        public Method getMethod() {
            return null;
        }

        @Override
        public Field getField() {
            return null;
        }

        @Override
        public Constructor getConstructor() {
            return null;
        }

        @Override
        public int getNumApplications() {
            return 0;
        }

        @Override
        public void applyJoinPoint(JoinPoint.StaticPart context) {
            LOGGER.info("Call apply on NULL_AJ_JOIN_POINT");
        }

        @Override
        public String toShortString() {
            return null;
        }

        @Override
        public String toString(AjJoinPointStringBuilder stringBuilder) {
            return null;
        }

        @Override
        public void addContext(String name, Object value) {}

        @Override
        public Object getContext(String name) {
            return null;
        }
    };

    private final Class<?> aspectClass;

    public AjJoinpointCollector(Class<?> aspectClass) {
        this.aspectClass = aspectClass;
        final AjUniverse universe = AjUniversesHolder.findOrCreateUniverse(currentUniverseName());
        universe.incrementNumberOfAspectInstances();
    }


    public final void applyJoinPoint(
            final JoinPoint.StaticPart thisJoinPointStaticPart,
            final JoinPoint.StaticPart thisEnclosingJoinPointStaticPart,
            final AjUnitAspect.Context context) {
        // final JoinPoint.StaticPart thisJoinPointStaticPart= thisJoinPointStaticPart.getStaticPart();
        // storeStandardContext(thisJoinPointStaticPart, context);

        final String universeName = currentUniverseName();
        LOGGER.info("ajUnit - {}: Apply AspectJ join point {} (calling join point is {})",
                        universeName,
                        thisJoinPointStaticPart,
                        thisEnclosingJoinPointStaticPart
                );
        final AjJoinPointType ajJoinPointType = AjJoinPointType.resolveFromJoinPoint(thisJoinPointStaticPart);
        final AjJoinPoint ajUnitJoinPoint=resolveAjJoinPoint(ajJoinPointType, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart, universeName);
        AjContexts.storeNamedContextValues(context, ajUnitJoinPoint);
        ajUnitJoinPoint.applyJoinPoint(ajJoinPointType.chooseContext(thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart));
        LOGGER.debug("ajUnit - {}: Applied ajUnit join point {}", universeName, ajUnitJoinPoint);
    }

    private String currentUniverseName() {
        return aspectClass.getName();
    }

    @SuppressWarnings("UnusedDeclaration")
    private void storeStandardContext(JoinPoint thisJoinPoint, AjUnitAspect.Context context) {
        final Object thisObject = thisJoinPoint.getThis();
        final Object targetObject = thisJoinPoint.getTarget();
        final Object[] args = thisJoinPoint.getArgs();
        context.addValue("_this", thisObject);
        context.addValue("_target", targetObject);
        context.addValue("_args", Arrays.asList(args));
    }

    private AjJoinPoint resolveAjJoinPoint(
            final AjJoinPointType ajJoinPointType,
            final JoinPoint.StaticPart thisJoinPointStaticPart,
            final JoinPoint.StaticPart thisEnclosingJoinPointStaticPart,
            final String universeName) {
        final List<AjJoinPoint> joinPoints = selectJoinPointsOfCurrentUniverse(ajJoinPointType, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart, universeName);
        validateResult(thisJoinPointStaticPart, joinPoints);
        return joinPoints.get(0);
    }

    private List<AjJoinPoint> selectJoinPointsOfCurrentUniverse(
            final AjJoinPointType ajJoinPointType,
            final JoinPoint.StaticPart thisJoinPointStaticPart,
            final JoinPoint.StaticPart thisEnclosingJoinPointStaticPart,
            String universeName) {
        final AjUniverse universe = AjUniversesHolder.findOrCreateUniverse(universeName);
        return doSelectJoinPointsOfCurrentUniverse(ajJoinPointType, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart, universe);
    }

    private List<AjJoinPoint> doSelectJoinPointsOfCurrentUniverse(
            final AjJoinPointType ajJoinPointType,
            final JoinPoint.StaticPart thisJoinPointStaticPart,
            final JoinPoint.StaticPart thisEnclosingJoinPointStaticPart,
            final AjUniverse universe) {
        if( universe.isInitialized() ) {
            final AjJoinPointMatcher ajJoinPointMatcher=ajJoinPointType.getJoinPointMatcher();
            final List<AjJoinPoint> joinPoints=new ArrayList<>();
            universe.visitJoinPoints(new AjJoinPointVisitor() {
                @Override
                public void visit(AjJoinPoint ajJoinPoint) {
                    if (ajJoinPointMatcher.match(thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart, ajJoinPoint)) {
                        joinPoints.add(ajJoinPoint);
                    }
                }
            });

            return joinPoints;
        }

        return Arrays.asList(NULL_AJ_JOIN_POINT);
    }

    private void validateResult(JoinPoint.StaticPart joinPoint, List<AjJoinPoint> joinPoints) {
        final int numAssociatedJoinPoints = joinPoints.size();
        AjAssert.assertCondition(
                numAssociatedJoinPoints > 0,
                MessageBuilders.message("Assertion failed: No associated ajUnit join point found for AspectJ join point").arg(joinPoint)
                        .line("Please add").arg(joinPoint.getSignature().getDeclaringTypeName()).part("to Test Fixtures.")
                        .line("Or the join point type").arg(joinPoint.getKind())
                        .part("is not yet supported: Propose a feature request to https://github.com/loddar/ajunit/issues.")
        );
        AjAssert.assertCondition(
                numAssociatedJoinPoints == 1,
                MessageBuilders.message("Assertion failed: Exactly one join point expected, got").arg(numAssociatedJoinPoints)
        );

    }

    @SuppressWarnings("UnusedDeclaration")
    private AjJoinPointMatcher resolveJoinPointMatcher(JoinPoint.StaticPart joinPoint) {
        final AjJoinPointType ajJoinPointType=AjJoinPointType.resolveFromJoinPoint(joinPoint);
        return ajJoinPointType.getJoinPointMatcher();
    }
}
