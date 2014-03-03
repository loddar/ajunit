/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
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
package org.failearly.ajunit.internal.util;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ReflectionUtils}.
 */
public class ReflectionUtilsTest {
    // The test subjects.
    @SuppressWarnings("all")
    private static class EmptyClass {
    }

    @SuppressWarnings("all")
    private static class CommonClass {
        private static final int MY_CONST = 1;
        public int publicField;
        protected int protectedField;
        /*default*/ int defaultField;
        private int privateField;

        private CommonClass(int publicField) {

        }

        CommonClass(final int publicField, final int protectedField, final int defaultField, final int privateField) {
            this.publicField = publicField;
            this.protectedField = protectedField;
            this.defaultField = defaultField;
            this.privateField = privateField;
        }

        public void publicMethod() {
        }

        protected void protectedMethod() {
        }

        /*default*/ void defaultMethod() {
        }

        private void privateMethod() {
        }

        public static void publicStaticMethod() {
        }
    }

    @SuppressWarnings("all")
    private static abstract class AbstractClass {
        private static final int MY_CONST = 1;
        public int publicField;
        protected int protectedField;
        /*default*/ int defaultField;
        private int privateField;

        public void publicMethod() {
        }

        protected void protectedMethod() {
        }

        /*default*/ void defaultMethod() {
        }

        private void privateMethod() {
        }

        public abstract void abstractMethod();
    }

    private static interface MarkerInterface {
    }

    @SuppressWarnings("unused")
    private static interface CommonInterface {
        int MY_CONST = 7;

        void anyMethod();
    }


    private final List<String> constructors = new ArrayList<>();
    private final List<String> methods = new ArrayList<>();
    private final List<String> fields = new ArrayList<>();
    private final List<String> classes=new ArrayList<>();
    private ClassVisitor classVisitor;

    @Before
    public void setUp() throws Exception {
        this.constructors.clear();
        this.methods.clear();
        this.fields.clear();
        this.classes.clear();
        this.classVisitor = new AbstractClassVisitor() {

            @Override
            public void visit(Class<?> declaringClass) {
                classes.add(declaringClass.getName());
            }

            @Override
            public void visit(final Method method) {
                methods.add(method.toGenericString());
            }

            @Override
            public void visit(final Constructor<?> constructor) {
                constructors.add(constructor.toGenericString());
            }

            @Override
            public void visit(final Field field) {
                fields.add(field.toGenericString());
            }
        };
    }

    @Test
    public void emptyClass() throws Exception {
        // act / when
        ReflectionUtils.visit(EmptyClass.class, this.classVisitor);

        // assert / then
        assertThat("Classes?",classes,containsInAnyOrder("org.failearly.ajunit.internal.util.ReflectionUtilsTest$EmptyClass"));
        assertThat("Only default constructor?", constructors, hasSize(1));
        assertThat("#Methods?", methods, hasSize(0));
        assertThat("#Fields?", fields, hasSize(0));
    }

    @Test
    public void commonClass() throws Exception {
        // act / when
        ReflectionUtils.visit(CommonClass.class, this.classVisitor);

        // assert / then
        assertThat("Classes?",classes,containsInAnyOrder("org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass"));
        assertThat("Constructors?", constructors, containsInAnyOrder(
                "private org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass(int)",
                "org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass(int,int,int,int)"
        ));
        assertThat("Methods?", methods, containsInAnyOrder(
                "public static void org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.publicStaticMethod()",
                "public void org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.publicMethod()",
                "protected void org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.protectedMethod()",
                "void org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.defaultMethod()",
                "private void org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.privateMethod()"
        ));
        assertThat("Fields?", fields, containsInAnyOrder(
                "private static final int org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.MY_CONST",
                "public int org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.publicField",
                "protected int org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.protectedField",
                "int org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.defaultField",
                "private int org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonClass.privateField"
        ));
    }

    @Test
    public void abstractClass() throws Exception {
        // act / when
        ReflectionUtils.visit(AbstractClass.class, this.classVisitor);

        // assert / then
        assertThat("Classes?",classes,containsInAnyOrder("org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass"));
        assertThat("Constructors?", constructors, containsInAnyOrder(
                "private org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass()"
        ));
        assertThat("Methods?", methods, containsInAnyOrder(
                "public void org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.publicMethod()",
                "protected void org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.protectedMethod()",
                "void org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.defaultMethod()",
                "private void org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.privateMethod()",
                "public abstract void org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.abstractMethod()"
        ));
        assertThat("Fields?", fields, containsInAnyOrder(
                "private static final int org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.MY_CONST",
                "public int org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.publicField",
                "protected int org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.protectedField",
                "int org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.defaultField",
                "private int org.failearly.ajunit.internal.util.ReflectionUtilsTest$AbstractClass.privateField"
        ));
    }

    @Test
    public void markerInterface() throws Exception {
        // act / when
        ReflectionUtils.visit(MarkerInterface.class, this.classVisitor);

        // assert / then
        assertThat("Classes?",classes,containsInAnyOrder("org.failearly.ajunit.internal.util.ReflectionUtilsTest$MarkerInterface"));
        assertThat("#Constructors?", constructors, hasSize(0));
        assertThat("#Methods?", methods, hasSize(0));
        assertThat("#Fields?", fields, hasSize(0));
    }

    @Test
    public void commonInterface() throws Exception {
        // act / when
        ReflectionUtils.visit(CommonInterface.class, this.classVisitor);

        // assert / then
        assertThat("Classes?",classes,containsInAnyOrder("org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonInterface"));
        assertThat("#Constructors?", constructors, hasSize(0));
        assertThat("Methods?", methods, containsInAnyOrder(
                "public abstract void org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonInterface.anyMethod()"
        ));
        assertThat("Fields?", fields, containsInAnyOrder(
                "public static final int org.failearly.ajunit.internal.util.ReflectionUtilsTest$CommonInterface.MY_CONST"
        ));
    }
}
