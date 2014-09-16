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
package org.failearly.ajunit.builder.generic;

import org.failearly.ajunit.builder.SelectorBuilder;
import org.failearly.ajunit.builder.types.LogicalOperator;

/**
 * LogicalSelector provides logical operations for the specified {@link org.failearly.ajunit.builder.SelectorBuilder} node.
 */
public interface LogicalSelector<SB extends SelectorBuilder> {

    /**
     * Creates a logical (sub) expression.
     * <br><br>
     * Calling {@code logicalExpression(LogicalOperator.AND)} does the same like {@link #and()}.
     */
    SB logicalExpression(LogicalOperator logicalOperator);

    /**
     * Logical OR. The expression will be closed by {@link #end()}.
     * @return next logical selector
     */
    SB or();

    /**
     * Alias for {@link #or()}.
     * @return next logical selector
     */
    SB union();

    /**
     * Alias for {@link #or()}.
     * @return next logical selector
     */
    SB anyOf();

    /**
     * Logical AND. The expression should be closed by {@link #end()}.
     * @return next logical selector
     */
    SB and();

    /**
     * Alias for {@link #and()}.
     * @return next logical selector
     */
    SB intersect();

    /**
     * Alias for {@link #and()}.
     * @return next logical selector
     */
    SB allOf();

    /**
     * Logical NOT(OR). The expression should be closed by {@link #end()}.
     * @return next logical selector
     */
    SB nor();

    /**
     * Alias for {@link #nor()}.
     * @return next logical operator
     */
    SB noneOf();

    /**
     * Alias for {@link #nor()}.
     * @return next logical operator
     */
    SB neitherNor();

    /**
     * Alias for {@link #nor()}.
     * @return next logical operator
     */
    SB complement();

    /**
     * Closes previous created logical expression and returns previous (parent) selector.
     * @return the parent selector.
     */
    SB end();
}
