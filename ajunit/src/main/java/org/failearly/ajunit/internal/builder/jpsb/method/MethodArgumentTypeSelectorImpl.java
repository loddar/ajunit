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
import org.failearly.ajunit.builder.ListLogicalOperator;
import org.failearly.ajunit.builder.Position;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.builder.method.MethodArgumentComponentTypeSelector;
import org.failearly.ajunit.builder.method.MethodArgumentTypeSelector;
import org.failearly.ajunit.builder.method.MethodArgumentsSelector;
import org.failearly.ajunit.internal.annotation.NotYetImplemented;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

import java.lang.annotation.Annotation;

/**
 * MethodArgumentTypeSelectorImpl is responsible for ...
 */
final class MethodArgumentTypeSelectorImpl extends JoinPointBuilderBase<MethodArgumentTypeSelectorImpl>
        implements MethodArgumentTypeSelector {

    private ClassSelectorBuilder<MethodArgumentTypeSelectorImpl> methodArgumentTypeSelectorBuilder;

    private MethodArgumentTypeSelectorImpl() {
        methodArgumentTypeSelectorBuilder = SelectorBuilders.createMethodArgumentTypeSelectorBuilder(this);
    }

    MethodArgumentTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodArgumentsSelectorImpl parent,
            CompositePredicate compositePredicate,
            Position relativeTo,
            int... positions) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(
                root, parent, this, createCompositeNode(compositePredicate, ListLogicalOperator.ALL_OF, relativeTo, positions))
            );
    }

    private static CompositePredicate createCompositeNode(
            CompositePredicate compositePredicate,
            ListLogicalOperator listLogicalOperator, Position relativeTo, int... positions) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        JoinPointSelectorUtils.createArgumentPositionTransformer(relativeTo, positions)
                ),
                JoinPointSelectorUtils.createListLogicalOperator(listLogicalOperator, compositePredicate)
        );
    }

    MethodArgumentTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodArgumentsSelectorImpl parent,
            CompositePredicate compositePredicate,
            ListLogicalOperator listLogicalOperator) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(
                root, parent, this, JoinPointSelectorUtils.createListLogicalOperator(listLogicalOperator, compositePredicate))
            );
    }

    private MethodArgumentTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodArgumentTypeSelectorImpl parent,
            CompositePredicate compositePredicate) {
        this();
        super.init(LogicalStructureBuilder.createBuilder(
                root, parent, this, compositePredicate)
            );
    }

    @Override
    public MethodArgumentsSelector endArgumentType() {
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

    @Override
    public MethodArgumentTypeSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return methodArgumentTypeSelectorBuilder.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodArgumentTypeSelector byExtending(Class<?> baseClass) {
        return methodArgumentTypeSelectorBuilder.byExtending(baseClass);
    }

    @Override
    public MethodArgumentTypeSelector byNotExtending(Class<?> baseClass) {
        return methodArgumentTypeSelectorBuilder.byNotExtending(baseClass);
    }

    @Override
    public MethodArgumentTypeSelector byImplementingAnyOf(Class<?>... interfaces) {
        return methodArgumentTypeSelectorBuilder.byImplementingAnyOf(interfaces);
    }

    @Override
    public MethodArgumentTypeSelector byImplementingAllOf(Class<?>... interfaces) {
        return methodArgumentTypeSelectorBuilder.byImplementingAllOf(interfaces);
    }

    @Override
    public MethodArgumentTypeSelector byImplementingNoneOf(Class<?>... interfaces) {
        return methodArgumentTypeSelectorBuilder.byImplementingNoneOf(interfaces);
    }

    @Override
    public MethodArgumentTypeSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return methodArgumentTypeSelectorBuilder.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodArgumentTypeSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return methodArgumentTypeSelectorBuilder.byTypeAnnotation(annotationClass);
    }

    @Override
    public MethodArgumentTypeSelector byEnum() {
        return methodArgumentTypeSelectorBuilder.byEnum();
    }

    @Override
    public MethodArgumentTypeSelector byAnnotation() {
        return methodArgumentTypeSelectorBuilder.byAnnotation();
    }

    @Override
    public MethodArgumentTypeSelector byInterface() {
        return methodArgumentTypeSelectorBuilder.byInterface();
    }

    @Override
    public MethodArgumentTypeSelector byPrimitiveWrapperType() {
        return methodArgumentTypeSelectorBuilder.byPrimitiveWrapperType();
    }

    @Override
    public MethodArgumentTypeSelector byCollection() {
        return methodArgumentTypeSelectorBuilder.byCollection();
    }

    @Override
    public MethodArgumentTypeSelector byMap() {
        return methodArgumentTypeSelectorBuilder.byMap();
    }

    @Override
    public MethodArgumentTypeSelector byArray() {
        return methodArgumentTypeSelectorBuilder.byArray();
    }

    @Override
    public MethodArgumentTypeSelector byArrayDimension(int dimension, DimensionComparator dimensionComparator) {
        return methodArgumentTypeSelectorBuilder.byArrayDimension(dimension, dimensionComparator);
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byComponentType() {
        return null;
    }

    @Override
    public MethodArgumentTypeSelector or() {
        return super.or(getMethodArgumentTypeSelectorBuilderFactory());
    }

    @Override
    public MethodArgumentTypeSelector union() {
        return this.or();
    }

    @Override
    public MethodArgumentTypeSelector anyOf() {
        return this.or();
    }

    @Override
    public MethodArgumentTypeSelector and() {
        return super.and(getMethodArgumentTypeSelectorBuilderFactory());
    }

    @Override
    public MethodArgumentTypeSelector intersect() {
        return this.and();
    }

    @Override
    public MethodArgumentTypeSelector allOf() {
        return this.and();
    }

    @Override
    public MethodArgumentTypeSelector nor() {
        return super.nor(getMethodArgumentTypeSelectorBuilderFactory());
    }

    @Override
    public MethodArgumentTypeSelector noneOf() {
        return this.nor();
    }

    @Override
    public MethodArgumentTypeSelector neitherNor() {
        return this.nor();
    }

    @Override
    public MethodArgumentTypeSelector complement() {
        return this.nor();
    }

    @Override
    public MethodArgumentTypeSelector end() {
        return super.doEndLogicalExpression(MethodArgumentTypeSelector.class, false);
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodArgumentTypeSelectorImpl,MethodArgumentTypeSelectorImpl> getMethodArgumentTypeSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, MethodArgumentTypeSelectorImpl, MethodArgumentTypeSelectorImpl>() {
            @Override
            public MethodArgumentTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodArgumentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodArgumentTypeSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

}
