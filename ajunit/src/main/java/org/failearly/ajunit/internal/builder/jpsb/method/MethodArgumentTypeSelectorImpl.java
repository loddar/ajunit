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
    @NotYetImplemented
    public MethodArgumentTypeSelector byCollection() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector byMap() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector or() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector union() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector anyOf() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector and() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector intersect() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector allOf() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector nor() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector noneOf() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector neitherNor() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector complement() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector end() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector byArray() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector byArrayDimension(int dimension, DimensionComparator dimensionComparator) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byComponentType() {
        return null;
    }
}
