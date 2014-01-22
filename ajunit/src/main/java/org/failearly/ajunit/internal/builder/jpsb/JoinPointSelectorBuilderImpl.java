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
 */
package org.failearly.ajunit.internal.builder.jpsb;

import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.RootBuilderBase;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.util.Set;

/**
 * JoinPointSelectorBuilderImpl is the implementation for JoinPointSelectorBuilder.
 */
public final class JoinPointSelectorBuilderImpl extends RootBuilderBase<JoinPointSelectorBuilderImpl> implements JoinPointSelectorBuilder {
    private final Set<AjJoinPointType> joinPointTypes;

    public JoinPointSelectorBuilderImpl(Set<AjJoinPointType> joinPointTypes) {
        init(LogicalStructureBuilder.createRootBuilder(this, LogicalPredicates.or()));
        this.joinPointTypes = joinPointTypes;
    }

    @Override
    public MethodJoinPointSelectorBuilder methodExecute() {
        return super.and(getMethodJoinPointBuilderFactory(AjJoinPointType.METHOD_EXECUTION));
    }

    @Override
    public MethodJoinPointSelectorBuilder methodCall() {
        return super.and(getMethodJoinPointBuilderFactory(AjJoinPointType.METHOD_CALL));
    }

    @Override
    public void notYetSpecified() {
        alwaysFalse();
    }

    public BuilderFactory<JoinPointSelectorBuilderImpl, JoinPointSelectorBuilderImpl, MethodJoinPointSelectorBuilderImpl>
                getMethodJoinPointBuilderFactory(final AjJoinPointType methodJoinPointType) {
        joinPointTypes.add(methodJoinPointType);
        return new BuilderFactory<JoinPointSelectorBuilderImpl, JoinPointSelectorBuilderImpl, MethodJoinPointSelectorBuilderImpl>() {
            @Override
            public MethodJoinPointSelectorBuilderImpl createBuilder(JoinPointSelectorBuilderImpl root,
                                                                    JoinPointSelectorBuilderImpl parent,
                                                                    CompositePredicate compositePredicate) {
                return new MethodJoinPointSelectorBuilderImpl(root, parent, compositePredicate, methodJoinPointType);
            }
        };
    }

    /**
     * Returns the join point types {@link org.failearly.ajunit.internal.universe.AjJoinPointType}, a selector builder has been created for.
     */
    public Set<AjJoinPointType> getJoinPointTypes() {
        return joinPointTypes;
    }
}
