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
package org.failearly.ajunit.internal.transformer.ajp;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.ajp.AjpMethodTransformer}.
 */
public class AjJoinPointTransformersTest {

    @Test
    public void ajpToMethod() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.METHOD_EXECUTION);
        final Transformer methodTransformer = AjJoinPointTransformers.methodTransformer();

        // act / when
        final Object output= methodTransformer.transform(joinPoint);

        // assert / then
        assertThat("Transformation result?", output, is((Object) resolveMethod()));
    }

    @Test
    public void ajpToField() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.FIELD_GET);
        final Transformer fieldTransformer = AjJoinPointTransformers.fieldTransformer();

        // act / when
        final Object output= fieldTransformer.transform(joinPoint);

        // assert / then
        assertThat("Transformation result?", output, is((Object) resolveField()));
    }

    @Test
    public void ajpToConstructor() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.CONSTRUCTOR_CALL);
        final Transformer fieldTransformer = AjJoinPointTransformers.constructorTransformer();

        // act / when
        final Object output= fieldTransformer.transform(joinPoint);

        // assert / then
        assertThat("Transformation result?", output, is((Object) resolveConstructor()));
    }

    @Test
    public void ajpToDeclaringClass() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.CONSTRUCTOR_EXECUTION);
        final Transformer fieldTransformer = AjJoinPointTransformers.declaringClassTransformer();

        // act / when
        final Object output= fieldTransformer.transform(joinPoint);

        // assert / then
        assertThat("Transformation result?", output, is((Object) resolveClass()));
    }



    @SuppressWarnings("unchecked")
    private AjJoinPoint prepareAjJoinPoint(AjJoinPointType joinPointType) throws NoSuchMethodException, NoSuchFieldException {
        final AjJoinPoint joinPoint = mock(AjJoinPoint.class);

        // act / when
        when(joinPoint.getJoinPointType()).thenReturn(joinPointType);
        when(joinPoint.getDeclaringClass()).thenReturn(resolveClass());
        when(joinPoint.getConstructor()).thenReturn(resolveConstructor());
        when(joinPoint.getMethod()).thenReturn(resolveMethod());
        when(joinPoint.getField()).thenReturn(resolveField());

        return joinPoint;
    }

    private static Class resolveClass() {
        return MyClass.class;
    }

    private static Method resolveMethod() throws NoSuchMethodException {
        return MyClass.class.getDeclaredMethod("anyMethod");
    }

    private static Constructor<?> resolveConstructor() throws NoSuchMethodException {
        return MyClass.class.getDeclaredConstructor(int.class);
    }

    private static Field resolveField() throws NoSuchFieldException {
        return MyClass.class.getDeclaredField("anyField");
    }



    @SuppressWarnings("unused")
    private static class MyClass {
        private int anyField;

        private MyClass(int anyField) {
            this.anyField = anyField;
        }

        public void anyMethod() {}
    }
}
