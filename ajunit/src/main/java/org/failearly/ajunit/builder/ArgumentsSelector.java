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
 * ArgumentsSelector is responsible for selecting methods/constructors by there arguments/parameter signature. This almost an
 * entry point. The actually work will be done by {@code TSB} and {@code ASB}.
 */
public interface ArgumentsSelector<
            SB extends ArgumentsSelector, TSB extends SelectorBuilder, ASB extends ArgumentAnnotationSelector, RT extends DeclaringClassSelector>
        extends SelectorBuilder{
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
    SB byNoArguments();

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
    SB byNumberOfArguments(int number, NumberComparator numberComparator);

    /**
     * Selects the arguments on given {@code positions} (relative to {@code relativeTo}) for inspecting the arguments
     * by their type.
     * If there is no argument at any positions the entire expression will evaluate to {@code false}.
     *
     *
     * @param relativeTo starting with position relative to.
     * @param positions the positions values starting with {@code 0}.
     * @return a new {@link org.failearly.ajunit.builder.ArgumentTypeSelector} instance.
     */
    TSB argumentTypes(Position relativeTo, int... positions);

    /**
     * Select methods or constructors based on there signature (the declared parameter types). The exactly position is not necessary.
     *
     * @param listLogicalOperator the list logical operator
     *
     * @return a new {@link org.failearly.ajunit.builder.ArgumentTypeSelector} instance.
     */
    TSB argumentTypes(ListLogicalOperator listLogicalOperator);

    /**
     * Selects the arguments on given {@code positions} (relative to {@code relativeTo}) for inspecting the arguments
     * by their annotations.
     * If there is no argument at any positions the entire expression will evaluate to {@code false}.
     *
     *
     * @param relativeTo starting with position relative to.
     * @param positions the positions values starting with {@code 0}.
     * @return a new {@link org.failearly.ajunit.builder.ArgumentAnnotationSelector} instance.
     */
    ASB argumentAnnotations(Position relativeTo, int... positions);

    /**
     * Select methods or constructors based on the declared annotations. The exactly position is not necessary.
     *
     * @param listLogicalOperator the list logical operator
     *
     * @return a new {@link org.failearly.ajunit.builder.ArgumentAnnotationSelector} instance.
     */
    ASB argumentAnnotations(ListLogicalOperator listLogicalOperator);

    /**
     * Terminates the argument selector.
     *
     * @return the parent selector ({@link org.failearly.ajunit.builder.ArgumentsSelector}).
     */
    RT endArgumentsSelector();
}
