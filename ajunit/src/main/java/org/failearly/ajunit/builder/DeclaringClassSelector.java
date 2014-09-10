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

import java.lang.annotation.Annotation;

/**
 * DeclaringClassSelector provides alias for {@link ClassSelector#byClass(Class)} and
 * {@link ClassSelector#byClassName(String, StringMatcher)}
 */
public interface DeclaringClassSelector<SB extends SelectorBuilder> extends ClassSelector<SB> {
    /**
     * Selects a joinpoint by it's declaring class.
     * Alias for {@link ClassSelector#byClass(Class)}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* com.company.MyClass.*(..))</code></li>
     * <li><code>call(* com.company.MyClass.*(..))</code></li>
     * </ul>
     *
     * @param declaringClass the declaring class.
     * @return itself
     */
    SB byDeclaringClass(Class<?> declaringClass);

    /**
     * Select a method by it's declaring class name (pattern).
     * Alias for {@link ClassSelector#byClassName(String, StringMatcher)}.<br/>
     * </br>
     * AspectJ pointcut definition examples:
     * <ul>
     * <li><code>execution(* **.My*.*(..))</code></li>
     * <li><code>call(* **.*Class.*(..))</code></li>
     * </ul>
     *
     * @param classNamePattern the declaring class.
     * @param matcherType  the matcher type
     * @return itself
     */
    SB byDeclaringClassName(String classNamePattern, StringMatcher matcherType);

    /**
     * The selector selects classes which has been annotated (depends on {@code logicalOperator}).
     * @param logicalOperator the logical operator to be used.
     * @param annotationClasses the annotation classes (at least one should be provided).
     * @return itself
     */
    @SuppressWarnings("all")
    SB byTypeAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses);
}
