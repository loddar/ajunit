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

import org.failearly.ajunit.AjUnitSetup;
import org.failearly.ajunit.SuppressedJoinPoint;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.util.ClassUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* The Implementation.
*/
final class AjUnitSetupImpl implements AjUnitSetup {
    private final List<Class<?>> testFixtureClasses = new ArrayList<>();

    private final List<Predicate> enabledJoinPoints = new LinkedList<>();

    private String aspectName;

    public AjUnitSetupImpl() {
    }

    List<Predicate> getEnabledJoinPoints() {
        return enabledJoinPoints;
    }

    List<Class<?>> getTestFixtureClasses() {
        return testFixtureClasses;
    }

    String getAspectName() {
        return aspectName;
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
        enabledJoinPoints.add(suppressedJoinPoint.predicate());
        return this;
    }

    @Override
    public void assignAspect(String fullQualifiedAspectName) {
        this.aspectName = fullQualifiedAspectName;
    }

}
