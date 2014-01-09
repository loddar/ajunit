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

/**
 * The predicate builder <b>internal</b>> interface for {@link org.failearly.ajunit.internal.builder.LogicalStructureBuilder}
 * and {@link org.failearly.ajunit.internal.builder.BuilderFactory}.
 *
 * @see org.failearly.ajunit.internal.builder.LogicalStructureBuilder
 */
interface Builder {
    /**
     * Adds a predicate to the builder.
     * @param predicate
     */
    void addPredicate(Predicate predicate);

    /**
     * Done building (or partly creating).
     */
    RootBuilder done();

    /**
     * Clean up the builder.
     */
    void cleanup();
}
