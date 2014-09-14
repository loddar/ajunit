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

import org.failearly.ajunit.builder.DimensionComparator;
import org.failearly.ajunit.builder.LogicalOperator;
import org.failearly.ajunit.builder.StringMatcher;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.failearly.ajunit.internal.predicate.clazz.ClassPredicates.*;
import static org.failearly.ajunit.internal.predicate.standard.LogicalPredicates.*;
import static org.failearly.ajunit.internal.predicate.standard.StandardPredicates.equalsTo;
import static org.failearly.ajunit.internal.predicate.standard.StandardPredicates.transformerPredicate;
import static org.failearly.ajunit.internal.transformer.clazz.ClassTransformers.*;

/**
 * ClassSelectorBuilder is responsible for ...
 */
public final class ClassSelectorBuilder<T extends Builder> extends SelectorBuilderBase<T> {
    ClassSelectorBuilder(PredicateAdder<T> predicateAdder) {
        super(predicateAdder);
    }

    public T byClass(Class<?> declaringClass) {
        return addPredicate(
                isClass(declaringClass)
        );
    }

    public T byClassName(String classNamePattern, StringMatcher matcherType) {
        return addPredicate(
                className(),
                JoinPointSelectorUtils.createStringMatcherPredicate(classNamePattern, matcherType)
        );
    }


    public T byExtending(Class<?> baseClass) {
        return addPredicate(
                isSubclassOf(baseClass)
        );
    }

    public T byNotExtending(Class<?> baseClass) {
        return addPredicate(
                not(isSubclassOf(baseClass))
        );
    }

    public T byImplementingAnyOf(Class<?>... interfaces) {
        return addPredicate(
                or(createImplementingInterfacePredicates(interfaces))
        );
    }

    public T byImplementingAllOf(Class<?>... interfaces) {
        return addPredicate(
                and(createImplementingInterfacePredicates(interfaces))
        );
    }

    public T byImplementingNoneOf(Class<?>... interfaces) {
        return addPredicate(
                nor(createImplementingInterfacePredicates(interfaces))
        );
    }

    public T byPackageName(String packageNamePattern, StringMatcher matcherType) {
        return addPredicate(
                packageName(),
                JoinPointSelectorUtils.createStringMatcherPredicate(packageNamePattern, matcherType)
        );
    }

    @SafeVarargs
    public final T byTypeAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotations) {
        return addPredicate(
                JoinPointSelectorUtils.createLogicalOperatorPredicate(logicalOperator)
                        .addPredicates(createTypeAnnotationsPredicates(annotations))
        );
    }

    public T byTypeAnnotation(Class<? extends Annotation> annotation) {
        return addPredicate(
                isAnnotationPresent(annotation)
        );
    }


    public T byArrayDimension(int dimension, DimensionComparator dimensionComparator) {
        return addPredicate(
                countArrayDimension(),
                JoinPointSelectorUtils.createDimensionComparatorPredicate(dimension, dimensionComparator)
        );
    }

    public T byArray() {
        return addPredicate(isArray());
    }

    public T byVoid() {
        return addPredicate(isVoid());
    }

    public T byPrimitive() {
        return addPredicate(isActuallyPrimitive());
    }

    public T byEnum() {
        return addPredicate(isEnum());
    }

    public T byAnnotation() {
        return addPredicate(isAnnotation());
    }


    public T byInterface() {
        return addPredicate(isInterface());
    }

    public T byPrimitiveWrapperType() {
        return addPredicate(
                and(
                        transformerPredicate(packageName(), equalsTo("java.lang")),
                        not(isClass(Number.class)),
                        or(
                                isSubclassOf(Number.class),
                                isClass(Void.class),
                                isClass(Boolean.class)
                        )
                )
        );
    }

    public T byCollection() {
        return addPredicate(isSubclassOf(Collection.class));
    }

    public T byMap() {
        return addPredicate(isSubclassOf(Map.class));
    }


    private static List<Predicate> createImplementingInterfacePredicates(Class<?>... interfaces) {
        final List<Predicate> predicates = new ArrayList<>(interfaces.length);
        for (Class<?> anInterface : interfaces) {
            predicates.add(isSubclassOf(anInterface));
        }
        return predicates;
    }

    private static List<Predicate> createTypeAnnotationsPredicates(Class<? extends Annotation>[] annotations) {
        AjAssert.assertCondition(
                annotations.length > 0,
                MessageBuilders.message("At least one annotation should be provided.")
        );
        final List<Predicate> predicates = new ArrayList<>(annotations.length);
        for (Class<? extends Annotation> anAnnotation : annotations) {
            predicates.add(isAnnotationPresent(anAnnotation));
        }
        return predicates;
    }

}
