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

import org.failearly.ajunit.builder.ListOperator;
import org.failearly.ajunit.builder.NumberComparator;
import org.failearly.ajunit.builder.Position;
import org.failearly.ajunit.builder.method.MethodJoinPointSelector;
import org.failearly.ajunit.builder.method.MethodParameterAnnotationSelector;
import org.failearly.ajunit.builder.method.MethodParameterTypeSelector;
import org.failearly.ajunit.builder.method.MethodParametersSelector;
import org.failearly.ajunit.internal.annotation.NotYetImplemented;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.list.ListTransformers;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

/**
 * The implementation of {@link org.failearly.ajunit.builder.method.MethodParametersSelector}.
 *
 * @see org.failearly.ajunit.builder.method.MethodJoinPointSelector#arguments(org.failearly.ajunit.builder.LogicalOperator)
 */
final class MethodParametersSelectorImpl extends JoinPointSelectorBuilderBase<MethodParametersSelectorImpl,MethodJoinPointSelector> implements MethodParametersSelector {

    MethodParametersSelectorImpl(
            JoinPointSelectorImpl root,
            MethodJoinPointSelectorImpl parent,
            CompositePredicate compositePredicate) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(root, parent, this, createCompositeNode(compositePredicate)));
    }

    private static CompositePredicate createCompositeNode(CompositePredicate compositePredicate) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.compose(
                        AjpTransformers.method(),
                        MethodTransformers.methodParameters()
                ),
                compositePredicate
        );
    }

    private MethodParametersSelectorImpl(JoinPointSelectorImpl root, MethodParametersSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private MethodParametersSelectorImpl() {
        super(MethodParametersSelectorImpl.class, MethodJoinPointSelector.class);
    }

    @Override
    public MethodJoinPointSelector endParametersSelector() {
        return terminateSubSelector();
    }

    @Override
    public MethodParametersSelector byNoParameters() {
        return byNumberOfParameters(0, NumberComparator.EQUALS);
    }

    @Override
    public MethodParametersSelector byNumberOfParameters(int number, NumberComparator numberComparator) {
        super.addPredicate(
                StandardPredicates.transformerPredicate(
                        ListTransformers.size(),
                        JoinPointSelectorUtils.createNumberComparatorPredicate(number, numberComparator)
                )
        );
        return this;
    }

    @Override
    public MethodParameterTypeSelector parameterTypes(Position relativeTo, int... positions) {
        return super.or(getMethodArgumentPositionsSelectorBuilderFactory(relativeTo, positions));
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodParametersSelectorImpl,MethodParameterTypeSelectorImpl>
    getMethodArgumentPositionsSelectorBuilderFactory(final Position relativeTo, final int... positions) {
        return new BuilderFactory<JoinPointSelectorImpl, MethodParametersSelectorImpl, MethodParameterTypeSelectorImpl>() {
            @Override
            public MethodParameterTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodParametersSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodParameterTypeSelectorImpl(root, parent, compositePredicate, relativeTo, positions);
            }
        };
    }

    @Override
    public MethodParameterTypeSelector parameterTypes(ListOperator listOperator) {
        return super.or(getMethodArgumentPositionsSelectorBuilderFactory(listOperator));
    }

    private BuilderFactory<JoinPointSelectorImpl, MethodParametersSelectorImpl, MethodParameterTypeSelectorImpl>
    getMethodArgumentPositionsSelectorBuilderFactory(final ListOperator listOperator) {
        return new BuilderFactory<JoinPointSelectorImpl, MethodParametersSelectorImpl, MethodParameterTypeSelectorImpl>() {
            @Override
            public MethodParameterTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodParametersSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodParameterTypeSelectorImpl(root, parent, compositePredicate, listOperator);
            }
        };
    }


    @Override
    @NotYetImplemented
    public MethodParameterAnnotationSelector parameterAnnotations(Position relativeTo, int... positions) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodParameterAnnotationSelector parameterAnnotations(ListOperator listOperator) {
        return super.and(getMethodArgumentAnnotationSelectorBuilderFactory(listOperator));
    }

    private BuilderFactory<JoinPointSelectorImpl, MethodParametersSelectorImpl, MethodParameterAnnotationSelectorImpl>
    getMethodArgumentAnnotationSelectorBuilderFactory(final ListOperator listOperator) {
        return new BuilderFactory<JoinPointSelectorImpl, MethodParametersSelectorImpl, MethodParameterAnnotationSelectorImpl>() {
            @Override
            public MethodParameterAnnotationSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodParametersSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodParameterAnnotationSelectorImpl(root, parent, compositePredicate, listOperator);
            }
        };
    }


    @Override
    protected final MethodParametersSelectorImpl newInstance(JoinPointSelectorImpl root, MethodParametersSelectorImpl parent, CompositePredicate compositePredicate) {
        return new MethodParametersSelectorImpl(root, parent, compositePredicate);
    }
}
