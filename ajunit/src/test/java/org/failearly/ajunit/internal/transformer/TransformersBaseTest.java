/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.internal.transformer;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Base class for (some) {@link org.failearly.ajunit.internal.transformer.Transformer} tests.
 */
public class TransformersBaseTest {

    protected static final String METHOD_NAME = "anyMethod";
    protected static final String FIELD_NAME = "anyField";
    protected static final String CONSTRUCTOR_NAME = "org.failearly.ajunit.internal.transformer.TransformersBaseTest$MyClass";

    protected static Class resolveClass() {
        return MyClass.class;
    }

    protected static Method resolveMethod() throws NoSuchMethodException {
        return MyClass.class.getDeclaredMethod(METHOD_NAME, int.class, String.class);
    }

    protected static Constructor<?> resolveConstructor() throws NoSuchMethodException {
        return MyClass.class.getDeclaredConstructor(int.class);
    }

    protected static Field resolveField() throws NoSuchFieldException {
        return MyClass.class.getDeclaredField(FIELD_NAME);
    }

    protected static void assertTransformationResult(Object output, Object expectedOutput) {
        assertThat("Transformation result?", output, is(expectedOutput));
    }


    @SafeVarargs
    protected static <O> void assertTransformationResults(final Object output, final O... expectedOutputs) {
        assertThat("Transformation result?", output, is((Object) Arrays.asList(expectedOutputs)));
    }

    @SafeVarargs
    protected static void assertTransformationAnnotationsResults(Object output, Class<? extends Annotation>... expectedAnnotationClasses) {
        final List<Class<? extends Annotation>> outputAnnotationClasses = toAnnotationClasses(output);
        assertTransformationResults(outputAnnotationClasses, expectedAnnotationClasses);
    }

    @SuppressWarnings("unchecked")
    protected static List<Class<? extends Annotation>> toAnnotationClasses(Object output) {
        final List<Class<? extends Annotation>> outputAnnotationClasses = new LinkedList<>();
        final List<? extends Annotation> outputAnnotations = (List<? extends Annotation>) output;
        for (final Annotation outputAnnotation : outputAnnotations) {
            outputAnnotationClasses.add(outputAnnotation.annotationType());
        }
        return outputAnnotationClasses;
    }

// Test Fixture class and annotation.

    @Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface AnyAnnotation {
        String value() default "";
    }

    @SuppressWarnings("unused")
    @AnyAnnotation
    private static final class MyClass {
        @AnyAnnotation
        private final int anyField;

        @AnyAnnotation @Deprecated
        protected MyClass(int anyField) throws IllegalArgumentException, ClassCastException {
            this.anyField = anyField;
        }

        @Deprecated
        @AnyAnnotation
        public static void anyMethod(@AnyAnnotation("???") int i, @AnyAnnotation("all") String s) throws IllegalArgumentException, ClassCastException {
        }
    }
}
