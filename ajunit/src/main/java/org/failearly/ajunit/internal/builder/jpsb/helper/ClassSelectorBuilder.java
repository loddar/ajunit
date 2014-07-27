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
import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.clazz.ClassPredicates;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ClassSelectorBuilder is responsible for ...
 */
public final class ClassSelectorBuilder<T extends Builder> extends SelectorBuilderBase<T> {
    ClassSelectorBuilder(PredicateAdder<T> predicateAdder) {
        super(predicateAdder);
    }

    public T byClass(Class<?> declaringClass) {
        return addPredicate(
                ClassPredicates.isClass(declaringClass)
        );
    }

    public T byClassName(String classNamePattern, StringMatcherType matcherType) {
        return addPredicate(
                ClassTransformers.classNameTransformer(),
                JoinPointSelectorUtils.createStringMatcherPredicate(classNamePattern, matcherType)
        );
    }


    public T byExtending(Class<?> baseClass) {
        return addPredicate(
                ClassPredicates.isSubclassOf(baseClass)
        );
    }

    public T byNotExtending(Class<?> baseClass) {
        return addPredicate(
                LogicalPredicates.not(ClassPredicates.isSubclassOf(baseClass))
        );
    }

    public T byImplementingAnyOf(Class<?>... interfaces) {
        return addPredicate(
                LogicalPredicates.or(createImplementingInterfacePredicates(interfaces))
        );
    }

    public T byImplementingAllOf(Class<?>... interfaces) {
        return addPredicate(
                LogicalPredicates.and(createImplementingInterfacePredicates(interfaces))
        );
    }

    public T byImplementingNoneOf(Class<?>... interfaces) {
        return addPredicate(
                LogicalPredicates.nor(createImplementingInterfacePredicates(interfaces))
        );
    }

    public T byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return addPredicate(
                ClassTransformers.packageNameTransformer(),
                JoinPointSelectorUtils.createStringMatcherPredicate(packageNamePattern, matcherType)
        );
    }

    @SafeVarargs
    public final T byTypeAnnotations(LogicalOperator logicalOperator, Class<? extends Annotation>... annotations) {
        return addPredicate(
                JoinPointSelectorUtils.createLogicalOperatorPredicate(logicalOperator)
                        .addPredicates(createAnnotationsPredicates(annotations))
        );
    }




    public T byArrayDimension(int dimension, DimensionComparator dimensionComparator) {
        return addPredicate(
                ClassTransformers.countArrayDimension(),
                JoinPointSelectorUtils.createDimensionComparatorPredicate(dimension, dimensionComparator)
        );
    }

    public T byArray() {
        return addPredicate(ClassPredicates.isArray());
    }

    public T byVoid() {
        return addPredicate(ClassPredicates.isVoid());
    }

    public T byPrimitive() {
        return addPredicate(ClassPredicates.isActuallyPrimitive());
    }

    public T byEnum() {
        return addPredicate(ClassPredicates.isEnum());
    }

    public T byAnnotation() {
        return addPredicate(ClassPredicates.isAnnotation());
    }


    public T byInterface() {
        return addPredicate(ClassPredicates.isInterface());
    }

    public T byPrimitiveWrapperType() {
        return addPredicate(
                LogicalPredicates.and(
                        StandardPredicates.transformerPredicate(ClassTransformers.packageNameTransformer(), StandardPredicates.equalsPredicate("java.lang")),
                        LogicalPredicates.not(ClassPredicates.isClass(Number.class)),
                        LogicalPredicates.or(
                            ClassPredicates.isSubclassOf(Number.class),
                            ClassPredicates.isClass(Void.class),
                            ClassPredicates.isClass(Boolean.class)
                        )
                )
        );
    }

    public T byCollection() {
        return addPredicate(ClassPredicates.isSubclassOf(Collection.class));
    }

    public T byMap() {
        return addPredicate(ClassPredicates.isSubclassOf(Map.class));
    }


    private static List<Predicate> createImplementingInterfacePredicates(Class<?>... interfaces) {
        final List<Predicate> predicates = new ArrayList<>(interfaces.length);
        for (Class<?> anInterface : interfaces) {
            predicates.add(ClassPredicates.isSubclassOf(anInterface));
        }
        return predicates;
    }

    @SafeVarargs
    private static List<Predicate> createAnnotationsPredicates(Class<? extends Annotation>... annotations) {
        AjAssert.assertCondition(
                    annotations.length>0,
                    MessageUtils.message("At least one annotation should be provided.")
            );
        final List<Predicate> predicates = new ArrayList<>(annotations.length);
        for (Class<? extends Annotation> anAnnotation : annotations) {
            predicates.add(ClassPredicates.isAnnotationPresent(anAnnotation));
        }
        return predicates;
    }
}
