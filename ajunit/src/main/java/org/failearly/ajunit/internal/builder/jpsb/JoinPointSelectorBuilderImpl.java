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
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.RootBuilderBase;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;

/**
 * JoinPointSelectorBuilderImpl is responsible for ...
 */
public final class JoinPointSelectorBuilderImpl extends RootBuilderBase<JoinPointSelectorBuilderImpl> implements JoinPointSelectorBuilder {

    public JoinPointSelectorBuilderImpl() {
        init(LogicalStructureBuilder.createRootBuilder(this, LogicalPredicates.or()));
    }

    @Override
    public MethodJoinPointSelectorBuilder methodExecute() {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder methodCall() {
        return null;
    }

    @Override
    public void notYetSpecified() {
        alwaysFalse();
    }
}
