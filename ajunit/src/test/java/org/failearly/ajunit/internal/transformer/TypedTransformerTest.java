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
package org.failearly.ajunit.internal.transformer;

import org.failearly.ajunit.internal.transformer.standard.StandardTransformers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.standard.StandardTransformers#transformerComposition(org.failearly.ajunit.internal.transformer.Transformer...)}.
 */
public class TypedTransformerTest {

    @SuppressWarnings({"all", "CanBeFinal"})
    private static class MyClass {
        private int value;

        private MyClass(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Test(expected = ClassCastException.class)
    public void inputTypeMismatch() throws Exception {
        // arrange / given
        final Transformer wrongTransformer = createWrongTypeTransformer();

        // act / when
        wrongTransformer.transform(new MyClass(0));
    }

    @Test
    public void correctTransformation() throws Exception {
        // arrange / given
        final Transformer transformer = createMyClassTransformer();
        final Integer expectedOutput = 100;
        final Object input=new MyClass(expectedOutput);

        // act / when
        final Object output = transformer.transform(input);

        // assert / then
        assertThat("Transformation result?", output, is((Object) expectedOutput));
  }

    private static Transformer createWrongTypeTransformer() {
        return StandardTransformers.identityTransformer(Runnable.class);
    }


    private static Transformer createMyClassTransformer() {
        return new TypedTransformer<MyClass,Integer>(MyClass.class) {
            @Override
            protected Integer doTypedTransform(MyClass input) {
                return input.getValue();
            }
        };
    }
}
