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
package org.failearly.ajunit.internal.transformer.ajp;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AjpTransformers is a utility class which provides factory methods for {@link org.failearly.ajunit.internal.universe.AjJoinPoint} transformers.
 */
public abstract class AjpTransformers {

    private static final Transformer AJP_METHOD_TRANSFORMER = new AjpTransformerBase<Method>() {
        @Override
        protected Method doTypedTransform(final AjJoinPoint input) {
            return input.getMethod();
        }
    };
    private static final Transformer AJP_FIELD_TRANSFORMER = new AjpTransformerBase<Field>() {
        @Override
        protected Field doTypedTransform(AjJoinPoint input) {
            return input.getField();
        }
    };
    private static final Transformer AJP_CONSTRUCTOR_TRANSFORMER = new AjpTransformerBase<Constructor<?>>() {
        @Override
        protected Constructor<?> doTypedTransform(AjJoinPoint input) {
            return input.getConstructor();
        }
    };
    private static final Transformer AJP_DECLARING_CLASS_TRANSFORMER = new AjpTransformerBase<Class<?>>() {
        @Override
        protected Class<?> doTypedTransform(AjJoinPoint input) {
            return input.getDeclaringClass();
        }
    };
    private static final Transformer AJP_NUMBER_OF_APPLICATIONS_TRANSFORMER = new AjpTransformerBase<Integer>() {
        @Override
        protected Integer doTypedTransform(AjJoinPoint input) {
            return input.getNumApplications();
        }
    };

    private AjpTransformers() {
    }

    /**
     * Provide a {@link org.failearly.ajunit.internal.universe.AjJoinPoint} to {@link java.lang.reflect.Method} transformer.
     * @see org.failearly.ajunit.internal.universe.AjJoinPoint#getMethod()
     */
    public static Transformer methodTransformer() {
        return AJP_METHOD_TRANSFORMER;
    }

    /**
     * Provide a {@link org.failearly.ajunit.internal.universe.AjJoinPoint} to {@link java.lang.reflect.Field} transformer.
     * @see org.failearly.ajunit.internal.universe.AjJoinPoint#getField()
     */
    public static Transformer fieldTransformer() {
        return AJP_FIELD_TRANSFORMER;
    }

    /**
     * Provide a {@link org.failearly.ajunit.internal.universe.AjJoinPoint} to {@link java.lang.reflect.Constructor} transformer.
     * @see org.failearly.ajunit.internal.universe.AjJoinPoint#getConstructor()
     */
    public static Transformer constructorTransformer() {
        return AJP_CONSTRUCTOR_TRANSFORMER;
    }

    /**
     * Provide a {@link org.failearly.ajunit.internal.universe.AjJoinPoint} to {@link java.lang.Class} transformer.
     *
     * @see org.failearly.ajunit.internal.universe.AjJoinPoint#getDeclaringClass()
     */
    public static Transformer declaringClassTransformer() {
        return AJP_DECLARING_CLASS_TRANSFORMER;
    }

    /**
     * @return value of {@link org.failearly.ajunit.internal.universe.AjJoinPoint#getNumApplications()}.
     */
    public static Transformer numberOfApplicationsTransformer() {
        return AJP_NUMBER_OF_APPLICATIONS_TRANSFORMER;
    }

    /**
     * Filter of {@link org.failearly.ajunit.internal.universe.AjJoinPoint}s,
     * which uses the {@link org.failearly.ajunit.internal.universe.AjJoinPointType} as filter criteria.
     * @param joinPointType the filter criteria.
     * @return {@code null} or the input.
     *
     * @see org.failearly.ajunit.internal.universe.AjJoinPoint#getJoinPointType()
     */
    public static Transformer ajpJoinPointFilterTransformer(AjJoinPointType joinPointType) {
        return new AjpJoinPointTypeFilter(joinPointType);
    }

}
