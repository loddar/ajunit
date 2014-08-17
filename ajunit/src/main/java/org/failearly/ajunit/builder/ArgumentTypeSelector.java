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
package org.failearly.ajunit.builder;

/**
 * ArgumentTypeSelector is responsible for selecting argument(s) of methods or constructors by argument type and position(s).
 *
 * @see org.failearly.ajunit.builder.ArgumentsSelector#argumentTypes(Position, int...)
 * @see org.failearly.ajunit.builder.ArgumentsSelector#argumentTypes(ListLogicalOperator)
 */
public interface ArgumentTypeSelector<ATB extends ArgumentTypeSelector, CTB extends ComponentTypeSelector, RB extends ArgumentsSelector>
        extends ExtendedClassSelector<ATB>,
        LogicalSelector<ATB>,
        ArrayTypeSelector<ATB,CTB> {
    /**
     * Ends argument position expression.
     * @return previous {@link org.failearly.ajunit.builder.ArgumentsSelector}.
     */
    RB endArgumentType();
}
