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

import org.failearly.ajunit.builder.*;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;

import java.lang.annotation.Annotation;

/**
 * The implementation of {@link org.failearly.ajunit.builder.MethodExceptionTypeSelector}.
 */
final class MethodExceptionTypeSelectorImpl
        extends JoinPointBuilderBase<MethodExceptionTypeSelectorImpl>
        implements MethodExceptionTypeSelector {


    private final ClassSelectorBuilder<MethodExceptionTypeSelectorImpl> methodExceptionTypeSelector;


    MethodExceptionTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodJoinPointSelectorImpl parent,
            CompositePredicate compositePredicate,
            ListLogicalOperator listLogicalOperator) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, createCompositeNode(compositePredicate, listLogicalOperator)));
    }

    private MethodExceptionTypeSelectorImpl(JoinPointSelectorImpl root, MethodExceptionTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private MethodExceptionTypeSelectorImpl() {
        this.methodExceptionTypeSelector = SelectorBuilders.createMethodExceptionTypeSelector(this);
    }

    private static CompositePredicate createCompositeNode(CompositePredicate compositePredicate, ListLogicalOperator listLogicalOperator) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                    AjpTransformers.methodTransformer(),
                    MethodTransformers.methodExceptionsTransformer()
                ),
                JoinPointSelectorUtils.createListLogicalOperator(listLogicalOperator, compositePredicate)
        );
    }

    @Override
    public MethodJoinPointSelector endExceptionTypes() {
        return super.doEndLogicalExpression(MethodJoinPointSelector.class, true);
    }

    @Override
    public MethodExceptionTypeSelector byRuntimeException() {
        return this.byExtending(RuntimeException.class);
    }

    @Override
    public MethodExceptionTypeSelector byError() {
        return this.byExtending(Error.class);
    }

    @Override
    public MethodExceptionTypeSelector byCheckedException() {
        return this.and()
                    .byExtending(Exception.class)
                    .nor()
                        .byExtending(RuntimeException.class)
                    .end()
               .end();
    }

    @Override
    public MethodExceptionTypeSelector byClass(Class<?> classType) {
        return methodExceptionTypeSelector.byClass(classType);
    }

    @Override
    public MethodExceptionTypeSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return methodExceptionTypeSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodExceptionTypeSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return methodExceptionTypeSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodExceptionTypeSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return null;
    }

    @Override
    public MethodExceptionTypeSelector byTypeAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses) {
        return null;
    }

    @Override
    @SafeVarargs
    public final MethodExceptionTypeSelector byAnyTypeAnnotations(Class<? extends Annotation>... annotations) {
        return null;
    }

    @Override
    public MethodExceptionTypeSelector byAllTypeAnnotations(Class<? extends Annotation>... annotations) {
        return null;
    }

    @Override
    public MethodExceptionTypeSelector byNoneOfTypeAnnotations(Class<? extends Annotation>... annotationClasses) {
        return null;
    }

    @Override
    public MethodExceptionTypeSelector byExtending(Class<?> baseClass) {
        return methodExceptionTypeSelector.byExtending(baseClass);
    }

    @Override
    public MethodExceptionTypeSelector byNotExtending(Class<?> baseClass) {
        return methodExceptionTypeSelector.byNotExtending(baseClass);
    }

    @Override
    public MethodExceptionTypeSelector byImplementingAnyOf(Class<?>... interfaces) {
        return methodExceptionTypeSelector.byImplementingAnyOf(interfaces);
    }

    @Override
    public MethodExceptionTypeSelector byImplementingAllOf(Class<?>... interfaces) {
        return methodExceptionTypeSelector.byImplementingAllOf(interfaces);
    }

    @Override
    public MethodExceptionTypeSelector byImplementingNoneOf(Class<?>... interfaces) {
        return methodExceptionTypeSelector.byImplementingNoneOf(interfaces);
    }

    @Override
    public MethodExceptionTypeSelector or() {
        return super.or(getMethodExceptionTypeSelectorBuilderFactory());
    }

    @Override
    public MethodExceptionTypeSelector union() {
        return this.or();
    }

    @Override
    public MethodExceptionTypeSelector anyOf() {
        return this.or();
    }

    @Override
    public MethodExceptionTypeSelector and() {
        return super.and(getMethodExceptionTypeSelectorBuilderFactory());
    }

    @Override
    public MethodExceptionTypeSelector intersect() {
        return this.and();
    }

    @Override
    public MethodExceptionTypeSelector allOf() {
        return this.and();
    }

    @Override
    public MethodExceptionTypeSelector nor() {
        return super.nor(getMethodExceptionTypeSelectorBuilderFactory());
    }

    @Override
    public MethodExceptionTypeSelector noneOf() {
        return this.nor();
    }

    @Override
    public MethodExceptionTypeSelector neitherNor() {
        return this.nor();
    }

    @Override
    public MethodExceptionTypeSelector complement() {
        return this.nor();
    }

    @Override
    public MethodExceptionTypeSelector end() {
        return super.doEndLogicalExpression(MethodExceptionTypeSelector.class, false);
    }

    private static BuilderFactory<JoinPointSelectorImpl,MethodExceptionTypeSelectorImpl,MethodExceptionTypeSelectorImpl>
        getMethodExceptionTypeSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, MethodExceptionTypeSelectorImpl, MethodExceptionTypeSelectorImpl>() {
            @Override
            public MethodExceptionTypeSelectorImpl createBuilder(
                    JoinPointSelectorImpl root, MethodExceptionTypeSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodExceptionTypeSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

}
