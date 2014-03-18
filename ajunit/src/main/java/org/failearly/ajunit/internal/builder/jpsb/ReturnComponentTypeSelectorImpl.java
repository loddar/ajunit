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

import org.failearly.ajunit.builder.ReturnComponentTypeSelector;
import org.failearly.ajunit.builder.ReturnTypeSelector;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * ReturnComponentTypeSelectorImpl is responsible for ...
 */
final class ReturnComponentTypeSelectorImpl
        extends BuilderBase<JoinPointSelectorImpl,ReturnComponentTypeSelectorImpl>
        implements ReturnComponentTypeSelector {


    private final ClassSelectorBuilder<ReturnComponentTypeSelectorImpl> returnComponentTypeSelector;
    private final AjJoinPointType joinPointType;

    ReturnComponentTypeSelectorImpl(
            AjJoinPointType joinPointType,
            JoinPointSelectorImpl root,
            ReturnTypeSelectorImpl parent,
            CompositePredicate compositePredicate,
            ClassSelectorBuilder<ReturnTypeSelectorImpl> returnTypeSelector) {
        this(joinPointType);
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        returnTypeSelector.byArray();
    }

    /**
     * Used by locally {@link #getReturnComponentTypeSelectorBuilderFactory(org.failearly.ajunit.internal.universe.AjJoinPointType)}.
     */
    private ReturnComponentTypeSelectorImpl(
            AjJoinPointType joinPointType, JoinPointSelectorImpl root, ReturnComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        this(joinPointType);
        init(LogicalStructureBuilder.createBuilder(root,parent,this,compositePredicate));
    }

    private ReturnComponentTypeSelectorImpl(AjJoinPointType joinPointType) {
        this.returnComponentTypeSelector = SelectorBuilders.createReturnComponentTypeSelectorBuilder(this, joinPointType);
        this.joinPointType = joinPointType;
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
    public ReturnComponentTypeSelector or() {
        return super.or(getReturnComponentTypeSelectorBuilderFactory(this.joinPointType));
    }

    @Override
    public ReturnComponentTypeSelector union() {
        return this.or();
    }

    @Override
    public ReturnComponentTypeSelector anyOf() {
        return this.or();
    }

    @Override
    public ReturnComponentTypeSelector and() {
        return super.and(getReturnComponentTypeSelectorBuilderFactory(this.joinPointType));
    }

    @Override
    public ReturnComponentTypeSelector intersect() {
        return this.and();
    }

    @Override
    public ReturnComponentTypeSelector allOf() {
        return this.and();
    }

    @Override
    public ReturnComponentTypeSelector nor() {
        return super.nor(getReturnComponentTypeSelectorBuilderFactory(this.joinPointType));
    }

    @Override
    public ReturnComponentTypeSelector noneOf() {
        return this.nor();
    }

    @Override
    public ReturnComponentTypeSelector neitherNor() {
        return this.nor();
    }

    @Override
    public ReturnComponentTypeSelector complement() {
        return this.nor();
    }

    @Override
    public ReturnComponentTypeSelector end() {
        return super.doEndLogicalExpression(ReturnComponentTypeSelector.class, false);
    }

    private static BuilderFactory<JoinPointSelectorImpl,ReturnComponentTypeSelectorImpl,ReturnComponentTypeSelectorImpl>
        getReturnComponentTypeSelectorBuilderFactory(final AjJoinPointType joinPointType) {
        return new BuilderFactory<JoinPointSelectorImpl, ReturnComponentTypeSelectorImpl, ReturnComponentTypeSelectorImpl>() {
            @Override
            public ReturnComponentTypeSelectorImpl createBuilder(JoinPointSelectorImpl root, ReturnComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
                return new ReturnComponentTypeSelectorImpl(joinPointType, root, parent, compositePredicate);
            }
        };
    }
}
