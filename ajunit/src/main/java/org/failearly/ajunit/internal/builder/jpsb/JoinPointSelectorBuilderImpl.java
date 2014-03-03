/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.builder.jpsb;

import org.failearly.ajunit.builder.JoinPointSelector;
import org.failearly.ajunit.builder.MethodCallJoinPointSelector;
import org.failearly.ajunit.builder.MethodExecutionJoinPointSelector;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.RootBuilderBase;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.util.Set;

/**
 * JoinPointSelectorBuilderImpl is the implementation for JoinPointSelector.
 */
public final class JoinPointSelectorBuilderImpl extends RootBuilderBase<JoinPointSelectorBuilderImpl> implements JoinPointSelector {
    private final Set<AjJoinPointType> joinPointTypes;

    public JoinPointSelectorBuilderImpl(Set<AjJoinPointType> joinPointTypes) {
        init(LogicalStructureBuilder.createRootBuilder(this, LogicalPredicates.or()));
        this.joinPointTypes = joinPointTypes;
    }

    @Override
    public MethodExecutionJoinPointSelector methodExecute() {
        return super.and(getMethodExecutionJoinPointBuilderFactory());
    }

    @Override
    public MethodCallJoinPointSelector methodCall() {
        return super.and(getMethodCallJoinPointBuilderFactory());
    }

    @Override
    public void notYetSpecified() {
        alwaysFalse();
    }

    private BuilderFactory<JoinPointSelectorBuilderImpl, JoinPointSelectorBuilderImpl, MethodExecutionJoinPointSelectorImpl>
            getMethodExecutionJoinPointBuilderFactory() {
        joinPointTypes.add(AjJoinPointType.METHOD_EXECUTION);
        return new BuilderFactory<JoinPointSelectorBuilderImpl, JoinPointSelectorBuilderImpl, MethodExecutionJoinPointSelectorImpl>() {
            @Override
            public MethodExecutionJoinPointSelectorImpl createBuilder(JoinPointSelectorBuilderImpl root,
                                                                    JoinPointSelectorBuilderImpl parent,
                                                                    CompositePredicate compositePredicate) {
                return new MethodExecutionJoinPointSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

    private BuilderFactory<JoinPointSelectorBuilderImpl, JoinPointSelectorBuilderImpl, MethodCallJoinPointSelectorImpl>
        getMethodCallJoinPointBuilderFactory() {
        joinPointTypes.add(AjJoinPointType.METHOD_CALL);
        return new BuilderFactory<JoinPointSelectorBuilderImpl, JoinPointSelectorBuilderImpl, MethodCallJoinPointSelectorImpl>() {
            @Override
            public MethodCallJoinPointSelectorImpl createBuilder(JoinPointSelectorBuilderImpl root,
                                                                      JoinPointSelectorBuilderImpl parent,
                                                                      CompositePredicate compositePredicate) {
                return new MethodCallJoinPointSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

}
