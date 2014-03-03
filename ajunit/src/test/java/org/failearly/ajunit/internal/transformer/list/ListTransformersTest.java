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
package org.failearly.ajunit.internal.transformer.list;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TransformersBaseTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.list.ListTransformers}.
 */
public class ListTransformersTest extends TransformersBaseTest {

    public static final List<String> EMPTY_LIST = Collections.emptyList();
    public static final List<String> THREE_ELEMENT_LIST = Arrays.asList("00", "10", "20");

    @Test
    public void emptyList() throws Exception {
        // arrange / given
        final Transformer listTransformer = ListTransformers.sizeTransformer();

        // act / when
        final Object output = listTransformer.transform(EMPTY_LIST);

        // assert / then
        assertTransformationResult(output, 0);
    }

    @Test
    public void noneEmptyList() throws Exception {
        // arrange / given
        final Transformer listTransformer = ListTransformers.sizeTransformer();

        // act / when
        final Object output = listTransformer.transform(THREE_ELEMENT_LIST);

        // assert / then
        assertTransformationResult(output, 3);
    }

    @Test
    public void getElementListFromNoneEmptyList() throws Exception {
        // arrange / given
        final Transformer listTransformer = ListTransformers.getElementListTransformer(1);

        // act / when
        final Object output = listTransformer.transform(THREE_ELEMENT_LIST);

        // assert / then
        assertTransformationResult(output, "10");
    }

    @Test
    public void getElementListOutOfBounds() throws Exception {
        // arrange / given
        final Transformer listTransformer = ListTransformers.getElementListTransformer(3);

        // act / when
        final Object output = listTransformer.transform(THREE_ELEMENT_LIST);

        // assert / then
        assertTransformationResult(output, null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void createGetElementListTransformerWithInvalidPosition() throws Exception {
        // act / when
        ListTransformers.getElementListTransformer(-2);
    }

    @Test
    public void getElementListFromEndNoneEmptyList() throws Exception {
        // arrange / given
        final Transformer listTransformer = ListTransformers.getElementListFromEndTransformer(0);

        // act / when
        final Object output = listTransformer.transform(THREE_ELEMENT_LIST);

        // assert / then
        assertTransformationResult(output, "20");
    }

    @Test
    public void getElementListFromEndOutOfBounds() throws Exception {
        // arrange / given
        final Transformer listTransformer = ListTransformers.getElementListFromEndTransformer(3);

        // act / when
        final Object output = listTransformer.transform(THREE_ELEMENT_LIST);

        // assert / then
        assertTransformationResult(output, null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void createGetElementListFromEndTransformerWithInvalidPosition() throws Exception {
        // act / when
        ListTransformers.getElementListFromEndTransformer(-1);
    }

}
