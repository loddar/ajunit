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
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.internal.transformer.standard.StandardTransformers#compose(org.failearly.ajunit.internal.transformer.Transformer...)}.
 */
public class TransformerCompositionTest {

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

    @SuppressWarnings({"all", "CanBeFinal"})
    private static class OtherClass {
        private MyClass myClass;

        private OtherClass(MyClass myClass) {
            this.myClass = myClass;
        }

        public MyClass getMyClass() {
            return myClass;
        }
    }


    @Test
    public void noTransformers() throws Exception {
        // arrange / given
        final Object input=new Object();
        final Transformer transformer=StandardTransformers.compose();

        // act / when
        final Object output = transformer.transform(input);

        // assert / then
        assertThat("transformation result?", output, sameInstance(input));
    }

    @Test
    public void singleTransformer() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.compose(createMyClassTransformer());
        final Integer expectedOutput = 100;
        final Object input=new MyClass(expectedOutput);

        // act / when
        final Object output = transformer.transform(input);

        // assert / then
        assertThat("Composition transformation result?", output, is((Object) expectedOutput));
    }

    @Test
    public void chainedTransformers() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.compose(createOtherClassTransformer(), createMyClassTransformer());
        final Integer expectedOutput = 200;
        final Object input=new OtherClass(new MyClass(expectedOutput));

        // act / when
        final Object output = transformer.transform(input);

        // assert / then
        assertThat("Composition transformation result?", output, is((Object)expectedOutput));
    }

    @Test
    public void chainedTransformersWithNull() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.compose(createOtherClassTransformer(), createMyClassTransformer());
        final Object input=new OtherClass(null);

        // act / when
        final Object output = transformer.transform(input);

        // assert / then
        assertThat("Composition transformation result?", output, nullValue());
    }

    @Test(expected = ClassCastException.class)
    public void inputTypeMismatch() throws Exception {
        // arrange / given
        final Transformer wrongTransformer = createOtherClassTransformer();
        final Transformer transformer=StandardTransformers.compose(wrongTransformer);

        // act / when
        transformer.transform(new MyClass(0));
    }


    /**
     * @return Transformer executing {@link org.failearly.ajunit.internal.transformer.standard.TransformerCompositionTest.OtherClass#getMyClass()}
     */
    private static Transformer createOtherClassTransformer() {
        return new TypedTransformer<OtherClass,MyClass>(OtherClass.class,"OtherClass2MyClass") {
            @Override
            protected MyClass doTypedTransform(final OtherClass input) {
                return input.getMyClass();
            }
        };
    }

    /**
     * @return Transformer executing {@link org.failearly.ajunit.internal.transformer.standard.TransformerCompositionTest.MyClass#getValue()}
     */
    private static Transformer createMyClassTransformer() {
        return new TypedTransformer<MyClass,Integer>(MyClass.class,"MyClass2Value") {
            @Override
            protected Integer doTypedTransform(final MyClass input) {
                return input.getValue();
            }
        };
    }
}
