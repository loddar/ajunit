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
package org.failearly.ajunit.internal.transformer.field;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.junit.Test;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.field.FieldTransformers}.
 */
public class FieldTransformersTest extends TransformersBaseTest {
    @Test
    public void testFieldTypeTransformer() throws Exception {
        // arrange / given
        final Transformer transformer = FieldTransformers.fieldType();

        // act / when
        final Object output = transformer.transform(resolveField());

        // assert / then
        assertTransformationResult(output, int.class);
    }

    @Test
    public void testFieldDeclaredAnnotationsTransformer() throws Exception {
        // arrange / given
        final Transformer transformer = FieldTransformers.fieldDeclaredAnnotations();

        // act / when
        final Object output = transformer.transform(resolveField());

        // assert / then
        assertTransformationAnnotationsResultList(output, AnyAnnotation.class);
    }
}
