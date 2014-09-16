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
package org.failearly.ajunit;

import org.failearly.ajunit.builder.types.*;

/**
 * Contains shortcuts/aliases for the ajUnit enum types.
 *
 * @see org.failearly.ajunit.builder.types.LogicalOperator
 * @see org.failearly.ajunit.builder.types.ListOperator
 * @see org.failearly.ajunit.builder.types.StringMatcher
 * @see org.failearly.ajunit.builder.types.NumberComparator
 * @see org.failearly.ajunit.builder.types.DimensionComparator
 * @see org.failearly.ajunit.builder.types.Position
 */
public interface AjUnitConstants {

// Shortcuts/Aliases for LogicalOperator

    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.LogicalOperator#AND}.
     */
    LogicalOperator AND = LogicalOperator.AND;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.LogicalOperator#AND}.
     */
    LogicalOperator ALL_OF = LogicalOperator.AND;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.LogicalOperator#AND}.
     */
    LogicalOperator INTERSECT = LogicalOperator.AND;

    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.LogicalOperator#OR}.
     */
    LogicalOperator OR = LogicalOperator.OR;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.LogicalOperator#OR}.
     */
    LogicalOperator ANY_OF = LogicalOperator.OR;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.LogicalOperator#OR}.
     */
    LogicalOperator UNION = LogicalOperator.OR;

    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.LogicalOperator#NOR}.
     */
    LogicalOperator NOR = LogicalOperator.NOR;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.LogicalOperator#NOR}.
     */
    LogicalOperator NONE_OF = LogicalOperator.NOR;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.LogicalOperator#NOR}.
     */
    LogicalOperator COMPLEMENT = LogicalOperator.NOR;


// Shortcuts/Aliases for ListOperator


    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.ListOperator#AT_LEAST_ONE}.
     */
    ListOperator AT_LEAST_ONE=ListOperator.AT_LEAST_ONE;
    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.ListOperator#EACH}.
     */
    ListOperator EACH=ListOperator.EACH;
    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.ListOperator#NONE}.
     */
    ListOperator NONE=ListOperator.NONE;




// Shortcuts/Aliases for StringMatcherType

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.StringMatcher#EQUALS}.
     */
    StringMatcher STR_EQUALS = StringMatcher.EQUALS;

    /**
     * @see {@link org.failearly.ajunit.builder.types.StringMatcher#STARTS_WITH}.
     */
    StringMatcher STR_STARTS_WITH = StringMatcher.STARTS_WITH;

    /**
     * @see {@link org.failearly.ajunit.builder.types.StringMatcher#ENDS_WITH}.
     */
    StringMatcher STR_ENDS_WITH = StringMatcher.ENDS_WITH;

    /**
     * @see {@link org.failearly.ajunit.builder.types.StringMatcher#CONTAINS}.
     */
    StringMatcher STR_CONTAINS = StringMatcher.CONTAINS;

    /**
     * @see {@link org.failearly.ajunit.builder.types.StringMatcher#REGEX}.
     */
    StringMatcher STR_REGEX = StringMatcher.REGEX;




// Shortcuts/Aliases for NumberComparator

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.NumberComparator#EQUALS}.
     */
    NumberComparator NUM_EQUALS=NumberComparator.EQUALS;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.NumberComparator#LESS_THEN}.
     */
    NumberComparator NUM_LESS_THEN=NumberComparator.LESS_THEN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.NumberComparator#LESS_EQUALS_THEN}.
     */
    NumberComparator NUM_LESS_EQUALS_THEN=NumberComparator.LESS_EQUALS_THEN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.NumberComparator#LESS_EQUALS_THEN}.
     */
    NumberComparator NUM_MAX=NumberComparator.LESS_EQUALS_THEN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.NumberComparator#GREATER_THEN}.
     */
    NumberComparator NUM_GREATER_THEN=NumberComparator.GREATER_THEN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.NumberComparator#GREATER_EQUALS_THEN}.
     */
    NumberComparator NUM_GREATER_EQUALS_THEN=NumberComparator.GREATER_EQUALS_THEN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.NumberComparator#GREATER_EQUALS_THEN}.
     */
    NumberComparator NUM_MIN=NumberComparator.GREATER_EQUALS_THEN;




// Shortcuts/Aliases for DimensionComparator

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.DimensionComparator#EQUALS}.
     */
    DimensionComparator DIM_EQUALS=DimensionComparator.EQUALS;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.DimensionComparator#LESS_THEN}.
     */
    DimensionComparator DIM_LESS_THEN=DimensionComparator.LESS_THEN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.DimensionComparator#MIN}.
     */
    DimensionComparator DIM_MIN=DimensionComparator.MIN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.DimensionComparator#GREATER_THEN}.
     */
    DimensionComparator DIM_GREATER_THEN=DimensionComparator.GREATER_THEN;

    /**
     * Alias for {@link org.failearly.ajunit.builder.types.DimensionComparator#MAX}.
     */
    DimensionComparator DIM_MAX=DimensionComparator.MAX;


    

// Shortcuts/Aliases for Position

    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.Position#FIRST}.
     */
    Position FIRST=Position.FIRST;

    /**
     * Shortcut for {@link org.failearly.ajunit.builder.types.Position#FIRST}.
     */
    Position LAST=Position.LAST;
}
