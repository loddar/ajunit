/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 marko (http://fail-early.com/contact)
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
 * MethodArgumentTypeSelector is responsible for selecting argument(s) of methods and constructors by argument type and position(s).
 *
 * @see org.failearly.ajunit.builder.MethodArgumentsSelector#byArgumentPosition(int)
 */
public interface MethodArgumentTypeSelector extends SelectorBuilder {
    /**
     * Ends Argument position expression.
     * @return previous {@link org.failearly.ajunit.builder.MethodArgumentsSelector}.
     */
    MethodArgumentsSelector endArgumentPosition();

    MethodArgumentTypeSelector byClass(Class<?> argumentClass);

    MethodArgumentTypeSelector byPrimitive();
}