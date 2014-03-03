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

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageUtils;

/**
 * The base class for all {@link Builder}.
 */
public abstract class BuilderBase<R extends RootBuilder, C extends Builder> implements Builder {
    private final Class<C> clazz;
    private LogicalStructureBuilder<R, C> logicalStructureBuilder;

    protected BuilderBase(Class<C> clazz) {
        this.clazz = clazz;
    }

    protected final <P extends Builder> void init(LogicalStructureBuilder<R, C> logicalStructureBuilder) {
        AjAssert.attributeIsNull(this.logicalStructureBuilder,"logicalStructureBuilder");
        this.logicalStructureBuilder = logicalStructureBuilder;
    }

    /**
     * Generic AND.
     * @param builderFactory the builder factory.
     * @param <N> the next builder type.
     * @return next builder.
     */
    protected final <N extends Builder> N and(final BuilderFactory<R, C, N> builderFactory) {
        return logicalStructureBuilder.and(builderFactory);
    }

    /**
     * Generic OR.
     * @param builderFactory the builder factory.
     * @param <N> the next builder type.
     * @return next builder.
     */
    protected final <N extends Builder> N or(final BuilderFactory<R, C, N> builderFactory) {
        return logicalStructureBuilder.or(builderFactory);
    }

    /**
     * Generic NOR.
     * @param builderFactory the builder factory.
     * @param <N> the next builder type.
     * @return next builder.
     */
    protected final <N extends Builder> N nor(final BuilderFactory<R, C, N> builderFactory) {
        return logicalStructureBuilder.nor(builderFactory);
    }

    protected C alwaysTrue() {
        addPredicate(StandardPredicates.alwaysTrue());
        return clazz.cast(this);
    }

    @SuppressWarnings("unchecked")
    protected C alwaysFalse() {
        addPredicate(StandardPredicates.alwaysFalse());
        return clazz.cast(this);
    }

    protected final <P> P doEndLogicalExpression(Class<P> builderClass, boolean recursiveEndExpressions) {
        Builder parent = this.endLogicalExpression();
        while(recursiveEndExpressions && ! builderClass.isInstance(parent) ) {
            parent=parent.endLogicalExpression();
        }
        AjAssert.assertCondition(
                builderClass.isInstance(parent),
                MessageUtils.message("Invalid termination of logical expression: Parent selector instance is not of expected type")
                        .arg(builderClass.getSimpleName())
        );
        return builderClass.cast(parent);
    }

    @Override
    public final Builder endLogicalExpression() {
        return this.logicalStructureBuilder.end();
    }

    @Override
    public R done() {
        return this.logicalStructureBuilder.done();
    }

    @Override
    public final void addPredicate(Predicate predicate) {
        logicalStructureBuilder.addPredicate(predicate);
    }

    /**
     * Returns {@code true} if any predicate has been defined (which is not logical predicate).
     */
    public boolean anyPredicateDefined() {
        return logicalStructureBuilder.anyPredicateDefined();
    }

    protected final Predicate doBuild() {
        return this.logicalStructureBuilder.build();
    }

    @Override
    public void cleanup() {
        this.logicalStructureBuilder.cleanup();
    }

}
