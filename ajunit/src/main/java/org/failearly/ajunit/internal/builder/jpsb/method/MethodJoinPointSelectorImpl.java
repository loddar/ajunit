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

import org.failearly.ajunit.builder.JoinPointSelector;
import org.failearly.ajunit.builder.method.MethodExceptionTypeSelector;
import org.failearly.ajunit.builder.method.MethodJoinPointSelector;
import org.failearly.ajunit.builder.method.MethodParametersSelector;
import org.failearly.ajunit.builder.method.ReturnTypeSelector;
import org.failearly.ajunit.builder.types.ListOperator;
import org.failearly.ajunit.builder.types.LogicalOperator;
import org.failearly.ajunit.builder.types.StringMatcher;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.MethodSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.modifier.AccessModifier;
import org.failearly.ajunit.modifier.MethodModifier;

import java.lang.annotation.Annotation;

/**
 * MethodJoinPointSelectorImpl is the implementation of {@link org.failearly.ajunit.builder.method.MethodJoinPointSelector}.
 */
public final class MethodJoinPointSelectorImpl
        extends JoinPointSelectorBuilderBase<MethodJoinPointSelectorImpl,JoinPointSelector> implements MethodJoinPointSelector {

    private final MethodSelectorBuilder<MethodJoinPointSelectorImpl> methodSelector;
    private final ClassSelectorBuilder<MethodJoinPointSelectorImpl>  declaringClassSelector;

    public MethodJoinPointSelectorImpl(
            JoinPointSelectorImpl root,
            JoinPointSelectorImpl parent,
            CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        this.anyMethod();
    }

    private MethodJoinPointSelectorImpl(
            JoinPointSelectorImpl root,
            MethodJoinPointSelectorImpl parent,
            CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private MethodJoinPointSelectorImpl() {
        super(MethodJoinPointSelectorImpl.class, JoinPointSelector.class);
        this.methodSelector = SelectorBuilders.createMethodSelectorBuilder(this);
        this.declaringClassSelector = SelectorBuilders.createDeclaringClassSelectorBuilder(this);
    }



    @Override
    public JoinPointSelector endMethod() {
        return terminateSubSelector();
    }

    private MethodJoinPointSelector anyMethod() {
        return null; // this.methodSelector.anyMethod();
    }

    @Override
    public MethodJoinPointSelector byName(String methodNamePattern, StringMatcher matcherType) {
        return methodSelector.byName(methodNamePattern, matcherType);
    }

    @Override
    public MethodJoinPointSelector byAnyOfAccessModifiers(AccessModifier... accessModifiers) {
        return methodSelector.byAnyOfAccessModifiers(accessModifiers);
    }

    @Override
    public MethodJoinPointSelector byNoneOfAccessModifiers(AccessModifier... accessModifiers) {
        return methodSelector.byNoneOfAccessModifiers(accessModifiers);
    }

    @Override
    public MethodJoinPointSelector byAnyOfMethodModifiers(MethodModifier... methodModifiers) {
        return methodSelector.byAnyOfMethodModifiers(methodModifiers);
    }

    @Override
    public MethodJoinPointSelector byNoneOfMethodModifiers(MethodModifier... methodModifiers) {
        return methodSelector.byNoneOfMethodModifiers(methodModifiers);
    }

    @Override
    public MethodJoinPointSelector byMethodAnnotation(Class<? extends Annotation> annotationClass) {
        return methodSelector.byMethodAnnotation(annotationClass);
    }

    @Override
    @SafeVarargs
    public final MethodJoinPointSelector byMethodAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses) {
        return methodSelector.byMethodAnnotations(logicalOperator, annotationClasses);
    }

    @Override
    public MethodJoinPointSelector byClass(Class<?> classType) {
        return this.declaringClassSelector.byClass(classType);
    }

    @Override
    public MethodJoinPointSelector byDeclaringClass(Class<?> declaringClass) {
        return byClass(declaringClass);
    }

    @Override
    public MethodJoinPointSelector byDeclaringClassName(String classNamePattern, StringMatcher matcherType) {
        return byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodJoinPointSelector byClassName(String classNamePattern, StringMatcher matcherType) {
        return this.declaringClassSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodJoinPointSelector byExtending(Class<?> baseClass) {
        return this.declaringClassSelector.byExtending(baseClass);
    }

    @Override
    public MethodJoinPointSelector byNotExtending(Class<?> baseClass) {
        return this.declaringClassSelector.byNotExtending(baseClass);
    }

    @Override
    public MethodJoinPointSelector byImplementingAnyOf(Class<?>... interfaces) {
        return this.declaringClassSelector.byImplementingAnyOf(interfaces);
    }

    @Override
    public MethodJoinPointSelector byImplementingAllOf(Class<?>... interfaces) {
        return this.declaringClassSelector.byImplementingAllOf(interfaces);
    }

    @Override
    public MethodJoinPointSelector byImplementingNoneOf(Class<?>... interfaces) {
        return this.declaringClassSelector.byImplementingNoneOf(interfaces);
    }

    @Override
    public MethodJoinPointSelector byPackageName(String packageNamePattern, StringMatcher matcherType) {
        return this.declaringClassSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodJoinPointSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return this.declaringClassSelector.byTypeAnnotation(annotationClass);
    }

    @Override
    @SafeVarargs
    public final MethodJoinPointSelector byTypeAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses) {
        return this.declaringClassSelector.byTypeAnnotations(logicalOperator, annotationClasses);
    }

    @Override
    public ReturnTypeSelector returnType() {
        return super.and(getReturnTypeSelectorBuilderFactory());
    }

    @Override
    public MethodExceptionTypeSelector exceptionTypes(ListOperator listOperator) {
        return super.or(getMethodExceptionTypeSelectorBuilderFactory(listOperator));
    }

    @Override
    public MethodJoinPointSelector byReturningVoid() {
        return returnType()
                .byVoid()
                .endReturnType();
    }

    @Override
    public MethodJoinPointSelector byReturning(Class<?> returnType) {
        return returnType()
                .byClass(returnType)
                .endReturnType();
    }

    @Override
    public MethodJoinPointSelector byReturningPrimitive() {
        return returnType()
                .byPrimitive()
                .endReturnType();
    }

    @Override
    public MethodJoinPointSelector byReturningEnum() {
        return returnType()
                .byEnum()
                .endReturnType();
    }


    @Override
    public MethodJoinPointSelector byReturningArray() {
        return returnType()
                .byArray()
                .endReturnType();
    }

    @Override
    public MethodJoinPointSelector byReturningCollection() {
        return returnType()
                .byCollection()
                .endReturnType();
    }

    @Override
    public MethodParametersSelector parameters() {
        return super.and(
                getMethodArgumentsSelectorBuilderFactory()
        );
    }

    @Override
    protected MethodJoinPointSelectorImpl newInstance(JoinPointSelectorImpl root, MethodJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
        return new MethodJoinPointSelectorImpl(root, parent, compositePredicate);
    }

    private static
    BuilderFactory<JoinPointSelectorImpl,MethodJoinPointSelectorImpl,ReturnTypeSelectorImpl>
        getReturnTypeSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl,MethodJoinPointSelectorImpl,ReturnTypeSelectorImpl>() {
            @Override
            public ReturnTypeSelectorImpl createBuilder(
                        JoinPointSelectorImpl root, MethodJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
                return new ReturnTypeSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

    private static
    BuilderFactory<JoinPointSelectorImpl,MethodJoinPointSelectorImpl,MethodExceptionTypeSelectorImpl>
        getMethodExceptionTypeSelectorBuilderFactory(final ListOperator listOperator) {
        return new BuilderFactory<JoinPointSelectorImpl, MethodJoinPointSelectorImpl, MethodExceptionTypeSelectorImpl>() {
            @Override
            public MethodExceptionTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodExceptionTypeSelectorImpl(root, parent, compositePredicate, listOperator);
            }
        };
    }

    private static
    BuilderFactory<JoinPointSelectorImpl,MethodJoinPointSelectorImpl,MethodParametersSelectorImpl>
        getMethodArgumentsSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, MethodJoinPointSelectorImpl, MethodParametersSelectorImpl>() {
            @Override
            public MethodParametersSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodParametersSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

}
