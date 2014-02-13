/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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

import org.failearly.ajunit.builder.JoinPointSelectorBuilder;
import org.failearly.ajunit.builder.MethodJoinPointSelectorBuilder;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.failearly.ajunit.modifier.AccessModifier;

/**
 * MethodJoinPointSelectorBuilderImpl is responsible for ...
 */
class MethodJoinPointSelectorBuilderImpl extends BuilderBase<JoinPointSelectorBuilderImpl,MethodJoinPointSelectorBuilderImpl> implements MethodJoinPointSelectorBuilder {

    private final AjJoinPointType joinPointType;

    MethodJoinPointSelectorBuilderImpl(
            JoinPointSelectorBuilderImpl root,
            JoinPointSelectorBuilderImpl parent,
            CompositePredicate compositePredicate,
            AjJoinPointType joinPointType) {
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        this.joinPointType = joinPointType;
    }

    @Override
    public JoinPointSelectorBuilder endMethod() {
        return super.end(JoinPointSelectorBuilderImpl.class);
    }

    @Override
    public MethodJoinPointSelectorBuilder byAnyOfAccessModifiers(AccessModifier... accessModifier) {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder byNoneOfAccessModifiers(AccessModifier... accessModifier) {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder byName(String methodName) {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder byDeclaringClass(Class<?> clazz) {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder byDeclaringClassName(String className) {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder byReturnType(Class<?> clazz) {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder byReturnType(String className) {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder anyMethod() {
        return addMethodPredicate(StandardPredicates.alwaysTrue());
    }

    private MethodJoinPointSelectorBuilder addMethodPredicate(Predicate predicate) {
        addPredicate(StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                        AjpTransformers.ajpJoinPointFilterTransformer(this.joinPointType),
                        AjpTransformers.methodTransformer()
                ),
                predicate
        ));
        return this;
    }

    @Override
    public MethodJoinPointSelectorBuilder or() {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder union() {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder anyOf() {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder and() {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder intersect() {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder allOf() {
        return null;
    }

    @Override
    public MethodJoinPointSelectorBuilder end() {
        return null;
    }
}
