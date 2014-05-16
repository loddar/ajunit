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

import org.failearly.ajunit.builder.MethodArgumentTypeSelector;
import org.failearly.ajunit.builder.MethodArgumentsSelector;
import org.failearly.ajunit.builder.MethodJoinPointSelector;
import org.failearly.ajunit.builder.NumberComparator;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.list.ListTransformers;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

/**
 * The implementation of {@link org.failearly.ajunit.builder.MethodArgumentsSelector}.
 */
final class MethodArgumentsSelectorImpl extends JoinPointBuilderBase<MethodArgumentsSelectorImpl> implements MethodArgumentsSelector {



    MethodArgumentsSelectorImpl(
            JoinPointSelectorImpl root,
            MethodJoinPointSelectorImpl parent,
            CompositePredicate compositePredicate) {
        super.init(LogicalStructureBuilder.createBuilder(root, parent, this, createCompositeNode(compositePredicate)));
    }

    private static CompositePredicate createCompositeNode(CompositePredicate compositePredicate) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        AjpTransformers.methodTransformer(),
                        MethodTransformers.methodArgumentsTransformer()
                ),
                compositePredicate
        );
    }

    @Override
    public MethodJoinPointSelector endArgumentsSelector() {
        return super.doEndLogicalExpression(MethodJoinPointSelector.class, true);
    }

    @Override
    public MethodArgumentsSelector byNoArguments() {
        return byNumberOfArguments(0, NumberComparator.EQUALS);
    }

    @Override
    public MethodArgumentsSelector byNumberOfArguments(int number, NumberComparator numberComparator) {
        super.addPredicate(
                StandardPredicates.transformerPredicate(
                        ListTransformers.sizeTransformer(),
                        JoinPointSelectorUtils.createNumberComparatorPredicate(number, numberComparator)
                )
        );
        return this;
    }

    @Override
    public MethodArgumentTypeSelector byArgumentPosition(int position) {
        return this.byArgumentPositions(position);
    }

    @Override
    public MethodArgumentTypeSelector byArgumentPositions(int... positions) {
        return super.and(getMethodArgumentPositionsSelectorBuilderFactory(positions));
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodArgumentsSelectorImpl,MethodArgumentTypeSelectorImpl>
    getMethodArgumentPositionsSelectorBuilderFactory(final int... positions) {
        return new BuilderFactory<JoinPointSelectorImpl, MethodArgumentsSelectorImpl, MethodArgumentTypeSelectorImpl>() {
            @Override
            public MethodArgumentTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodArgumentsSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodArgumentTypeSelectorImpl(root, parent, compositePredicate, positions);
            }
        };
    }
}
