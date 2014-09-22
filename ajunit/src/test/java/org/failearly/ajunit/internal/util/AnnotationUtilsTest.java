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
package org.failearly.ajunit.internal.util;

import org.junit.Test;

import java.lang.annotation.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link AnnotationUtils}.
 */
public class AnnotationUtilsTest {
    @AjName("MyUniverse")
    private static class MyClass {}

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @AjName("MyCustomUniverseAnnotation")
    @interface CustomUniverseAnnotation{}

    @CustomUniverseAnnotation
    private static class OtherClass {}


    @Test
    public void findClassAnnotation() throws Exception {
        // act / when
        final AjName ajName= AnnotationUtils.findClassAnnotation(MyClass.class, AjName.class);

        // assert / then
        assertThat(ajName.value(), is("MyUniverse"));
    }

    @Test
    public void findClassAnnotationMeta() throws Exception {
        // act / when
        final AjName ajName=AnnotationUtils.findClassAnnotation(OtherClass.class, AjName.class);

        // assert / then
        assertThat(ajName.value(), is("MyCustomUniverseAnnotation"));
    }

    @Test
    public void noneExistingClassAnnotation() throws Exception {
        // act / when
        final AjName ajName=AnnotationUtils.findClassAnnotation(AnnotationUtilsTest.class, AjName.class);

        // assert / then
        assertThat(ajName, nullValue());
    }

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    public static @interface AjName {
        /**
         * The universe name.
         */
        String value();
    }
}
