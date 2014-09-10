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
package org.failearly.ajunit.internal.transformer.annotation;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.annotation.AnnotationTransformers}
 */
public class AnnotationTransformersTest extends TransformersBaseTest {

    @Test
    public void toAnnotationType() throws Exception {
        // arrange / given
        final Transformer transformer = AnnotationTransformers.toAnnotationType();

        // act / when
        final Annotation input = resolveMethodAnnotations()[1];
        final Object output = transformer.transform(input);

        // assert / then
        assertTransformationResult(output, AnyAnnotation.class);
    }

    @Test
    public void toAnnotationTypesDim1() throws Exception {
        // arrange / given
        final Transformer transformer = AnnotationTransformers.toAnnotationTypesDim1();

        // act / when
        final Object output = transformer.transform(resolveMethodAnnotations());

        // assert / then
        assertTransformationResultList(output, Deprecated.class, AnyAnnotation.class);
    }

    @Test
    public void toAnnotationTypesDim2() throws Exception {
        // arrange / given
        final Transformer transformer = AnnotationTransformers.toAnnotationTypesDim2();

        // act / when
        final Object output = transformer.transform(resolveMethodParameterAnnotations());

        // assert / then
        assertTransformationResultList(output, Arrays.asList(AnyAnnotation.class), Arrays.asList(OtherAnnotation.class, AnyAnnotation.class));
    }
}