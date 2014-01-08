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

import org.failearly.ajunit.internal.predicate.CompositePredicate;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.util.AjAssert;

/**
 * Responsible for building a logical structure.
 *
 * @see org.failearly.ajunit.internal.builder.BuilderBase#init(LogicalStructureBuilder)
 */
public final class LogicalStructureBuilder<R extends RootBuilder, C extends Builder> {
    private final CompositePredicate compositePredicate;
    private Builder parent;
    private C current;
    private R root;

    private LogicalStructureBuilder(R root, Builder parent, C current, CompositePredicate compositePredicate) {
        this.root = root;
        this.parent = parent;
        this.current = current;
        this.compositePredicate = compositePredicate;
        this.root.onTop(current);
    }

    /**
     * Factory method for creating <i>the</i> ROOT builder instance.
     * @param root the ROOT builder instance. (will be used in the entire structure.)
     * @param compositePredicate the associated logical predicate.
     * @param <R> the ROOT builder type.
     * @return the logical structure builder.
     */
    public static <R extends RootBuilder> LogicalStructureBuilder<R, R> rootBuilder(R root, CompositePredicate compositePredicate) {
        return new LogicalStructureBuilder<>(root, root, root, compositePredicate);
    }


    /**
     * Factory method for creating first level builder instances.
     * @param root the ROOT builder instance
     * @param current the current builder.
     * @param compositePredicate the associated logical predicate.
     * @param <R> the ROOT builder type.
     * @param <C> the CURRENT builder type.
     * @return the logical structure builder.
     */
    public static <R extends RootBuilder, C extends Builder> LogicalStructureBuilder<R, C>
            createFirstLevelBuilder(R root, C current, CompositePredicate compositePredicate) {
        root.addPredicate(compositePredicate);
        return new LogicalStructureBuilder<>(root, current, current, compositePredicate);
    }

    /**
     * Factory method for creating builder instances below the ROOT and first level, where all builder nodes could be heterogeneous.
     * @param root the ROOT builder instance
     * @param parent the parent builder instance.
     * @param current the current builder.
     * @param compositePredicate the associated logical predicate.
     * @param <R> the ROOT builder type.
     * @param <C> the CURRENT builder type.
     * @return  the logical structure builder.
     */
    public static <R extends RootBuilder, C extends Builder> LogicalStructureBuilder<R, C>
            createBelowFirstLevelBuilder(R root, Builder parent, C current, CompositePredicate compositePredicate) {
        parent.addPredicate(compositePredicate);
        return new LogicalStructureBuilder<>(root, parent, current, compositePredicate);
    }

    /**
     * Creates a new AND builder node.
     */
    <N extends Builder> N and(BuilderFactory<R, C, N> builderFactory) {
        return createNewBuilderNode(builderFactory, LogicalPredicates.and());
    }

    /**
     * Creates a new NAND builder node.
     */
    <N extends Builder> N nand(BuilderFactory<R, C, N> builderFactory) {
        return createNewBuilderNode(builderFactory, LogicalPredicates.nand());
    }

    /**
     * Creates a new OR builder node.
     */
    <N extends Builder> N or(BuilderFactory<R, C, N> builderFactory) {
        return createNewBuilderNode(builderFactory, LogicalPredicates.or());
    }

    /**
     * Creates a new NOR builder node.
     */
    <N extends Builder> N nor(BuilderFactory<R, C, N> builderFactory) {
        return createNewBuilderNode(builderFactory, LogicalPredicates.nor());
    }

    /**
     * Creates a new XOR builder node.
     */
    <N extends Builder> N xor(BuilderFactory<R, C, N> builderFactory) {
        return createNewBuilderNode(builderFactory, LogicalPredicates.xor());
    }

    <N extends Builder> N createNewBuilderNode(BuilderFactory<R, C, N> builderFactory, CompositePredicate compositePredicate) {
        return builderFactory.createBuilder(this.root, this.current, compositePredicate);
    }

    /**
     * Adds a {@code predicate} to current predicate composite.
     */
    void addPredicate(Predicate predicate) {
        compositePredicate.addPredicate(predicate);
    }

    /**
     * @return the parent builder node.
     */
    Builder end() {
        this.root.onTop(this.parent);
        return cleanup();
    }

    /**
     * Cleanup builder. Otherwise there will be memory leaks.
     *
     * @return parent builder.
     * @see Builder#cleanup()
     */
    Builder cleanup() {
        final Builder parent = this.parent;
        this.root = null;
        this.parent = null;
        this.current = null;
        return parent;
    }

    /**
     * Jumps to the root builder node.
     *
     * @return the root builder node.
     * @see
     */
    R done() {
        final R root = this.root;
        cleanupAllWithoutRoot(root);
        return root;
    }

    private void cleanupAllWithoutRoot(final R root) {
        Builder parent = this.current;
        while (parent != root && parent != null) {
            parent = parent.cleanup();
        }
    }

    /**
     * Build has been finished.
     * Caution: This method should be called by the ROOT builder.
     * @return the predicate build.
     */
    Predicate build() {
        AjAssert.state(this.parent==this.root,"This method should be called by the ROOT builder.");
        cleanup();
        return compositePredicate;
    }
}
