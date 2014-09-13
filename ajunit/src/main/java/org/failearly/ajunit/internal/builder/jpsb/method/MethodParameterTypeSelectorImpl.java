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

import org.failearly.ajunit.builder.DimensionComparator;
import org.failearly.ajunit.builder.ListOperator;
import org.failearly.ajunit.builder.Position;
import org.failearly.ajunit.builder.StringMatcher;
import org.failearly.ajunit.builder.method.MethodParameterComponentTypeSelector;
import org.failearly.ajunit.builder.method.MethodParameterTypeSelector;
import org.failearly.ajunit.builder.method.MethodParametersSelector;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

import java.lang.annotation.Annotation;

/**
 * MethodArgumentTypeSelectorImpl is responsible for ...
 */
final class MethodParameterTypeSelectorImpl extends JoinPointSelectorBuilderBase<MethodParameterTypeSelectorImpl,MethodParametersSelector>
        implements MethodParameterTypeSelector {

    private ClassSelectorBuilder<MethodParameterTypeSelectorImpl> methodArgumentTypeSelectorBuilder;

    private MethodParameterTypeSelectorImpl() {
        super(MethodParameterTypeSelectorImpl.class, MethodParametersSelector.class);
        methodArgumentTypeSelectorBuilder = SelectorBuilders.createMethodArgumentTypeSelectorBuilder(this);
    }

    MethodParameterTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodParametersSelectorImpl parent,
            CompositePredicate compositePredicate,
            Position relativeTo,
            int... positions) {
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
                        MethodTransformers.methodParameters(),
                        JoinPointSelectorUtils.createArgumentPositionTransformer(relativeTo, positions)
                ),
                JoinPointSelectorUtils.createListLogicalOperator(listOperator, compositePredicate)
        );
    }

    MethodParameterTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodParametersSelectorImpl parent,
            CompositePredicate compositePredicate,
            ListOperator listOperator) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(
                root, parent, this, createCompositeNode(compositePredicate, listOperator))
            );
    }

    private static CompositePredicate createCompositeNode(
            CompositePredicate compositePredicate,
            ListOperator listOperator) {
        return StandardPredicates.transformerPredicate(
                MethodTransformers.methodParameters(),
                JoinPointSelectorUtils.createListLogicalOperator(listOperator, compositePredicate)
        );
    }

    private MethodParameterTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodParameterTypeSelectorImpl parent,
            CompositePredicate compositePredicate) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    @Override
    public MethodParametersSelector endParameterType() {
        return terminateSubSelector();
    }

    @Override
    public MethodParameterTypeSelector byClass(Class<?> argumentClass) {
        return methodArgumentTypeSelectorBuilder.byClass(argumentClass);
    }

    @Override
    public MethodParameterTypeSelector byPrimitive() {
        return methodArgumentTypeSelectorBuilder.byPrimitive();
    }

    @Override
    public MethodParameterTypeSelector byClassName(String classNamePattern, StringMatcher matcherType) {
        return methodArgumentTypeSelectorBuilder.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodParameterTypeSelector byExtending(Class<?> baseClass) {
        return methodArgumentTypeSelectorBuilder.byExtending(baseClass);
    }

    @Override
    public MethodParameterTypeSelector byNotExtending(Class<?> baseClass) {
        return methodArgumentTypeSelectorBuilder.byNotExtending(baseClass);
    }

    @Override
    public MethodParameterTypeSelector byImplementingAnyOf(Class<?>... interfaces) {
        return methodArgumentTypeSelectorBuilder.byImplementingAnyOf(interfaces);
    }

    @Override
    public MethodParameterTypeSelector byImplementingAllOf(Class<?>... interfaces) {
        return methodArgumentTypeSelectorBuilder.byImplementingAllOf(interfaces);
    }

    @Override
    public MethodParameterTypeSelector byImplementingNoneOf(Class<?>... interfaces) {
        return methodArgumentTypeSelectorBuilder.byImplementingNoneOf(interfaces);
    }

    @Override
    public MethodParameterTypeSelector byPackageName(String packageNamePattern, StringMatcher matcherType) {
        return methodArgumentTypeSelectorBuilder.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodParameterTypeSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return methodArgumentTypeSelectorBuilder.byTypeAnnotation(annotationClass);
    }

    @Override
    public MethodParameterTypeSelector byEnum() {
        return methodArgumentTypeSelectorBuilder.byEnum();
    }

    @Override
    public MethodParameterTypeSelector byAnnotation() {
        return methodArgumentTypeSelectorBuilder.byAnnotation();
    }

    @Override
    public MethodParameterTypeSelector byInterface() {
        return methodArgumentTypeSelectorBuilder.byInterface();
    }

    @Override
    public MethodParameterTypeSelector byPrimitiveWrapperType() {
        return methodArgumentTypeSelectorBuilder.byPrimitiveWrapperType();
    }

    @Override
    public MethodParameterTypeSelector byCollection() {
        return methodArgumentTypeSelectorBuilder.byCollection();
    }

    @Override
    public MethodParameterTypeSelector byMap() {
        return methodArgumentTypeSelectorBuilder.byMap();
    }

    @Override
    public MethodParameterTypeSelector byArray() {
        return methodArgumentTypeSelectorBuilder.byArray();
    }

    @Override
    public MethodParameterTypeSelector byArrayDimension(int dimension, DimensionComparator dimensionComparator) {
        return methodArgumentTypeSelectorBuilder.byArrayDimension(dimension, dimensionComparator);
    }

    @Override
    public MethodParameterComponentTypeSelector componentType() {
        return super.and(getMethodArgumentComponentTypeSelectorBuilderFactory());
    }

    private BuilderFactory<JoinPointSelectorImpl, MethodParameterTypeSelectorImpl, MethodParameterComponentTypeSelectorImpl>
    getMethodArgumentComponentTypeSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, MethodParameterTypeSelectorImpl, MethodParameterComponentTypeSelectorImpl>() {
            @Override
            public MethodParameterComponentTypeSelectorImpl createBuilder(
                                                                    JoinPointSelectorImpl root,
                                                                    MethodParameterTypeSelectorImpl parent,
                                                                    CompositePredicate compositePredicate) {
                return new MethodParameterComponentTypeSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

    @Override
    protected final MethodParameterTypeSelectorImpl newInstance(JoinPointSelectorImpl root, MethodParameterTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        return new MethodParameterTypeSelectorImpl(root, parent, compositePredicate);
    }
}
