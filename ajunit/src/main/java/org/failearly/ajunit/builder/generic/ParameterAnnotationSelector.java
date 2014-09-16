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

import org.failearly.ajunit.builder.types.LogicalOperator;

import java.lang.annotation.Annotation;

/**
 * ArgumentAnnotationSelector is responsible for selecting methods/constructors by argument/parameter annotations.
 *
 * @see ParametersSelector
 */
public interface ParameterAnnotationSelector<SB extends ParameterAnnotationSelector, RT extends ParametersSelector> {

    /**
     * The selector selects methods/constructors with annotated parameters (depends on {@code logicalOperator}).
     *
     * @param logicalOperator the logical operator.
     * @param annotationClasses the annotation classes (at least one must be provided).
     * @return itself
     */
    @SuppressWarnings("all")
    SB byParameterAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotationClasses);

    /**
     * The selector selects any method/constructor with any or all parameter(s) with any existing parameter
     * annotation.
     *
     * @return itself
     */
    SB byExistingParameterAnnotation();

    /**
     * The opposite selector of {@link #byExistingParameterAnnotation()} selects any method/constructor with any or all parameter(s) with missing parameter
     * annotation.
     *
     * @return itself
     */
    SB byMissingParameterAnnotation();


    /**
     * Ends the argument annotation selector expression.
     * @return the arguments selector (parent)
     */
    RT endParameterAnnotation();

}
