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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * IsMethodAnnotationPresentPredicate checks if the annotation class is present on a given {@link java.lang.reflect.Method}
 * object.
 */
final class IsMethodAnnotationPresentPredicate extends MethodPredicate {
    private final Class<? extends Annotation> annotationClass;

    IsMethodAnnotationPresentPredicate(Class<? extends Annotation> annotationClass) {
        super("IsMethodAnnotationPresent("+annotationClass.getName()+")");
        this.annotationClass = annotationClass;
    }

    @Override
    protected boolean doTypedTest(final Method method) {
        return method.isAnnotationPresent(this.annotationClass);
    }
}
