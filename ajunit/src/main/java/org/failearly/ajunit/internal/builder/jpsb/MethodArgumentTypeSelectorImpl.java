/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 marko (http://fail-early.com/contact)
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

import org.failearly.ajunit.builder.ListLogicalOperator;
import org.failearly.ajunit.builder.MethodArgumentTypeSelector;
import org.failearly.ajunit.builder.MethodArgumentsSelector;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.list.ListTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

/**
 * MethodArgumentTypeSelectorImpl is responsible for ...
 */
final class MethodArgumentTypeSelectorImpl extends BuilderBase<JoinPointSelectorImpl,MethodArgumentTypeSelectorImpl>
        implements MethodArgumentTypeSelector {

    private ClassSelectorBuilder<MethodArgumentTypeSelectorImpl> methodArgumentTypeSelectorBuilder;

    MethodArgumentTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodArgumentsSelectorImpl parent,
            CompositePredicate compositePredicate,
            int... positions) {
        super.init(LogicalStructureBuilder.createBuilder(
                root, parent, this, createCompositeNode(compositePredicate, ListLogicalOperator.ALL_OF, positions))
            );
        methodArgumentTypeSelectorBuilder = SelectorBuilders.createMethodArgumentTypeSelectorBuilder(this);
    }

    private static CompositePredicate createCompositeNode(
            CompositePredicate compositePredicate,
            ListLogicalOperator listLogicalOperator, int... positions) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        ListTransformers.getElementsFromList(positions)
                ),
                JoinPointSelectorUtils.createListLogicalOperator(listLogicalOperator, compositePredicate)
        );
    }

    @Override
    public MethodArgumentsSelector endArgumentPosition() {
        return super.doEndLogicalExpression(MethodArgumentsSelector.class, true);
    }

    @Override
    public MethodArgumentTypeSelector byClass(Class<?> argumentClass) {
        return methodArgumentTypeSelectorBuilder.byClass(argumentClass);
    }

    @Override
    public MethodArgumentTypeSelector byPrimitive() {
        return methodArgumentTypeSelectorBuilder.byPrimitive();
    }
}
