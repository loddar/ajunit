/*
 * ajUnit - Unit Testing AspectJ pointcut definitions.
 *
 * Copyright (C) 2013-2013  Marko Umek (ajunit.contact(at)gmail.com)
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

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link org.failearly.ajunit.util.ClassUtils}.
 */
public class ClassUtilsTest {

    private static interface MyInterface {}
    private static interface MySecondInterface extends MyInterface {}

    private static class MyBaseClass implements MyInterface {}
    private static class MyClass extends MyBaseClass implements MySecondInterface {}
    private static class SimpleClassWithoutInterfaces {}

    @Test
    @SuppressWarnings("unchecked")
    public void classWithoutInheritanceAndInterfaces() throws Exception {
        // act / when
        final Collection<Class<?>> classes = ClassUtils.getClassesAndInterfaces(SimpleClassWithoutInterfaces.class);

        // assert / then
        assertThat("Collected Classes/Interfaces?",
                classes,
                containsInAnyOrder(SimpleClassWithoutInterfaces.class, Object.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void classWithoutInheritance() throws Exception {
        // act / when
        final Collection<Class<?>> classes = ClassUtils.getClassesAndInterfaces(MyBaseClass.class);

        // assert / then
        assertThat("Collected Classes/Interfaces?",
                classes,
                containsInAnyOrder(MyBaseClass.class, Object.class, MyInterface.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void classWithInheritance() throws Exception {
        // act / when
        final Collection<Class<?>> classes = ClassUtils.getClassesAndInterfaces(MyClass.class);

        // assert / then
        assertThat("Collected Classes/Interfaces?",
                classes,
                containsInAnyOrder(MyClass.class, MyBaseClass.class, Object.class, MySecondInterface.class, MyInterface.class));
    }

    @Test
    public void interfaceOnly() throws Exception {
        // act / when
        final List<Class<?>> interfaces = toList(ClassUtils.getClassesAndInterfaces(MyInterface.class));

        // assert / then
        assertThat("Collected Interfaces?", interfaces.get(0).equals(MyInterface.class), is(true));
    }

    private static List<Class<?>> toList(final Collection<Class<?>> classes) {
        return new ArrayList<>(classes);
    }

}
