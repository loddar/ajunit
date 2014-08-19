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
import org.failearly.ajunit.builder.method.MethodArgumentComponentTypeSelector;
import org.failearly.ajunit.builder.method.MethodArgumentTypeSelector;
import org.failearly.ajunit.internal.annotation.NotYetImplemented;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorBuilderBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.predicate.CompositePredicate;

import java.lang.annotation.Annotation;

/**
 * MethodArgumentComponentTypeSelector is the implementation of {@link org.failearly.ajunit.builder.method.MethodArgumentComponentTypeSelector}
 *
 * @see org.failearly.ajunit.builder.method.MethodArgumentTypeSelector#componentType()
 */
final class MethodArgumentComponentTypeSelectorImpl
        extends JoinPointSelectorBuilderBase<MethodArgumentComponentTypeSelectorImpl>
        implements MethodArgumentComponentTypeSelector {


    private MethodArgumentComponentTypeSelectorImpl() {
        super(MethodArgumentComponentTypeSelectorImpl.class);
    }

    private MethodArgumentComponentTypeSelectorImpl(JoinPointSelectorImpl root, MethodArgumentComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    @Override
    @NotYetImplemented
    public MethodArgumentTypeSelector endComponentType() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byPrimitive() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byEnum() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byAnnotation() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byInterface() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byPrimitiveWrapperType() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byCollection() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byMap() {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byClass(Class<?> classType) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byClassName(String classNamePattern, StringMatcherType matcherType) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byExtending(Class<?> baseClass) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byNotExtending(Class<?> baseClass) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byImplementingAnyOf(Class<?>... interfaces) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byImplementingAllOf(Class<?>... interfaces) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byImplementingNoneOf(Class<?>... interfaces) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return null;
    }

    @Override
    @NotYetImplemented
    public MethodArgumentComponentTypeSelector byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return null;
    }

    @Override
    protected final MethodArgumentComponentTypeSelectorImpl newInstance(JoinPointSelectorImpl root, MethodArgumentComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        return new MethodArgumentComponentTypeSelectorImpl(root, parent, compositePredicate);
    }
}
