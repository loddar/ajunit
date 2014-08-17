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
package org.failearly.ajunit.internal.builder.jpsb.method;

import org.failearly.ajunit.builder.ListLogicalOperator;
import org.failearly.ajunit.builder.NumberComparator;
import org.failearly.ajunit.builder.Position;
import org.failearly.ajunit.builder.method.MethodArgumentTypeSelector;
import org.failearly.ajunit.builder.method.MethodArgumentsSelector;
import org.failearly.ajunit.builder.method.MethodJoinPointSelector;
import org.failearly.ajunit.builder.method.MethodsArgumentAnnotationSelector;
import org.failearly.ajunit.internal.annotation.NotYetImplemented;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.list.ListTransformers;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

/**
 * The implementation of {@link org.failearly.ajunit.builder.method.MethodArgumentsSelector}.
 *
 * @see org.failearly.ajunit.builder.method.MethodJoinPointSelector#arguments(org.failearly.ajunit.builder.LogicalOperator)
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

    private MethodArgumentsSelectorImpl(JoinPointSelectorImpl root, MethodArgumentsSelectorImpl parent, CompositePredicate compositePredicate) {
        super.init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
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
    public MethodArgumentTypeSelector argumentTypes(Position relativeTo, int... positions) {
        return super.or(getMethodArgumentPositionsSelectorBuilderFactory(relativeTo, positions));
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodArgumentsSelectorImpl,MethodArgumentTypeSelectorImpl>
    getMethodArgumentPositionsSelectorBuilderFactory(final Position relativeTo, final int... positions) {
        return new BuilderFactory<JoinPointSelectorImpl, MethodArgumentsSelectorImpl, MethodArgumentTypeSelectorImpl>() {
            @Override
            public MethodArgumentTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodArgumentsSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodArgumentTypeSelectorImpl(root, parent, compositePredicate, relativeTo, positions);
            }
        };
    }

    @Override
    public MethodArgumentTypeSelector argumentTypes(ListLogicalOperator listLogicalOperator) {
        return super.or(getMethodArgumentPositionsSelectorBuilderFactory(listLogicalOperator));
    }

    private BuilderFactory<JoinPointSelectorImpl, MethodArgumentsSelectorImpl, MethodArgumentTypeSelectorImpl>
    getMethodArgumentPositionsSelectorBuilderFactory(final ListLogicalOperator listLogicalOperator) {
        return new BuilderFactory<JoinPointSelectorImpl, MethodArgumentsSelectorImpl, MethodArgumentTypeSelectorImpl>() {
            @Override
            public MethodArgumentTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodArgumentsSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodArgumentTypeSelectorImpl(root, parent, compositePredicate, listLogicalOperator);
            }
        };
    }


    @Override
    @NotYetImplemented
    public MethodsArgumentAnnotationSelector argumentAnnotations(Position relativeTo, int... positions) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodsArgumentAnnotationSelector argumentAnnotations(ListLogicalOperator listLogicalOperator) {
        return null;
    }

    @Override
    public MethodArgumentsSelector or() {
        return super.or(getMethodArgumentsSelectorBuilderFactory());
    }

    @Override
    public MethodArgumentsSelector union() {
        return this.or();
    }

    @Override
    public MethodArgumentsSelector anyOf() {
        return this.or();
    }

    @Override
    public MethodArgumentsSelector and() {
        return super.and(getMethodArgumentsSelectorBuilderFactory());
    }

    @Override
    public MethodArgumentsSelector intersect() {
        return this.and();
    }

    @Override
    public MethodArgumentsSelector allOf() {
        return this.and();
    }

    @Override
    public MethodArgumentsSelector nor() {
        return super.nor(getMethodArgumentsSelectorBuilderFactory());
    }

    @Override
    public MethodArgumentsSelector noneOf() {
        return this.nor();
    }

    @Override
    public MethodArgumentsSelector neitherNor() {
        return this.nor();
    }

    @Override
    public MethodArgumentsSelector complement() {
        return this.nor();
    }

    @Override
    public MethodArgumentsSelector end() {
        return super.doEndLogicalExpression(MethodArgumentsSelector.class, false);
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodArgumentsSelectorImpl,MethodArgumentsSelectorImpl> getMethodArgumentsSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl,MethodArgumentsSelectorImpl,MethodArgumentsSelectorImpl>() {
            @Override
            public MethodArgumentsSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodArgumentsSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodArgumentsSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

}
