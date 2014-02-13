/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.util;

import java.lang.annotation.Annotation;

/**
 * Utility class for inspecting annotations.
 */
public abstract class AnnotationUtils {
    private AnnotationUtils() {
    }

    /**
     * Find the specified (meta-)annotation of given class. If more then one annotation is present the first will be
     * returned.
     *
     * @param aClass  the class to be inspect.
     * @param annotationClass the annotation class to be found.
     * @param <T> the annotation type
     * @return the annotation instance of {@code annotationClass} or {@code null}.
     */
    public static <T extends Annotation> T findClassAnnotation(final Class<?> aClass, final Class<T> annotationClass) {
        T annotation=aClass.getAnnotation(annotationClass);
        if( annotation==null ) {
            annotation = findMetaAnnotation(aClass.getAnnotations(), annotationClass);
        }
        return annotation;
    }

    private static <T extends Annotation> T findMetaAnnotation(final Annotation[] annotations, final Class<T> annotationClass) {
        T metaAnnotation=null;
        for (Annotation annotation : annotations) {
            final Class<? extends Annotation> currentMetaAnnotation = annotation.annotationType();
            metaAnnotation = currentMetaAnnotation.getAnnotation(annotationClass);
            if( metaAnnotation!=null ) {
                break;
            }
        }

        return metaAnnotation;
    }
}
