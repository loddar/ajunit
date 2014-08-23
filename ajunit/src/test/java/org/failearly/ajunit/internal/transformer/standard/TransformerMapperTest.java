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
import org.failearly.ajunit.internal.transformer.clazz.ClassTransformers;
import org.failearly.ajunit.internal.util.AjUnitUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * TransformerMapperTestTest contains tests for TransformerMapperTest
 */
public class TransformerMapperTest {

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
    public void map2ArraysAsList() throws Exception {
        // arrange / given
        final List<String[]> intArrayList= Arrays.asList(new String[] {"1","2","3"}, new String[] {"4","5","3"});
        final Transformer transformer=StandardTransformers.map(StandardTransformers.arrayAsList());

        // act / when
        final Object output = transformer.transform(intArrayList);

        // assert / then
        assertThat("Transformation result?", output, is(Arrays.asList(Arrays.asList("1","2","3"), Arrays.asList("4","5","3"))));
    }
}
