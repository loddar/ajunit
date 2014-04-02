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
 * MethodArgumentsSelector is responsible for for selecting joinpoints based on the number and/or types of the (declared) argument list.
 */
public interface MethodArgumentsSelector extends SelectorBuilder {


    /**
     * Select constructor or method join points without any arguments.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(*.*())</code>: any method without arguments</li>
     * <li><code>execution(*.new())</code>: default constructor</li>
     * </ul>
     *
     * @return itself
     */
    MethodArgumentsSelector byNoArguments();

    /**
     * Select constructor or method join points with expected number of arguments using the {@code numberComparator}.<br/>
     * <br/>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(*.*(*))</code>: any method with one argument</li>
     * <li><code>call(*.*(*,..))</code>: any method with at least one argument</li>
     * </ul>
     *
     * @param number           number of expected arguments.
     * @param numberComparator the number comparator.
     * @return itself
     */
    MethodArgumentsSelector byNumberOfArguments(int number, NumberComparator numberComparator);


    /**
     * Selects a single argument at given {@code position} for inspecting the argument by it's type. If there is no argument at these position the
     * expression will evaluate to {@code false}.
     *
     * @param position the position value starting with {@code 0}.
     * @return a new MethodArgumentTypeSelector instance.
     */
    MethodArgumentTypeSelector byArgumentPosition(int position);


    /**
     * Selects the arguments on given {@code positions} for inspecting the arguments by their type. If there is no argument at any positions the
     * entire expression will evaluate to {@code false}.
     *
     * @param positions the position values starting with {@code 0}.
     * @return a new MethodArgumentTypeSelector instance.
     */
    MethodArgumentTypeSelector byArgumentPositions(int... positions);

    /**
     * Terminates the argument selector.
     *
     * @return the parent selector.
     */
    MethodJoinPointSelector endArgumentsSelector();
}
