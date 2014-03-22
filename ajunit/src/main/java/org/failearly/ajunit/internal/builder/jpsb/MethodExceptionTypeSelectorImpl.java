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

import org.failearly.ajunit.builder.ListLogicalOperator;
import org.failearly.ajunit.builder.MethodExceptionTypeSelector;
import org.failearly.ajunit.builder.MethodJoinPointSelector;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.JoinPointSelectorUtils;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.clazz.ClassPredicates;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.ajp.AjpTransformers;
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.transformer.method.MethodTransformers;
import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of {@link org.failearly.ajunit.builder.MethodExceptionTypeSelector}.
 */
final class MethodExceptionTypeSelectorImpl
        extends BuilderBase<JoinPointSelectorImpl,MethodExceptionTypeSelectorImpl>
        implements MethodExceptionTypeSelector {


    MethodExceptionTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodJoinPointSelectorImpl parent,
            CompositePredicate compositePredicate,
            AjJoinPointType joinPointType,
            ListLogicalOperator listLogicalOperator) {
        init(LogicalStructureBuilder.createBuilder(root, parent, this, createCompositeNode(compositePredicate, joinPointType, listLogicalOperator)));
    }

    private MethodExceptionTypeSelectorImpl(JoinPointSelectorImpl root, MethodExceptionTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));

    }

    private static CompositePredicate createCompositeNode(CompositePredicate compositePredicate, AjJoinPointType joinPointType, ListLogicalOperator listLogicalOperator) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.transformerComposition(
                    AjpTransformers.ajpJoinPointFilterTransformer(joinPointType),
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
        super.addPredicate(
                ClassPredicates.isSubclassOf(RuntimeException.class)
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byError() {
        super.addPredicate(ClassPredicates.isSubclassOf(Error.class));
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byCheckedException() {
        super.addPredicate(
                LogicalPredicates.and(
                        ClassPredicates.isSubclassOf(Exception.class),
                        LogicalPredicates.not(ClassPredicates.isSubclassOf(RuntimeException.class))
                )
            );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byClass(Class<?> classType) {
        super.addPredicate(
                ClassPredicates.isClass(classType)
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        super.addPredicate(
                StandardPredicates.transformerPredicate(
                        ClassTransformers.classNameTransformer(),
                        JoinPointSelectorUtils.createStringMatcherPredicate(classNamePattern, matcherType)
                )
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        super.addPredicate(
                StandardPredicates.transformerPredicate(
                        ClassTransformers.packageNameTransformer(),
                        JoinPointSelectorUtils.createStringMatcherPredicate(packageNamePattern, matcherType)
                )
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byExtending(Class<?> baseClass) {
        super.addPredicate(
                ClassPredicates.isSubclassOf(baseClass)
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byNotExtending(Class<?> baseClass) {
        super.addPredicate(
                LogicalPredicates.not(ClassPredicates.isSubclassOf(baseClass))
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byImplementingAnyOf(Class<?>... interfaces) {
        super.addPredicate(
                LogicalPredicates.or(
                        createIsSubClassOfPredicates(interfaces)
                )
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byImplementingAllOf(Class<?>... interfaces) {
        super.addPredicate(
                LogicalPredicates.and(
                        createIsSubClassOfPredicates(interfaces)
                )
        );
        return this;
    }

    @Override
    public MethodExceptionTypeSelector byImplementingNoneOf(Class<?>... interfaces) {
        super.addPredicate(
                LogicalPredicates.nor(
                        createIsSubClassOfPredicates(interfaces)
                )
        );
        return this;
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

    private static List<Predicate> createIsSubClassOfPredicates(Class<?>... interfaces) {
        final List<Predicate> predicates = new ArrayList<>(interfaces.length);
        for (Class<?> anInterface : interfaces) {
            predicates.add(ClassPredicates.isSubclassOf(anInterface));
        }
        return predicates;
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
