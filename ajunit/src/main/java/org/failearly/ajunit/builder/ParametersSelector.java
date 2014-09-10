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
 * ParametersSelector is responsible for selecting methods/constructors by there parameter signature.
 */
public interface ParametersSelector<
            SB extends ParametersSelector, TSB extends SelectorBuilder, ASB extends ParameterAnnotationSelector, RT extends DeclaringClassSelector>
        extends SelectorBuilder{
    /**
     * Select constructor or method join points without any parameter.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(*.*())</code>: any method without parameters</li>
     * <li><code>execution(*.new())</code>: default constructor</li>
     * </ul>
     *
     * @return itself
     */
    SB byNoParameters();

    /**
     * Select constructor or method join points with expected number of parameters using the {@code numberComparator}.<br/>
     * <br/>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(*.*(*))</code>: any method with one parameter</li>
     * <li><code>call(*.*(*,..))</code>: any method with at least one parameter</li>
     * </ul>
     *
     * @param number           number of expected parameters.
     * @param numberComparator the number comparator.
     * @return itself
     */
    SB byNumberOfParameters(int number, NumberComparator numberComparator);

    /**
     * Selects the parameters on given {@code positions} (relative to {@code relativeTo}) for inspecting the parameters
     * by their type.
     * If there is no parameter at any positions the entire expression will evaluate to {@code false}.
     *
     *
     * @param relativeTo starting with position relative to.
     * @param positions the positions values starting with {@code 0}.
     * @return a new {@link ParameterTypeSelector} instance.
     */
    TSB parameterTypes(Position relativeTo, int... positions);

    /**
     * Select methods or constructors based on there signature (the declared parameter types). The exact position is not necessary.
     *
     * @param listOperator the list logical operator defines how the parameter list will be evaluated.
     *
     * @return a new {@link ParameterTypeSelector} instance.
     */
    TSB parameterTypes(ListOperator listOperator);

    /**
     * Selects the parameters on given {@code positions} (relative to {@code relativeTo}) for inspecting the parameters
     * by their annotations.
     * If there is no parameter at any positions the entire expression will evaluate to {@code false}.
     *
     *
     * @param relativeTo starting with position relative to.
     * @param positions the positions values starting with {@code 0}.
     * @return a new {@link ParameterAnnotationSelector} instance.
     */
    ASB parameterAnnotations(Position relativeTo, int... positions);

    /**
     * Select methods or constructors based on the declared annotations. The exactly position is not necessary.
     *
     * @param listOperator the list operator defines how the parameter list will be evaluated.
     *
     * @return a new {@link ParameterAnnotationSelector} instance.
     */
    ASB parameterAnnotations(ListOperator listOperator);

    /**
     * Terminates the parameter selector expression.
     *
     * @return the parent selector ({@link ParametersSelector}).
     */
    RT endParametersSelector();
}
