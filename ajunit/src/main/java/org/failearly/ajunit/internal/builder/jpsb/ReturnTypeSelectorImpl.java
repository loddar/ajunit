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

import org.failearly.ajunit.builder.*;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;

import java.lang.annotation.Annotation;

/**
 * The implementation of {@link org.failearly.ajunit.builder.ReturnTypeSelector}.
 */
final class ReturnTypeSelectorImpl
        extends JoinPointBuilderBase<ReturnTypeSelectorImpl>
        implements ReturnTypeSelector {

    private final ClassSelectorBuilder<ReturnTypeSelectorImpl> returnTypeSelector;

    ReturnTypeSelectorImpl(
            JoinPointSelectorImpl root, MethodJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private ReturnTypeSelectorImpl(
            JoinPointSelectorImpl root, ReturnTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private ReturnTypeSelectorImpl() {
        this.returnTypeSelector = SelectorBuilders.createReturnTypeSelectorBuilder(this);
    }

    @Override
    public MethodJoinPointSelector endReturnType() {
        return super.doEndLogicalExpression(MethodJoinPointSelector.class, true);
    }

    @Override
    public ReturnTypeSelector byClass(Class<?> classType) {
        return returnTypeSelector.byClass(classType);
    }

    @Override
    public ReturnTypeSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return returnTypeSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public ReturnTypeSelector byExtending(Class<?> baseClass) {
        return returnTypeSelector.byExtending(baseClass);
    }

    @Override
    public ReturnTypeSelector byNotExtending(Class<?> baseClass) {
        return returnTypeSelector.byNotExtending(baseClass);
    }

    @Override
    public ReturnTypeSelector byImplementingAnyOf(Class<?>... interfaces) {
        return returnTypeSelector.byImplementingAnyOf(interfaces);
    }

    @Override
    public ReturnTypeSelector byImplementingAllOf(Class<?>... interfaces) {
        return returnTypeSelector.byImplementingAllOf(interfaces);
    }

    @Override
    public ReturnTypeSelector byImplementingNoneOf(Class<?>... interfaces) {
        return returnTypeSelector.byImplementingNoneOf(interfaces);
    }

    @Override
    public ReturnTypeSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return returnTypeSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public ReturnTypeSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return null;
    }

    @Override
    public ReturnTypeSelector byTypeAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses) {
        return null;
    }

    @Override
    @SafeVarargs
    public final ReturnTypeSelector byAnyTypeAnnotations(Class<? extends Annotation>... annotations) {
        return null;
    }

    @Override
    public ReturnTypeSelector byAllTypeAnnotations(Class<? extends Annotation>... annotations) {
        return null;
    }

    @Override
    public ReturnTypeSelector byNoneOfTypeAnnotations(Class<? extends Annotation>... annotationClasses) {
        return null;
    }

    @Override
    public ReturnTypeSelector byArray() {
        return this.returnTypeSelector.byArray();
    }

    @Override
    public ReturnTypeSelector byArrayDimension(int dimension, DimensionComparator dimensionComparator) {
        return returnTypeSelector.byArrayDimension(dimension, dimensionComparator);
    }

    @Override
    public ReturnComponentTypeSelector byComponentType() {
        return super.and(
                getReturnComponentTypeSelectorBuilderFactory(this.returnTypeSelector)
        );
    }

    @Override
    public ReturnTypeSelector byVoid() {
        return returnTypeSelector.byVoid();
    }

    @Override
    public ReturnTypeSelector byPrimitive() {
        return returnTypeSelector.byPrimitive();
    }

    @Override
    public ReturnTypeSelector byEnum() {
        return returnTypeSelector.byEnum();
    }

    @Override
    public ReturnTypeSelector byAnnotation() {
        return returnTypeSelector.byAnnotation();
    }

    @Override
    public ReturnTypeSelector byInterface() {
        return returnTypeSelector.byInterface();
    }

    @Override
    public ReturnTypeSelector byPrimitiveWrapperType() {
        return returnTypeSelector.byPrimitiveWrapperType();
    }

    @Override
    public ReturnTypeSelector byCollection() {
        return returnTypeSelector.byCollection();
    }

    @Override
    public ReturnTypeSelector byMap() {
        return returnTypeSelector.byMap();
    }

    @Override
    public ReturnTypeSelector or() {
        return super.or(getReturnTypeSelectorBuilderFactory());
    }

    @Override
    public ReturnTypeSelector union() {
        return this.or();
    }

    @Override
    public ReturnTypeSelector anyOf() {
        return this.or();
    }

    @Override
    public ReturnTypeSelector and() {
        return super.and(getReturnTypeSelectorBuilderFactory());
    }

    @Override
    public ReturnTypeSelector intersect() {
        return this.and();
    }

    @Override
    public ReturnTypeSelector allOf() {
        return this.and();
    }

    @Override
    public ReturnTypeSelector nor() {
        return super.nor(getReturnTypeSelectorBuilderFactory());
    }

    @Override
    public ReturnTypeSelector noneOf() {
        return this.nor();
    }

    @Override
    public ReturnTypeSelector neitherNor() {
        return this.nor();
    }

    @Override
    public ReturnTypeSelector complement() {
        return this.nor();
    }

    @Override
    public ReturnTypeSelector end() {
        return super.doEndLogicalExpression(ReturnTypeSelector.class, false);
    }

    private static BuilderFactory<JoinPointSelectorImpl,ReturnTypeSelectorImpl,ReturnComponentTypeSelectorImpl>
        getReturnComponentTypeSelectorBuilderFactory(
            final ClassSelectorBuilder<ReturnTypeSelectorImpl> returnTypeSelector
    ) {
        return new BuilderFactory<JoinPointSelectorImpl, ReturnTypeSelectorImpl, ReturnComponentTypeSelectorImpl>() {
            @Override
            public ReturnComponentTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, ReturnTypeSelectorImpl parent, CompositePredicate compositePredicate) {
                return new ReturnComponentTypeSelectorImpl(root, parent, compositePredicate, returnTypeSelector);
            }
        };
    }

    private static BuilderFactory<JoinPointSelectorImpl,ReturnTypeSelectorImpl,ReturnTypeSelectorImpl>
        getReturnTypeSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, ReturnTypeSelectorImpl, ReturnTypeSelectorImpl>() {
            @Override
            public ReturnTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, ReturnTypeSelectorImpl parent, CompositePredicate compositePredicate) {
                return new ReturnTypeSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

}
