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
package org.failearly.ajunit.internal.builder.jpsb;

import org.failearly.ajunit.builder.SelectorBuilder;
import org.failearly.ajunit.builder.types.LogicalOperator;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.helper.AjUnitTypesPredicateFactory;

/**
 * JoinPointBuilderBase is the base class for all Joinpoint Selector builder classes. It already provides implementations for
 * all methods of {@link org.failearly.ajunit.builder.generic.LogicalSelector}.
 * <p>
 * Only {@code newInstance(JoinPointSelectorImpl, C, CompositePredicate)} must be implemented.
 */
@SuppressWarnings("unused")
public abstract class JoinPointSelectorBuilderBase<C extends Builder, P extends SelectorBuilder>
        extends BuilderBase<JoinPointSelectorImpl, C, P> {
    protected JoinPointSelectorBuilderBase(Class<C> thisClass, Class<P> parentClass) {
        super(thisClass, parentClass);
    }

    public final C logicalExpression(LogicalOperator logicalOperator) {
        return super.createNewBuilderNode(
                AjUnitTypesPredicateFactory.createLogicalOperatorPredicate(logicalOperator),
                super.createLogicalExpressionBuilderFactory()
        );
    }
}
