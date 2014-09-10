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

import org.failearly.ajunit.builder.SelectorBuilder;
import org.failearly.ajunit.builder.StringMatcher;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.util.AjAssert;

import java.lang.annotation.Annotation;

/**
 * ComponentTypeSelectorBase provides implementations for {@link org.failearly.ajunit.builder.ComponentTypeSelector}.
 */
public abstract class ComponentTypeSelectorBase<C extends Builder, P extends SelectorBuilder> extends JoinPointSelectorBuilderBase<C,P> {
    private final ClassSelectorBuilder<C> componentTypeSelector;

    protected ComponentTypeSelectorBase(Class<C> thisClass, Class<P> parentClass) {
        super(thisClass, parentClass);
        this.componentTypeSelector = createComponentTypeSelector();
        AjAssert.attributeIsNotNull(componentTypeSelector,"componentTypeSelector");
    }

    protected abstract ClassSelectorBuilder<C> createComponentTypeSelector();

    public final C byClass(Class<?> classType) {
        return componentTypeSelector.byClass(classType);
    }

    public final C byClassName(String classNamePattern, StringMatcher matcherType) {
        return componentTypeSelector.byClassName(classNamePattern, matcherType);
    }

    public final C byExtending(Class<?> baseClass) {
        return componentTypeSelector.byExtending(baseClass);
    }

    public final C byNotExtending(Class<?> baseClass) {
        return componentTypeSelector.byNotExtending(baseClass);
    }

    public final C byImplementingAnyOf(Class<?>... interfaces) {
        return componentTypeSelector.byImplementingAnyOf(interfaces);
    }

    public final C byImplementingAllOf(Class<?>... interfaces) {
        return componentTypeSelector.byImplementingAllOf(interfaces);
    }

    public final C byImplementingNoneOf(Class<?>... interfaces) {
        return componentTypeSelector.byImplementingNoneOf(interfaces);
    }

    public final C byPackageName(String packageNamePattern, StringMatcher matcherType) {
        return componentTypeSelector.byPackageName(packageNamePattern, matcherType);
    }

    public final C byTypeAnnotation(Class<? extends Annotation> annotationClass) {
        return componentTypeSelector.byTypeAnnotation(annotationClass);
    }

    public final C byPrimitive() {
        return componentTypeSelector.byPrimitive();
    }

    public final C byEnum() {
        return componentTypeSelector.byEnum();
    }

    public final C byAnnotation() {
        return componentTypeSelector.byAnnotation();
    }

    public final C byInterface() {
        return componentTypeSelector.byInterface();
    }

    public final C byPrimitiveWrapperType() {
        return componentTypeSelector.byPrimitiveWrapperType();
    }

    public final C byCollection() {
        return componentTypeSelector.byCollection();
    }

    public final C byMap() {
        return componentTypeSelector.byMap();
    }

    public final P endComponentType() {
        return terminateSubSelector();
    }
}
