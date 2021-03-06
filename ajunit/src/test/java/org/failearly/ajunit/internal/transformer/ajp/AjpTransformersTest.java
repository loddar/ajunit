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
package org.failearly.ajunit.internal.transformer.ajp;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.failearly.ajunit.internal.universe.AjJoinPoint;
import org.failearly.ajunit.internal.universe.AjJoinPointType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.ajp.AjpTransformers}.
 */
public class AjpTransformersTest extends TransformersBaseTest {

    private static final int NUMBER_OF_APPLICATIONS = 2;

    @Test
    public void ajpToMethod() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.METHOD_EXECUTION);
        final Transformer methodTransformer = AjpTransformers.method();

        // act / when
        final Object output = methodTransformer.transform(joinPoint);

        // assert / then
        assertTransformationResult(output, resolveMethod());
    }

    @Test
    public void ajpToField() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.FIELD_GET);
        final Transformer fieldTransformer = AjpTransformers.field();

        // act / when
        final Object output = fieldTransformer.transform(joinPoint);

        // assert / then
        assertTransformationResult(output, resolveField());
    }

    @Test
    public void ajpToConstructor() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.CONSTRUCTOR_CALL);
        final Transformer fieldTransformer = AjpTransformers.constructor();

        // act / when
        final Object output = fieldTransformer.transform(joinPoint);

        // assert / then
        assertTransformationResult(output, resolveConstructor());
    }

    @Test
    public void ajpToDeclaringClass() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.CONSTRUCTOR_EXECUTION);
        final Transformer fieldTransformer = AjpTransformers.declaringClass();

        // act / when
        final Object output = fieldTransformer.transform(joinPoint);

        // assert / then
        assertTransformationResult(output, resolveClass());
    }


    @Test
    public void ajpJoinPointTypePassesFilter() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.METHOD_EXECUTION);
        final Transformer transformer = AjpTransformers.ajpJoinPointFilter(AjJoinPointType.METHOD_EXECUTION);

        // act / when
        final Object output = transformer.transform(joinPoint);

        // assert / then
        assertThat("Transformation result?", output, sameInstance((Object) joinPoint));
    }

    @Test
    public void ajpJoinPointTypeDoesNotPassFilter() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType.METHOD_EXECUTION);
        final Transformer transformer = AjpTransformers.ajpJoinPointFilter(AjJoinPointType.METHOD_CALL);

        // act / when
        final Object output = transformer.transform(joinPoint);

        // assert / then
        assertThat("Transformation result?", output, nullValue());
    }

    @Test
    public void ajpNumberOfApplications() throws Exception {
        // arrange / given
        final AjJoinPoint joinPoint = prepareAjJoinPoint(AjJoinPointType._UNKNOWN);
        final Transformer transformer = AjpTransformers.numberOfApplications();

        // act / when
        final Object output = transformer.transform(joinPoint);

        // assert / then
        assertThat("Transformation result?", output, is((Object) NUMBER_OF_APPLICATIONS));
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
        when(joinPoint.getNumApplications()).thenReturn(NUMBER_OF_APPLICATIONS);

        return joinPoint;
    }


}
