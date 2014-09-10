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
package org.failearly.ajunit.internal.transformer.method;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.annotation.AnnotationTransformers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.failearly.ajunit.internal.transformer.standard.StandardTransformers.compose;
import static org.failearly.ajunit.internal.transformer.standard.StandardTransformers.named;

/**
 * MethodTransformers provides factory methods for {@link java.lang.reflect.Method} related transformations.
 */
public final class MethodTransformers {

    private static final Transformer METHOD_RETURN_TYPE_TRANSFORMER = new MethodTransformerBase<Class<?>>("MethodReturnType") {
        @Override
        protected Class<?> doTypedTransform(final Method method) {
            return method.getReturnType();
        }
    };
    private static final Transformer METHOD_PARAMETERS_TRANSFORMER = new MethodListTransformerBase<Class<?>>("MethodParameters") {
        @Override
        protected List<Class<?>> doTypedTransform(final Method method) {
            return convert(method.getParameterTypes());
        }
    };
    private static final Transformer METHOD_EXCEPTIONS_TRANSFORMER = new MethodListTransformerBase<Class<?>>("MethodExceptions") {
        @Override
        protected List<Class<?>> doTypedTransform(final Method method) {
            return convert(method.getExceptionTypes());
        }
    };
    private static final Transformer METHOD_PARAMETER_ANNOTATIONS =new MethodTransformerBase<Annotation[][]>("MethodParametersAnnotations") {
        @Override
        protected Annotation[][] doTypedTransform(final Method method) {
            return method.getParameterAnnotations();
        }
    };

    private MethodTransformers() {
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Method#getReturnType()}.
     */
    public static Transformer methodReturnType() {
        return METHOD_RETURN_TYPE_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Method#getParameterTypes()} as {@link java.util.List}.
     */
    public static Transformer methodParameters() {
        return METHOD_PARAMETERS_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Method#getExceptionTypes()} as {@link java.util.List}.
     */
    public static Transformer methodExceptions() {
        return METHOD_EXCEPTIONS_TRANSFORMER;
    }

    /**
     * The returned transformer resolves the method parameter annotations ({@link java.lang.reflect.Method#getParameterAnnotations()}) and convert it to a
     * list and the {@link java.lang.annotation.Annotation} type will be resolved.
     *
     * @see java.lang.reflect.Method#getParameterAnnotations()
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public static Transformer methodParameterAnnotationsType() {
        return named("MethodParameterAnnotationsType", compose(
                methodRawParameterAnnotations(),
                AnnotationTransformers.toAnnotationTypesDim2()
            ));
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Method#getParameterAnnotations()}.
     * @see java.lang.annotation.Annotation#annotationType()
     */
    public static Transformer methodRawParameterAnnotations() {
        return METHOD_PARAMETER_ANNOTATIONS;
    }
}
