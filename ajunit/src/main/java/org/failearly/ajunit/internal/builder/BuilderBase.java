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
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;

/**
 * The base class for all {@link Builder}.
 */
public abstract class BuilderBase<R extends RootBuilder, C extends Builder, P> implements Builder {
    private LogicalStructureBuilder<R, C> logicalStructureBuilder;
    private final Class<P> parentClass;
    private final Class<C> thisClass;

    protected BuilderBase(Class<C> thisClass, Class<P> parentClass) {
        this.thisClass = thisClass;
        this.parentClass = parentClass;
    }

    protected final void init(LogicalStructureBuilder<R, C> logicalStructureBuilder) {
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

    /**
     * Creates a generic node.
     * @param compositePredicate the logical predicate.
     * @param builderFactory the builder factory.
     * @param <N> the next builder type.
     * @return next builder.
     */
    protected final <N extends Builder> N createNewBuilderNode(CompositePredicate compositePredicate, final BuilderFactory<R, C, N> builderFactory) {
        return logicalStructureBuilder.createNewBuilderNode(compositePredicate, builderFactory);
    }

    public final C or() {
        return or(createLogicalExpressionBuilderFactory());
    }

    public final C union() {
        return or();
    }

    public final C anyOf() {
        return or();
    }

    public final C and() {
        return and(createLogicalExpressionBuilderFactory());
    }

    public final C intersect() {
        return and();
    }

    public final C allOf() {
        return and();
    }

    public final C nor() {
        return nor(createLogicalExpressionBuilderFactory());
    }

    public final C noneOf() {
        return nor();
    }

    public final C neitherNor() {
        return nor();
    }

    public final C complement() {
        return nor();
    }

    public final C end() {
        return doEndLogicalExpression(this.thisClass, false);
    }

    protected BuilderFactory<R, C, C> createLogicalExpressionBuilderFactory() {
        return new BuilderFactory<R, C, C>() {
            @Override
            public C createBuilder(R root, C parent, CompositePredicate compositePredicate) {
                return newInstance(root, parent, compositePredicate);
            }
        };
    }

    /**
     * Create a new instance of C (for logical expressions).
     *
     * @param root               the root instance.
     * @param parent             the parent of current class.
     * @param compositePredicate the composite predicate.
     * @return a new instance.
     */
    protected abstract C newInstance(R root, C parent, CompositePredicate compositePredicate);

    /**
     * Terminates the sub selector expression.
     *
     * @return the parent selector builder.
     */
    protected final P terminateSubSelector() {
        return doEndLogicalExpression(parentClass, true);
    }


    @SuppressWarnings("unchecked")
    protected C alwaysTrue() {
        addPredicate(StandardPredicates.alwaysTrue());
        return (C)this;
    }

    @SuppressWarnings("unchecked")
    protected C alwaysFalse() {
        addPredicate(StandardPredicates.alwaysFalse());
        return (C)this;
    }

    protected final <P> P doEndLogicalExpression(Class<P> builderClass, boolean recursiveEndExpressions) {
        Builder parent = this.endLogicalExpression();
        while(recursiveEndExpressions && ! builderClass.isInstance(parent) ) {
            parent=parent.endLogicalExpression();
        }
        AjAssert.assertCondition(
                builderClass.isInstance(parent),
                MessageBuilders.message("Invalid termination of logical expression: Parent selector instance is not of expected type")
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
