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

import org.failearly.ajunit.builder.method.MethodArgumentComponentTypeSelector;
import org.failearly.ajunit.builder.method.MethodArgumentTypeSelector;
import org.failearly.ajunit.internal.builder.LogicalStructureBuilder;
import org.failearly.ajunit.internal.builder.jpsb.ComponentTypeSelectorBase;
import org.failearly.ajunit.internal.builder.jpsb.JoinPointSelectorImpl;
import org.failearly.ajunit.internal.builder.jpsb.helper.ClassSelectorBuilder;
import org.failearly.ajunit.internal.builder.jpsb.helper.SelectorBuilders;
import org.failearly.ajunit.internal.predicate.CompositePredicate;

/**
 * MethodArgumentComponentTypeSelector is the implementation of {@link org.failearly.ajunit.builder.method.MethodArgumentComponentTypeSelector}
 *
 * @see org.failearly.ajunit.builder.method.MethodArgumentTypeSelector#componentType()
 */
final class MethodArgumentComponentTypeSelectorImpl
        extends ComponentTypeSelectorBase<MethodArgumentComponentTypeSelectorImpl,MethodArgumentTypeSelector>
        implements MethodArgumentComponentTypeSelector {


    MethodArgumentComponentTypeSelectorImpl(
                        JoinPointSelectorImpl root,
                        MethodArgumentTypeSelectorImpl parent,
                        CompositePredicate compositePredicate,
                        ClassSelectorBuilder<MethodArgumentTypeSelectorImpl> methodArgumentTypeSelectorBuilder) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
        methodArgumentTypeSelectorBuilder.byArray();
    }

    private MethodArgumentComponentTypeSelectorImpl(JoinPointSelectorImpl root, MethodArgumentComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        this();
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    private MethodArgumentComponentTypeSelectorImpl() {
        super(MethodArgumentComponentTypeSelectorImpl.class, MethodArgumentTypeSelector.class);
    }

    @Override
    protected ClassSelectorBuilder<MethodArgumentComponentTypeSelectorImpl> createComponentTypeSelector() {
        return SelectorBuilders.createXxx(this);
    }

    @Override
    protected final MethodArgumentComponentTypeSelectorImpl newInstance(JoinPointSelectorImpl root, MethodArgumentComponentTypeSelectorImpl parent, CompositePredicate compositePredicate) {
        return new MethodArgumentComponentTypeSelectorImpl(root, parent, compositePredicate);
    }
}
