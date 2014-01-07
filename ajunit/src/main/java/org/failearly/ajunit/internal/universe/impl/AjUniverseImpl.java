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
package org.failearly.ajunit.internal.universe.impl;

import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;
import org.failearly.ajunit.internal.universe.AjUniverse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * AjUniverseImpl is responsible for ...
 */
final class AjUniverseImpl implements AjUniverse {

    private static final Logger LOGGER = LoggerFactory.getLogger(AjUniverseImpl.class);

    private final String universeName;
    private final List<AjJoinPoint> joinPoints;
    private boolean initialized = false;
    private int aspectInstanceCounter=0;

    AjUniverseImpl(final String universeName) {
        this(universeName, new LinkedList<AjJoinPoint>());
    }

    AjUniverseImpl(final String universeName, final List<AjJoinPoint> joinPoints) {
        this.universeName = universeName;
        this.joinPoints = joinPoints;
    }

    public String getUniverseName() {
        return universeName;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    void markInitialized() {
        this.initialized = true;
    }

    @Override
    public void addJoinpoint(final AjJoinPointType joinPointType, final Method method) {
        final AjJoinPointImpl ajJoinPoint = createAjJoinPoint(joinPointType);
        ajJoinPoint.setMethod(method);
        LOGGER.info("Add method join point: {}", ajJoinPoint);
    }

    @Override
    public void addJoinpoint(final AjJoinPointType joinPointType, final Field field) {
        final AjJoinPointImpl ajJoinPoint = createAjJoinPoint(joinPointType);
        ajJoinPoint.setField(field);
        LOGGER.info("Add field join point: {}", ajJoinPoint);
    }

    @Override
    public void addJoinpoint(final AjJoinPointType joinPointType, final Constructor constructor) {
        final AjJoinPointImpl ajJoinPoint = createAjJoinPoint(joinPointType);
        ajJoinPoint.setConstructor(constructor);
        LOGGER.info("Add constructor join point: {}", ajJoinPoint);
    }

    private AjJoinPointImpl createAjJoinPoint(final AjJoinPointType joinPointType) {
        final AjJoinPointImpl joinPoint = new AjJoinPointImpl(joinPointType);
        joinPoints.add(joinPoint);
        return joinPoint;
    }


    @Override
    public void visitJoinPoints(AjJoinPointVisitor joinPointVisitor) {
        for (AjJoinPoint joinPoint : joinPoints) {
            joinPointVisitor.visit(joinPoint);
        }
    }

    @Override
    public void increaseAspectInstances() {
        this.aspectInstanceCounter++;
    }

    @Override
    public int getAspectInstanceCounter() {
        return aspectInstanceCounter;
    }
}
