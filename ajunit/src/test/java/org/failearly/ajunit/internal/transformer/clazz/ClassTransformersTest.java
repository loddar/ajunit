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
package org.failearly.ajunit.internal.transformer.clazz;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.junit.Test;

import java.lang.reflect.Modifier;

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
        assertTransformationAnnotationsResults(output, AnyAnnotation.class);
    }

}
