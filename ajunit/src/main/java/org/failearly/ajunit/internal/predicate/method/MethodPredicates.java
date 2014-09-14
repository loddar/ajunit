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
package org.failearly.ajunit.internal.predicate.method;

import org.failearly.ajunit.internal.predicate.Predicate;
import org.failearly.ajunit.internal.util.AjAssert;
import org.failearly.ajunit.internal.util.MessageBuilders;

import java.lang.annotation.Annotation;

/**
 * MethodPredicates provides factory methods on {@link java.lang.reflect.Method} object.
 */
public final class MethodPredicates {
    private MethodPredicates() {
    }

    /**
     * Predicate checks if the {@code annotationClass} is present on given {@link java.lang.reflect.Method}.
     * @param annotationClass the annotation class.
     * @see java.lang.reflect.Method#isAnnotationPresent(Class)
     */
    public static Predicate isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        AjAssert.assertCondition(annotationClass.isAnnotation(), MessageBuilders.message("Only annotation class expected: ").arg(annotationClass));
        return new IsMethodAnnotationPresentPredicate(annotationClass);
    }
}
