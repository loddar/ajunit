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

import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.util.*;

/**
 * TestResultEvaluator is responsible for evaluating the test results of {@link ApplyJoinPointSelector}.
 */
final class TestResultEvaluator {
    private final List<AjJoinPoint> selectedJoinPoints=new LinkedList<>();
    private final List<AjJoinPoint> noneSelectedJoinPoints=new LinkedList<>();
    private final List<AjJoinPoint> suppressedJoinPoints=new LinkedList<>();
    private final Set<AjJoinPointType> joinPointTypes=new HashSet<>();


    TestResultEvaluator init(ApplyJoinPointSelector joinPointVisitor, Collection<AjJoinPointType> ajJoinPointTypes) {
        this.selectedJoinPoints.addAll(joinPointVisitor.getSelectedJoinPoints());
        this.noneSelectedJoinPoints.addAll(joinPointVisitor.getNoneSelectedJoinPoints());
        this.suppressedJoinPoints.addAll(joinPointVisitor.getSuppressedJoinPoints());
        this.joinPointTypes.addAll(ajJoinPointTypes);
        return this;
    }


    void evaluateTestResult(TestResultCollector testResultCollector) {
        evaluateSelectedJoinPoints(testResultCollector);
        evaluateNoneSelectedJoinPoints(testResultCollector);
        evaluateSuppressedJoinPoints(testResultCollector);
    }

    private void evaluateSuppressedJoinPoints(TestResultCollector testResultCollector) {
        for (AjJoinPoint suppressedJoinPoint : suppressedJoinPoints ) {
            if( suppressedJoinPoint.getNumApplications()>0 ) {
                testResultCollector.suppressedButApplied(suppressedJoinPoint.toShortString());
            }
        }
    }

    private void evaluateNoneSelectedJoinPoints(TestResultCollector testResultCollector) {
        checkForMissingNoneSelected(testResultCollector);
        checkForNoneSelectedButApplied(testResultCollector);
    }

    private void checkForNoneSelectedButApplied(TestResultCollector testResultCollector) {
        for (AjJoinPoint noneSelectedJoinPoint : noneSelectedJoinPoints) {
            if( noneSelectedJoinPoint.getNumApplications()>0 ) {
                testResultCollector.noneSelectedButApplied(noneSelectedJoinPoint.toShortString());
            }
        }

    }

    private void checkForMissingNoneSelected(TestResultCollector testResultCollector) {
        if( hasNoKindedJoinPoints(this.noneSelectedJoinPoints) ) {
            testResultCollector.missingNoneSelected();
        }
    }

    private void evaluateSelectedJoinPoints(TestResultCollector testResultCollector) {
        checkForNoJoinPointsSelected(testResultCollector);
        checkForSelectedButNotApplied(testResultCollector);
    }

    private void checkForSelectedButNotApplied(TestResultCollector testResultCollector) {
        for (AjJoinPoint selectedJoinPoint : selectedJoinPoints) {
            if( selectedJoinPoint.getNumApplications()==0 ) {
                testResultCollector.selectedButNotApplied(selectedJoinPoint.toShortString());
            }
        }
    }

    private void checkForNoJoinPointsSelected(TestResultCollector testResultCollector) {
        if(hasNoKindedJoinPoints(this.selectedJoinPoints)) {
            testResultCollector.noJoinPointsSelected();
        }
    }


    private boolean hasNoKindedJoinPoints(List<AjJoinPoint> joinPoints) {
        for (AjJoinPoint joinPoint : joinPoints) {
            if( joinPointTypes.contains(joinPoint.getJoinPointType())) {
                return false;
            }
        }
        return true;
    }
}
