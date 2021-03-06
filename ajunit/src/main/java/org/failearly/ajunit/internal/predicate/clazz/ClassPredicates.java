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
package org.failearly.ajunit.internal.predicate.clazz;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.predicate.standard.LogicalPredicates;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;

import java.lang.annotation.Annotation;

/**
 * ClassPredicates provides factory methods on {@link java.lang.Class} object.
 */
public final class ClassPredicates {


    private static final Predicate IS_PRIMITIVE = new ClassPredicateBase("IsPrimitive") {
        @Override
        protected boolean doTypedTest(Class clazz) {
            return clazz.isPrimitive();
        }
    };
    private static final Predicate IS_ENUM = new ClassPredicateBase("IsEnum") {
        @Override
        protected boolean doTypedTest(Class clazz) {
            return clazz.isEnum();
        }
    };
    private static final Predicate IS_ARRAY = new ClassPredicateBase("IsArray") {
        @Override
        protected boolean doTypedTest(Class clazz) {
            return clazz.isArray();
        }
    };
    private static final Predicate IS_ANNOTATION = new ClassPredicateBase("IsAnnotation") {
        @Override
        protected boolean doTypedTest(Class clazz) {
            return clazz.isAnnotation();
        }
    };
    private static final Predicate IS_INTERFACE = new ClassPredicateBase("IsInterface") {
        @Override
        protected boolean doTypedTest(Class clazz) {
            return clazz.isInterface();
        }
    };
    private static final Predicate IS_VOID = isClass(void.class);

    private ClassPredicates() {
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} checks for {@link Class#isPrimitive()}.
     */
    public static Predicate isPrimitive() {
        return IS_PRIMITIVE;
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} checks for {@link Class#isEnum()}.
     */
    public static Predicate isEnum() {
        return IS_ENUM;
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} checks for {@link Class#isArray()}.
     */
    public static Predicate isArray() {
        return IS_ARRAY;
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} checks for {@link Class#isAnnotation()}.
     */
    public static Predicate isAnnotation() {
        return IS_ANNOTATION;
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} checks for {@link Class#isInterface()}.
     * Caution: An {@link java.lang.annotation.Annotation} is also an interface.
     */
    public static Predicate isInterface() {
        return IS_INTERFACE;
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} checks for {@code void}.
     */
    public static Predicate isVoid() {
        return IS_VOID;
    }

    /**
     * Returned {@link org.failearly.ajunit.internal.predicate.Predicate} checks for {@link Class#isPrimitive()} and not
     * {@code void}.
     */
    public static Predicate isActuallyPrimitive() {
        return LogicalPredicates.and(
                LogicalPredicates.not(isVoid()),
                isPrimitive()
        );
    }

    /**
     * Predicate checks for inheritance or implementation of specified {@code clazz}.
     * @see java.lang.Class#isAssignableFrom(Class)
     */
    public static Predicate isSubclassOf(Class<?> clazz) {
        return new IsSubclassOfPredicate(clazz);
    }

    /**
     * Predicate checks if the {@code annotationClass} is present on given type.
     * @param annotationClass the annotation class.
     * @see Class#isAnnotationPresent(Class)
     */
    public static Predicate isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        AjAssert.assertCondition(annotationClass.isAnnotation(), MessageBuilders.message("Only annotation class expected: ").arg(annotationClass));
        return new IsTypeAnnotationPresentPredicate(annotationClass);
    }

    /**
     * Predicate checks class identity.
     * @see java.lang.Class#equals(Object)
     */
    public static Predicate isClass(Class<?> clazz) {
        return new IsClassPredicate(clazz);
    }

}
