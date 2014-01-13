/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
public class YBuilder extends BuilderBase<TopBuilder,YBuilder> {

    YBuilder(TopBuilder root, Builder parent, CompositePredicate compositePredicate) {
        init(LogicalStructureBuilder.createBuilder(root, parent, this, compositePredicate));
    }

    public YBuilder and() {
        return super.and(getYBuilderFactory());
    }

    public YBuilder or() {
        return super.or(getYBuilderFactory());
    }

    private BuilderFactory<TopBuilder, YBuilder, YBuilder> getYBuilderFactory() {
        return new BuilderFactory<TopBuilder, YBuilder, YBuilder>() {
            @Override
            public YBuilder createBuilder(TopBuilder root, YBuilder parent, CompositePredicate compositePredicate) {
                return new YBuilder(root, parent, compositePredicate);
            }
        };
    }

    public XBuilder andX() {
        return super.and(getXBuilderFactory());
    }

    private BuilderFactory<TopBuilder, YBuilder, XBuilder> getXBuilderFactory() {
        return new BuilderFactory<TopBuilder, YBuilder, XBuilder>() {
            @Override
            public XBuilder createBuilder(TopBuilder root, YBuilder parent, CompositePredicate compositePredicate) {
                return new XBuilder(root, parent, compositePredicate);
            }
        };
    }

    public YBuilder end() {
        return super.end(YBuilder.class);
    }

    public TopBuilder endTop() {
        return super.end(TopBuilder.class);
    }
}
