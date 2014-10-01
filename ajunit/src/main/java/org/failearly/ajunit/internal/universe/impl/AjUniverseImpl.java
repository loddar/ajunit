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
    private int numberOfAspectInstances=0;

    AjUniverseImpl(final String universeName) {
        this(universeName, new LinkedList<>());
    }

    /**
     *  Just for test purpose declared as package private.
     */
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    AjUniverseImpl(final String universeName, final List<AjJoinPoint> joinPoints) {
        this.universeName = universeName;
        this.joinPoints = joinPoints;
    }

    @Override
    public String getUniverseName() {
        return universeName;
    }

    @Override
    public String getAspectName() {
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
        doAddJoinPoint(AjJoinPointFactory.createAjJoinPoint(joinPointType, method));
    }

    @Override
    public void addJoinpoint(final AjJoinPointType joinPointType, final Field field) {
        doAddJoinPoint(AjJoinPointFactory.createAjJoinPoint(joinPointType, field));
    }

    @Override
    public void addJoinpoint(final AjJoinPointType joinPointType, final Constructor<?> constructor) {
        doAddJoinPoint(AjJoinPointFactory.createAjJoinPoint(joinPointType, constructor));
    }

    private void doAddJoinPoint(AjJoinPoint ajJoinPoint) {
        joinPoints.add(ajJoinPoint);
        LOGGER.info("Universe {}: Add join point {}", this.universeName, ajJoinPoint);
    }

    @Override
    public void visitJoinPoints(AjJoinPointVisitor joinPointVisitor) {
        for (AjJoinPoint joinPoint : joinPoints) {
            joinPointVisitor.visit(joinPoint);
        }
    }

    @Override
    public void incrementNumberOfAspectInstances() {
        this.numberOfAspectInstances+=1;
    }

    @Override
    public int getNumberOfAspectInstances() {
        return this.numberOfAspectInstances;
    }
}
