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
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;

/**
 * <pre>
 * top*->X*
 * top*->Y*->X*
 * </pre>
 */
public final class TopBuilder extends RootBuilderBase<TopBuilder> {

    public TopBuilder() {
        init(LogicalStructureBuilder.createRootBuilder(this, LogicalPredicates.or()));
    }

    private TopBuilder(TopBuilder root, TopBuilder parent, CompositePredicate compositePredicate) {
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    public XBuilder xBuilder() {
        return super.and(getXBuilderFactory());
    }

    private BuilderFactory<TopBuilder, TopBuilder, XBuilder> getXBuilderFactory() {
        return new BuilderFactory<TopBuilder, TopBuilder, XBuilder>() {
            @Override
            public XBuilder createBuilder(TopBuilder root, TopBuilder parent, CompositePredicate compositePredicate) {
                return new XBuilder(root, parent, compositePredicate);
            }
        };
    }

    public YBuilder yBuilder() {
        return super.or(getYBuilderFactory());
    }

    private BuilderFactory<TopBuilder, TopBuilder, YBuilder> getYBuilderFactory() {
        return new BuilderFactory<TopBuilder, TopBuilder, YBuilder>() {
            @Override
            public YBuilder createBuilder(TopBuilder root, TopBuilder parent, CompositePredicate compositePredicate) {
                return new YBuilder(root, parent, compositePredicate);
            }
        };
    }

    public TopBuilder and() {
        return super.and(getRootBuilderFactory());
    }

    public TopBuilder or() {
        return super.or(getRootBuilderFactory());
    }

    private BuilderFactory<TopBuilder, TopBuilder, TopBuilder> getRootBuilderFactory() {
        return new BuilderFactory<TopBuilder, TopBuilder, TopBuilder>() {
            @Override
            public TopBuilder createBuilder(TopBuilder root, TopBuilder parent, CompositePredicate compositePredicate) {
                return new TopBuilder(root, parent, compositePredicate);
            }
        };
    }

    public TopBuilder end() {
        return super.doEndLogicalExpression(TopBuilder.class);
    }
}
