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
package org.failearly.ajunit;

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
import java.util.*;

/**
 * AjUnitAspectBase is THE base class for all ajUnit based aspects.
 * This method should be called by the aspect's {@code advice}.
 */
public abstract class AjUnitAspectBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjUnitAspectBase.class);
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

    protected AjUnitAspectBase() {
        final AjUniverse universe = AjUniversesHolder.findOrCreateUniverse(currentUniverseName());
        universe.incrementNumberOfAspectInstances();
    }

    /**
     * Factory method for {@link org.failearly.ajunit.AjUnitAspectBase.ContextBuilder}.
     * @return new ContextBuilder.
     */
    protected static ContextBuilder context() {
        return new ContextBuilderImpl();
    }

    /**
     * Convenient factory method for {@link org.failearly.ajunit.AjUnitAspectBase.ContextBuilder} with one named context.
     * @return new ContextBuilder.
     */
    protected static ContextBuilder context(String name1, Object val1) {
        return context().addContext(name1, val1);
    }

    /**
     * Convenient factory method for {@link org.failearly.ajunit.AjUnitAspectBase.ContextBuilder} with two named context.
     * @return new ContextBuilder.
     */
    protected static ContextBuilder context(String name1, Object val1, String name2, Object val2) {
        return context().addContext(name1, val1).addContext(name2, val2);
    }

    /**
     * <i>Apply the join point</i>, means to find the associated ajUnit join point and then mark it as applied.
     * @param thisJoinPointStaticPart the AspectJ join point.
     * @param thisEnclosingJoinPointStaticPart the enclosing join point.
     */
    protected final void applyJoinPoint(final JoinPoint.StaticPart thisJoinPointStaticPart, final JoinPoint.StaticPart thisEnclosingJoinPointStaticPart) {
        this.applyJoinPoint(thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart, context());
    }

    /**
     * <i>Apply the join point</i>, means to find the associated ajUnit join point and then mark it as applied.
     *
     * @param thisJoinPointStaticPart  the current join point.
     * @param thisEnclosingJoinPointStaticPart the enclosing join point ({@code thisEnclosingJoinPointStaticPart})
     * @param contextBuilder context builder.
     *
     * @see #context()
     * @see #context(String, Object)
     * @see #context(String, Object, String, Object)
     */
    protected final void applyJoinPoint(
            final JoinPoint.StaticPart thisJoinPointStaticPart,
            final JoinPoint.StaticPart thisEnclosingJoinPointStaticPart,
            final ContextBuilder contextBuilder) {
        // final JoinPoint.StaticPart thisJoinPointStaticPart= thisJoinPointStaticPart.getStaticPart();
        // storeStandardContext(thisJoinPointStaticPart, contextBuilder);

        final String universeName = currentUniverseName();
        LOGGER.info("ajUnit - {}: Apply AspectJ join point {} (calling join point is {})",
                        universeName,
                        thisJoinPointStaticPart,
                        thisEnclosingJoinPointStaticPart
                );
        final AjJoinPointType ajJoinPointType = AjJoinPointType.resolveFromJoinPoint(thisJoinPointStaticPart);
        final AjJoinPoint ajUnitJoinPoint=resolveAjJoinPoint(ajJoinPointType, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart, universeName);
        contextBuilder.storeNamedContextValues(ajUnitJoinPoint);
        ajUnitJoinPoint.applyJoinPoint(ajJoinPointType.chooseContext(thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart));
        LOGGER.debug("ajUnit - {}: Applied ajUnit join point {}", universeName, ajUnitJoinPoint);
    }

    private String currentUniverseName() {
        return this.getClass().getName();
    }

    private void storeStandardContext(JoinPoint thisJoinPoint, ContextBuilder contextBuilder) {
        final Object thisObject = thisJoinPoint.getThis();
        final Object targetObject = thisJoinPoint.getTarget();
        final Object[] args = thisJoinPoint.getArgs();
        contextBuilder.addContext("_this", thisObject);
        contextBuilder.addContext("_target", targetObject);
        contextBuilder.addContext("_args", Arrays.asList(args));
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

    private AjJoinPointMatcher resolveJoinPointMatcher(JoinPoint.StaticPart joinPoint) {
        final AjJoinPointType ajJoinPointType=AjJoinPointType.resolveFromJoinPoint(joinPoint);
        return ajJoinPointType.getJoinPointMatcher();
    }

    /**
     * ContextBuilder is responsible for collecting (named) context values associated with current joint point.
     */
    protected interface ContextBuilder {
        /**
         * Add a (named) context value.
         * @param name the context name.
         * @param value the context value.
         * @return itself.
         */
        ContextBuilder addContext(String name, Object value);

        /**
         * Internal use.
         */
        void storeNamedContextValues(AjJoinPoint ajJoinPoint);
    }

    private static final class ContextBuilderImpl implements ContextBuilder {
        private final Map<String, Object> contextMap=new HashMap<>();
        private ContextBuilderImpl() {}

        @Override
        public ContextBuilder addContext(final String name, Object value) {
            this.contextMap.put(name, value);
            return this;
        }

        @Override
        public void storeNamedContextValues(final AjJoinPoint ajJoinPoint) {
            for (Map.Entry<String, Object> context : contextMap.entrySet()) {
                ajJoinPoint.addContext(context.getKey(), context.getValue());
            }
        }
    }
}
