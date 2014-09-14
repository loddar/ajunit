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

import org.failearly.ajunit.builder.LogicalOperator;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.method.MethodPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * MethodSelectorBuilder is responsible for ...
 */
public final class MethodSelectorBuilder<T extends Builder> extends MemberSelectorBuilder<T> {

    MethodSelectorBuilder(PredicateAdder<T> predicateAdder) {
        super(predicateAdder);
    }

    public T anyMethod() {
        return addPredicate(StandardPredicates.alwaysTrue());
    }

    public T byMethodAnnotation(Class<? extends Annotation> annotationClass) {
        return addPredicate(
                MethodPredicates.isAnnotationPresent(annotationClass)
        );
    }

    public T byMethodAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>[] annotationClasses) {
        return addPredicate(
                JoinPointSelectorUtils.createLogicalOperatorPredicate(logicalOperator)
                        .addPredicates(createMethodAnnotationsPredicates(annotationClasses))

        );
    }

    private static List<Predicate> createMethodAnnotationsPredicates(Class<? extends Annotation>[] annotations) {
        AjAssert.assertCondition(
                annotations.length > 0,
                MessageBuilders.message("At least one annotation should be provided.")
        );
        final List<Predicate> predicates = new ArrayList<>(annotations.length);
        for (Class<? extends Annotation> anAnnotation : annotations) {
            predicates.add(MethodPredicates.isAnnotationPresent(anAnnotation));
        }
        return predicates;
    }

}
