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
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.method.MethodTransformers}
 */
public class MethodTransformersTest extends TransformersBaseTest {

    @Test
    public void methodReturnType() throws Exception {
        // arrange / given
        final Transformer transformer = MethodTransformers.methodReturnType();

        // act / when
        final Object output = transformer.transform(resolveMethod());

        // assert / then
        assertTransformationResult(output, void.class);
    }

    @Test
    public void methodParameters() throws Exception {
        // arrange / given
        final Transformer transformer = MethodTransformers.methodParameters();

        // act / when
        final Object output = transformer.transform(resolveMethod());

        // assert / then
        assertTransformationResultList(output, int.class, String.class);
    }

    @Test
    public void methodDeclaredExceptions() throws Exception {
        // arrange / given
        final Transformer transformer = MethodTransformers.methodExceptions();

        // act / when
        final Object output = transformer.transform(resolveMethod());

        // assert / then
        assertTransformationResultList(output, IllegalArgumentException.class, ClassCastException.class);
    }


    @Test
    public void methodParameterAnnotationsType() throws Exception {
        // arrange / given
        final Transformer transformer = MethodTransformers.methodParameterAnnotationsType();

        // act / when
        final Object output = transformer.transform(resolveMethod());

        // assert / then
        assertTransformationResultList(output,
                Arrays.asList(AnyAnnotation.class), Arrays.asList(OtherAnnotation.class, AnyAnnotation.class)
            );
    }
    @Test
    public void methodRawParameterAnnotations() throws Exception {
        // arrange / given
        final Transformer transformer = MethodTransformers.methodRawParameterAnnotations();

        // act / when
        final Object output = transformer.transform(resolveMethod());

        // assert / then
        assertTransformationResult(output,
                resolveMethodParameterAnnotations()
            );
    }
}
