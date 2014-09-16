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
import org.failearly.ajunit.builder.types.DimensionComparator;

/**
 * ArrayTypeSelector provides methods based on {@link Class} object.
 *
 * @see Class#isArray()
 */
public interface ArrayTypeSelector<SB extends SelectorBuilder, RT extends ExtendedClassSelector> {
    /**
     * Selects method, field or constructor joinpoints the method's return type, the field's type or the parameter's type
     * is an array.
     * Examples:<br/>
     * <ul>
     * <li>{@code String[] getArray()}</li>
     * <li>{@code int[][] myIntMatrix;}</li>
     * </ul>
     *
     * @return itself
     * @see Class#isArray()
     */
    SB byArray();

    /**
     * Selector builder for methods the return type or parameter type or the field is an array type of {@code value}.
     * Examples:<br/>
     * <ul>
     *     <li>{@code int[] getArray()}: value=1</li>
     *     <li>{@code String[] myStringArray;}: value=1</li>
     *     <li>{@code int[][] getMatrix}: value=2 </li>
     * </ul>
     * @param dimension the array's dimension (>=1)
     * @param dimensionComparator how the dimension will be compared.
     * @return itself
     */
    SB byArrayDimension(int dimension, DimensionComparator dimensionComparator);

    /**
     * Starts a sub selector for array's component type. The basic logical operator is {@link org.failearly.ajunit.builder.types.LogicalOperator#AND}.
     * Using this sub select, will only select arrays, even if no additional select has been applied - {@link #byArray()} will be added.
     *
     * @return new component type selector.
     *
     * @see Class#getComponentType()
     * @see org.failearly.ajunit.builder.method.ReturnComponentTypeSelector#endComponentType()
     * @see #byArray()
     */
    RT componentType();

}
