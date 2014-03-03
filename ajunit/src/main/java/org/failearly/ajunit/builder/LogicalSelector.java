/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.builder;

/**
 * LogicalSelector provides logical operations.
 */
public interface LogicalSelector<B extends SelectorBuilder> extends SelectorBuilder {
    /**
     * Logical OR. The expression will be closed by {@link #end()}.
     * @return next logical selector
     */
    B or();

    /**
     * Alias for {@link #or()}.
     * @return next logical selector
     */
    B union();

    /**
     * Alias for {@link #or()}.
     * @return next logical selector
     */
    B anyOf();

    /**
     * Logical AND. The expression should be closed by {@link #end()}.
     * @return next logical selector
     */
    B and();

    /**
     * Alias for {@link #and()}.
     * @return next logical selector
     */
    B intersect();

    /**
     * Alias for {@link #and()}.
     * @return next logical selector
     */
    B allOf();

    /**
     * Logical NOT(OR). The expression should be closed by {@link #end()}.
     * @return next logical selector
     */
    B nor();

    /**
     * Alias for {@link #nor()}.
     * @return next logical operator
     */
    B noneOf();

    /**
     * Alias for {@link #nor()}.
     * @return next logical operator
     */
    B neitherNor();

    /**
     * Alias for {@link #nor()}.
     * @return next logical operator
     */
    B complement();

    /**
     * Closes previous created logical expression and returns previous (parent) selector.
     * @return the parent selector.
     */
    B end();
}
