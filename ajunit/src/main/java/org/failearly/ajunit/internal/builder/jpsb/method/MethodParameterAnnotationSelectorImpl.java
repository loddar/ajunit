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

import org.failearly.ajunit.builder.method.MethodParameterAnnotationSelector;
import org.failearly.ajunit.builder.method.MethodParametersSelector;
import org.failearly.ajunit.builder.types.ListOperator;
import org.failearly.ajunit.builder.types.LogicalOperator;
import org.failearly.ajunit.builder.types.Position;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.AjUnitTypesPredicateFactory;
import org.failearly.ajunit.internal.builder.jpsb.helper.ParameterAnnotationSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

import java.lang.annotation.Annotation;

/**
 * MethodsArgumentAnnotationSelectorImpl is the implementation of  {@link org.failearly.ajunit.builder.method.MethodParameterAnnotationSelector}.
 */
final class MethodParameterAnnotationSelectorImpl
        extends JoinPointSelectorBuilderBase<MethodParameterAnnotationSelectorImpl,MethodParametersSelector>
        implements MethodParameterAnnotationSelector {


    private final ParameterAnnotationSelectorBuilder<MethodParameterAnnotationSelectorImpl> parameterAnnotationSelectorBuilder;


    private MethodParameterAnnotationSelectorImpl() {
        super(MethodParameterAnnotationSelectorImpl.class, MethodParametersSelector.class);
        parameterAnnotationSelectorBuilder = SelectorBuilders.createParameterAnnotationSelectorBuilder(this);
    }

    MethodParameterAnnotationSelectorImpl(
            JoinPointSelectorImpl root, MethodParametersSelectorImpl parent, CompositePredicate compositePredicate, ListOperator listOperator) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(
                        root, parent, this, createCompositeNode(compositePredicate, listOperator))
        );
    }

    public MethodParameterAnnotationSelectorImpl(
            JoinPointSelectorImpl root, MethodParametersSelectorImpl parent, CompositePredicate compositePredicate, Position relativeTo, int... positions) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(
                        root, parent, this, createCompositeNode(compositePredicate, ListOperator.EACH, relativeTo, positions))
        );
    }

    private static CompositePredicate createCompositeNode(
            CompositePredicate compositePredicate,
            ListOperator listOperator, Position relativeTo, int... positions) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.compose(
                        MethodTransformers.methodParameterAnnotationsType(),
                        AjUnitTypesPredicateFactory.createArgumentPositionTransformer(relativeTo, positions)
                ),
                AjUnitTypesPredicateFactory.createListLogicalOperator(listOperator, compositePredicate)
        );
    }

    private static CompositePredicate createCompositeNode(
            CompositePredicate compositePredicate,
            ListOperator listOperator) {
        return StandardPredicates.transformerPredicate(
                MethodTransformers.methodParameterAnnotationsType(),
                AjUnitTypesPredicateFactory.createListLogicalOperator(listOperator, compositePredicate)
        );
    }


     private MethodParameterAnnotationSelectorImpl(JoinPointSelectorImpl root, MethodParameterAnnotationSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(
                        root, parent, this, compositePredicate)
        );
    }

    @Override
    protected MethodParameterAnnotationSelectorImpl newInstance(JoinPointSelectorImpl root, MethodParameterAnnotationSelectorImpl parent, CompositePredicate compositePredicate) {
        return new MethodParameterAnnotationSelectorImpl(root, parent, compositePredicate);
    }

    @Override
    @SafeVarargs
    public final MethodParameterAnnotationSelector byParameterAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses) {
        return parameterAnnotationSelectorBuilder.byParameterAnnotations(logicalOperator, annotationClasses);
    }

    @Override
    public MethodParameterAnnotationSelector byExistingParameterAnnotation() {
        return parameterAnnotationSelectorBuilder.byAnyExistingParameterAnnotation();
    }

    @Override
    public MethodParameterAnnotationSelector byMissingParameterAnnotation() {
        return parameterAnnotationSelectorBuilder.byNotExistingParameterAnnotations();
    }

    @Override
    public MethodParametersSelector endParameterAnnotation() {
        return super.terminateSubSelector();
    }
}
