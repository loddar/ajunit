/*
 * AJUnit - Unit Testing AspectJ pointcuts definitions.
 *
 * Copyright (C) 2013  Marko Umek (ajunit.contact(at)gmail.com)
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
package org.failearly.ajunit.util;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.util.ReflectionUtils}.
 */
public class ReflectionUtilsTest {
    // The test subjects.
    private static class EmptyClass {}
    private static class CommonClass {
        private static final int MY_CONST=1;
        public int publicField;
        protected int protectedField;
        /*default*/int defaultField;
        private int privateField;

        private CommonClass(int publicField) {

        }

        CommonClass(final int publicField, final int protectedField, final int defaultField, final int privateField) {
            this.publicField = publicField;
            this.protectedField = protectedField;
            this.defaultField = defaultField;
            this.privateField = privateField;
        }

        public void publicMethod() {}
        protected void protectedMethod() {}
        /*default*/ void defaultMethod() {}
        private void privateMethod() {}
        public static void publicStaticMethod() {}
    }
    private static abstract class AbstractClass {
        private static final int MY_CONST=1;
        public int publicField;
        protected int protectedField;
        /*default*/int defaultField;
        private int privateField;

        public void publicMethod() {}
        protected void protectedMethod() {}
        /*default*/ void defaultMethod() {}
        private void privateMethod() {}
        public abstract void abstractMethod();
    }
    private static interface MarkerInterface {}
    private static interface CommonInterface {
        int MY_CONST=7;
        void anyMethod();
    }


    private final List<Constructor> constructors=new ArrayList<>();
    private final List<Method> methods=new ArrayList<>();
    private final List<Field> fields=new ArrayList<>();
    private ClassVisitor classVisitor;

    @Before
    public void setUp() throws Exception {
        this.classVisitor = new AbstractClassVisitor() {

            @Override
            public void visit(final Method method) {
                methods.add(method);
            }

            @Override
            public void visit(final Constructor<?> constructor) {
                constructors.add(constructor);
            }

            @Override
            public void visit(final Field field) {
                fields.add(field);
            }
        };
    }

    @Test
    public void emptyClass() throws Exception {
        // act / when
        ReflectionUtils.visit(EmptyClass.class, this.classVisitor);

        // assert / then
        assertThat("Only default constructor?", constructors, hasSize(1));
        assertThat("#Methods?", methods, hasSize(0));
        assertThat("#Fields?", fields, hasSize(0));
    }

    @Test
    public void commonClass() throws Exception {
        // act / when
        ReflectionUtils.visit(CommonClass.class, this.classVisitor);

        // assert / then
        assertThat("#Constructors?", constructors, hasSize(2));
        assertThat("#Methods?", methods, hasSize(5));
        assertThat("#Fields?", fields, hasSize(5));
    }

    @Test
    public void abstractClass() throws Exception {
        // act / when
        ReflectionUtils.visit(AbstractClass.class, this.classVisitor);

        // assert / then
        assertThat("#Constructors?", constructors, hasSize(1));
        assertThat("#Methods?", methods, hasSize(5));
        assertThat("#Fields?", fields, hasSize(5));
    }

    @Test
    public void markerInterface() throws Exception {
        // act / when
        ReflectionUtils.visit(MarkerInterface.class, this.classVisitor);

        // assert / then
        assertThat("#Constructors?", constructors, hasSize(0));
        assertThat("#Methods?", methods, hasSize(0));
        assertThat("#Fields?", fields, hasSize(0));
    }

    @Test
    public void commonInterface() throws Exception {
        // act / when
        ReflectionUtils.visit(CommonInterface.class, this.classVisitor);

        // assert / then
        assertThat("#Constructors?", constructors, hasSize(0));
        assertThat("#Methods?", methods, hasSize(1));
        assertThat("#Fields?", fields, hasSize(1));
    }
}
