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

import org.failearly.ajunit.builder.JoinPointSelector;
import org.failearly.ajunit.builder.MethodExecutionJoinPointSelector;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.builder.BuilderBase;
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
 * MethodExecutionJoinPointSelectorImpl is the implementation of {@link org.failearly.ajunit.builder.MethodExecutionJoinPointSelector}.
 */
final class MethodExecutionJoinPointSelectorImpl
        extends BuilderBase<JoinPointSelectorImpl, MethodExecutionJoinPointSelectorImpl> implements MethodExecutionJoinPointSelector {

    private static final AjJoinPointType JOIN_POINT_TYPE=AjJoinPointType.METHOD_EXECUTION;

    private final MethodSelectorBuilder<MethodExecutionJoinPointSelectorImpl> methodSelector;
    private final ClassSelectorBuilder<MethodExecutionJoinPointSelectorImpl>  declaringClassSelector;

    MethodExecutionJoinPointSelectorImpl(
            JoinPointSelectorImpl root,
            JoinPointSelectorImpl parent,
            CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        this.anyMethod();
    }

    private MethodExecutionJoinPointSelectorImpl(
            JoinPointSelectorImpl root, MethodExecutionJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private MethodExecutionJoinPointSelectorImpl() {
        super(MethodExecutionJoinPointSelectorImpl.class);
        this.methodSelector = SelectorBuilders.createMethodSelectorBuilder(this, JOIN_POINT_TYPE);
        this.declaringClassSelector = SelectorBuilders.createDeclaringClassSelectorBuilder(this, JOIN_POINT_TYPE);
    }

    @Override
    public JoinPointSelector endMethod() {
        return super.doEndLogicalExpression(JoinPointSelector.class, true);
    }

    private MethodExecutionJoinPointSelector anyMethod() {
        return this.methodSelector.anyMethod();
    }

    @Override
    public MethodExecutionJoinPointSelector byName(String methodNamePattern, StringMatcherType matcherType) {
        return methodSelector.byName(methodNamePattern, matcherType);
    }

    @Override
    public MethodExecutionJoinPointSelector byAnyOfAccessModifiers(AccessModifier... accessModifiers) {
        return methodSelector.byAnyOfAccessModifiers(accessModifiers);
    }

    @Override
    public MethodExecutionJoinPointSelector byNoneOfAccessModifiers(AccessModifier... accessModifiers) {
        return methodSelector.byNoneOfAccessModifiers(accessModifiers);
    }

    @Override
    public MethodExecutionJoinPointSelector byAnyOfMethodModifiers(MethodModifier... methodModifiers) {
        return methodSelector.byAnyOfMethodModifiers(methodModifiers);
    }

    @Override
    public MethodExecutionJoinPointSelector byNoneOfMethodModifiers(MethodModifier... methodModifiers) {
        return methodSelector.byNoneOfMethodModifiers(methodModifiers);
    }

    @Override
    public MethodExecutionJoinPointSelector byClass(Class<?> classType) {
        return this.declaringClassSelector.byClass(classType);
    }

    @Override
    public MethodExecutionJoinPointSelector byDeclaringClass(Class<?> declaringClass) {
        return byClass(declaringClass);
    }

    @Override
    public MethodExecutionJoinPointSelector byDeclaringClassName(String classNamePattern, StringMatcherType matcherType) {
        return byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodExecutionJoinPointSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return this.declaringClassSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodExecutionJoinPointSelector byExtending(Class<?> baseClass) {
        return this.declaringClassSelector.byExtending(baseClass);
    }

    @Override
    public MethodExecutionJoinPointSelector byImplementingAnyOf(Class<?>... interfaces) {
        return this.declaringClassSelector.byImplementingAnyOf(interfaces);
    }

    @Override
    public MethodExecutionJoinPointSelector byImplementingAllOf(Class<?>... interfaces) {
        return this.declaringClassSelector.byImplementingAllOf(interfaces);
    }

    @Override
    public MethodExecutionJoinPointSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return this.declaringClassSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodExecutionJoinPointSelector or() {
        return super.or(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodExecutionJoinPointSelector union() {
        return or();
    }

    @Override
    public MethodExecutionJoinPointSelector anyOf() {
        return or();
    }

    @Override
    public MethodExecutionJoinPointSelector and() {
        return super.and(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodExecutionJoinPointSelector intersect() {
        return and();
    }

    @Override
    public MethodExecutionJoinPointSelector allOf() {
        return and();
    }

    @Override
    public MethodExecutionJoinPointSelector nor() {
        return super.nor(getMethodJoinPointSelectorBuilderFactory());
    }

    @Override
    public MethodExecutionJoinPointSelector noneOf() {
        return nor();
    }

    @Override
    public MethodExecutionJoinPointSelector neitherNor() {
        return nor();
    }

    @Override
    public MethodExecutionJoinPointSelector complement() {
        return nor();
    }

    @Override
    public MethodExecutionJoinPointSelector end() {
        return super.doEndLogicalExpression(MethodExecutionJoinPointSelector.class, false);
    }

    private BuilderFactory<JoinPointSelectorImpl,MethodExecutionJoinPointSelectorImpl,MethodExecutionJoinPointSelectorImpl>
            getMethodJoinPointSelectorBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, MethodExecutionJoinPointSelectorImpl, MethodExecutionJoinPointSelectorImpl>() {
            @Override
            public MethodExecutionJoinPointSelectorImpl createBuilder(
                    JoinPointSelectorImpl root, MethodExecutionJoinPointSelectorImpl parent, CompositePredicate compositePredicate) {
                return new MethodExecutionJoinPointSelectorImpl(root, parent, compositePredicate);
            }
        };
    }

}
