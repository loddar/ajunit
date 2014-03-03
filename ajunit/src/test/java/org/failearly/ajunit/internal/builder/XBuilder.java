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
package org.failearly.ajunit.internal.builder;

import org.failearly.ajunit.internal.predicate.CompositePredicate;

/**
 * YBuilder is responsible for ...
 */
public class XBuilder extends BuilderBase<TopBuilder,XBuilder> {

    XBuilder(TopBuilder root, Builder parent, CompositePredicate compositePredicate) {
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    public XBuilder or() {
        return super.or(getXBuilderFactory());
    }

    private BuilderFactory<TopBuilder, XBuilder, XBuilder> getXBuilderFactory() {
        return new BuilderFactory<TopBuilder, XBuilder, XBuilder>() {
            @Override
            public XBuilder createBuilder(TopBuilder root, XBuilder parent, CompositePredicate compositePredicate) {
                return new XBuilder(root, parent, compositePredicate);
            }
        };
    }

    public XBuilder end() {
        return super.doEndLogicalExpression(XBuilder.class);
    }

    public YBuilder endY() {
        return super.doEndLogicalExpression(YBuilder.class);
    }

    public TopBuilder endTop() {
        return super.doEndLogicalExpression(TopBuilder.class);
    }
}
