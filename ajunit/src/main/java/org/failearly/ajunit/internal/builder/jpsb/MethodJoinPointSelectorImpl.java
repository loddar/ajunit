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
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.builder.jpsb.helper.MethodSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.modifier.AccessModifier;
import org.failearly.ajunit.modifier.MethodModifier;

/**
 * MethodJoinPointSelectorImpl is the implementation of {@link org.failearly.ajunit.builder.MethodJoinPointSelector}.
 */
final class MethodJoinPointSelectorImpl
        extends BuilderBase<JoinPointSelectorImpl, MethodJoinPointSelectorImpl> implements MethodJoinPointSelector {

    private final MethodSelectorBuilder<MethodJoinPointSelectorImpl> methodSelector;
    private final ClassSelectorBuilder<MethodJoinPointSelectorImpl>  declaringClassSelector;
    private final AjJoinPointType joinPointType;

    MethodJoinPointSelectorImpl(
            JoinPointSelectorImpl root,
            JoinPointSelectorImpl parent,
            CompositePredicate compositePredicate,
            AjJoinPointType joinPointType) {
        this(joinPointType);
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        this.anyMethod();
    }

    private MethodJoinPointSelectorImpl(
                JoinPointSelectorImpl root,
                MethodJoinPointSelectorImpl parent,
                CompositePredicate compositePredicate,
                AjJoinPointType joinPointType) {
        this(joinPointType);
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private MethodJoinPointSelectorImpl(AjJoinPointType joinPointType) {
        this.joinPointType = joinPointType;
        this.methodSelector = SelectorBuilders.createMethodSelectorBuilder(this, joinPointType);
        this.declaringClassSelector = SelectorBuilders.createDeclaringClassSelectorBuilder(this, joinPointType);
    }

    @Override
    public JoinPointSelector endMethod() {
        return super.doEndLogicalExpression(JoinPointSelector.class, true);
    }

    private MethodJoinPointSelector anyMethod() {
        return this.methodSelector.anyMethod();
    }

    @Override
    public MethodJoinPointSelector byName(String methodNamePattern, StringMatcherType matcherType) {
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
    public MethodJoinPointSelector byClass(Class<?> classType) {
        return this.declaringClassSelector.byClass(classType);
    }

    @Override
    public MethodJoinPointSelector byDeclaringClass(Class<?> declaringClass) {
        return byClass(declaringClass);
    }

    @Override
    public MethodJoinPointSelector byDeclaringClassName(String classNamePattern, StringMatcherType matcherType) {
        return byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodJoinPointSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return this.declaringClassSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodJoinPointSelector byExtending(Class<?> baseClass) {
        return this.declaringClassSelector.byExtending(baseClass);
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
    public MethodJoinPointSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return this.declaringClassSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodJoinPointSelector or() {
        return super.or(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodJoinPointSelector union() {
        return or();
    }

    @Override
    public MethodJoinPointSelector anyOf() {
        return or();
    }

    @Override
    public MethodJoinPointSelector and() {
        return super.and(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodJoinPointSelector intersect() {
        return and();
    }

    @Override
    public MethodJoinPointSelector allOf() {
        return and();
    }

    @Override
    public MethodJoinPointSelector nor() {
        return super.nor(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodJoinPointSelector noneOf() {
        return nor();
    }

    @Override
    public MethodJoinPointSelector neitherNor() {
        return nor();
    }

    @Override
    public MethodJoinPointSelector complement() {
        return nor();
    }

    @Override
    public ReturnTypeSelector byReturnType(LogicalOperator logicalOperator) {
        return createNewBuilderNode(
                JoinPointSelectorUtils.createLogicalOperatorPredicate(logicalOperator),
                getReturnTypeSelectorBuilderFactory()
            );
    }

    private
    BuilderFactory<JoinPointSelectorImpl,MethodJoinPointSelectorImpl,ReturnTypeSelectorImpl>
        getReturnTypeSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl,MethodJoinPointSelectorImpl,ReturnTypeSelectorImpl>() {
            @Override
            public ReturnTypeSelectorImpl createBuilder(
                        JoinPointSelectorImpl root, MethodJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
                return new ReturnTypeSelectorImpl(joinPointType, root, parent, compositePredicate);
            }
        };
    }

    @Override
    public MethodJoinPointSelector end() {
        return super.doEndLogicalExpression(MethodJoinPointSelector.class, false);
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodJoinPointSelectorImpl,MethodJoinPointSelectorImpl>
            getMethodJoinPointSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, MethodJoinPointSelectorImpl, MethodJoinPointSelectorImpl>() {
            @Override
            public MethodJoinPointSelectorImpl createBuilder(
                    JoinPointSelectorImpl root, MethodJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodJoinPointSelectorImpl(root, parent, compositePredicate, joinPointType);
            }
        };
    }
}
