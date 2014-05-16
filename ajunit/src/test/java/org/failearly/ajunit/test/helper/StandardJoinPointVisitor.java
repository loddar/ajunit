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
package org.failearly.ajunit.test.helper;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilder;
import org.failearly.ajunit.internal.universe.AjJoinPointStringBuilders;
import org.failearly.ajunit.internal.universe.AjJoinPointVisitor;

import java.util.LinkedList;
import java.util.List;

/**
 * StandardJoinPointVisitor applies the predicate and collects all matching join points. Uses
 * an {@link org.failearly.ajunit.internal.universe.AjJoinPointStringBuilder} for converting the
 * {@link org.failearly.ajunit.internal.universe.AjJoinPoint} to a string. These strings will be collected.
 *
 * The Standard implementation uses the {@link org.failearly.ajunit.internal.universe.AjJoinPointStringBuilders#toLongStringBuilder()}.
 */
public class StandardJoinPointVisitor implements AjJoinPointVisitor {
    private final Predicate predicate;
    private final List<String> selectedJoinPoints = new LinkedList<>();
    private final AjJoinPointStringBuilder ajJoinPointStringBuilder;


    public StandardJoinPointVisitor(Predicate predicate) {
        this(predicate, AjJoinPointStringBuilders.toLongStringBuilder());
    }

    public StandardJoinPointVisitor(Predicate predicate, AjJoinPointStringBuilder ajJoinPointStringBuilder) {
        this.predicate = predicate;
        this.ajJoinPointStringBuilder = ajJoinPointStringBuilder;
    }

    @Override
    public void visit(AjJoinPoint joinPoint) {
        if (predicate.test(joinPoint)) {
            selectedJoinPoints.add(toString(joinPoint));
        }
    }

    private String toString(AjJoinPoint joinPoint) {
        return joinPoint.toString(this.ajJoinPointStringBuilder);
    }

    public final List<String> getSelectedJoinPoints() {
        return selectedJoinPoints;
    }
}
