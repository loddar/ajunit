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
import org.failearly.ajunit.internal.builder.jpsb.helper.MethodSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.modifier.AccessModifier;
import org.failearly.ajunit.modifier.MethodModifier;

/**
 * MethodCallJoinPointSelectorImpl is the implementation of {@link org.failearly.ajunit.builder.MethodCallJoinPointSelector}.
 */
final class MethodCallJoinPointSelectorImpl
        extends MethodJoinPointSelectorBase<MethodCallJoinPointSelectorImpl>
        implements MethodCallJoinPointSelector {

    private static final AjJoinPointType JOIN_POINT_TYPE=AjJoinPointType.METHOD_CALL;

    private final MethodSelectorBuilder<MethodCallJoinPointSelectorImpl> methodSelector;
    private final ClassSelectorBuilder<MethodCallJoinPointSelectorImpl>  declaringClassSelector;

    MethodCallJoinPointSelectorImpl(
            JoinPointSelectorImpl root,
            JoinPointSelectorImpl parent,
            CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        this.anyMethod();
    }

    private MethodCallJoinPointSelectorImpl(JoinPointSelectorImpl root, MethodCallJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }


    private MethodCallJoinPointSelectorImpl() {
        super(MethodCallJoinPointSelectorImpl.class);
        this.methodSelector = SelectorBuilders.createMethodSelectorBuilder(this, JOIN_POINT_TYPE);
        this.declaringClassSelector = SelectorBuilders.createDeclaringClassSelectorBuilder(this, JOIN_POINT_TYPE);
    }

    @Override
    public JoinPointSelector endMethod() {
        return super.doEndLogicalExpression(JoinPointSelector.class, true);
    }

    private MethodCallJoinPointSelector anyMethod() {
        return this.methodSelector.anyMethod();
    }

    @Override
    public MethodCallJoinPointSelector byName(String methodNamePattern, StringMatcherType matcherType) {
        return methodSelector.byName(methodNamePattern, matcherType);
    }

    @Override
    public MethodCallJoinPointSelector byAnyOfAccessModifiers(AccessModifier... accessModifiers) {
        return methodSelector.byAnyOfAccessModifiers(accessModifiers);
    }

    @Override
    public MethodCallJoinPointSelector byNoneOfAccessModifiers(AccessModifier... accessModifiers) {
        return methodSelector.byNoneOfAccessModifiers(accessModifiers);
    }

    @Override
    public MethodCallJoinPointSelector byAnyOfMethodModifiers(MethodModifier... methodModifiers) {
        return methodSelector.byAnyOfMethodModifiers(methodModifiers);
    }

    @Override
    public MethodCallJoinPointSelector byNoneOfMethodModifiers(MethodModifier... methodModifiers) {
        return methodSelector.byNoneOfMethodModifiers(methodModifiers);
    }

    @Override
    public MethodCallJoinPointSelector byClass(Class<?> classType) {
        return this.declaringClassSelector.byClass(classType);
    }

    @Override
    public MethodCallJoinPointSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return this.declaringClassSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodCallJoinPointSelector byDeclaringClass(Class<?> declaringClass) {
        return byClass(declaringClass);
    }

    @Override
    public MethodCallJoinPointSelector byDeclaringClassName(String classNamePattern, StringMatcherType matcherType) {
        return byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodCallJoinPointSelector byExtending(Class<?> baseClass) {
        return this.declaringClassSelector.byExtending(baseClass);
    }

    @Override
    public MethodCallJoinPointSelector byImplementingAnyOf(Class<?>... interfaces) {
        return this.declaringClassSelector.byImplementingAnyOf(interfaces);
    }

    @Override
    public MethodCallJoinPointSelector byImplementingAllOf(Class<?>... interfaces) {
        return this.declaringClassSelector.byImplementingAllOf(interfaces);
    }

    @Override
    public MethodCallJoinPointSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return this.declaringClassSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodCallJoinPointSelector or() {
        return super.or(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodCallJoinPointSelector union() {
        return or();
    }

    @Override
    public MethodCallJoinPointSelector anyOf() {
        return or();
    }

    @Override
    public MethodCallJoinPointSelector and() {
        return super.and(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodCallJoinPointSelector intersect() {
        return and();
    }

    @Override
    public MethodCallJoinPointSelector allOf() {
        return and();
    }

    @Override
    public MethodCallJoinPointSelector nor() {
        return super.nor(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodCallJoinPointSelector noneOf() {
        return nor();
    }

    @Override
    public MethodCallJoinPointSelector neitherNor() {
        return nor();
    }

    @Override
    public MethodCallJoinPointSelector complement() {
        return nor();
    }

    @Override
    public ReturnTypeSelector<MethodCallJoinPointSelector> byReturnType(LogicalOperator logicalOperator) {
        return null;
    }

    @Override
    public MethodCallJoinPointSelector end() {
        return super.doEndLogicalExpression(MethodCallJoinPointSelector.class, false);
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodCallJoinPointSelectorImpl,MethodCallJoinPointSelectorImpl>
    getMethodJoinPointSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, MethodCallJoinPointSelectorImpl, MethodCallJoinPointSelectorImpl>() {
            @Override
            public MethodCallJoinPointSelectorImpl createBuilder(JoinPointSelectorImpl root, MethodCallJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodCallJoinPointSelectorImpl(root, parent, compositePredicate);
            }
        };
    }
}
