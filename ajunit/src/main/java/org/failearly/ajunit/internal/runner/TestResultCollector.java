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

/**
 * FailingTestResultCollector is responsible for ...
 */
interface TestResultCollector {
    /**
     * No join point has been selected at all.
     */
    void noJoinPointsSelected();

    /**
     * The join point has been selected but not applied.
     *
     * @param selectJoinPoint the string representation of the selected join point.
     */
    void selectedButNotApplied(String selectJoinPoint);

    /**
     * Called in case that the test subject classes are all in the selected part and the NONE selected join points are empty.
     */
    void missingNoneSelected();

    /**
     * The opposite of {@link #selectedButNotApplied(String)}. The joinpoint has NOT been selected, but at least once applied.
     * @param appliedJoinPoint the string representation of the applied join point.
     */
    void noneSelectedButApplied(String appliedJoinPoint);

    /**
     * Suppressed join point has been applied.
     * @param suppressedJoinPoint the string representation of the suppressed but applied join point.
     */
    void suppressedButApplied(String suppressedJoinPoint);
}
