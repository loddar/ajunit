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
package org.failearly.ajunit.internal.predicate.clazz;

import org.failearly.ajunit.builder.AnyAnnotation;
import org.failearly.ajunit.builder.TestSubject1;
import org.failearly.ajunit.internal.predicate.Predicate;
import org.junit.Test;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ClassPredicatesTest is responsible for ...
 */
public class ClassPredicatesTest {

    private static interface AnyInterface {}
    private static abstract class AnyBaseClass implements AnyInterface {}
    private static class AnyClass extends AnyBaseClass {}

    @Test
    public void isPrimitivePredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isPrimitive();

        // assert / then
        assertClassType(predicate, int.class, true);
        assertClassType(predicate, void.class, true);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, false);
    }

    @Test
    public void isActuallyPrimitivePredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isActuallyPrimitive();

        // assert / then
        assertClassType(predicate, int.class, true);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, false);
    }

    @Test
    public void isEnumPredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isEnum();

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, true);
        assertClassType(predicate, Retention.class, false);
    }

    @Test
    public void isArrayPredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isArray();

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, true);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, false);
    }

    @Test
    public void isAnnotationPredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isAnnotation();

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, true);
    }

    @Test
    public void isInterfacePredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isInterface();

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, true);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, true);
    }

    @Test
    public void isClassPredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isClass(String.class);

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, false);
        assertClassType(predicate, String.class, true);
    }

    @Test
    public void isSubclassOfInterfacePredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isSubclassOf(AnyInterface.class);

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, false);
        assertClassType(predicate, AnyInterface.class, true);
        assertClassType(predicate, AnyBaseClass.class, true);
        assertClassType(predicate, AnyClass.class, true);
    }

    @Test
    public void isSubclassOfBaseClassPredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isSubclassOf(AnyBaseClass.class);

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, false);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, false);
        assertClassType(predicate, AnyInterface.class, false);
        assertClassType(predicate, AnyBaseClass.class, true);
        assertClassType(predicate, AnyClass.class, true);
    }

    @Test
    public void isVoidPredicate() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isVoid();

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, void.class, true);
        assertClassType(predicate, int[].class, false);
        assertClassType(predicate, Object.class, false);
        assertClassType(predicate, Serializable.class, false);
        assertClassType(predicate, RetentionPolicy.class, false);
        assertClassType(predicate, Retention.class, false);
    }

    @Test
    public void isAnnotationPresent() throws Exception {
        // arrange / given
        final Predicate predicate = ClassPredicates.isAnnotationPresent(AnyAnnotation.class);

        // assert / then
        assertClassType(predicate, int.class, false);
        assertClassType(predicate, TestSubject1.class, true);
    }

    private static void assertClassType(Predicate predicate, Class<?> clazz, boolean expectedResult) {
        assertThat(predicate+": " + clazz.getSimpleName() + "?", predicate.test(clazz), is(expectedResult));
    }
}
