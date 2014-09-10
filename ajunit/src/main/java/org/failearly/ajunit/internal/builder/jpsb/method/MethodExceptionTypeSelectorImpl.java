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

import org.failearly.ajunit.builder.ListOperator;
import org.failearly.ajunit.builder.StringMatcher;
import org.failearly.ajunit.builder.method.MethodExceptionTypeSelector;
import org.failearly.ajunit.builder.method.MethodJoinPointSelector;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
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
 * The implementation of {@link org.failearly.ajunit.builder.method.MethodExceptionTypeSelector}.
 */
final class MethodExceptionTypeSelectorImpl
        extends JoinPointSelectorBuilderBase<MethodExceptionTypeSelectorImpl,MethodJoinPointSelector>
        implements MethodExceptionTypeSelector {


    private final ClassSelectorBuilder<MethodExceptionTypeSelectorImpl> methodExceptionTypeSelector;


    MethodExceptionTypeSelectorImpl(
            JoinPointSelectorImpl root,
            MethodJoinPointSelectorImpl parent,
            CompositePredicate compositePredicate,
            ListOperator listOperator) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, createCompositeNode(compositePredicate, listOperator)));
    }

    private MethodExceptionTypeSelectorImpl(JoinPointSelectorImpl root, MethodExceptionTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private MethodExceptionTypeSelectorImpl() {
        super(MethodExceptionTypeSelectorImpl.class, MethodJoinPointSelector.class);
        this.methodExceptionTypeSelector = SelectorBuilders.createMethodExceptionTypeSelector(this);
    }

    private static CompositePredicate createCompositeNode(CompositePredicate compositePredicate, ListOperator listOperator) {
        return StandardPredicates.transformerPredicate(
                StandardTransformers.compose(
                        AjpTransformers.method(),
                        MethodTransformers.methodExceptions()
                ),
                JoinPointSelectorUtils.createListLogicalOperator(listOperator, compositePredicate)
        );
    }

    @Override
    public MethodJoinPointSelector endExceptionTypes() {
        return terminateSubSelector();
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
    public MethodExceptionTypeSelector byClassName(String classNamePattern, StringMatcher matcherType) {
        return methodExceptionTypeSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public MethodExceptionTypeSelector byPackageName(String packageNamePattern, StringMatcher matcherType) {
        return methodExceptionTypeSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public MethodExceptionTypeSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return methodExceptionTypeSelector.byTypeAnnotation(annotationClass);
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
    protected final MethodExceptionTypeSelectorImpl newInstance(JoinPointSelectorImpl root, MethodExceptionTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        return new MethodExceptionTypeSelectorImpl(root, parent, compositePredicate);
    }
}
