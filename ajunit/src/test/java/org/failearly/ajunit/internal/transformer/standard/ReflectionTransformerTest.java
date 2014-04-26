/*
 * ajUnit - Unit Testing AspectJ.
 *
 * Copyright (C) 2013-2014 marko (http://fail-early.com/contact)
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
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * ReflectionTransformerTest contains tests for {@link org.failearly.ajunit.internal.transformer.standard.StandardTransformers#reflection(Class, String)}.
 */
public class ReflectionTransformerTest {

    @Test
    public void accessDeclaredMethod() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.reflection(A.class, "getValue");

        // act / when
        final A input = new A();
        final String value = (String) transformer.transform(input);

        // assert / then
        assertThat("A->getValue()?", value, is(input.getValue()));
    }

    @Test
    public void accessInheritedMethod() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.reflection(A.class, "toString");

        // act / when
        final A input = new A();
        final String value = (String) transformer.transform(input);

        // assert / then
        assertThat("A->toString()?", value, is(input.toString()));
    }

    @Test
    public void accessUnknownMethod() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.reflection(A.class, "unknown-method");

        // act / when
        final A input = new A();
        final String value = (String) transformer.transform(input);

        // assert / then
        assertThat("A->unknown-method?", value, nullValue());
    }

    @Test
    public void accessInaccessibleMethod() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.reflection(B.class, "getValue");

        // act / when
        final B input = new B();
        final String value = (String) transformer.transform(input);

        // assert / then
        assertThat("B->getValue()?", value, is(input.getValue()));
    }

    @Test
    public void accessStaticMethod() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.reflection(C.class, "getValue");

        // act / when
        final C input = new C();
        final String value = (String) transformer.transform(input);

        // assert / then
        assertThat("C->getValue()?", value, is(C.getValue()));
    }

    @Test
    public void accessVoidMethod() throws Exception {
        // arrange / given
        final Transformer transformer=StandardTransformers.reflection(C.class, "doAnything");

        // act / when
        final C input = new C();
        final String value = (String) transformer.transform(input);

        // assert / then
        assertThat("C->doAnything()?", value, nullValue());
    }


    private static class A {
        B getB() {
            return new B();
        }
        String getValue() {
            return "A.value";
        }
    }

    private static class B {

        C getC() {
            return new C();
        }

        private String getValue() {
            return "B.value";
        }
    }

    private static class C {
        static String getValue() {
            return "C.value";
        }

        void doAnything() {}
    }


}
