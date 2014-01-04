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
package org.failearly.ajunit.internal.transformer.member;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.junit.Test;

import java.lang.reflect.Modifier;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.member.MemberTransformers}.
 */
public class MemberTransformersTest extends TransformersBaseTest {
    @Test
    public void methodName() throws Exception {
        // arrange / given
        final Transformer transformer = MemberTransformers.nameTransformer();

        // act / when
        final Object output = transformer.transform(resolveMethod());

        // assert / then
        assertTransformationResult(output, METHOD_NAME);
    }

    @Test
    public void fieldName() throws Exception {
        // arrange / given
        final Transformer transformer = MemberTransformers.nameTransformer();

        // act / when
        final Object output = transformer.transform(resolveField());

        // assert / then
        assertTransformationResult(output, FIELD_NAME);
    }

    @Test
    public void constructorName() throws Exception {
        // arrange / given
        final Transformer transformer = MemberTransformers.nameTransformer();

        // act / when
        final Object output = transformer.transform(resolveConstructor());

        // assert / then
        assertTransformationResult(output, CONSTRUCTOR_NAME);
    }

    @Test
    public void methodModifier() throws Exception {
        // arrange / given
        final Transformer transformer = MemberTransformers.modifierTransformer();

        // act / when
        final Object output = transformer.transform(resolveMethod());

        // assert / then
        assertTransformationResult(output, Modifier.PUBLIC | Modifier.STATIC);
    }

    @Test
    public void fieldModifier() throws Exception {
        // arrange / given
        final Transformer transformer = MemberTransformers.modifierTransformer();

        // act / when
        final Object output = transformer.transform(resolveField());

        // assert / then
        assertTransformationResult(output, Modifier.PRIVATE | Modifier.FINAL);
    }

    @Test
    public void constructorModifier() throws Exception {
        // arrange / given
        final Transformer transformer = MemberTransformers.modifierTransformer();

        // act / when
        final Object output = transformer.transform(resolveConstructor());

        // assert / then
        assertTransformationResult(output, Modifier.PROTECTED);
    }

}
