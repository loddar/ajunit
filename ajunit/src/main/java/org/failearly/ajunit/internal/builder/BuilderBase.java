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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.builder;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.util.AjAssert;

/**
 * The base class for all {@link Builder}.
 */
public abstract class BuilderBase<R extends RootBuilder, C extends Builder> implements Builder {
    private LogicalStructureBuilder<R, C> logicalStructureBuilder;

    protected BuilderBase() {
    }

    protected <P extends Builder> void init(LogicalStructureBuilder<R, C> logicalStructureBuilder) {
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
     * Generic NAND.
     * @param builderFactory the builder factory.
     * @param <N> the next builder type.
     * @return next builder.
     */
    protected final <N extends Builder> N nand(final BuilderFactory<R, C, N> builderFactory) {
        return logicalStructureBuilder.nand(builderFactory);
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

    /**
     * Generic XOR.
     * @param builderFactory the builder factory.
     * @param <N> the next builder type.
     * @return next builder.
     */
    protected final <N extends Builder> N xor(final BuilderFactory<R, C, N> builderFactory) {
        return logicalStructureBuilder.xor(builderFactory);
    }

    @SuppressWarnings("unchecked")
    protected C alwaysTrue() {
        addPredicate(StandardPredicates.alwaysTrue());
        return (C) this;
    }

    @SuppressWarnings("unchecked")
    protected C alwaysFalse() {
        addPredicate(StandardPredicates.alwaysFalse());
        return (C) this;
    }

    protected final <P extends Builder> P end(Class<P> builderClass) {
        return builderClass.cast(this.logicalStructureBuilder.end());
    }

    @Override
    public R done() {
        return this.logicalStructureBuilder.done();
    }

    /**
     * Add a predicate and negate it.
     */
    protected final void addNotPredicate(Predicate predicate) {
        logicalStructureBuilder.addPredicate(LogicalPredicates.not(predicate));
    }

    @Override
    public final void addPredicate(Predicate predicate) {
        logicalStructureBuilder.addPredicate(predicate);
    }

    protected final Predicate doBuild() {
        return this.logicalStructureBuilder.build();
    }

    @Override
    public void cleanup() {
        this.logicalStructureBuilder.cleanup();
    }
}
