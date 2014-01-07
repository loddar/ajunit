/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.failearly.ajunit.internal.transformer.constructor;

import org.failearly.ajunit.internal.transformer.Transformer;

/**
 * ConstructorTransformers provides factory methods for {@link java.lang.reflect.Constructor} related transformations.
 */
public final class ConstructorTransformers {

    private static final Transformer CONSTRUCTOR_ANNOTATIONS_TRANSFORMER = new ConstructorDeclaredAnnotationsTransformer();
    private static final Transformer CONSTRUCTOR_PARAMETERS_TRANSFORMER = new ConstructorParametersTransformer();
    private static final Transformer CONSTRUCTOR_EXCEPTIONS_TRANSFORMER = new ConstructorExceptionsTransformer();

    private ConstructorTransformers() {
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Constructor#getParameterTypes()}.
     */
    public static Transformer constructorParametersTransformer() {
        return CONSTRUCTOR_PARAMETERS_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Constructor#getExceptionTypes()}.
     */
    public static Transformer constructorExceptionsTransformer() {
        return CONSTRUCTOR_EXCEPTIONS_TRANSFORMER;
    }

    /**
     * The returned Transformer executes {@link java.lang.reflect.Constructor#getParameterTypes()}.
     */
    public static Transformer constructorDeclaredAnnotationsTransformer() {
        return CONSTRUCTOR_ANNOTATIONS_TRANSFORMER;
    }
}
