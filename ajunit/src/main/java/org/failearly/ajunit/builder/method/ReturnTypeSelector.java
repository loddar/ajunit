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
package org.failearly.ajunit.builder.method;

import org.failearly.ajunit.builder.ArrayTypeSelector;
import org.failearly.ajunit.builder.ExtendedClassSelector;
import org.failearly.ajunit.builder.LogicalSelector;

/**
 * ReturnTypeSelector provides selectors for {@link java.lang.reflect.Method#getReturnType()}.
 */
public interface ReturnTypeSelector
     extends ExtendedClassSelector<ReturnTypeSelector>,
        LogicalSelector<ReturnTypeSelector>,
        ArrayTypeSelector<ReturnTypeSelector,ReturnComponentTypeSelector> {

    /**
     * Selects method joinpoints the method's return type is {@code void}.
     *
     * Examples:<br/>
     * <ul>
     *     <li>{@code void setValue(int)}</li>
     *     <li>{@code void run()}</li>
     * </ul>
     * @return itself
     */
    ReturnTypeSelector byVoid();

    /**
     * Terminates the {@link MethodJoinPointSelector#byReturnType(org.failearly.ajunit.builder.LogicalOperator)}
     * expression.
     * @return the method join point selector.
     */
    MethodJoinPointSelector endReturnType();
}
