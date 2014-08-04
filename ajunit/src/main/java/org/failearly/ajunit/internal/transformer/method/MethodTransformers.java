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

import java.lang.reflect.Method;
import java.util.List;

/**
 * MethodTransformers provides factory methods for {@link java.lang.reflect.Method} related transformations.
 */
public final class MethodTransformers {

    private static final Transformer METHOD_RETURN_TYPE_TRANSFORMER = new MethodTransformerBase<Class<?>>() {
        @Override
        protected Class<?> doTypedTransform(final Method input) {
            return input.getReturnType();
        }
    };
    private static final Transformer METHOD_ARGUMENTS_TRANSFORMER = new MethodListTransformerBase<Class<?>>() {
        @Override
        protected List<Class<?>> doTypedTransform(Method input) {
            return convert(input.getParameterTypes());
        }
    };
    private static final Transformer METHOD_EXCEPTIONS_TRANSFORMER = new MethodListTransformerBase<Class<?>>() {
        @Override
        protected List<Class<?>> doTypedTransform(Method input) {
            return convert(input.getExceptionTypes());
        }
    };

    private MethodTransformers() {
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Method#getReturnType()}.
     */
    public static Transformer methodReturnTypeTransformer() {
        return METHOD_RETURN_TYPE_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Method#getParameterTypes()}.
     */
    public static Transformer methodArgumentsTransformer() {
        return METHOD_ARGUMENTS_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Method#getExceptionTypes()}.
     */
    public static Transformer methodExceptionsTransformer() {
        return METHOD_EXCEPTIONS_TRANSFORMER;
    }

}
