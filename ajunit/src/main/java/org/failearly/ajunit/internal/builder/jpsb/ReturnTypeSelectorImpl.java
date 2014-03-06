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

import org.failearly.ajunit.builder.MethodJoinPointSelector;
import org.failearly.ajunit.builder.ReturnTypeSelector;
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

/**
 * ReturnTypeSelectorImpl is responsible for ...
 */
final class ReturnTypeSelectorImpl<IT extends MethodJoinPointSelectorBase<IT>, IF extends MethodJoinPointSelector<IF>>
        extends BuilderBase<JoinPointSelectorImpl,ReturnTypeSelectorImpl<IT, IF>>
        implements ReturnTypeSelector<IF> {

    private final Class<IF> parentClass;
    private final ClassSelectorBuilder<ReturnTypeSelectorImpl<IT,IF>> returnTypeSelector;

    private ReturnTypeSelectorImpl(Class<IF> parentClass, AjJoinPointType joinPointType) {
        this.parentClass = parentClass;
        this.returnTypeSelector = SelectorBuilders.createReturnTypeSelectorBuilder(this, joinPointType);
    }

    ReturnTypeSelectorImpl(Class<IF> parentClass, AjJoinPointType joinPointType, JoinPointSelectorImpl root, IT parent, CompositePredicate compositePredicate) {
        this(parentClass, joinPointType);
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    @Override
    public IF endReturnType() {
        return super.doEndLogicalExpression(parentClass, true);
    }

    @Override
    public ReturnTypeSelector<IF> byClass(Class<?> classType) {
        return returnTypeSelector.byClass(classType);
    }

    @Override
    public ReturnTypeSelector<IF> byClassName(String classNamePattern, StringMatcherType matcherType) {
        return returnTypeSelector.byClassName(classNamePattern, matcherType);
    }

    @Override
    public ReturnTypeSelector<IF> byExtending(Class<?> baseClass) {
        return this;
    }

    @Override
    public ReturnTypeSelector<IF> byImplementingAnyOf(Class<?>... interfaces) {
        return this;
    }

    @Override
    public ReturnTypeSelector<IF> byImplementingAllOf(Class<?>... interfaces) {
        return this;
    }

    @Override
    public ReturnTypeSelector<IF> byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return this;
    }
}
