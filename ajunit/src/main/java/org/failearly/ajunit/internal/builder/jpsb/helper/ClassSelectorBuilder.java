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

import org.failearly.ajunit.builder.StringMatcherType;
import org.failearly.ajunit.internal.builder.Builder;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.predicate.standard.StandardPredicates;
import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassSelectorBuilder is responsible for ...
 */
public final class ClassSelectorBuilder<T extends Builder> extends SelectorBuilderBase<T> {
    ClassSelectorBuilder(T predicateBuilder, AjJoinPointType joinPointType, Transformer ajJoinPointTransformer) {
        super(predicateBuilder, joinPointType, ajJoinPointTransformer);
    }

    public T byClass(Class<?> declaringClass) {
        return addPredicate(
                StandardPredicates.predicateEquals(declaringClass)
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
                StandardPredicates.predicateIsSubclass(baseClass)
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

    public T byPackageName(String packageNamePattern, StringMatcherType matcherType) {
        return addPredicate(
                ClassTransformers.packageNameTransformer(),
                JoinPointSelectorUtils.createStringMatcherPredicate(packageNamePattern, matcherType)
        );
    }

    private static List<Predicate> createImplementingInterfacePredicates(Class<?>... interfaces) {
        final List<Predicate> predicates = new ArrayList<>(interfaces.length);
        for (Class<?> anInterface : interfaces) {
            predicates.add(StandardPredicates.predicateIsSubclass(anInterface));
        }
        return predicates;
    }

}
