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
package org.failearly.ajunit.internal.transformer.standard;

import org.failearly.ajunit.internal.transformer.Transformer;
import org.failearly.ajunit.internal.transformer.TypedTransformer;
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.util.AjUnitUtils;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * TransformerMapperTestTest contains tests for TransformerMapperTest
 */
public class TransformerMapperTest {

    public static final TypedTransformer<String, Integer> TO_INTEGER_AND_INCREMENT = new TypedTransformer<String,Integer>(String.class, "ToIntegerAndIncrement") {
        @Override
        protected Integer doTypedTransform(String input) {
            return Integer.parseInt(input) + 1;
        }
    };

    @Test
    public void map2ArraysComponentTypes() throws Exception {
        // arrange / given
        final List<Class<?>> classList = AjUnitUtils.toClassList(String[].class, int[].class, Integer.class);
        final Transformer transformer=StandardTransformers.map(ClassTransformers.arrayComponentType());

        // act / when
        final Object output = transformer.transform(classList);

        // assert / then
        assertThat("Transformation result?", output, is(AjUnitUtils.toClassList(String.class, int.class)));
    }

    @Test
    public void toListDim1() throws Exception {
        // arrange / given
        final String[] stringArray = new String[] {"1","2","3"};
        final Transformer transformer=StandardTransformers.toListDim1();

        // act / when
        final Object output = transformer.transform(stringArray);

        // assert / then
        assertThat("Transformation result?", output, is(asList("1", "2", "3")));
    }

    @Test
    public void toListDim1WithAdditionalTransformer() throws Exception {
        // arrange / given
        final String[] stringArray = new String[] {"1","2","3"};
        final Transformer transformer=StandardTransformers.toListDim1(TO_INTEGER_AND_INCREMENT);

        // act / when
        final Object output = transformer.transform(stringArray);

        // assert / then
        assertThat("Transformation result?", output, is(asList(2, 3, 4)));
    }

    @Test
    public void toListDim2() throws Exception {
        // arrange / given
        final String[][] stringArrayDim2 = new String[][]{ new String[] {"1","2","3"}, new String[] {"7","8"} };
        final Transformer transformer=StandardTransformers.toListDim2();

        // act / when
        final Object output = transformer.transform(stringArrayDim2);

        // assert / then
        assertThat("Transformation result?", output, is(asList(asList("1", "2", "3"), asList("7", "8"))));
    }
    @Test
    public void toListDim2WithAdditionalTransformer() throws Exception {
        // arrange / given
        final String[][] stringArrayDim2 = new String[][]{ new String[] {"1","2","3"}, new String[] {"7","8"} };
        final Transformer transformer=StandardTransformers.toListDim2(TO_INTEGER_AND_INCREMENT);

        // act / when
        final Object output = transformer.transform(stringArrayDim2);

        // assert / then
        assertThat("Transformation result?", output, is(asList(asList(2,3,4), asList(8,9))));
    }
}
