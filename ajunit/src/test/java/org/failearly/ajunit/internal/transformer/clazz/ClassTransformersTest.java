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
package org.failearly.ajunit.internal.transformer.clazz;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.clazz.ClassTransformers}
 */
public class ClassTransformersTest extends TransformersBaseTest {
    @Test
    public void className() throws Exception {
        // arrange / given
        final Transformer transformer = ClassTransformers.classNameTransformer();

        // act / when
        final Object output = transformer.transform(resolveClass());

        // assert / then
        assertTransformationResult(output, "MyClass");
    }

    @Test
    public void packageName() throws Exception {
        // arrange / given
        final Transformer transformer = ClassTransformers.packageNameTransformer();

        // act / when
        final Object output = transformer.transform(resolveClass());

        // assert / then
        assertTransformationResult(output, "org.failearly.ajunit.internal.transformer");
    }

    @Test
    public void classModifiers() throws Exception {
        // arrange / given
        final Transformer transformer = ClassTransformers.classModifiers();

        // act / when
        final Object output = transformer.transform(resolveClass());

        // assert / then
        assertTransformationResult(output, (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL));
    }

    @Test
    public void classAnnotations() throws Exception {
        // arrange / given
        final Transformer transformer = ClassTransformers.classAnnotations();

        // act / when
        final Object output = transformer.transform(resolveClass());

        // assert / then
        assertTransformationAnnotationsResultList(output, AnyAnnotation.class);
    }

    @Test
    public void arrayComponentType() throws Exception {
        assertArrayComponentType(int.class, null);
        assertArrayComponentType(int[].class, int.class);
        assertArrayComponentType(String.class, null);
        assertArrayComponentType(String[][].class, String.class);
    }

    private static void assertArrayComponentType(Class<?> clazz, Class expectedClass) {
        final Transformer transformer = ClassTransformers.arrayComponentType();

        // act / when
        final Object componentClass = transformer.transform(clazz);

        // assert / then
        if( expectedClass==null )
            assertThat("Component type of " + clazz.getSimpleName() + " should be null?", componentClass, nullValue());
        else
            assertThat("Component type of " + clazz.getSimpleName() + "?", componentClass.equals(expectedClass), is(true));
    }


    @Test
    public void arrayDimension() throws Exception {
        assertArrayDimension(int.class, 0);
        assertArrayDimension(String.class, 0);
        assertArrayDimension(int[].class, 1);
        assertArrayDimension(String[].class, 1);
        assertArrayDimension(int[][][][].class, 4);
        assertArrayDimension(String[][][][][].class, 5);
    }

    private static void assertArrayDimension(Class<?> clazz, int expectedDimension) {
        final Transformer transformer = ClassTransformers.countArrayDimension();

        // act / when
        assertThat("Array value of " + clazz.getSimpleName() + "?", (Integer)transformer.transform(clazz), is(expectedDimension));
    }

}
