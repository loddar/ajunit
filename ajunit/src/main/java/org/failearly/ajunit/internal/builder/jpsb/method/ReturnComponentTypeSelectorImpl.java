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

import org.failearly.ajunit.builder.method.ReturnComponentTypeSelector;
import org.failearly.ajunit.builder.method.ReturnTypeSelector;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.ComponentTypeSelectorBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;

/**
 * The implementation of ReturnComponentTypeSelector.
 *
 * @see ReturnTypeSelector#componentType()
 */
final class ReturnComponentTypeSelectorImpl
        extends ComponentTypeSelectorBase<ReturnComponentTypeSelectorImpl, ReturnTypeSelector>
        implements ReturnComponentTypeSelector {


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
        super(ReturnComponentTypeSelectorImpl.class, ReturnTypeSelector.class);
    }

    @Override
    protected ClassSelectorBuilder<ReturnComponentTypeSelectorImpl> createComponentTypeSelector() {
        return SelectorBuilders.createReturnComponentTypeSelectorBuilder(this);
    }

    @Override
    protected ReturnComponentTypeSelectorImpl newInstance(JoinPointSelectorImpl root, ReturnComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        return new ReturnComponentTypeSelectorImpl(root, parent, compositePredicate);
    }
}
