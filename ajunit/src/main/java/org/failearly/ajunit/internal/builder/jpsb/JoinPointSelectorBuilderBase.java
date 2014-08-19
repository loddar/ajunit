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

import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.builder.BuilderBase;
import org.failearly.ajunit.internal.builder.BuilderFactory;
import org.failearly.ajunit.internal.predicate.CompositePredicate;

/**
 * JoinPointBuilderBase is the base class for all Joinpoint Selector builder classes.
 */
public abstract class JoinPointSelectorBuilderBase<C extends Builder> extends BuilderBase<JoinPointSelectorImpl,C> {
    private final Class<C> parentBuilderClass;

    protected JoinPointSelectorBuilderBase(Class<C> parentBuilderClass) {
        this.parentBuilderClass = parentBuilderClass;
    }

    public C or() {
        return super.or(createLogicalExpressionBuilderFactory());
    }

    public final C union() {
        return or();
    }

    public final C anyOf() {
        return or();
    }

    public C and() {
        return super.and(createLogicalExpressionBuilderFactory());
    }

    public final C intersect() {
        return and();
    }

    public final C allOf() {
        return and();
    }

    public C nor() {
        return super.nor(createLogicalExpressionBuilderFactory());
    }

    public final C noneOf() {
        return nor();
    }

    public final C neitherNor() {
        return nor();
    }

    public final C complement()  {
        return nor();
    }

    public C end()  {
        return super.doEndLogicalExpression(this.parentBuilderClass, false);
    }

    private BuilderFactory<JoinPointSelectorImpl, C, C> createLogicalExpressionBuilderFactory() {
        return new BuilderFactory<JoinPointSelectorImpl, C, C>() {
            @Override
            public C createBuilder(JoinPointSelectorImpl root, C parent, CompositePredicate compositePredicate) {
                return newInstance(root, parent, compositePredicate);
            }
        };
    }

    /**
     * Create a new instance of C (for logical expressions).
     * @param root the root instance.
     * @param parent the parent of current class.
     * @param compositePredicate the composite predicate.
     * @return a new instance.
     */
    protected abstract C newInstance(JoinPointSelectorImpl root, C parent, CompositePredicate compositePredicate);
}
