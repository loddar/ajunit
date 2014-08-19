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

import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.builder.method.ReturnComponentTypeSelector;
import org.failearly.ajunit.builder.method.ReturnTypeSelector;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;

import java.lang.annotation.Annotation;

/**
 * The implementation of ReturnComponentTypeSelector.
 *
 * @see ReturnTypeSelector#componentType()
 */
final class ReturnComponentTypeSelectorImpl
        extends JoinPointSelectorBuilderBase<ReturnComponentTypeSelectorImpl>
        implements ReturnComponentTypeSelector {


    private final ClassSelectorBuilder<ReturnComponentTypeSelectorImpl> returnComponentTypeSelector;

    ReturnComponentTypeSelectorImpl(
            JoinPointSelectorImpl root, ReturnTypeSelectorImpl parent, CompositePredicate compositePredicate,
            ClassSelectorBuilder<ReturnTypeSelectorImpl> returnTypeSelector) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        returnTypeSelector.byArray();
    }

    private ReturnComponentTypeSelectorImpl(
            JoinPointSelectorImpl root, ReturnComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root,parent,this,compositePredicate));
    }

    private ReturnComponentTypeSelectorImpl() {
        super(ReturnComponentTypeSelectorImpl.class);
        this.returnComponentTypeSelector = SelectorBuilders.createReturnComponentTypeSelectorBuilder(this);
    }

    @Override
    public ReturnTypeSelector endComponentType() {
        return super.doEndLogicalExpression(ReturnTypeSelector.class, true);
    }

    @Override
    public ReturnComponentTypeSelector byClass(Class<?> classType) {
        return returnComponentTypeSelector.byClass(classType);
    }

    @Override
    public ReturnComponentTypeSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return returnComponentTypeSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public ReturnComponentTypeSelector byExtending(Class<?> baseClass) {
        return returnComponentTypeSelector.byExtending(baseClass);
    }

    @Override
    public ReturnComponentTypeSelector byNotExtending(Class<?> baseClass) {
        return returnComponentTypeSelector.byNotExtending(baseClass);
    }

    @Override
    public ReturnComponentTypeSelector byImplementingAnyOf(Class<?>... interfaces) {
        return returnComponentTypeSelector.byImplementingAnyOf(interfaces);
    }

    @Override
    public ReturnComponentTypeSelector byImplementingAllOf(Class<?>... interfaces) {
        return returnComponentTypeSelector.byImplementingAllOf(interfaces);
    }

    @Override
    public ReturnComponentTypeSelector byImplementingNoneOf(Class<?>... interfaces) {
        return returnComponentTypeSelector.byImplementingNoneOf(interfaces);
    }

    @Override
    public ReturnComponentTypeSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return returnComponentTypeSelector.byPackageName(packageNamePattern, matcherType);
    }

    @Override
    public ReturnComponentTypeSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return returnComponentTypeSelector.byTypeAnnotation(annotationClass);
    }

    @Override
    public ReturnComponentTypeSelector byPrimitive() {
        return returnComponentTypeSelector.byPrimitive();
    }

    @Override
    public ReturnComponentTypeSelector byEnum() {
        return returnComponentTypeSelector.byEnum();
    }

    @Override
    public ReturnComponentTypeSelector byAnnotation() {
        return returnComponentTypeSelector.byAnnotation();
    }

    @Override
    public ReturnComponentTypeSelector byInterface() {
        return returnComponentTypeSelector.byInterface();
    }

    @Override
    public ReturnComponentTypeSelector byPrimitiveWrapperType() {
        return returnComponentTypeSelector.byPrimitiveWrapperType();
    }

    @Override
    public ReturnComponentTypeSelector byCollection() {
        return returnComponentTypeSelector.byCollection();
    }

    @Override
    public ReturnComponentTypeSelector byMap() {
        return returnComponentTypeSelector.byMap();
    }

    @Override
    protected ReturnComponentTypeSelectorImpl newInstance(JoinPointSelectorImpl root, ReturnComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        return new ReturnComponentTypeSelectorImpl(root, parent, compositePredicate);
    }
}
