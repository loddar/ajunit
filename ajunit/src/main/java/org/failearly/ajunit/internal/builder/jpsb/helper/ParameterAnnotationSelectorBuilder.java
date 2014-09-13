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
package org.failearly.ajunit.internal.builder.jpsb.helper;

import org.failearly.ajunit.builder.ListOperator;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static org.failearly.ajunit.internal.predicate.standard.StandardPredicates.equalsTo;

/**
 * ParameterAnnotationSelectorBuilder is responsible for ...
 */
public final class ParameterAnnotationSelectorBuilder<T extends Builder> extends SelectorBuilderBase<T> {
    ParameterAnnotationSelectorBuilder(PredicateAdder<T> predicateAdder) {
        super(predicateAdder);
    }

    @SafeVarargs
    public final T byParameterAnnotations(ListOperator listOperator, Class<? extends Annotation>... annotationClasses) {
        return super.addPredicate(JoinPointSelectorUtils.createListLogicalOperator(listOperator, LogicalPredicates.or().addPredicates(createAnnotationTypeEqualsToPredicates(annotationClasses))));
    }

    private static List<Predicate> createAnnotationTypeEqualsToPredicates(Class<? extends Annotation>[] annotationClasses) {
        AjAssert.assertCondition(
                annotationClasses.length > 0,
                MessageUtils.message("At least one annotation should be provided.")
        );
        final List<Predicate> predicates = new ArrayList<>(annotationClasses.length);
        for (Class<? extends Annotation> anAnnotationClass : annotationClasses) {
            predicates.add(equalsTo(anAnnotationClass));
        }
        return predicates;
    }

}
