/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2014 Marko Umek (ajunit.contact(at)fail-early.com)
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

import org.failearly.ajunit.AjUniverseName;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link AnnotationUtils}.
 */
public class AnnotationUtilsTest {
    @AjUniverseName("MyUniverse")
    private static class MyClass {}

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @AjUniverseName("MyCustomUniverseAnnotation")
    @interface CustomUniverseAnnotation{}

    @CustomUniverseAnnotation
    private static class OtherClass {}


    @Test
    public void findClassAnnotation() throws Exception {
        // act / when
        final AjUniverseName universeName= AnnotationUtils.findClassAnnotation(MyClass.class, AjUniverseName.class);

        // assert / then
        assertThat(universeName.value(), is("MyUniverse"));
    }

    @Test
    public void findClassAnnotationMeta() throws Exception {
        // act / when
        final AjUniverseName universeName=AnnotationUtils.findClassAnnotation(OtherClass.class, AjUniverseName.class);

        // assert / then
        assertThat(universeName.value(), is("MyCustomUniverseAnnotation"));
    }

    @Test
    public void noneExistingClassAnnotation() throws Exception {
        // act / when
        final AjUniverseName universeName=AnnotationUtils.findClassAnnotation(AnnotationUtilsTest.class, AjUniverseName.class);

        // assert / then
        assertThat(universeName, nullValue());
    }
}
